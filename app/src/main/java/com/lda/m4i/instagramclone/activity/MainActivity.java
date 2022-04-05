package com.lda.m4i.instagramclone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.auth.FirebaseAuth;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lda.m4i.instagramclone.R;
import com.lda.m4i.instagramclone.config.FirebaseConfiguration;
import com.lda.m4i.instagramclone.fragment.FeedFragment;
import com.lda.m4i.instagramclone.fragment.PostFragment;
import com.lda.m4i.instagramclone.fragment.ProfileFragment;
import com.lda.m4i.instagramclone.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Instagram");
        setSupportActionBar(toolbar);

        //Firebase
        fbAuth = FirebaseConfiguration.getFirebaseAuth();

        //BottomNavigationView
        configBottomConfigurationView();
    }

    private void configBottomConfigurationView() {
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavigation);
        //Initial configuration
        bottomNavigationViewEx.enableAnimation(true);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
        //Activate navigation
        activateNavigation(bottomNavigationViewEx);

        //initial selected item
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewPage, new FeedFragment()).commit();
    }

    private void activateNavigation(BottomNavigationViewEx bottomNavigationViewEx) {
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragmentTransaction.replace(R.id.viewPage, new FeedFragment()).commit();
                        return true;
                    case R.id.action_search:
                        fragmentTransaction.replace(R.id.viewPage, new SearchFragment()).commit();
                        return true;
                    case R.id.action_post:
                        fragmentTransaction.replace(R.id.viewPage, new PostFragment()).commit();
                        return true;
                    case R.id.action_profile:
                        fragmentTransaction.replace(R.id.viewPage, new ProfileFragment()).commit();
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_exit:
                logOffUser();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOffUser() {
        try {
            fbAuth.signOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}