package com.example.healthy_v_7.panels;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.healthy_v_7.R;

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
            bmiString = "Your BMI is " + bmi;
            bmiTextBox.setText(bmiString);

        } catch(NumberFormatException npe){
            bmiTextBox.setText("The input must be a number!");
        }

    }
}
