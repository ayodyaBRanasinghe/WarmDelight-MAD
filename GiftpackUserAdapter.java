package com.warmdelightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.warmdelightapp.Model.giftpack;
import com.warmdelightapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GiftpackUserAdapter extends RecyclerView.Adapter<GiftpackUserAdapter.GiftpackViewHolder> {


    Context context;


    ArrayList<giftpack> giftpackList;


    public GiftpackUserAdapter(Context context, ArrayList<giftpack> giftpackList) {
        this.context = context;
        this.giftpackList = giftpackList;


    }

    @NonNull
    @Override
    public GiftpackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.giftpack_card_user, parent,false);
        return new GiftpackViewHolder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull GiftpackViewHolder holder, int position) {

        giftpack giftpacks = giftpackList.get(position);


        holder.name.setText(giftpacks.getName());
        holder.price.setText(giftpacks.getPrice());
        Picasso.get().load(giftpacks.getImageurl()).into(holder.giftpackImage);

    }

    @Override
    public int getItemCount() {
        return giftpackList.size();
    }

    public static class GiftpackViewHolder extends RecyclerView.ViewHolder{


        TextView name,price;
        ImageView giftpackImage;


        public GiftpackViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.usergiftpack_name);
            price = itemView.findViewById(R.id.usergiftpack_price);
            giftpackImage = itemView.findViewById(R.id.usergiftpackimage);
        }
    }


}
