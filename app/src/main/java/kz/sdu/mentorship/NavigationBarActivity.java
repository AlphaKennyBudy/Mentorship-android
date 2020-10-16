package kz.sdu.mentorship;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Console;

public abstract class NavigationBarActivity extends AppCompatActivity {

    private static final String EXTRA_INTENT = "itemId";
    private static int lastItemId = R.id.home_item;
    private Context context;

    protected final void onCreate(Bundle savedInstanceState, Context context, int layoutId) {
        super.onCreate(savedInstanceState);
        this.context = context;
        setContentView(layoutId);
        configureBottomNavigation();
    }

    private void configureBottomNavigation() {
        final BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null && !bundle.containsKey(EXTRA_INTENT)) {
            bottomNavigation.setSelectedItemId(lastItemId);
        }

        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int itemId = item.getItemId();
                        switch (itemId) {
                            case R.id.home_item:
                                makeIntent(MainActivity.class, R.id.home_item);
                                break;
                            case R.id.search_item:
                                makeIntent(SearchActivity.class, R.id.search_item);
                                break;
                            case R.id.profile_item:
                                makeIntent(LoginActivity.class, R.id.profile_item);
                                break;
                            default:
                                return false;
                        }
                        lastItemId = itemId;
                        return true;
                    }
                }
        );

        if (bundle != null && bundle.containsKey(EXTRA_INTENT)) {
            bottomNavigation.setSelectedItemId(bundle.getInt(EXTRA_INTENT));
        }
    }

    public void makeIntent(Class<?> destinationActivity, int itemId) {
        if (context.getClass() != destinationActivity) {
            Intent intent = new Intent(context, destinationActivity);
            intent.putExtra(EXTRA_INTENT, itemId);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            startActivity(intent);
            overridePendingTransition(0, 0);
            finishAffinity();
        }
    }
}
