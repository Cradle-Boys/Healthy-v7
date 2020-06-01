package com.example.healthy_v_7.model;

import com.example.healthy_v_7.R;

import java.util.ArrayList;

/**
 * Created by paolotormon on 27 May 2020
 */
public class ProfilePicsData {

    public static ArrayList<String> mImageTexts = new ArrayList<String>() {
        {
            add("Zeroth");
            add("First");
//            add("Second");
//            add("Third");
        }
    };
    public static ArrayList<Integer> mImages = new ArrayList<Integer>() {
        {
            add(R.drawable.prof_icon_0);
            add(R.drawable.prof_icon_1);
//            add(R.drawable.prof_icon_2);
//            add(R.drawable.prof_icon_3);

        }
    };
    public static ArrayList<Boolean> mImageStatus = new ArrayList<Boolean>() {
        {
            add(true);
            add(true);
            add(false);
            add(false);
            add(false);

        }
    };
    public static ArrayList<Integer> mallImages = new ArrayList<Integer>() {
        {
            add(R.drawable.prof_icon_0);
            add(R.drawable.prof_icon_1);
            add(R.drawable.prof_icon_2);
            add(R.drawable.prof_icon_3);
            add(R.drawable.prof_icon_4);
            add(R.drawable.prof_icon_5);

        }
    };

    public static ArrayList<String> mAllImageTexts = new ArrayList<String>() {
        {
            add("Zeroth");
            add("First");
            add("Second");
            add("Third");
            add("Fourth");
            add("Fifth");
        }
    };
}
