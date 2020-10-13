package kz.sdu.mentorship;

public class Job {
    private String jobName;
    private String dutyType;
    private int salary;

    public Job(String jobName, String dutyType, int salary) {
        this.jobName = jobName;
        this.dutyType = dutyType;
        this.salary = salary;
    }

    public String getJobName() {
        return jobName;
    }

    public String getDutyType() {
        return dutyType;
    }

    public int getSalary() {
        return salary;
    }
}
