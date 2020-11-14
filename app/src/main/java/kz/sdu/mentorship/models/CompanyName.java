package kz.sdu.mentorship.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyName {
    @SerializedName("company_name")
    String companyName;

    public String getCompanyName() {
        return companyName;
    }
}
