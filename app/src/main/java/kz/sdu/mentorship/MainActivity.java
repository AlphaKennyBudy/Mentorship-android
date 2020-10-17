package kz.sdu.mentorship;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends NavigationBarActivity implements JobsAdapter.OnJobListener {
    public static List<Vacancy> vacancies;
    public static List<Employer> employers;

    private RecyclerView jobsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static final String EXTRA_INFO = "by_home";

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this, R.layout.activity_main);

        if (vacancies == null) {
            fetchVacancies();
        } else if (jobsList == null) {
            createRecyclerPopularJobs();
            createRecyclerNearbyJobs();
        }
        setSwipeRefresh();
    }

    @Override
    public void onJobClick(int position) {
        Intent intent = new Intent(this, VacancyDetailsActivity.class);
        intent.putExtra(VacancyDetailsActivity.EXTRA_INTENT, position);
        intent.putExtra(VacancyDetailsActivity.EXTRA_SOURCE, EXTRA_INFO);
        startActivity(intent);
    }

    private void fetchVacancies() {
        NetworkService
                .getInstance()
                .getJSONApi()
                .getAllVacancies()
                .enqueue(new Callback<List<Vacancy>>() {
                    @Override
                    public void onResponse(Call<List<Vacancy>> call, Response<List<Vacancy>> response) {
                        vacancies = response.body();
                        createRecyclerPopularJobs();
                        createRecyclerNearbyJobs();
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

    private void createRecyclerPopularJobs() {
        if (vacancies == null || vacancies.isEmpty()) return;
        ArrayList<Integer> dummyImages = generateDummyImages(vacancies.size(), 0);
        createRecycler(R.id.rv_popular_jobs, LinearLayoutManager.HORIZONTAL, vacancies,
                        dummyImages, R.layout.popular_job_list_item);
    }

    private void createRecyclerNearbyJobs() {
        if (vacancies == null || vacancies.isEmpty()) return;
        ArrayList<Integer> dummyImages = generateDummyImages(vacancies.size(), 1);
        createRecycler(R.id.rv_nearby_jobs, LinearLayoutManager.VERTICAL, vacancies,
                dummyImages, R.layout.nearby_job_list_item);
    }

    private void createRecycler(int viewId, int orientation, List<Vacancy> vacancies,
                                ArrayList<Integer> images, int layoutId) {
        jobsList = getRecyclerById(viewId, orientation);
        JobsAdapter jobsAdapter = new JobsAdapter(layoutId, images, vacancies, this);
        jobsList.setAdapter(jobsAdapter);
    }

    private RecyclerView getRecyclerById(int viewId, int orientation) {
        RecyclerView jobsList = findViewById(viewId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,  orientation, false);
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

    private void setSwipeRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vacancies = null;
                fetchVacancies();
            }
        });
    }
}