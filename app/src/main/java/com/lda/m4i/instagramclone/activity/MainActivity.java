package com.lda.m4i.instagramclone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.lda.m4i.instagramclone.R;
import com.lda.m4i.instagramclone.config.FirebaseConfiguration;

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