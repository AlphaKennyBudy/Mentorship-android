package kz.sdu.mentorship;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployerId {
    @SerializedName("employer_id")
    @Expose
    String employerId;

    public EmployerId(String employerId) {
        this.employerId = employerId;
    }

    public String getEmployer_id() {
        return employerId;
    }
}
