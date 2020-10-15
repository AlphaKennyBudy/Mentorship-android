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

    private void loadVacancy() {
        if (MainActivity.vacancies == null || MainActivity.vacancies.isEmpty()) return;

        Vacancy vacancy = null;
        if (source.equals(MainActivity.EXTRA_INFO)) {
            vacancy = MainActivity.vacancies.get(vacancyId);
        } else if (source.equals(VacancyBySearchActivity.EXTRA_INFO)) {
            vacancy = VacancyBySearchActivity.vacancies.get(vacancyId);
        }
        if (vacancy == null) return;

        TextView jobName = findViewById(R.id.job_name);
        TextView companyN12ame = findViewById(R.id.company_name);
        TextView location = findViewById(R.id.location);
        TextView experience = findViewById(R.id.experience);
        TextView dutyType = findViewById(R.id.duty_type);
        TextView jobDescription = findViewById(R.id.job_description);

        jobName.setText(vacancy.getJobName());
        dutyType.setText(vacancy.getDutyType());
        jobDescription.setText(vacancy.getRequirements());
    }

    private void configureStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
        }
    }
}