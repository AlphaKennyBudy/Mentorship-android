package kz.sdu.mentorship.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kz.sdu.mentorship.fragments.HomeFragment;
import kz.sdu.mentorship.fragments.ProfileFragment;
import kz.sdu.mentorship.fragments.SearchFragment;
import kz.sdu.mentorship.models.Employer;
import kz.sdu.mentorship.adapters.JobsAdapter;
import kz.sdu.mentorship.network.NetworkService;
import kz.sdu.mentorship.R;
import kz.sdu.mentorship.models.Vacancy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static Fragment lastFragment;
    private static int lastItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (lastFragment == null) {
            lastFragment = new HomeFragment();
            lastItemId = R.id.home_item;
        }
        loadFragment(lastFragment);

        configureBottomNavigation();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void configureBottomNavigation() {
        BottomNavigationView navigation = findViewById(R.id.nav_bar);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home_item:
                if (lastItemId != R.id.home_item) {
                    fragment = new HomeFragment();
                }
                break;
            case R.id.search_item:
                if (lastItemId != R.id.search_item) {
                    fragment = new SearchFragment();
                }
                break;
            case R.id.profile_item:
                if (lastItemId != R.id.profile_item) {
                    fragment = new ProfileFragment();
                }
        }
        lastItemId = item.getItemId();
        return loadFragment(fragment);
    }
}