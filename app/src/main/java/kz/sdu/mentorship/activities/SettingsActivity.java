package kz.sdu.mentorship.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import kz.sdu.mentorship.R;
import kz.sdu.mentorship.adapters.ProfileListAdapter;
import kz.sdu.mentorship.adapters.SettingsAdapter;
import kz.sdu.mentorship.network.SessionManager;

public class SettingsActivity extends AppCompatActivity {
    private final String[] titles = {"Edit profile", "Change password", "Two-step verification", "Log out"};
    private final int[] icons = {
            R.drawable.ic_baseline_arrow_forward_ios_24,
            R.drawable.ic_baseline_arrow_forward_ios_24,
            R.drawable.ic_baseline_arrow_forward_ios_24,
            -1
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        configureListView();
    }

    private void configureListView() {
        ListView listView = findViewById(R.id.settings_lv);
        SettingsAdapter listAdapter = new SettingsAdapter(this, titles, icons);
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