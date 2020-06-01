package com.example.healthy_v_7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthy_v_7.helper.CalendarUtil;
import com.example.healthy_v_7.panels.BMIFragment;
import com.example.healthy_v_7.panels.HomeFragment;
import com.example.healthy_v_7.panels.ProfileFragment;
import com.example.healthy_v_7.panels.SettingsFragment;
import com.example.healthy_v_7.panels.ShopFragment;
import com.example.healthy_v_7.panels.SocialFragment;
import com.example.healthy_v_7.web_views.WebViewActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.Timer;
import java.util.TimerTask;


public class HomeActivity extends AppCompatActivity implements SensorEventListener{


    //INSTANCE VARIABLES LISTED IN ORDER OF HOW IT WAS ADDED ONTO THE APPLICATION
    TextView stepsTextView;
    TextView pointsTextView;
    TextView dailyAveTextView;
    SensorManager sensorManager;
    boolean isActive;
    int totalSteps;//total since last device reboot
    int milestoneSteps;
    int todaySteps;

    int points;
    int todayAddedGold;
    int totalGold;
    int initialGold;
    ProgressBar progressBar;
    TextView greetingTextView;

    long todayDate;
    long tomorrowDate;

    //clickables
    Button workoutButton;
    Button dietButton;
    LinearLayout bmiButton;

    //get user data
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    BottomNavigationView bottomNavigationView;

    //just to make invisible when changing fragment
    ConstraintLayout homeConstraintLayout;
    ConstraintLayout homeSuperConstraintLayout;

    //test
    FrameLayout frameLayout;

    //data
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toast.makeText(this, "Steps are reset PER MINUTE for demo purposes. Points are retained.", Toast.LENGTH_LONG).show();

        stepsTextView = findViewById(R.id.stepsTextView);
        pointsTextView = findViewById(R.id.pointsTextView);
        dailyAveTextView = findViewById(R.id.dailyAveTextView);
        progressBar = findViewById(R.id.progressBar);
        greetingTextView = findViewById(R.id.greetingTextView);
        workoutButton = findViewById(R.id.workoutButton);
        dietButton = findViewById(R.id.dietButton);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bmiButton = findViewById(R.id.bmiButton);
//        bottomNavigationView.setBackgroundResource(R.drawable.background_gradient_3);

        //for invisibility and blur
        homeConstraintLayout = findViewById(R.id.homeNoNavConstraintLayout);
        homeSuperConstraintLayout = findViewById(R.id.homeSuperConstraintLayout);


        sharedPreferences = getSharedPreferences("saved",Context.MODE_PRIVATE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        todaySteps = 0;//for testing
        points =0;
        todayAddedGold=0;

        progressBarFiller();
        lookUpFirstName();//for greetingTextView
        setupListeners();//listeners for clickables

        //so that backpressed has >1 BackStackEntryCount when pressing other 4 fragments (HomeFragment is blank)
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment()).commit();


