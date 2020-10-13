package kz.sdu.mentorship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureBottomNavigation();
        createRecyclerPopularJobs();
    }

    private void createRecyclerPopularJobs() {
        RecyclerView popularJobsList = getRecycler(R.id.rv_popular_jobs, LinearLayoutManager.HORIZONTAL);

        ArrayList<Job> dummyJobsContent = generateDummyJobs();
        ArrayList<Integer> dummyImages = generateDummyImages(dummyJobsContent.size());
        JobsAdapter jobsAdapter = new JobsAdapter(R.layout.popular_job_list_item, dummyImages, dummyJobsContent);

        popularJobsList.setAdapter(jobsAdapter);
    }

    private RecyclerView getRecycler(int viewId, int orientation) {
        RecyclerView jobsList = findViewById(viewId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,  orientation, false);
        jobsList.setLayoutManager(layoutManager);
        jobsList.setHasFixedSize(true);
        return jobsList;
    }

    private ArrayList<Job> generateDummyJobs() {
        return new ArrayList<>(Arrays.asList(
            new Job("Product Designer", "Full time",50),
            new Job("Android Developer", "Part time",69),
            new Job("Web Developer", "Full time",89),
            new Job("Data Scientist", "Full time",300))
        );
    }

    private ArrayList<Integer> generateDummyImages(int dummyJobsSize) {
        ArrayList<Integer> dummyImages = new ArrayList<>();
        for (int i = 0; i < dummyJobsSize; i++) {
            dummyImages.add(R.drawable.dummy_img);
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