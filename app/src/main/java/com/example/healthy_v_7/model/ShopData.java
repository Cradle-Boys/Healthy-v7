package com.example.healthy_v_7.model;

import com.example.healthy_v_7.R;

import java.util.ArrayList;

/**
 * Created by paolotormon on 1 June 2020
 */
public class ShopData {

    public static ArrayList<String> mImageTexts = new ArrayList<String>() {
        {
            add("Tyler - 1,000 gold");
            add("Karen - 1,000 gold");
            add("Ashley - 1,000 gold");
            add("Primo - 1,000 gold");
            add("Dan - 1,000 gold");
            add("Ada - 1,000 gold");
            add("Jorge - 1,000 gold");
            add("Obi - 1,000 gold");
        }
    };
    public static ArrayList<Integer> mImages = new ArrayList<Integer>() {
        {
            add(R.drawable.prof_icon_2);
            add(R.drawable.prof_icon_3);
            add(R.drawable.prof_icon_4);
            add(R.drawable.prof_icon_5);
            add(R.drawable.prof_icon_6);
            add(R.drawable.prof_icon_7);
            add(R.drawable.prof_icon_8);
            add(R.drawable.prof_icon_9);

        }
    };
    public static ArrayList<Boolean> mImageStatus = new ArrayList<Boolean>() {
        {
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(true);
            add(false);

        }
    };
    public static ArrayList<Integer> mallImages = new ArrayList<Integer>() {
        {
            add(R.drawable.prof_icon_4);
            add(R.drawable.prof_icon_5);
            add(R.drawable.prof_icon_6);
            add(R.drawable.prof_icon_7);

        }
    };

    public static ArrayList<String> mAllImageTexts = new ArrayList<String>() {
        {
            add("Azeroth - 1,000 pts");
            add("Primo - 1,000 pts");
            add("Dan - 1,000 pts");
            add("Ada - 1,000 pts");
        }
    };
}
