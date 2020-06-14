package com.example.healthy_v_7.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.healthy_v_7.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    Button changePasswordButton;
    EditText oldPassEditText;
    EditText newPassEditText;
    EditText confirmNewPassEditText;
    TextView confirmationTextView;

    CountDownTimer countDownTimer;

    int timerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        changePasswordButton=findViewById(R.id.changePasswordButton);
        newPassEditText=findViewById(R.id.newPassEditText);
        confirmNewPassEditText=findViewById(R.id.confirmNewPassEditText);
        confirmationTextView=findViewById(R.id.confirmationTextView);
        oldPassEditText=findViewById(R.id.oldPassEditText);
        timerTime= 1503;//1.503 seconds

        final String oldPass;

        final SharedPreferences sharedPreferences = getSharedPreferences("saved", Context.MODE_PRIVATE);
        oldPass=sharedPreferences.getString("oldPassword","default");

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldPassInput = oldPassEditText.getText().toString();
                final String newpass = newPassEditText.getText().toString();
                String confirmNewPass=confirmNewPassEditText.getText().toString();

                if(!(oldPassInput.equals(oldPass))){
                    confirmationTextView.setTextColor(0XFFB53737);
                    confirmationTextView.setBackgroundColor(0XFFFFFFFF);
                    confirmationTextView.setText("The old password is not correct.");
                    confirmationTextView.animate().alpha(1).setDuration(1000).setInterpolator(new DecelerateInterpolator()).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            confirmationTextView.animate().alpha(0).setDuration(1000).setInterpolator(new AccelerateInterpolator()).start();
                        }
                    }).start();
                    return;
                }
                if(newpass.equals("")){
                    confirmationTextView.setTextColor(0XFFB53737);
                    confirmationTextView.setBackgroundColor(0XFFFFFFFF);
                    confirmationTextView.setText("Please enter your new password.");
                    confirmationTextView.animate().alpha(1).setDuration(1000).setInterpolator(new DecelerateInterpolator()).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            confirmationTextView.animate().alpha(0).setDuration(1000).setInterpolator(new AccelerateInterpolator()).start();
                        }
                    }).start();
                }
                else if(!newpass.equals(confirmNewPass)){
                    confirmationTextView.setTextColor(0XFFB53737);
                    confirmationTextView.setBackgroundColor(0XFFFFFFFF);
                    confirmationTextView.setText("The passwords you entered were not the same. Please try again");
                    confirmationTextView.animate().alpha(1).setDuration(1000).setInterpolator(new DecelerateInterpolator()).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            confirmationTextView.animate().alpha(0).setDuration(2000).setInterpolator(new AccelerateInterpolator()).start();
                        }
                    }).start();
                }else if((confirmNewPass.length()<6)){
                    confirmationTextView.setTextColor(0XFFB53737);
                    confirmationTextView.setBackgroundColor(0XFFFFFFFF);
                    confirmationTextView.setText("Please enter more than 5 characters");
                    confirmationTextView.animate().alpha(1).setDuration(1000).setInterpolator(new DecelerateInterpolator()).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            confirmationTextView.animate().alpha(0).setDuration(2000).setInterpolator(new AccelerateInterpolator()).start();
                        }
                    }).start();
                }else{
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                    user.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TAG", "User password updated.");
                                confirmationTextView.setTextColor(0XFF03DAC5);
                                confirmationTextView.setBackgroundColor(0XD4EB876E);
                                confirmationTextView.setText("You successfully changed your password!");
                                sharedPreferences.edit().putString("oldPass",newpass).apply();
                                confirmationTextView.animate().alpha(1).setDuration(1000).setInterpolator(new DecelerateInterpolator()).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        confirmationTextView.animate().alpha(0).setDuration(2300).setInterpolator(new AccelerateInterpolator()).start();
                                    }
                                }).start();
                                setTimer();
                            }else{
                                confirmationTextView.setTextColor(0XFFB53737);
                                confirmationTextView.setBackgroundColor(0XFFFFFFFF);

                                Log.w("tag", "createUserWithEmail:failure", task.getException());
                                confirmationTextView.setText("Database error. Try again later. Please sign in again");
                                confirmationTextView.animate().alpha(1).setDuration(200).setInterpolator(new DecelerateInterpolator()).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        confirmationTextView.animate().alpha(0).setDuration(3000).setInterpolator(new AccelerateInterpolator()).start();
                                    }
                                }).start();
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    public void setTimer(){
        countDownTimer = new CountDownTimer(timerTime, 500) {
            @Override public void onTick(long millisUntilFinished) {

            }

            @Override public void onFinish() {
                finish();
            }
        }.start();
    }
}
