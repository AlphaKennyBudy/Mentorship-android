package kz.sdu.mentorship;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrationActivity extends NavigationBarActivity {

    private TextInputEditText emailEditText;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText passwordConfirmEditText;
    private EditText[] fields;

    @SuppressLint("MissingSuperCall")
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this, R.layout.activity_registration);

        findAllViews();
        setEmptyTextListeners();
        createPasswordListener();
    }

    public void onClickLogin(View view) {
        makeIntent(LoginActivity.class);
        finishAffinity();
    }

    public void onClickNext(View view) {
        if (!RegistrationLastStepActivity.validate(fields)) {
            Toast.makeText(this, "There are empty fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!validatePassword()) {
            Toast.makeText(this, "Password do not match!", Toast.LENGTH_SHORT).show();
            return;
        }
        makeIntent(RegistrationLastStepActivity.class);
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
        fields = new EditText[]{emailEditText, usernameEditText, passwordEditText, passwordConfirmEditText};
    }

    private void setEmptyTextListeners() {
        RegistrationLastStepActivity.createEmptyTextListener(emailEditText, (TextInputLayout) findViewById(R.id.email_input_layout));
        RegistrationLastStepActivity.createEmptyTextListener(usernameEditText, (TextInputLayout) findViewById(R.id.username_input_layout));
        RegistrationLastStepActivity.createEmptyTextListener(passwordEditText, (TextInputLayout) findViewById(R.id.password_input_layout));
    }

    private void createPasswordListener() {
        final TextInputLayout secondPasswordLayout = findViewById(R.id.password_confirm_input_layout);

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

    private void makeIntent(Class<?> destinationClass) {
        Intent intent = new Intent(this, destinationClass);
        startActivity(intent);
    }
}