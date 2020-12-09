package kz.sdu.mentorship.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kz.sdu.mentorship.activities.NavigationBarActivity;
import kz.sdu.mentorship.activities.VacancyDetailsActivity;
import kz.sdu.mentorship.models.Employer;
import kz.sdu.mentorship.adapters.JobsAdapter;
import kz.sdu.mentorship.network.NetworkService;
import kz.sdu.mentorship.R;
import kz.sdu.mentorship.models.Vacancy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements JobsAdapter.OnJobListener {
    public static List<Vacancy> vacancies;

    private RecyclerView jobsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private boolean isDetached;
    public static final String EXTRA_INFO = "by_home";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();
        if (vacancies == null) {
            fetchVacancies(view);
        } else if (jobsList == null) {
            createRecyclerPopularJobs(view);
            createRecyclerNearbyJobs(view);
        }
        setSwipeRefresh(view);
        return view;
    }

    @Override
    public void onJobClick(int position) {
        Intent intent = new Intent(getActivity(), VacancyDetailsActivity.class);
        intent.putExtra(VacancyDetailsActivity.EXTRA_INTENT, position);
        intent.putExtra(VacancyDetailsActivity.EXTRA_SOURCE, EXTRA_INFO);
        startActivity(intent);
    }

    /**
     * Makes asynchronous request and calls functions
     * that fill recyclerViews with content (if a fragment was not detached)
     * and disables swipeRefresh's loading circle.
     *
     * @param view  the view instance for calling helper functions
     */
    private void fetchVacancies(final View view) {
        NetworkService
                .getInstance()
                .getJSONApi()
                .getAllVacancies()
                .enqueue(new Callback<List<Vacancy>>() {
                    @Override
                    public void onResponse(Call<List<Vacancy>> call, Response<List<Vacancy>> response) {
                        if (isDetached) return;

                        vacancies = response.body();
                        if (vacancies == null) {
                            Toast.makeText(context, getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                            return;
                        }

                        createRecyclerPopularJobs(view);
                        createRecyclerNearbyJobs(view);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<Vacancy>> call, Throwable t) {
                        if (isDetached) return;
                        Log.d("error", Objects.requireNonNull(t.getMessage()));
                    }
                });
    }

    /**
     * Creates recycler view with popular jobs
     *
     * @param view  the object to pass into createRecycler function
     */
    private void createRecyclerPopularJobs(View view) {
        if (vacancies == null || vacancies.isEmpty()) return;
        ArrayList<Integer> dummyImages = generateDummyImages(vacancies.size(), 0);
        createRecycler(view, R.id.rv_popular_jobs, LinearLayoutManager.HORIZONTAL, vacancies,
                dummyImages, R.layout.popular_job_list_item);
    }

    /**
     * Creates recycler view with nearby jobs
     *
     * @param view  the object to pass into createRecycler function
     */
    private void createRecyclerNearbyJobs(View view) {
        if (vacancies == null || vacancies.isEmpty()) return;
        ArrayList<Integer> dummyImages = generateDummyImages(vacancies.size(), 1);
        createRecycler(view, R.id.rv_nearby_jobs, LinearLayoutManager.VERTICAL, vacancies,
                dummyImages, R.layout.nearby_job_list_item);
    }

    /**
     * Gets recyclerView from a helper getRecyclerById function and sets jobsAdapter to it
     *
     * @param view         the object to get and configure recycler by getRecyclerById function
     * @param viewId       the id of the view for finding it in getRecyclerById function
     * @param orientation  the orientation of the recyclerview (horizontal, vertical)
     * @param vacancies    list of vacancies that will be placed on viewholder
     * @param images       list of dummy images that will be placed on viewholder
     * @param layoutId     layout id that will be setted as viewholder layout in recycler view
     */
    private void createRecycler(View view, int viewId, int orientation, List<Vacancy> vacancies,
                                ArrayList<Integer> images, int layoutId) {
        jobsList = getRecyclerById(view, viewId, orientation);
        JobsAdapter jobsAdapter = new JobsAdapter(layoutId, images, vacancies, this);
        jobsList.setAdapter(jobsAdapter);
    }

    /**
     * Helper function, that returns recyclerview with configured layoutManager
     *
     * @param view         the view instance with which we can find other views
     * @param viewId       the id of recyclerView
     * @param orientation  the orientation of the recyclerview (horizontal, vertical)
     * @return             the recyclerView that we need in other functions
     */
    private RecyclerView getRecyclerById(View view, int viewId, int orientation) {
        RecyclerView jobsList = view.findViewById(viewId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,  orientation, false);
        jobsList.setLayoutManager(layoutManager);
        jobsList.setHasFixedSize(true);
        return jobsList;
    }

    /**
     * Generate dummy images for recyclerView
     *
     * @param vacanciesSize  the number of images that we need to create, we get it from vacancies list
     * @param imageType      the type of image (0 - for horizontal recyclerView, 1 - for vertical)
     * @return               list of integers that are referring to id of images
     */
    private ArrayList<Integer> generateDummyImages(int vacanciesSize, int imageType) {
        ArrayList<Integer> dummyImages = new ArrayList<>();
        for (int i = 0; i < vacanciesSize; i++) {
            if (imageType == 0) {
                dummyImages.add(R.drawable.dummy_img);
            } else if (imageType == 1) {
                dummyImages.add(R.drawable.dummy_img_2);
            }
        }
        return dummyImages;
    }

    /**
     * Configures swipeRefresh. When a user swipes,
     * the function fetches the list of vacancies even when we have the list already
     *
     * @param view  the view instance that we can use for
     *              finding other views and call other functions which requires a view as a parameter
     */
    private void setSwipeRefresh(final View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vacancies = null;
                fetchVacancies(view);
            }
        });
    }

    /**
     * Sets isDetaches to true, which we need to make sure that our
     * asynchronous function (fetchVacancies) does not address elements
     * that have already been deleted (because fragment was detached)
     */
    @Override
    public void onDetach() {
        super.onDetach();
        this.isDetached = true;
    }
}