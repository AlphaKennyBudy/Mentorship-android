package kz.sdu.mentorship.models;

import com.google.gson.annotations.SerializedName;

public class Vacancy {
    @SerializedName("_id")
    String vacancyId;

    @SerializedName("employer_id")
    String employerId;

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

    public Vacancy(String vacancyId, String employerId, String jobName, String dutyType, String requirements, int salary, String country, String city, int experience) {
        this.vacancyId = vacancyId;
        this.employerId = employerId;
        this.jobName = jobName;
        this.dutyType = dutyType;
        this.requirements = requirements;
        this.salary = salary;
        this.country = country;
        this.city = city;
        this.experience = experience;
    }

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

    public String getVacancyId() {
        return vacancyId;
    }

    public String getEmployerId() {
        return employerId;
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
