package kz.sdu.mentorship;

import com.google.gson.annotations.SerializedName;

public class Vacancy {
    @SerializedName("vacancy_id")
    int vacancyId;

    @SerializedName("employee_id")
    int employeeId;

    @SerializedName("name_of_vacancy")
    String jobName;

    @SerializedName("duty")
    String dutyType;

    @SerializedName("requirements")
    String requirements;

    @SerializedName("salary")
    int salary;

    public int getVacancyId() {
        return vacancyId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getJobName() {
        return jobName;
    }

    public String getDutyType() {
        return dutyType;
    }

    public String getRequirements() {
        return requirements;
    }

    public int getSalary() {
        return salary;
    }
}
