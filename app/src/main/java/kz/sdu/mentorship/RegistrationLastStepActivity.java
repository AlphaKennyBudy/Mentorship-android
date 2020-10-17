package kz.sdu.mentorship;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationLastStepActivity extends NavigationBarActivity {
    private static final String[] countries = {"Kazakhstan", "Other"};
    private static final String[][] cities = {{
        "Almaty", "Nur-Sultan", "Pavlodar", "Aktobe", "Semey",
            "Atyrau", "Baikonur", "Balqash", "Zhezkazgan", "Karagandy",
            "Kentau", "Kyzylorda", "Kokshetau", "Kostanay", "Zhanaozen",
            "Petropavl", "Taldykorgan", "Shymkent", "Oskemen", "Oral",
            "Taraz", "Other"
    }};

    private SessionManager sessionManager;

    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_PASSWORD = "password";
    public static final String EXTRA_EMAIL = "email";

    String email;
    String username;
    String password;

    MaterialAutoCompleteTextView countriesView;
    MaterialAutoCompleteTextView citiesView;
    TextInputEditText phoneEditText;
    TextInputEditText firstNameEditText;
    TextInputEditText lastNameEditText;

    TextInputLayout countryLayout;
    TextInputLayout cityLayout;
    TextInputLayout phoneLayout;
    TextInputLayout firstNameLayout;
    TextInputLayout lastNameLayout;

    EditText[] fields;
    TextInputLayout[] layouts;


    @SuppressLint("MissingSuperCall")
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this, R.layout.activity_registration_last_step);
        sessionManager = new SessionManager(this);
        findAllViews();
        configureAutoCompleteViews();
        setEmptyTextListeners();
    }

    public void onClickRegister(View view) {
        if (validate(fields, layouts)) {
            getIntentExtra();
            User user = new User(username, email, phoneEditText.getText().toString(), password,
                    firstNameEditText.getText().toString(), lastNameEditText.getText().toString(),
                    countriesView.getText().toString(), citiesView.getText().toString());
            makeRegisterRequest(user);
        }
    }

    public void makeRegisterRequest(User user) {
        NetworkService
            .getInstance()
            .getJSONApi()
            .register(user)
            .enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        ProfileActivity.user = response.body().getUser();
                        sessionManager.saveToken(response.body().getToken());
                        makeIntent(ProfileActivity.class);
                    } else if (response.code() == 400) {
                        // TODO
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("error", Objects.requireNonNull(t.getMessage()));
                }
            });
    }

    public static boolean validate(EditText[] fields, TextInputLayout[] layouts) {
        boolean result = true;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].length() == 0) {
                layouts[i].setError("This field is required!");
                result = false;
            }
        }
        return result;
    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        email = intent.getStringExtra(EXTRA_EMAIL);
        password = intent.getStringExtra(EXTRA_PASSWORD);
        username = intent.getStringExtra(EXTRA_USERNAME);
    }

    private void findAllViews() {
        countriesView = findViewById(R.id.country_edit_text);
        citiesView = findViewById(R.id.city_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        firstNameEditText = findViewById(R.id.first_name_edit_text);
        lastNameEditText = findViewById(R.id.last_name_edit_text);
        countryLayout = findViewById(R.id.country_input_layout);
        cityLayout = findViewById(R.id.city_input_layout);
        phoneLayout = findViewById(R.id.phone_input_layout);
        firstNameLayout = findViewById(R.id.first_name_input_layout);
        lastNameLayout = findViewById(R.id.last_name_input_layout);

        fields = new EditText[]{countriesView, citiesView, phoneEditText, firstNameEditText, lastNameEditText};
        layouts = new TextInputLayout[]{countryLayout, cityLayout, phoneLayout, firstNameLayout, lastNameLayout};
    }


    private void configureAutoCompleteViews() {
        setAdapterToView(countriesView, countries);
        countriesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setAdapterToView(citiesView, cities[position]);
            }
        });
    }

    private void setAdapterToView(MaterialAutoCompleteTextView view, String[] array) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.custom_dropdown_list_item, array);
        view.setAdapter(arrayAdapter);
    }

    private void setEmptyTextListeners() {
        createEmptyTextListener(countriesView, countryLayout);
        createEmptyTextListener(citiesView, cityLayout);
        createEmptyTextListener(phoneEditText, phoneLayout);
        createEmptyTextListener(firstNameEditText, firstNameLayout);
        createEmptyTextListener(lastNameEditText, lastNameLayout);
    }

    public static void createEmptyTextListener(EditText editText, final TextInputLayout inputLayout) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    inputLayout.setError("This field is required!");
                } else {
                    inputLayout.setErrorEnabled(false);
                }
            }
        });
    }

    private void makeIntent(Class<?> destinationClass) {
        Intent intent = new Intent(this, destinationClass);
        startActivity(intent);
        finishAffinity();
    }
}