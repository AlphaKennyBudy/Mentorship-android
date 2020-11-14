package kz.sdu.mentorship.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import kz.sdu.mentorship.models.CheckMailRequest;
import kz.sdu.mentorship.models.CheckMailResponse;
import kz.sdu.mentorship.network.NetworkService;
import kz.sdu.mentorship.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends NavigationBarActivity {
    private Button nextButton;

    private final String EMAIL_KEY = "email";
    private final String USERNAME_KEY = "username";
    private final String PASSWORD_KEY = "password";
    private final String PASSWORD_CONFIRM_KEY = "password_confirm";

    private TextInputEditText emailEditText;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText passwordConfirmEditText;

    private TextInputLayout emailLayout;
    private TextInputLayout usernameLayout;
    private TextInputLayout firstPasswordLayout;
    private TextInputLayout secondPasswordLayout;

    private EditText[] fields;
    private TextInputLayout[] layouts;

    @SuppressLint("MissingSuperCall")
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this, R.layout.activity_registration);

        findAllViews();
        setEmptyTextListeners();
        createPasswordListener();
    }

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState (savedInstanceState);
        emailEditText.setText(savedInstanceState.getString(EMAIL_KEY));
        usernameEditText.setText(savedInstanceState.getString(USERNAME_KEY));
        passwordEditText.setText(savedInstanceState.getString(PASSWORD_KEY));
        passwordConfirmEditText.setText(savedInstanceState.getString(PASSWORD_CONFIRM_KEY));
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState (outState);
        outState.putString(EMAIL_KEY, emailEditText.getText().toString());
        outState.putString(USERNAME_KEY, usernameEditText.getText().toString());
        outState.putString(PASSWORD_KEY, passwordEditText.getText().toString());
        outState.putString(PASSWORD_CONFIRM_KEY, passwordConfirmEditText.getText().toString());
    }

    public void onClickLogin(View view) {
        makeIntent(LoginActivity.class);
        finishAffinity();
    }

    public void onClickNext(View view) {
        boolean hasErrors = false;
        if (!RegistrationLastStepActivity.validate(fields, layouts)) {
            hasErrors = true;
        }
        if (!validatePassword()) {
            secondPasswordLayout.setError("Password do not match!");
            hasErrors = true;
        }
        validateEmail (hasErrors);
    }

    private void validateEmail(final boolean hasErrors) {
        NetworkService
            .getInstance()
            .getJSONApi()
            .checkMail(new CheckMailRequest(emailEditText.getText().toString()))
            .enqueue(new Callback<CheckMailResponse>() {
                @Override
                public void onResponse(Call<CheckMailResponse> call, Response<CheckMailResponse> response) {
                    if (response.body() != null && response.body().isRegistered()) {
                        emailLayout.setError("Email is already in use!");
                    } else if (!response.body().isRegistered() && !hasErrors) {
                        makeIntentWithExtra(RegistrationLastStepActivity.class);
                    }
                }

                @Override
                public void onFailure(Call<CheckMailResponse> call, Throwable t) {
                    Log.d("error", Objects.requireNonNull(t.getMessage()));
                }
            });
    }

    private boolean validatePassword() {
        String firstPassword = String.valueOf(passwordEditText.getText());
        String secondPassword = String.valueOf(passwordConfirmEditText.getText());
        return firstPassword.equals(secondPassword);
    }

    private void findAllViews() {
        emailEditText = findViewById(R.id.email_edit_text);
        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        passwordConfirmEditText = findViewById(R.id.password_confirm_edit_text);
        emailLayout = findViewById(R.id.email_input_layout);
        usernameLayout = findViewById(R.id.username_input_layout);
        firstPasswordLayout = findViewById(R.id.password_input_layout);
        secondPasswordLayout = findViewById(R.id.password_confirm_input_layout);

        fields = new EditText[]{emailEditText, usernameEditText, passwordEditText, passwordConfirmEditText};
        layouts = new TextInputLayout[]{emailLayout, usernameLayout, firstPasswordLayout, secondPasswordLayout};
    }

    private void setEmptyTextListeners() {
        RegistrationLastStepActivity.createEmptyTextListener(emailEditText, (TextInputLayout) findViewById(R.id.email_input_layout));
        RegistrationLastStepActivity.createEmptyTextListener(usernameEditText, (TextInputLayout) findViewById(R.id.username_input_layout));
        RegistrationLastStepActivity.createEmptyTextListener(passwordEditText, (TextInputLayout) findViewById(R.id.password_input_layout));
    }

    private void createPasswordListener() {
        passwordConfirmEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(String.valueOf(passwordEditText.getText()))) {
                    secondPasswordLayout.setError("Password do not match!");
                } else {
                    secondPasswordLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (passwordConfirmEditText.getText().length() != 0) {
                    if (!s.toString().equals(String.valueOf(passwordConfirmEditText.getText()))) {
                        secondPasswordLayout.setError("Password do not match!");
                    } else {
                        secondPasswordLayout.setErrorEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void makeIntentWithExtra(Class<?> destinationClass) {
        Intent intent = new Intent(this, destinationClass);
        intent.putExtra(RegistrationLastStepActivity.EXTRA_USERNAME, usernameEditText.getText().toString());
        intent.putExtra(RegistrationLastStepActivity.EXTRA_PASSWORD, passwordEditText.getText().toString());
        intent.putExtra(RegistrationLastStepActivity.EXTRA_EMAIL, emailEditText.getText().toString());
        startActivity(intent);
    }

    private void makeIntent(Class<?> destinationClass) {
        Intent intent = new Intent(this, destinationClass);
        startActivity(intent);
    }
}