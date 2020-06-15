package com.example.healthy_v_7.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthy_v_7.R;

import java.util.ArrayList;

/**
 * Created by paolotormon on 15 June 2020
 */
public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> bmi;
    private ArrayList<String> date;
    private Context mcontext;
//    SharedPreferences sharedPreferences;

    public HistoryRecyclerViewAdapter(Context mcontext, ArrayList<String> bmi, ArrayList<String> date) {
        this.bmi = bmi;
        this.date = date;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public HistoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.layout_list_item_history, parent, false);
        HistoryRecyclerViewAdapter.ViewHolder holder = new HistoryRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerViewAdapter.ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder called");
        holder.date.setText(date.get(position));
        holder.bmi.setText(bmi.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"onClicL: clicked on: "+ bmi.get(position));
//                sharedPreferences = mcontext.getSharedPreferences("saved",Context.MODE_PRIVATE);
//                sharedPreferences.edit().putInt("profile_pic",mImages.get(position)).apply();
//                Toast.makeText(mcontext, mImageTexts.get(position), Toast.LENGTH_SHORT).show();
//                if(position>=0){
//                    Toast.makeText(mcontext, "profile picture changed!", Toast.LENGTH_SHORT).show();
//                }
//                ((AppCompatActivity)mcontext).getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bmi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView date;
        TextView bmi;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_textView);
            bmi = itemView.findViewById(R.id.bmi_textView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
