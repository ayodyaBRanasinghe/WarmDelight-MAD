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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.warmdelightapp.Adapters.CakeAdminAdapter;
import com.warmdelightapp.Model.cake;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CakesAdmin extends AppCompatActivity {

    RecyclerView cakerecyclerView;
    DatabaseReference cakedatabase;
    CakeAdminAdapter cakeadapter;
    ArrayList<cake> cakeList;
    FloatingActionButton addcakebutton;
    EditText searchET;
    String str="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cakes_admin);

        cakerecyclerView = findViewById(R.id.cakelistrecycler);
        cakedatabase = FirebaseDatabase.getInstance().getReference("Cakes");
        cakerecyclerView.setHasFixedSize(true);
        cakerecyclerView.setLayoutManager(new LinearLayoutManager(CakesAdmin.this));

        cakeList = new ArrayList<>();
        cakeadapter = new CakeAdminAdapter(CakesAdmin.this,cakeList);
        cakerecyclerView.setAdapter(cakeadapter);

        searchET = findViewById(R.id.search_cake_text);
        addcakebutton = findViewById(R.id.addcake);



        addcakebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addcakeIntent = new Intent(CakesAdmin.this, CreateCake.class);
                startActivity(addcakeIntent);
            }
        });

        cakerecyclerView = findViewById(R.id.cakelistrecycler);
        cakedatabase = FirebaseDatabase.getInstance().getReference("Cakes");

        cakerecyclerView.setHasFixedSize(true);
        cakerecyclerView.setLayoutManager(new LinearLayoutManager(CakesAdmin.this));

        cakeList = new ArrayList<>();
        cakeadapter = new CakeAdminAdapter(CakesAdmin.this,cakeList);
        cakerecyclerView.setAdapter(cakeadapter);

        cakedatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    cake cakes = datasnapshot.getValue(cake.class);
                    cakeList.add(cakes);
                }
                cakeadapter.notifyDataSetChanged();

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
                    cakerecyclerView = findViewById(R.id.cakelistrecycler);
                    cakedatabase = FirebaseDatabase.getInstance().getReference("Cakes");

                    cakerecyclerView.setHasFixedSize(true);
                    cakerecyclerView.setLayoutManager(new LinearLayoutManager(CakesAdmin.this));

                    cakeList = new ArrayList<>();
                    cakeadapter = new CakeAdminAdapter(CakesAdmin.this,cakeList);
                    cakerecyclerView.setAdapter(cakeadapter);

                    cakedatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot datasnapshot : snapshot.getChildren()){

                                cake giftpacks = datasnapshot.getValue(cake.class);
                                cakeList.add(giftpacks);
                            }
                            cakeadapter.notifyDataSetChanged();

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
        FirebaseRecyclerOptions<cake> options = null;
        if (str.equals("")){
            options = new FirebaseRecyclerOptions.Builder<cake>().setQuery(cakedatabase, cake.class).build();
        }else{
            options = new FirebaseRecyclerOptions.Builder<cake>().setQuery(cakedatabase.orderByChild("name").startAt(str).endAt(str + "\uf8ff"), cake.class).build();
        }
        FirebaseRecyclerAdapter<cake , CakeAdminViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<cake, CakeAdminViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull CakeAdminViewHolder cakeAdminViewHolder, int i, @NonNull cake cakes) {
                cakeAdminViewHolder.name.setText(cakes.getName());
                cakeAdminViewHolder.cost.setText(cakes.getCost());
                cakeAdminViewHolder.price.setText(cakes.getPrice());

                Picasso.get().load(cakes.getImageurl()).placeholder(R.mipmap.ic_launcher).into(cakeAdminViewHolder.cakeImage);

                cakeAdminViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CakesAdmin.this, CakeDetails.class);
                        intent.putExtra("cakename",cakes.getName());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public CakeAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cake_card_admin, parent, false);
                CakeAdminViewHolder viewHolder = new CakeAdminViewHolder(view);
                return viewHolder;
            }
        };
        cakerecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class CakeAdminViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView name,price,cost;
        ImageView cakeImage;


        public CakeAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.admincake_name);
            price = itemView.findViewById(R.id.admincake_price);
            cost = itemView.findViewById(R.id.admincake_cost);
            cakeImage = itemView.findViewById(R.id.admincakeimage);
            cardView = itemView.findViewById(R.id.cakeadmincard);
        }
    }



}