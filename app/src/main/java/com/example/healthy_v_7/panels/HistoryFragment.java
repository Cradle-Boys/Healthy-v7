package com.example.healthy_v_7.panels;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.healthy_v_7.R;
import com.example.healthy_v_7.helper.HistoryRecyclerViewAdapter;
import com.example.healthy_v_7.helper.ObjectSerializer;
import com.example.healthy_v_7.helper.ProfilePicRecyclerViewAdapter;
import com.example.healthy_v_7.model.ProfilePicsData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    Button exit_pic_button;
    SharedPreferences sharedPreferences;
    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = rootview.findViewById(R.id.history_recycler_view);
        sharedPreferences = getActivity().getSharedPreferences("saved",Context.MODE_PRIVATE);
//        String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

        ArrayList<String> bmi=new ArrayList<>();
        ArrayList<String> date=new ArrayList<>();
//        try {
//            bmi= (ArrayList<String>) ObjectSerializer.deserialize(
//                    sharedPreferences.getString("bmi",
//                            ObjectSerializer.serialize(new ArrayList<String>())));
//            date= (ArrayList<String>) ObjectSerializer.deserialize(
//                    sharedPreferences.getString("date",
//                            ObjectSerializer.serialize(new ArrayList<String>())));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        bmi.add("24.5");
//        bmi.add("26.5");
//        bmi.add("37.5");
//
//
//        date.add(timeStamp);
//        date.add(timeStamp);
//        date.add(timeStamp);

//        try {
//            sharedPreferences.edit().putString("bmi",ObjectSerializer.serialize(bmi)).apply();
//            Log.i("bmi", ObjectSerializer.serialize(bmi));
//            sharedPreferences.edit().putString("date",ObjectSerializer.serialize(date)).apply();
//            Log.i("date", ObjectSerializer.serialize(date));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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

        HistoryRecyclerViewAdapter adapter = new HistoryRecyclerViewAdapter(getContext(),bmi,date);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Log.i("HISTORY", "initrecyclerview: init recyclerview");
        exit_pic_button = rootview.findViewById(R.id.exit_pic_button);

        exit_pic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return rootview;
    }
}
