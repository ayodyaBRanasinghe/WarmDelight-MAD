package com.warmdelightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.warmdelightapp.Model.delivery;
import com.warmdelightapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DeliveryUserAdapter extends RecyclerView.Adapter<DeliveryUserAdapter.DeliveryViewHolder> {



    Context context;


    ArrayList<delivery> deliveryList;
    public DeliveryUserAdapter(Context context, ArrayList<delivery> deliveryList) {
        this.context = context;
        this.deliveryList = deliveryList;
    }
    @NonNull
    @Override
    public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.delivery_card_user, parent,false);
        return new DeliveryViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder holder, int position) {

        delivery deliverys = deliveryList.get(position);


        holder.name.setText(deliverys.getName());
        holder.price.setText(deliverys.getPrice());
        holder.qty.setText(deliverys.getQty());
        Picasso.get().load(deliverys.getImageurl()).into(holder.Image);


    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

    public static class DeliveryViewHolder extends RecyclerView.ViewHolder{


        TextView name,price,qty;
        ImageView Image;
        CardView cardView;


        public DeliveryViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.userOrdername);
            price = itemView.findViewById(R.id.userOrderprice);
            Image = itemView.findViewById(R.id.userOrderimage);
            qty = itemView.findViewById(R.id.userOrderqty);
            cardView = itemView.findViewById(R.id.orderusercard);
        }
    }
}
