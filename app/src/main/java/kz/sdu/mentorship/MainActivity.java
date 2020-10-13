package kz.sdu.mentorship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private RecyclerView popularJobsList;
    private JobsAdapter jobsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureBottomNavigation();
        configureRecyclerPopularJobs();
    }

    private void configureRecyclerPopularJobs() {
        popularJobsList = findViewById(R.id.rv_jobs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popularJobsList.setLayoutManager(layoutManager);
        popularJobsList.setHasFixedSize(true);

        ArrayList<Job> dummyJobsContent = generateDummyJobs();
        ArrayList<Integer> dummyImages = generateDummyImages(dummyJobsContent.size());
        jobsAdapter = new JobsAdapter(dummyImages, dummyJobsContent);

        popularJobsList.setAdapter(jobsAdapter);
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