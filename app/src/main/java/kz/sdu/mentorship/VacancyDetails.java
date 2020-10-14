package kz.sdu.mentorship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class VacancyDetails extends AppCompatActivity {
    public static final String EXTRA_INTENT = "position";
    private int vacancyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_details);

        Intent intent = getIntent();
        vacancyId = intent.getIntExtra(EXTRA_INTENT, 0);
        loadVacancy();
        configureStatusBar();
    }

    public void onClickBack(View view) {
        finish();
    }

    private void loadVacancy() {
        if (MainActivity.vacancies == null || MainActivity.vacancies.isEmpty()) return;

        Vacancy vacancy = MainActivity.vacancies.get(vacancyId);

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