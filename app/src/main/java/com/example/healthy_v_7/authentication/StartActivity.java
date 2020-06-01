package com.example.healthy_v_7.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthy_v_7.HomeActivity;
import com.example.healthy_v_7.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class StartActivity extends AppCompatActivity {
    //INSTANCE VARIABLES LISTED IN ORDER OF HOW IT WAS ADDED ONTO THE APPLICATION

    EditText inputEmail;
    EditText inputPassword;
    Button loginButton;
    Button registerButton;
    private FirebaseAuth mAuth;
    ConstraintLayout constraintLayout;
    GoogleSignInClient mGoogleSignInClient;

    //  RC can be any value since it's going to get changed
    private static int RC_SIGN_IN = 12312;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.goRegisterButton);
        inputEmail = findViewById(R.id.emailLoginEditText);
        inputPassword = findViewById(R.id.passwordLoginEditText);
        constraintLayout = findViewById(R.id.constraintLayout);

        setButtonListeners();
        setGoogleSignIn();

        // Initialize Firebase Authorization
        mAuth = FirebaseAuth.getInstance();

    }

    public void setButtonListeners() {

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(inputEmail.getText().toString().equals("") || inputPassword.getText().toString().equals(""))) {
                    mAuth.signInWithEmailAndPassword(inputEmail.getText().toString(),
                            inputPassword.getText().toString())
                            .addOnCompleteListener(StartActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("tag", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("tag", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getBaseContext(),
                                                Objects.requireNonNull(task.getException()).toString(),
                                                Toast.LENGTH_SHORT).show();
//                            updateUI(null);

                                    }
                                }
                            });
                }else{
                    Toast.makeText(StartActivity.this, "Enter email and password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.googleRegisterButton);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.googleRegisterButton:
                        signIn();
                        break;
                    // ...
                }
            }
        });
    }

    //BELOW IS IMPORTED ACTIVITY
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no     need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(@org.jetbrains.annotations.NotNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.d("tag", "firebaseAuthWithGoogle:" + account.getId());
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("tag", "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "signInWithCredential:success");
                            Snackbar.make(constraintLayout, "Authentication Succeeded.", Snackbar.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("tag", "signInWithCredential:failure", task.getException());
                            Snackbar.make(constraintLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
//         Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        }
    }

    @Override
    protected void onResume() {
        inputEmail.setText("");
        inputPassword.setText("");
        super.onResume();
    }
}
