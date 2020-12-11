package kz.sdu.mentorship.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import kz.sdu.mentorship.models.LoginRequest;
import kz.sdu.mentorship.models.LoginResponse;
import kz.sdu.mentorship.network.NetworkService;
import kz.sdu.mentorship.R;
import kz.sdu.mentorship.network.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private final String LOGIN_KEY = "login";
    private final String PASSWORD_KEY = "password";

    SessionManager sessionManager;
    private TextInputEditText loginEditText;
    private TextInputLayout loginLayout;
    private TextInputEditText passwordEditText;
    private TextInputLayout passwordLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        sessionManager = new SessionManager(this);
        findViews();
        setEmptyListeners();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        loginEditText.setText(savedInstanceState.getString(LOGIN_KEY));
        passwordEditText.setText(savedInstanceState.getString(PASSWORD_KEY));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LOGIN_KEY, loginEditText.getText().toString());
        outState.putString(PASSWORD_KEY, passwordEditText.getText().toString());
    }

    public void onClickLogin(View view) {
        if (validateEmptyFields()) {
            NetworkService
                .getInstance()
                .getJSONApi()
                .login(new LoginRequest(loginEditText.getText().toString(),
                        passwordEditText.getText().toString()))
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            sessionManager.saveToken(loginResponse.getToken());
                            ProfileActivity.user = loginResponse.getUser();
                            makeIntent(ProfileActivity.class);
                        } else if (response.code() == 400) {
                            passwordLayout.setError(getString(R.string.login_password_incorrect));
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("error", Objects.requireNonNull(t.getMessage()));
                    }
                });
        }
    }

    private void makeIntent(Class<?> destinationClass) {
        Intent intent = new Intent(this, destinationClass);
        startActivity(intent);
        finishAffinity();
    }

    private void setEmptyListeners() {
        RegistrationLastStepActivity.createEmptyTextListener(this, (EditText) loginEditText, loginLayout);
        RegistrationLastStepActivity.createEmptyTextListener(this, (EditText) passwordEditText, passwordLayout);
    }

    private boolean validateEmptyFields() {
        boolean result = true;
        if (loginEditText.getText().length() == 0) {
            loginLayout.setError(getString(R.string.field_is_required));
            result = false;
        }
        if (passwordEditText.getText().length() == 0) {
            passwordLayout.setError(getString(R.string.field_is_required));
            result = false;
        }
        return result;
    }

    private void findViews() {
        loginEditText = findViewById(R.id.login_edit_text);
        loginLayout = findViewById(R.id.login_input_layout);
        passwordEditText = findViewById(R.id.password_edit_text);
        passwordLayout = findViewById(R.id.password_input_layout);
    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}