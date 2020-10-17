package kz.sdu.mentorship;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VacancyDetailsActivity extends NavigationBarActivity {
    public static final String EXTRA_INTENT = "position";
    public static final String EXTRA_SOURCE = "by";
    private int vacancyId;
    private String source;

    @SuppressLint("MissingSuperCall")
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this, R.layout.activity_vacancy_details);

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
        if (MainActivity.vacancies == null || MainActivity.vacancies.isEmpty()) return;

        Vacancy vacancy = null;
        if (source.equals(MainActivity.EXTRA_INFO)) {
            vacancy = MainActivity.vacancies.get(vacancyId);
        } else if (source.equals(VacancyBySearchActivity.EXTRA_INFO)) {
            vacancy = VacancyBySearchActivity.vacancies.get(vacancyId);
        }
        if (vacancy == null) return;

        setDataToViews(vacancy);
    }

    private void setDataToViews(Vacancy vacancy) {
        TextView jobName = findViewById(R.id.job_name);
        TextView companyName = findViewById(R.id.company_name);
        TextView location = findViewById(R.id.location);
        TextView experience = findViewById(R.id.experience);
        TextView dutyType = findViewById(R.id.duty_type);
        TextView jobDescription = findViewById(R.id.job_description);

        getCompanyName(companyName, vacancy.getEmployerId());
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
                            Log.d("a", "AAAAAAAAAAAADSSDSD");
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