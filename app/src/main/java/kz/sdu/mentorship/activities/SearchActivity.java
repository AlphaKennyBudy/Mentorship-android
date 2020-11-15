package kz.sdu.mentorship.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.commons.text.WordUtils;

import java.util.HashSet;
import java.util.Set;

import kz.sdu.mentorship.R;
import kz.sdu.mentorship.fragments.HomeFragment;
import kz.sdu.mentorship.models.Vacancy;

public class SearchActivity extends NavigationBarActivity {

    private ListView categoriesListView;
    public static String[] categories;

    @SuppressLint("MissingSuperCall")
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this, R.layout.activity_search);

        configureCategoriesList();
    }

    private void configureCategoriesList() {
        if (HomeFragment.vacancies == null) return;
        if (categoriesListView != null && categoriesListView.getAdapter() == null && categories != null) {
            setAdapterToCategoriesView();
            return;
        }
        fetchCategoriesList();
        setAdapterToCategoriesView();
        setOnClickListener();
    }

    private void fetchCategoriesList() {
        Set<String> jobNames = new HashSet<>();
        for (Vacancy vacancy: HomeFragment.vacancies) {
            vacancy.setJobName(WordUtils.capitalizeFully(vacancy.getJobName()));
            jobNames.add(vacancy.getJobName());
        }
        categoriesListView = findViewById(R.id.categories_list);
        categories = new String[jobNames.size()];
        jobNames.toArray(categories);
    }

    private void setAdapterToCategoriesView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        categoriesListView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                makeIntent(position);
            }
        });
    }

    private void makeIntent(int position) {
        Intent intent = new Intent(this, VacancyBySearchActivity.class);
        intent.putExtra(VacancyBySearchActivity.EXTRA_INTENT, position);
        startActivity(intent);
    }
}