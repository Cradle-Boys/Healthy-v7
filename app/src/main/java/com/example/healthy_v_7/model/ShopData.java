package com.example.healthy_v_7.model;

import com.example.healthy_v_7.R;

import java.util.ArrayList;

/**
 * Created by paolotormon on 1 June 2020
 */
public class ShopData {

    public static ArrayList<String> mImageTexts = new ArrayList<String>() {
        {
            add("1 Shopee coin 10,000");
            add("Tyler - 1,000");
            add("Karen - 1,000");
            add("Ashley - 1,000");
            add("Primo - 1,000");
            add("Dan - 1,000");
            add("Ada - 1,000");
            add("Jorge - 1,000");
            add("Obi - 1,000");
            add("Sunray Theme 2000");
            add("Cream Theme 2000");
        }
    };
    public static ArrayList<Integer> mImages = new ArrayList<Integer>() {
        {
            add(R.drawable.ic_shopee_small);
            add(R.drawable.prof_icon_2);
            add(R.drawable.prof_icon_3);
            add(R.drawable.prof_icon_4);
            add(R.drawable.prof_icon_5);
            add(R.drawable.prof_icon_6);
            add(R.drawable.prof_icon_7);
            add(R.drawable.prof_icon_8);
            add(R.drawable.prof_icon_9);
            add(R.drawable.background_gradient_3);
            add(R.drawable.background_gradient_4_white);

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
