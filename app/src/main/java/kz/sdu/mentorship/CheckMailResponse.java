package kz.sdu.mentorship;

import com.google.gson.annotations.SerializedName;

public class CheckMailResponse {
    @SerializedName("isRegistered")
    boolean isRegistered;

    @SerializedName("message")
    String message;

    public boolean isRegistered() {
        return isRegistered;
    }
}
