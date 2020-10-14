package kz.sdu.mentorship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static List<Vacancy> vacancies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureBottomNavigation();

        fetchData();
    }

    private void fetchData() {
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
                    }

                    @Override
                    public void onFailure(Call<List<Vacancy>> call, Throwable t) {
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
        RecyclerView jobsList = getRecyclerById(viewId, orientation);
        JobsAdapter jobsAdapter = new JobsAdapter(layoutId, images, vacancies);
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

    private void configureBottomNavigation() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home_item:
                            // TODO
                            break;
                        case R.id.search_item:
                            // TODO
                            break;
                        case R.id.profile_item:
                            // TODO
                            break;
                        default:
                            return false;
                    }
                    return true;
                }
            }
        );
    }
}