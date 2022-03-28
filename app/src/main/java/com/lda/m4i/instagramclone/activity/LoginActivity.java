package com.lda.m4i.instagramclone.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.lda.m4i.instagramclone.R;
import com.lda.m4i.instagramclone.config.FirebaseConfiguration;
import com.lda.m4i.instagramclone.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.activity_login_et_email);
        etPassword = findViewById(R.id.activity_login_et_password);

        fbAuth = FirebaseConfiguration.getFirebaseAuth();
    }

    public void loginUser(User user) {
        fbAuth.signInWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    openMainActivity();
                } else {
                    String exception = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        exception = "The user is not registered.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "E-mail and password don't correspond to a registered user.";
                    } catch (Exception e) {
                        exception = "Error on trying to register: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this,
                            exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void validateUserLogin(View v) {

        String textEmail = etEmail.getText().toString();
        String textPassword = etPassword.getText().toString();

        if (!textEmail.isEmpty()) {
            if (!textPassword.isEmpty()) {

                //Create user
                User user = new User();
                user.setEmail(textEmail);
                user.setPassword(textPassword);
                loginUser(user);

            } else {
                Toast.makeText(LoginActivity.this, "Please fill the password field.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Please fill the e-mail field.", Toast.LENGTH_SHORT).show();
        }

    }

    public void openRegisterActivity(View v) {
        Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(i);
    }

    public void openMainActivity() {
        Intent i = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(i);
    }
}