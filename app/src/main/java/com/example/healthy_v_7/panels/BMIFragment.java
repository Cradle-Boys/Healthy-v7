package com.example.healthy_v_7.panels;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.healthy_v_7.R;
import com.example.healthy_v_7.helper.ObjectSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BMIFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BMIFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private TextView weight;
    private TextView height;
    private double bmi;
    private Button calculateButton;
    private TextView bmiTextBox;
    private String bmiString;
    private Button saveButton;
    SharedPreferences sharedPreferences;

    public BMIFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BMIFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BMIFragment newInstance(String param1, String param2) {
        BMIFragment fragment = new BMIFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bmi, container, false);
        weight = rootView.findViewById(R.id.weightInput);
        height = rootView.findViewById(R.id.heightInput);
        bmiTextBox = rootView.findViewById(R.id.bmiResult);
        calculateButton = rootView.findViewById(R.id.calculateButton);
        saveButton = rootView.findViewById(R.id.saveButton);
        sharedPreferences = getActivity().getSharedPreferences("saved",Context.MODE_PRIVATE);

        final String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());





        saveButton.setOnClickListener(new View.OnClickListener() {
            String saveweight;
            String saveheight;

            @Override
            public void onClick(View v) {
                if(bmiTextBox.getText().toString().equals("")||bmiTextBox.getText().toString().equals("Calculate BMI first!")||bmiTextBox.getText().toString().equals("Data Saved")){
                    bmiTextBox.setText("Calculate BMI first!");
                    return;
                }
                Log.i("save","saved h and w");
                ArrayList<String> bmi=new ArrayList<>();
                ArrayList<String> date=new ArrayList<>();
                try {
                    bmi= (ArrayList<String>) ObjectSerializer.deserialize(
                            sharedPreferences.getString("bmi",
                                    ObjectSerializer.serialize(new ArrayList<String>())));
                    date= (ArrayList<String>) ObjectSerializer.deserialize(
                            sharedPreferences.getString("date",
                                    ObjectSerializer.serialize(new ArrayList<String>())));

                } catch (IOException e) {
                    e.printStackTrace();
                }



                saveweight=weight.getText().toString();
                saveheight=height.getText().toString();
                sharedPreferences.edit().putString("currentWeight",saveweight).commit();
                sharedPreferences.edit().putString("currentHeight",saveheight).commit();
                bmi.add(bmiTextBox.getText().toString());
                date.add(timeStamp);

                try {
                    sharedPreferences.edit().putString("bmi",ObjectSerializer.serialize(bmi)).apply();
                    Log.i("bmi", ObjectSerializer.serialize(bmi));
                    sharedPreferences.edit().putString("date",ObjectSerializer.serialize(date)).apply();
                    Log.i("date", ObjectSerializer.serialize(date));
                    bmiTextBox.setText("Data Saved");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                calculateBMI();
            }
        });

        container.setAlpha(1f);
        return rootView;
    }

    public void calculateBMI(){
        String weightString;
        double weightValue;
        String heightString;
        double heightValue;
        try {
            weightString = weight.getText().toString();
            weightValue = Double.parseDouble(weightString);
            heightString = height.getText().toString();
            heightValue = Double.parseDouble(heightString);

            bmi = weightValue / (heightValue * heightValue);
            if(bmi<18.5){
                bmiTextBox.setTextColor(0x880022FF);
            }else if(bmi<25){
                bmiTextBox.setTextColor(0xFF00FF00);
            }else if(bmi<30){
                bmiTextBox.setTextColor(0xFFFFFF00);
            }else if(bmi<35){
                bmiTextBox.setTextColor(0xFFFFA500);
            }else{
                bmiTextBox.setTextColor(0xFFFF0000);
            }
            bmiString = "BMI: \n"+String.format("%.2f", bmi) ;
            bmiTextBox.setText(bmiString);

        } catch(NumberFormatException npe){
            bmiTextBox.setText("The input must be a number!");
        }

    }
}
