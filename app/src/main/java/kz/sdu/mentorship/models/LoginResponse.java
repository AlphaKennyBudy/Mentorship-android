package kz.sdu.mentorship.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    String token;

    @SerializedName("user")
    User user;

    @SerializedName("message")
    String message;

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}
