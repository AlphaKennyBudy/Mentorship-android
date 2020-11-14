package kz.sdu.mentorship.network;

import java.util.List;

import kz.sdu.mentorship.models.CheckMailRequest;
import kz.sdu.mentorship.models.CheckMailResponse;
import kz.sdu.mentorship.models.CompanyName;
import kz.sdu.mentorship.models.Employer;
import kz.sdu.mentorship.models.EmployerId;
import kz.sdu.mentorship.models.LoginRequest;
import kz.sdu.mentorship.models.LoginResponse;
import kz.sdu.mentorship.models.User;
import kz.sdu.mentorship.models.Vacancy;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MentorshipApi {
    @GET("/vacancies")
    Call<List<Vacancy>> getAllVacancies();

    @GET("/employers")
    Call<List<Employer>> getAllEmployers();


    @POST("/vacancies/getCompanyName")
    Call<CompanyName> getCompanyName(@Body EmployerId employerId);

    @POST("/user/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("/user/me")
    Call<User> getUserInfo(@Header("token") String token);

    @POST("/user/checkMail")
    Call<CheckMailResponse> checkMail(@Body CheckMailRequest request);

    @POST("/user/signup")
    Call<LoginResponse> register(@Body User user);
}
