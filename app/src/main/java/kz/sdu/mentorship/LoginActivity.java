package kz.sdu.mentorship;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends NavigationBarActivity {

    SessionManager sessionManager;
    private TextInputEditText loginEditText;
    private TextInputLayout loginLayout;
    private TextInputEditText passwordEditText;
    private TextInputLayout passwordLayout;

    @SuppressLint("MissingSuperCall")
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this, R.layout.activity_login);
        sessionManager = new SessionManager(this);
        findViews();
        setEmptyListeners();
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
                            Toast.makeText(LoginActivity.this, "Login or password is incorrect!", Toast.LENGTH_SHORT).show();
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
        RegistrationLastStepActivity.createEmptyTextListener((EditText) loginEditText, loginLayout);
        RegistrationLastStepActivity.createEmptyTextListener((EditText) passwordEditText, passwordLayout);
    }

    private boolean validateEmptyFields() {
        boolean result = true;
        if (loginEditText.getText().length() == 0) {
            loginLayout.setError("This field is required!");
            result = false;
        }
        if (passwordEditText.getText().length() == 0) {
            passwordLayout.setError("This field is required!");
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