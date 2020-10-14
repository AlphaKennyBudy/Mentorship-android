package kz.sdu.mentorship;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class NavigationBarActivity extends AppCompatActivity {

    private Context context;

    protected final void onCreate(Bundle savedInstanceState, Context context, int layoutId) {
        super.onCreate(savedInstanceState);
        this.context = context;
        setContentView(layoutId);
        configureBottomNavigation();
    }

    private void configureBottomNavigation() {
        final BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_item:
                                makeIntent(MainActivity.class);
                                break;
                            case R.id.search_item:
                                makeIntent(SearchActivity.class);
                                break;
                            case R.id.profile_item:
                                // TODO
                                break;
                            default:
                                return false;
                        }
                        return true;
                    }
                }
        );
    }

    public void makeIntent(Class<?> destinationActivity) {
        if (context.getClass() != destinationActivity) {
            Intent intent = new Intent(context, destinationActivity);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }
}
