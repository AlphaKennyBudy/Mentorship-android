package kz.sdu.mentorship;

import com.google.gson.annotations.SerializedName;

public class CheckMailRequest {
    @SerializedName("email")
    String email;

    public CheckMailRequest(String email) {
        this.email = email;
    }
}