        //test
        frameLayout = findViewById(R.id.bmi_fragment_container);


    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count > 0) {
            getSupportFragmentManager().popBackStack();
            Toast.makeText(this, count + "", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < count; ++i) {
                getSupportFragmentManager().popBackStack();
            }
            setHomeVisible();
            bottomNavigationView.setSelectedItemId(R.id.nav_home);

        } else {
//            closes application
            finishAffinity();
        }
    }

    private void setHomeVisible() {
        Log.i("tag", "Setting home visible");
        homeConstraintLayout.setVisibility(View.VISIBLE);
        homeSuperConstraintLayout.setAlpha(1f);
    }

    private void setHomeInvisible() {
        Log.i("tag", "Setting home invisible");
        homeConstraintLayout.setVisibility(View.INVISIBLE);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_home) {
                setHomeVisible();
            } else {
                setHomeInvisible();
            }

            switch (item.getItemId()) {
                case R.id.nav_home:
//                    selectedFragment=new HomeFragment();DONT PUT kasi may isa na sa onCreate
                    break;
                case R.id.nav_shop:
                    selectedFragment = new ShopFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment();
                    break;
                case R.id.nav_social:
                    selectedFragment = new SocialFragment();
                    break;
                case R.id.nav_settings:
                    selectedFragment = new SettingsFragment();
                    break;

            }

            if (item.getItemId() == R.id.nav_home) {
                int count = getSupportFragmentManager().getBackStackEntryCount();
                for (int i = 0; i < count; ++i) {
                    getSupportFragmentManager().popBackStack();
                }
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment).addToBackStack("previousFragment").commit();
            }

            return true;
        }
    };

    public void setupListeners() {

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        bmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeSuperConstraintLayout.setAlpha(0.3f);
                //fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.bmi_fragment_container, new BMIFragment()).addToBackStack("previousFragment")
                        .commit();

            }
        });

        workoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WebViewActivity.class);
                intent.putExtra("page", "workout");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });

        dietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WebViewActivity.class);
                intent.putExtra("page", "diet");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });
    }

    public void lookUpFirstName() {
        if (user != null) {
            String name = user.getDisplayName();
            if (name == null) {
                Toast.makeText(this, "name is null", Toast.LENGTH_SHORT).show();
                // If the above were null, iterate the provider data
                // and set with the first non null data
                for (UserInfo userInfo : user.getProviderData()) {
                    if (name == null && userInfo.getDisplayName() != null) {
                        name = userInfo.getDisplayName();
                    }
                }
                if(name==null){
                    name=sharedPreferences.getString("firstName","User");
                }
            }
            greetingTextView.setText("Hello, " + name);
        }
    }

    @Override
    protected void onDestroy() {
        Log.i("tag", "destroyed homeActivity");
        super.onDestroy();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }


    //HERE BELOW FOR THE STEP COUNTER

    public void progressBarFiller() {

        progressBar.setMax(100);//sets progress bar from 0 to 100

        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        //FOR NO PHONE WITH NO SENSOR JUST TO TEST
        if (countSensor == null) {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
            progressBar.setProgress(211);
            int totalGold = sharedPreferences.getInt("totalGold",1000);
            sharedPreferences.edit().putInt("totalGold",totalGold).apply();
        }
        //FOR PHONE WITH SENSOR
        else {
            final Timer t = new Timer();
            TimerTask tt = new TimerTask() {

                @Override
                public void run() {

                    progressBar.setProgress(todaySteps);
                    if (todaySteps == 100) {
                        t.cancel();
                    }
                }
            };
            t.schedule(tt, 0, 500);//update time every half second
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        totalSteps = (int) event.values[0];

        if(sharedPreferences.getBoolean("isSameDay",false)){
            tomorrowDate=sharedPreferences.getLong("tomorrow",0);
            milestoneSteps=sharedPreferences.getInt("milestoneSteps",0);
            initialGold =sharedPreferences.getInt("initialGold",0);

            todaySteps=totalSteps-milestoneSteps;
            todayAddedGold=todaySteps;
            totalGold= initialGold +todayAddedGold;
            sharedPreferences.edit().putInt("totalGold",totalGold).apply();

            if(CalendarUtil.getNow()>=tomorrowDate){
                sharedPreferences.edit().putInt("initialGold",totalGold).apply();//initial points for the next day
                sharedPreferences.edit().putBoolean("isSameDay",false).apply();
            }

        }else{
            milestoneSteps=totalSteps;
            tomorrowDate= CalendarUtil.getNowPlusMinute();
            sharedPreferences.edit().putLong("tomorrow",tomorrowDate).apply();
            sharedPreferences.edit().putBoolean("isSameDay",true).apply();
            sharedPreferences.edit().putInt("milestoneSteps",milestoneSteps).apply();
            if(sharedPreferences.getBoolean("firstTime",true)){
                sharedPreferences.edit().putInt("initialGold",0).apply();
                sharedPreferences.edit().putBoolean("firstTime",false).apply();
            }

        }

//        if (!isActive) {
//            milestoneSteps = totalSteps;
//            todaySteps=sharedPreferences.getInt("todaySteps",0);
//            Log.i("milestoneSteps", milestoneSteps + "");//log files for debugging
//            Log.i("totalSteps", totalSteps + "");
//            Log.i("todaySteps", todaySteps + "");
//            isActive = true;
////            todayDate = Util.getToday();
////            tomorrowDate = Util.getTomorrow();
////            Log.i("date today",String.valueOf(todayDate));
////            Log.i("date tomorrow",String.valueOf(tomorrowDate));
//
////            todayDate = Util.getNow();
//            tomorrowDate = Util.getNowPlusHalfMinute();
//            Log.i("date today",String.valueOf(todayDate));
//            Log.i("date tomorrow",String.valueOf(tomorrowDate));
//
//
//        } else {
//            todaySteps = totalSteps - milestoneSteps;
//            Log.i("elsemilestoneSteps", milestoneSteps + "");
//            Log.i("elsetotalSteps", totalSteps + "");
//            Log.i("elsetodaySteps", todaySteps + "");
//            Log.i("updating now",String.valueOf(Util.getNow()));
//            if(Util.getNow()>=tomorrowDate){
//                isActive=false;
//                Log.i("NEW DAY","CALLED");
//                sharedPreferences.edit().putInt("todaySteps",0).apply();
//            }
//            sharedPreferences.edit().putInt("todaySteps",todaySteps).apply();
//        }

        stepsTextView.setText(String.valueOf(todaySteps));
        pointsTextView.setText(String.valueOf(totalGold));
        dailyAveTextView.setText("Daily average: " + todaySteps);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        isActive = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
//            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        isActive=false;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}