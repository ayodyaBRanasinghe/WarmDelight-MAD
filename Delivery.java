package com.warmdelightapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.warmdelightapp.Adapters.DeliveryUserAdapter;
import com.warmdelightapp.Model.delivery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Delivery extends AppCompatActivity {

    RecyclerView deliveryrecyclerView;
    DatabaseReference deliverydatabaseuser;
    DeliveryUserAdapter deliveryadapteruser;
    ArrayList<delivery> deliveryListuser;
    EditText searchET;
    String str="";

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        Intent deliveryIntent = getIntent();
        String UserName1 = deliveryIntent.getStringExtra("userName");
        username = UserName1;

        deliveryrecyclerView = findViewById(R.id.deliverylistrecycleruser);
        deliverydatabaseuser = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Delivery").child(username);

        deliveryrecyclerView.setHasFixedSize(true);
        deliveryrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        deliveryListuser = new ArrayList<>();
        deliveryadapteruser = new DeliveryUserAdapter(this,deliveryListuser);
        deliveryrecyclerView.setAdapter(deliveryadapteruser);

        searchET = findViewById(R.id.search_delivery_text_user);

        deliverydatabaseuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    delivery deliverys = datasnapshot.getValue(delivery.class);
                    deliveryListuser.add(deliverys);
                }
                deliveryadapteruser.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(searchET.getText().toString().equals("")){
                    deliveryrecyclerView = findViewById(R.id.deliverylistrecycleruser);
                    deliverydatabaseuser = FirebaseDatabase.getInstance().getReference("Delivery").child(username);

                    searchET = findViewById(R.id.search_delivery_text_user);

                    deliveryrecyclerView.setHasFixedSize(true);
                    deliveryrecyclerView.setLayoutManager(new LinearLayoutManager(Delivery.this));

                    deliveryListuser = new ArrayList<>();
                    deliveryadapteruser = new DeliveryUserAdapter(Delivery.this,deliveryListuser);
                    deliveryrecyclerView.setAdapter(deliveryadapteruser);

                    deliverydatabaseuser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot datasnapshot : snapshot.getChildren()){

                                delivery deliverys = datasnapshot.getValue(delivery.class);
                                deliveryListuser.add(deliverys);
                            }
                            deliveryadapteruser.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else {
                    str = s.toString();
                    onStart();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<delivery> options = null;
        if (str.equals("")){
            options = new FirebaseRecyclerOptions.Builder<delivery>().setQuery(deliverydatabaseuser, delivery.class).build();
        }else{
            options = new FirebaseRecyclerOptions.Builder<delivery>().setQuery(deliverydatabaseuser.orderByChild("name").startAt(str).endAt(str + "\uf8ff"),delivery.class).build();
        }
        FirebaseRecyclerAdapter<delivery, DeliveryUserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<delivery, DeliveryUserViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull DeliveryUserViewHolder DeliveryUserViewHolder, int i, @NonNull delivery deliverys) {
                DeliveryUserViewHolder.name.setText(deliverys.getName());
                DeliveryUserViewHolder.price.setText(deliverys.getPrice());
                DeliveryUserViewHolder.qty.setText(deliverys.getQty());

                Picasso.get().load(deliverys.getImageurl()).placeholder(R.mipmap.ic_launcher).into(DeliveryUserViewHolder.flowerImage);

            }

            @NonNull
            @Override
            public DeliveryUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_card_user, parent, false);
                DeliveryUserViewHolder viewHolder = new DeliveryUserViewHolder(view);
                return viewHolder;
            }
        };
        deliveryrecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class DeliveryUserViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView name,price,qty;
        ImageView flowerImage;


        public DeliveryUserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userOrdername);
            price = itemView.findViewById(R.id.userOrderprice);
            qty = itemView.findViewById(R.id.userOrderqty);
            flowerImage = itemView.findViewById(R.id.userOrderimage);
            cardView = itemView.findViewById(R.id.orderusercard);
        }
    }

}