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

    @SerializedName("country")
    String country;

    @SerializedName("city")
    String city;

    @SerializedName("experience")
    int experience;

    public String getCity() {
        return city;
    }

    public int getExperience() {
        return experience;
    }

    public String getCountry() {
        return country;
    }

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

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
