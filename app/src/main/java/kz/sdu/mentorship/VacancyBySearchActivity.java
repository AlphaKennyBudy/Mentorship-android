package kz.sdu.mentorship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import static kz.sdu.mentorship.MainActivity.vacancies;

public class VacancyBySearchActivity extends AppCompatActivity implements JobsAdapter.OnJobListener {
    public static final String EXTRA_INTENT = "position";
    public static final String EXTRA_INFO = "by_search";
    public static List<Vacancy> vacancies;
    private RecyclerView jobsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_by_search);

        Intent intent = getIntent();
        int position = intent.getIntExtra(EXTRA_INTENT,0);
        fetchVacanciesFromCategory(position);
        createRecyclerBySearch();
    }

    private void fetchVacanciesFromCategory(int position) {
        String jobName = SearchActivity.categories[position];
        vacancies = new ArrayList<>();
        for (Vacancy vacancy: MainActivity.vacancies) {
            if (vacancy.getJobName().equals(jobName)) {
                vacancies.add(vacancy);
            }
        }
    }

    // TODO: REFACTOR ALL DUPLICATE FUNCTIONS FROM MAIN ACTIVITY
    @Override
    public void onJobClick(int position) {
        Intent intent = new Intent(this, VacancyDetailsActivity.class);
        intent.putExtra(VacancyDetailsActivity.EXTRA_INTENT, position);
        intent.putExtra(VacancyDetailsActivity.EXTRA_SOURCE, EXTRA_INFO);
        startActivity(intent);
    }

    private void createRecyclerBySearch() {
        if (vacancies == null || vacancies.isEmpty()) return;
        ArrayList<Integer> dummyImages = generateDummyImages(vacancies.size(), 1);
        createRecycler(R.id.rv_jobs_by_search, LinearLayoutManager.VERTICAL, vacancies,
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
}