package kz.sdu.mentorship;

import com.google.gson.annotations.SerializedName;

public class Employer {
    @SerializedName("vacancy_id")
    int vacancy_id;

    @SerializedName("name")
    String name;

    @SerializedName("surname")
    String surname;

    @SerializedName("email")
    String email;

    @SerializedName("phone")
    String phone;

    @SerializedName("company_name")
    String companyName;

    @SerializedName("industry")
    String industry;

    @SerializedName("bin")
    String bin;

    @SerializedName("office_address")
    String officeAddress;

    @SerializedName("phone_office")
    String phoneOffice;

    @SerializedName("company_description")
    String companyDescription;

    @SerializedName("logo")
    String logo;

    public int getVacancy_id() {
        return vacancy_id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public String getBin() {
        return bin;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public String getPhoneOffice() {
        return phoneOffice;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public String getLogo() {
        return logo;
    }
}
