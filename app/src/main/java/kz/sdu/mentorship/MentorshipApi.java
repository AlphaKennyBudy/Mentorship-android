package kz.sdu.mentorship;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MentorshipApi {
    @GET("/vacancies")
    Call<List<Vacancy>> getAllVacancies();
}
