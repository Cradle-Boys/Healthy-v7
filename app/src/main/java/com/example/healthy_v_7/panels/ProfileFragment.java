package com.example.healthy_v_7.panels;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthy_v_7.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView nameTextView;
    TextView bioTextView;
    ImageView profileImageView;
    Button achievementsButton;
    BottomNavigationView bottomNavigationView;

    TextView weightTextView;
    TextView heightTextView;

    SharedPreferences sharedPreferences;
    SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    public void setListeners(){

        achievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.edit_profilepic_fragment_container, new AchievementsFragment())
                        .addToBackStack("previousFragment")
                        .commit();
            }
        });


        bioTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.edit_bio_fragment_container, new EditBioFragment())
                        .addToBackStack("previousFragment")
                        .commit();
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.edit_profilepic_fragment_container, new EditProfilePicFragment())
                        .addToBackStack("previousFragment")
                        .commit();
            }
        });

        //VERY VERY GOOD
        prefListener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs,
                                                          String key) {
                        if (key.equals("bio")) {
                            bioTextView.setText(sharedPreferences.getString("bio","bio here"));
                            weightTextView.setText("hi");
                        }else if(key.equals("profile_pic")){
                            profileImageView.setImageResource(sharedPreferences.getInt("profile_pic",R.drawable.prof_icon_0));
                        }
//                        else if(key.equals("currentHeight")){
//                            heightTextView.setText(sharedPreferences.getString("currentHeight","Height"));
//                        }else if(key.equals("currentWeight")){
//                            String weight=sharedPreferences.getString("currentWeight","Weight");
//                            weightTextView.setText(weight);
////                            Log.i("preflistener","weight "+weight);
//                        }
                    }
                };
        sharedPreferences.registerOnSharedPreferenceChangeListener(prefListener);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        nameTextView = rootView.findViewById(R.id.nameTextView);
        bioTextView = rootView.findViewById(R.id.bioTextView);
        profileImageView = rootView.findViewById(R.id.profilePic_imageView);
        achievementsButton = rootView.findViewById(R.id.achievementsButton);
        weightTextView = rootView.findViewById(R.id.weight_text_view_dynamic);
        heightTextView = rootView.findViewById(R.id.heightTextView);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);

        sharedPreferences = getActivity().getSharedPreferences("saved",Context.MODE_PRIVATE);
        bioTextView.setText(sharedPreferences.getString("bio","put bio here"));
        profileImageView.setImageResource(sharedPreferences.getInt("profile_pic",R.drawable.prof_icon_default));
        sharedPreferences.edit().putString("bio",bioTextView.getText().toString()).apply();
        weightTextView.setText((sharedPreferences.getString("currentWeight","Weight"))+" kg");
        heightTextView.setText((sharedPreferences.getString("currentHeight","Height"))+" m");


        setListeners();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String name = user.getDisplayName();
            if (name == null) {
                Toast.makeText(getContext(), "name is null", Toast.LENGTH_SHORT).show();
                // If the above were null, iterate the provider data
                // and set with the first non null data
                for (UserInfo userInfo : user.getProviderData()) {
                    if (name == null && userInfo.getDisplayName() != null) {
                        name = userInfo.getDisplayName();
                    }
                }
            }
            nameTextView.setText(name);
        }
        return rootView;
    }

}
