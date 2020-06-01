package com.example.healthy_v_7.panels;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.healthy_v_7.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditBioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditBioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SharedPreferences sharedPreferences;
    EditText edit_bio_edit_text;
    Button save_bio_button;
    public EditBioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditBioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditBioFragment newInstance(String param1, String param2) {
        EditBioFragment fragment = new EditBioFragment();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_edit_bio, container, false);
        edit_bio_edit_text = rootView.findViewById(R.id.edit_bio_editText);
        save_bio_button = rootView.findViewById(R.id.save_bio_button);

        sharedPreferences = getContext().getSharedPreferences("saved",Context.MODE_PRIVATE);
        edit_bio_edit_text.setText(sharedPreferences.getString("bio","put bio here"));
        save_bio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String savedBio = edit_bio_edit_text.getText().toString();

                sharedPreferences.edit().putString("bio",savedBio).apply();
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });

        return  rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
