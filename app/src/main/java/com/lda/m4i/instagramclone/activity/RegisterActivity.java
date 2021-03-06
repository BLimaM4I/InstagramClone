package com.lda.m4i.instagramclone.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.lda.m4i.instagramclone.R;
import com.lda.m4i.instagramclone.config.FirebaseConfiguration;
import com.lda.m4i.instagramclone.helper.Base64Custom;
import com.lda.m4i.instagramclone.helper.FirebaseUserAccess;
import com.lda.m4i.instagramclone.model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUserName, etEmail, etPassword;
    private Button btnRegister;
    private ProgressBar pbRegister;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initialize components
        initializeComponents();

        //Click on register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRegisterUser(v);
            }
        });
    }

    private void initializeComponents() {
        etUserName = findViewById(R.id.activity_register_et_userName);
        etEmail = findViewById(R.id.activity_register_et_email);
        etEmail.requestFocus();
        etPassword = findViewById(R.id.activity_register_et_password);
        btnRegister = findViewById(R.id.activity_register_btn_register);
        pbRegister = findViewById(R.id.activity_register_progressBar);
    }

    public void saveUserInFirebase(final User user) {

        pbRegister.setVisibility(View.VISIBLE);
        fbAuth = FirebaseConfiguration.getFirebaseAuth();
        fbAuth.createUserWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(
                this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            pbRegister.setVisibility(View.GONE);

                            Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                            FirebaseUserAccess.updateUserName(user.getName());
                            try {
                                String userIdIdentification = Base64Custom.encodeBase64(user.getEmail());
                                user.setUserId(userIdIdentification);
                                user.save();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                //go to Main Activity
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                        } else {
                            pbRegister.setVisibility(View.GONE);
                            String exception = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                exception = "Please introduce a stronger password";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                exception = "Please insert a valid e-mail";
                            } catch (FirebaseAuthUserCollisionException e) {
                                exception = "This account is already registered";
                            } catch (Exception e) {
                                exception = "Error in registering: " + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(RegisterActivity.this, exception, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void validateRegisterUser(View v) {

        //recover the texts for the fields
        String textName = etUserName.getText().toString();
        String textEmail = etEmail.getText().toString();
        String textPassword = etPassword.getText().toString();

        if (!textName.isEmpty()) {
            if (!textEmail.isEmpty()) {
                if (!textPassword.isEmpty()) {

                    User user = new User();

                    user.setName(textName);
                    user.setEmail(textEmail);
                    user.setPassword(textPassword);

                    saveUserInFirebase(user);

                } else {
                    Toast.makeText(RegisterActivity.this, "Please fill the password field", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Please fill the e-mail field", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "Please fill the name field", Toast.LENGTH_SHORT).show();
        }
    }
}