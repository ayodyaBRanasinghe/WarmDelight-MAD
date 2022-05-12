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
import com.warmdelightapp.Adapters.GiftpackAdminAdapter;
import com.warmdelightapp.Model.giftpack;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GiftpacksAdmin extends AppCompatActivity {

    RecyclerView giftpackrecyclerView;
    DatabaseReference giftpackdatabase;
    GiftpackAdminAdapter giftpackadapter;
    ArrayList<giftpack> giftpackList;
    FloatingActionButton addgiftpackbutton;
    EditText searchET;
    String str="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftpacks_admin);

        giftpackrecyclerView = findViewById(R.id.giftpacklistrecycler);
        giftpackdatabase = FirebaseDatabase.getInstance().getReference("Giftpacks");
        giftpackrecyclerView.setHasFixedSize(true);
        giftpackrecyclerView.setLayoutManager(new LinearLayoutManager(GiftpacksAdmin.this));

        giftpackList = new ArrayList<>();
        giftpackadapter = new GiftpackAdminAdapter(GiftpacksAdmin.this,giftpackList);
        giftpackrecyclerView.setAdapter(giftpackadapter);

        searchET = findViewById(R.id.search_giftpack_text);
        addgiftpackbutton = findViewById(R.id.addgiftpack);



        addgiftpackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addgiftpackIntent = new Intent(GiftpacksAdmin.this, com.warmdelightapp.CreateGiftpack.class);
                startActivity(addgiftpackIntent);
            }
        });

        giftpackrecyclerView = findViewById(R.id.giftpacklistrecycler);
        giftpackdatabase = FirebaseDatabase.getInstance().getReference("Giftpacks");

        giftpackrecyclerView.setHasFixedSize(true);
        giftpackrecyclerView.setLayoutManager(new LinearLayoutManager(GiftpacksAdmin.this));

        giftpackList = new ArrayList<>();
        giftpackadapter = new GiftpackAdminAdapter(GiftpacksAdmin.this,giftpackList);
        giftpackrecyclerView.setAdapter(giftpackadapter);

        giftpackdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    giftpack giftpacks = datasnapshot.getValue(giftpack.class);
                    giftpackList.add(giftpacks);
                }
                giftpackadapter.notifyDataSetChanged();

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
                    giftpackrecyclerView = findViewById(R.id.giftpacklistrecycler);
                    giftpackdatabase = FirebaseDatabase.getInstance().getReference("Giftpacks");

                    giftpackrecyclerView.setHasFixedSize(true);
                    giftpackrecyclerView.setLayoutManager(new LinearLayoutManager(GiftpacksAdmin.this));

                    giftpackList = new ArrayList<>();
                    giftpackadapter = new GiftpackAdminAdapter(GiftpacksAdmin.this,giftpackList);
                    giftpackrecyclerView.setAdapter(giftpackadapter);

                    giftpackdatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot datasnapshot : snapshot.getChildren()){

                                giftpack giftpacks = datasnapshot.getValue(giftpack.class);
                                giftpackList.add(giftpacks);
                            }
                            giftpackadapter.notifyDataSetChanged();

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
        FirebaseRecyclerOptions<giftpack> options = null;
        if (str.equals("")){
            options = new FirebaseRecyclerOptions.Builder<giftpack>().setQuery(giftpackdatabase,giftpack.class).build();
        }else{
            options = new FirebaseRecyclerOptions.Builder<giftpack>().setQuery(giftpackdatabase.orderByChild("name").startAt(str).endAt(str + "\uf8ff"),giftpack.class).build();
        }
        FirebaseRecyclerAdapter<giftpack, GiftpackAdminViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<giftpack, GiftpackAdminViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull GiftpackAdminViewHolder giftpackAdminViewHolder, int i, @NonNull giftpack giftpacks) {
                giftpackAdminViewHolder.name.setText(giftpacks.getName());
                giftpackAdminViewHolder.cost.setText(giftpacks.getCost());
                giftpackAdminViewHolder.price.setText(giftpacks.getPrice());

                Picasso.get().load(giftpacks.getImageurl()).placeholder(R.mipmap.ic_launcher).into(giftpackAdminViewHolder.giftpackImage);

                giftpackAdminViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GiftpacksAdmin.this, GiftpackDetails.class);
                        intent.putExtra("giftpackname",giftpacks.getName());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public GiftpackAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.giftpack_card_admin, parent, false);
                GiftpackAdminViewHolder viewHolder = new GiftpackAdminViewHolder(view);
                return viewHolder;
            }
        };
        giftpackrecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class GiftpackAdminViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView name,price,cost;
        ImageView giftpackImage;


        public GiftpackAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.admingiftpack_name);
            price = itemView.findViewById(R.id.admingiftpack_price);
            cost = itemView.findViewById(R.id.admingiftpack_cost);
            giftpackImage = itemView.findViewById(R.id.admingiftpackimage);
            cardView = itemView.findViewById(R.id.giftpackadmincard);
        }
    }



}