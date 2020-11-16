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
    public static List<Employer> employers;

    private RecyclerView jobsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
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
//        Intent intent = new Intent(this, VacancyDetailsActivity.class);
//        intent.putExtra(VacancyDetailsActivity.EXTRA_INTENT, position);
//        intent.putExtra(VacancyDetailsActivity.EXTRA_SOURCE, EXTRA_INFO);
//        startActivity(intent);
    }

    private void fetchVacancies(final View view) {
        NetworkService
                .getInstance()
                .getJSONApi()
                .getAllVacancies()
                .enqueue(new Callback<List<Vacancy>>() {
                    @Override
                    public void onResponse(Call<List<Vacancy>> call, Response<List<Vacancy>> response) {
                        vacancies = response.body();
                        if (vacancies == null) {
                            Toast.makeText(context, getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                            return;
                        }

                        createRecyclerPopularJobs(view);
                        createRecyclerNearbyJobs(view);
                        swipeRefreshLayout.setRefreshing(false);

                        if (employers == null) {
//                            fetchEmployers();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Vacancy>> call, Throwable t) {
                        Log.d("error", Objects.requireNonNull(t.getMessage()));
                    }
                });
    }

    private void fetchEmployers() {
        NetworkService
                .getInstance()
                .getJSONApi()
                .getAllEmployers()
                .enqueue(new Callback<List<Employer>>() {
                    @Override
                    public void onResponse(Call<List<Employer>> call, Response<List<Employer>> response) {
                        employers = response.body();
                    }

                    @Override
                    public void onFailure(Call<List<Employer>> call, Throwable t) {
                        Log.d("error", Objects.requireNonNull(t.getMessage()));
                    }
                });
    }

    private void createRecyclerPopularJobs(View view) {
        if (vacancies == null || vacancies.isEmpty()) return;
        ArrayList<Integer> dummyImages = generateDummyImages(vacancies.size(), 0);
        createRecycler(view, R.id.rv_popular_jobs, LinearLayoutManager.HORIZONTAL, vacancies,
                dummyImages, R.layout.popular_job_list_item);
    }

    private void createRecyclerNearbyJobs(View view) {
        if (vacancies == null || vacancies.isEmpty()) return;
        ArrayList<Integer> dummyImages = generateDummyImages(vacancies.size(), 1);
        createRecycler(view, R.id.rv_nearby_jobs, LinearLayoutManager.VERTICAL, vacancies,
                dummyImages, R.layout.nearby_job_list_item);
    }

    private void createRecycler(View view, int viewId, int orientation, List<Vacancy> vacancies,
                                ArrayList<Integer> images, int layoutId) {
        jobsList = getRecyclerById(view, viewId, orientation);
        JobsAdapter jobsAdapter = new JobsAdapter(layoutId, images, vacancies, this);
        jobsList.setAdapter(jobsAdapter);
    }

    private RecyclerView getRecyclerById(View view, int viewId, int orientation) {
        RecyclerView jobsList = view.findViewById(viewId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,  orientation, false);
        jobsList.setLayoutManager(layoutManager);
        jobsList.setHasFixedSize(true);
        return jobsList;
    }

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
}