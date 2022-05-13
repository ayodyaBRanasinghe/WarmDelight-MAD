package com.warmdelightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.warmdelightapp.Model.cake;
import com.warmdelightapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CakeUserAdapter extends RecyclerView.Adapter<CakeUserAdapter.CakeViewHolder> {


    Context context;


    ArrayList<cake> cakeList;


    public CakeUserAdapter(Context context, ArrayList<cake> cakeList) {
        this.context = context;
        this.cakeList = cakeList;


    }

    @NonNull
    @Override
    public CakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cake_card_user, parent,false);
        return new CakeViewHolder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull CakeViewHolder holder, int position) {

        cake cakes = cakeList.get(position);


        holder.name.setText(cakes.getName());
        holder.price.setText(cakes.getPrice());
        Picasso.get().load(cakes.getImageurl()).into(holder.cakeImage);

    }

    @Override
    public int getItemCount() {
        return cakeList.size();
    }

    public static class CakeViewHolder extends RecyclerView.ViewHolder{


        TextView name,price;
        ImageView cakeImage;


        public CakeViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.usercake_name);
            price = itemView.findViewById(R.id.usercake_price);
            cakeImage = itemView.findViewById(R.id.usercakeimage);
        }
    }


}
