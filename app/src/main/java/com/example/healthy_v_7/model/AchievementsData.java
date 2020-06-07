package com.example.healthy_v_7.model;

import com.example.healthy_v_7.R;

import java.util.ArrayList;

/**
 * Created by paolotormon on 07 June 2020
 */
public class AchievementsData {
    public static ArrayList<String> mImageTexts = new ArrayList<String>() {
        {
            add("Burn 100 calories in 1 day");
            add("Burn 300 calories in 1 day");
            add("Burn 600 calories in 1 day");
            add("Burn 1000 calories in 1 day");
            add("Accumulate 1000 gold");
            add("Accumulate 5000 gold");
            add("Accumulate 30,000 gold");
            add("Accumulate 100,000 gold");
        }
    };

    public static ArrayList<Integer> mImages = new ArrayList<Integer>() {
        {
            add(R.drawable.ic_ach_baby_footprint);
            add(R.drawable.ic_ach_run);
            add(R.drawable.ic_ach_fast_run);
            add(R.drawable.ic_ach_rocket);
            add(R.drawable.ic_ach_coin);
            add(R.drawable.ic_ach_cash);
            add(R.drawable.ic_ach_moneybag);
            add(R.drawable.ic_ach_treasure);
        }
    };
}
