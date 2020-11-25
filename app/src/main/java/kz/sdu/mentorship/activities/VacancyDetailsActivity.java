package kz.sdu.mentorship.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import kz.sdu.mentorship.fragments.HomeFragment;
import kz.sdu.mentorship.fragments.SearchFragment;
import kz.sdu.mentorship.models.CompanyName;
import kz.sdu.mentorship.models.EmployerId;
import kz.sdu.mentorship.network.NetworkService;
import kz.sdu.mentorship.R;
import kz.sdu.mentorship.models.Vacancy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VacancyDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_INTENT = "position";
    public static final String EXTRA_SOURCE = "by";
    private int vacancyId;
    private String source;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_details);
        Intent intent = getIntent();
        vacancyId = intent.getIntExtra(EXTRA_INTENT, 0);
        source = intent.getStringExtra(EXTRA_SOURCE);

        loadVacancy();
        configureStatusBar();
    }

    public void onClickBack(View view) {
        finish();
    }

    @SuppressLint("DefaultLocale")
    private void loadVacancy() {
//        if (HomeFragment.vacancies == null || HomeFragment.vacancies.isEmpty()) return;

        Vacancy vacancy = null;
        if (source.equals(HomeFragment.EXTRA_INFO)) {
            vacancy = HomeFragment.vacancies.get(vacancyId);
        } else if (source.equals(SearchFragment.EXTRA_INFO)) {
            vacancy = SearchFragment.vacancies.get(vacancyId);
        }
        if (vacancy == null) return;

        setDataToViews(vacancy);
    }

    @SuppressLint("DefaultLocale")
    private void setDataToViews(Vacancy vacancy) {
        TextView jobName = findViewById(R.id.job_name);
        TextView companyName = findViewById(R.id.company_name);
        TextView location = findViewById(R.id.description);
        TextView experience = findViewById(R.id.experience);
        TextView dutyType = findViewById(R.id.duty_type);
        TextView jobDescription = findViewById(R.id.job_description);

//        getCompanyName(companyName, vacancy.getEmployerId());
        jobName.setText(vacancy.getJobName());
        dutyType.setText(vacancy.getDutyType());
        jobDescription.setText(vacancy.getRequirements());
        location.setText(String.format("%s, %s", vacancy.getCountry(), vacancy.getCity()));

        int requireExperience = vacancy.getExperience();
        String finalExperienceText = "Experience is not required";
        if (requireExperience != 0) {
            finalExperienceText = String.format("Minimum %d year", requireExperience);
        }
        experience.setText(finalExperienceText);
    }

    private void getCompanyName(final TextView companyView, String employerId) {
        NetworkService
                .getInstance()
                .getJSONApi()
                .getCompanyName(new EmployerId(employerId))
                .enqueue(new Callback<CompanyName>() {
                    @Override
                    public void onResponse(Call<CompanyName> call, Response<CompanyName> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            companyView.setText(response.body().getCompanyName());
                        }
                    }

                    @Override
                    public void onFailure(Call<CompanyName> call, Throwable t) {
                        Log.d("error", Objects.requireNonNull(t.getMessage()));
                    }
                });
    }

    private void configureStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
        }
    }
}