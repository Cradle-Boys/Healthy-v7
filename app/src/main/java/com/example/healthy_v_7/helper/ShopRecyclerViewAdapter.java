package com.example.healthy_v_7.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthy_v_7.R;

import java.util.ArrayList;

public class ShopRecyclerViewAdapter extends RecyclerView.Adapter<ShopRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImageTexts;
    private ArrayList<Integer> mImages;
    private Context mcontext;
    SharedPreferences sharedPreferences;
    int totalGold;

    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }

    public ShopRecyclerViewAdapter(Context mcontext, ArrayList<String> mImageTexts, ArrayList<Integer> mImages) {
        this.mImageTexts = mImageTexts;
        this.mImages = mImages;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item_shop, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder called");
        holder.image.setImageResource(mImages.get(position));
        holder.imageName.setText(mImageTexts.get(position));
        sharedPreferences = mcontext.getSharedPreferences("saved",Context.MODE_PRIVATE);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG,"onClicL: clicked on: "+ mImageTexts.get(position));
                if(position==0){
                    Toast.makeText(mcontext, "Not available at the moment", Toast.LENGTH_SHORT).show();
                }else if(position==9||position==10){
                    totalGold=sharedPreferences.getInt("totalGold",0);
                    if(totalGold>=2000){
                        sharedPreferences.edit().putInt("totalGold",totalGold-2000).apply();//for instant UI update
                        if(position==9){
                            sharedPreferences.edit().putBoolean("SunrayTheme",true).apply();
                            sharedPreferences.edit().putBoolean("CreamTheme",false).apply();
                        }else{
                            sharedPreferences.edit().putBoolean("CreamTheme",true).apply();
                            sharedPreferences.edit().putBoolean("SunrayTheme",false).apply();
                        }
                        sharedPreferences.edit().putBoolean("removeMoreGold",true).apply();
                        mImages.remove(position);
                        mImageTexts.remove(position);
                        notifyDataSetChanged();
                    }else{
                        Toast.makeText(mcontext, "Not enough gold!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    totalGold=sharedPreferences.getInt("totalGold",0);
                    if(totalGold>=1000){
                        sharedPreferences.edit().putInt("totalGold",totalGold-1000).apply();//for instant UI update
                        sharedPreferences.edit().putBoolean("removeGold",true).apply();
                        sharedPreferences.edit().putInt("profile_pic",mImages.get(position)).apply();
                        Toast.makeText(mcontext, "You bought "+mImageTexts.get(position), Toast.LENGTH_SHORT).show();
                        mImages.remove(position);
                        mImageTexts.remove(position);
                        notifyDataSetChanged();

                    }else{
                        Toast.makeText(mcontext, "Not enough gold!", Toast.LENGTH_SHORT).show();
                    }

//                ((AppCompatActivity)mcontext).getSupportFragmentManager().popBackStack();
                }
                
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageTexts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }


}
