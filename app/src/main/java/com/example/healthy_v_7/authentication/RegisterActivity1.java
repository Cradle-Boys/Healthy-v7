package com.example.healthy_v_7.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthy_v_7.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class RegisterActivity1 extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("tag", "destroyed RegisterActivity");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.goRegisterButton);
    }

    public void signInClicked(View view) {
        //create user
        if (!(emailEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals(""))) {

            if(passwordEditText.getText().toString().length()<6){
                Toast.makeText(this, "Password too short! Enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(),
                    passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(firstNameEditText.getText().toString()+" "+lastNameEditText.getText().toString())
                                        .build();
                                //to circumvent race condition
                                SharedPreferences sharedPreferences = getSharedPreferences("saved",Context.MODE_PRIVATE);
                                sharedPreferences.edit().putString("firstName",firstNameEditText.getText().toString()).apply();
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("tag", "User profile updated.");
                                                }
                                            }
                                        });

                                // Sign in success, update UI with the signed-in user's information
                                Log.d("tag", "createUserWithEmail:success");
                                Toast.makeText(RegisterActivity1.this, "Account creation success!", Toast.LENGTH_SHORT).show();
                                finish();
                                /*
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "email verification sent", Toast
                                            .LENGTH_SHORT)
                                                    .show();
                                        }
                                    }
                                });*/

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("tag", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity1.this,
                                        Objects.requireNonNull(task.getException()).toString(),
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "Insert email and password!", Toast.LENGTH_SHORT).show();
        }

    }


}
