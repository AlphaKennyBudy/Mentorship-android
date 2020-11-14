package kz.sdu.mentorship.activities;

import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

import kz.sdu.mentorship.network.NetworkService;
import kz.sdu.mentorship.adapters.ProfileListAdapter;
import kz.sdu.mentorship.R;
import kz.sdu.mentorship.network.SessionManager;
import kz.sdu.mentorship.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends NavigationBarActivity {
    public String[] titles;
    public static User user;

    @SuppressLint("MissingSuperCall")
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this, R.layout.activity_profile);

        if (user == null) {
            fetchData();
        } else {
            setDataToViews();
        }
        createAdapter();
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(NavigationBarActivity.EXTRA_INTENT, R.id.home_item);
        startActivity(intent);
        finish();
    }

    private void fetchData() {
        NetworkService
            .getInstance()
            .getJSONApi()
            .getUserInfo(SessionManager.fetchToken(this))
            .enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        user = response.body();
                        setDataToViews();
                    } else if (response.code() == 400) {
                        // TODO
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("error", Objects.requireNonNull(t.getMessage()));
                }
            });
    }

    private void setDataToViews() {
        TextView nameView = findViewById(R.id.full_name);
        TextView locationView = findViewById(R.id.location);
        TextView numberView = findViewById(R.id.number);
        TextView emailView = findViewById(R.id.email);

        nameView.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
        locationView.setText(String.format("%s, %s", user.getCity(), user.getCountry()));
        numberView.setText(user.getPhoneNumber());
        emailView.setText(user.getEmail());
        setProfileImage(String.valueOf(user.getFirstName().charAt(0)));
    }

    private void setProfileImage(String firstLetter) {
        ImageView profileImage = findViewById(R.id.profile_image);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, Color.BLACK);
        profileImage.setImageDrawable(drawable);
    }


    private void createAdapter() {
        titles = new String[]{"Favorites", "Offers", "Settings", "Log out"};

        int[] images = {R.drawable.ic_baseline_favorite_border_dark_24,
                R.drawable.ic_baseline_assignment_24, R.drawable.ic_baseline_settings_24,
                R.drawable.ic_baseline_power_settings_new_24};

        int[] colors = {
                ContextCompat.getColor(this,R.color.black),
                ContextCompat.getColor(this,R.color.black),
                ContextCompat.getColor(this,R.color.black),
                ContextCompat.getColor(this,R.color.lightRed)
        };
        setAdapter(this, titles, images, colors);
    }

    private void setAdapter(Context context, String[] titles, int[] images, int[] colors) {
        ProfileListAdapter listAdapter = new ProfileListAdapter(context, titles, images, colors);
        ListView listView = findViewById(R.id.title_list);
        listView.setAdapter(listAdapter);
        setListenerToView(this, listView);

    }

    private void setListenerToView(final Context context, ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (titles[position].equalsIgnoreCase("Log out")) {
                    new MaterialAlertDialogBuilder(context)
                            .setTitle("Log Out")
                            .setMessage("Are you sure you want to log out?")
                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    logOut();
                                }
                            })
                            .show();
                }
            }
        });
    }

    private void logOut() {
        SessionManager.removeToken(this);
        makeIntent(LoginActivity.class);
    }

    private void makeIntent(Class<?> destinationClass) {
        Intent intent = new Intent(this, destinationClass);
        startActivity(intent);
        finishAffinity();
    }

}