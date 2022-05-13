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
import com.warmdelightapp.Adapters.CakeUserAdapter;
import com.warmdelightapp.Model.cake;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Cakes extends AppCompatActivity {

    RecyclerView cakerecyclerView;
    DatabaseReference cakedatabaseuser;
    CakeUserAdapter cakeadapteruser;
    ArrayList<cake> cakeListuser;
    EditText searchET;
    String str="";

    String username = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cakes);


        cakerecyclerView = findViewById(R.id.cakelistrecycleruser);
        cakedatabaseuser = FirebaseDatabase.getInstance().getReference("Cakes");


        Intent cakeIntent = getIntent();
        String UserName1 = cakeIntent.getStringExtra("userName");


        username = UserName1;

        cakerecyclerView.setHasFixedSize(true);
        cakerecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cakeListuser = new ArrayList<>();
        cakeadapteruser = new CakeUserAdapter(this,cakeListuser);
        cakerecyclerView.setAdapter(cakeadapteruser);

        searchET = findViewById(R.id.search_cake_text_user);



        cakedatabaseuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    cake cakes = datasnapshot.getValue(cake.class);
                    cakeListuser.add(cakes);
                }
                cakeadapteruser.notifyDataSetChanged();

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
                    cakerecyclerView = findViewById(R.id.cakelistrecycleruser);
                    cakedatabaseuser = FirebaseDatabase.getInstance().getReference("Cakes");

                    searchET = findViewById(R.id.search_cake_text_user);

                    cakerecyclerView.setHasFixedSize(true);
                    cakerecyclerView.setLayoutManager(new LinearLayoutManager(Cakes.this));

                    cakeListuser = new ArrayList<>();
                    cakeadapteruser = new CakeUserAdapter(Cakes.this,cakeListuser);
                    cakerecyclerView.setAdapter(cakeadapteruser);

                    cakedatabaseuser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot datasnapshot : snapshot.getChildren()){

                                cake cakes = datasnapshot.getValue(cake.class);
                                cakeListuser.add(cakes);
                            }
                            cakeadapteruser.notifyDataSetChanged();

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
            options = new FirebaseRecyclerOptions.Builder<cake>().setQuery(cakedatabaseuser, cake.class).build();
        }else{
            options = new FirebaseRecyclerOptions.Builder<cake>().setQuery(cakedatabaseuser.orderByChild("name").startAt(str).endAt(str + "\uf8ff"), cake.class).build();
        }
        FirebaseRecyclerAdapter<cake, CakeUserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<cake, CakeUserViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull CakeUserViewHolder cakeUserViewHolder, int i, @NonNull cake cakes) {
                cakeUserViewHolder.name.setText(cakes.getName());
                cakeUserViewHolder.price.setText(cakes.getPrice());

                Picasso.get().load(cakes.getImageurl()).placeholder(R.mipmap.ic_launcher).into(cakeUserViewHolder.cakeImage);

                cakeUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Cakes.this, CakeDetailsUser.class);
                        intent.putExtra("cakename",cakes.getName());
                        intent.putExtra("username",username);

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public CakeUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cake_card_user, parent, false);
                CakeUserViewHolder viewHolder = new CakeUserViewHolder(view);
                return viewHolder;
            }
        };
        cakerecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class CakeUserViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView name,price;
        ImageView cakeImage;


        public CakeUserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.usercake_name);
            price = itemView.findViewById(R.id.usercake_price);
            cakeImage = itemView.findViewById(R.id.usercakeimage);
            cardView = itemView.findViewById(R.id.cakeusercard);
        }
    }
}