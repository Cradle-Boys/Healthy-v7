package com.example.healthy_v_7.panels;

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
import com.example.healthy_v_7.helper.AchievementsRecyclerViewAdapter;
import com.example.healthy_v_7.helper.ProfilePicRecyclerViewAdapter;
import com.example.healthy_v_7.model.ProfilePicsData;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfilePicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfilePicFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "EditProfilePicture";
    RecyclerView recyclerView;
    Button exit_pic_button;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProfilePicFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EditProfilePicFragment newInstance(String param1, String param2) {
        EditProfilePicFragment fragment = new EditProfilePicFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_edit_profile_picture, container, false);
        checkPossible();
        recyclerView = rootView.findViewById(R.id.profile_pic_recycler_view);
        exit_pic_button = rootView.findViewById(R.id.exit_pic_button);

        Log.i(TAG, "initrecyclerview: init recyclerview");
        ProfilePicRecyclerViewAdapter adapter = new ProfilePicRecyclerViewAdapter(getContext(), ProfilePicsData.mImageTexts,
                ProfilePicsData.mImages);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        exit_pic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return rootView;
    }

//    public boolean indexExists(final List list, final int index) {
//        return index >= 0 && index < list.size();
//    }

    public void checkPossible(){
        int counter=4;
        while(counter<ProfilePicsData.mImageStatus.size()){
            if(ProfilePicsData.mImageStatus.get(counter)&&!ProfilePicsData.mImageTexts.contains(ProfilePicsData.mAllImageTexts.get(counter))){
                ProfilePicsData.mImages.add(ProfilePicsData.mallImages.get(counter));
                ProfilePicsData.mImageTexts.add(ProfilePicsData.mAllImageTexts.get(counter));
            }
            counter++;
        }
    }

}
