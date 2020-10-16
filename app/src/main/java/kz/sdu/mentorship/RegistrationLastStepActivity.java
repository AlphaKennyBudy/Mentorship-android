package kz.sdu.mentorship;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class RegistrationLastStepActivity extends NavigationBarActivity {
    private static final String[] countries = {"Kazakhstan", "Other"};
    private static final String[][] cities = {{
        "Almaty", "Nur-Sultan", "Pavlodar", "Aktobe", "Semey",
            "Atyrau", "Baikonur", "Balqash", "Zhezkazgan", "Karagandy",
            "Kentau", "Kyzylorda", "Kokshetau", "Kostanay", "Zhanaozen",
            "Petropavl", "Taldykorgan", "Shymkent", "Oskemen", "Oral",
            "Taraz", "Other"
    }};
    MaterialAutoCompleteTextView countriesView;
    MaterialAutoCompleteTextView citiesView;
    TextInputEditText phoneEditText;
    TextInputEditText firstNameEditText;
    TextInputEditText lastNameEditText;
    EditText[] fields;


    @SuppressLint("MissingSuperCall")
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this, R.layout.activity_registration_last_step);
        findAllViews();
        configureAutoCompleteViews();
        setEmptyTextListeners();
    }

    public void onClickRegister(View view) {
        if (validate(fields)) {

        } else {
            Toast.makeText(this, "There are empty fields!", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean validate(EditText[] fields) {
        for (EditText field: fields) {
            if (field.length() == 0) return false;
        }
        return true;
    }

    private void findAllViews() {
        countriesView = findViewById(R.id.country_edit_text);
        citiesView = findViewById(R.id.city_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        firstNameEditText = findViewById(R.id.first_name_edit_text);
        lastNameEditText = findViewById(R.id.last_name_edit_text);
        fields = new EditText[]{countriesView, citiesView, phoneEditText, firstNameEditText, lastNameEditText};
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
        createEmptyTextListener(countriesView, (TextInputLayout) findViewById(R.id.country_input_layout));
        createEmptyTextListener(citiesView, (TextInputLayout) findViewById(R.id.city_input_layout));
        createEmptyTextListener(phoneEditText, (TextInputLayout) findViewById(R.id.phone_input_layout));
        createEmptyTextListener(firstNameEditText, (TextInputLayout) findViewById(R.id.first_name_input_layout));
        createEmptyTextListener(lastNameEditText, (TextInputLayout) findViewById(R.id.last_name_input_layout));
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
}