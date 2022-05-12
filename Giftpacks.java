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
import com.warmdelightapp.Adapters.GiftpackUserAdapter;
import com.warmdelightapp.Model.giftpack;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Giftpacks extends AppCompatActivity {

    RecyclerView giftpackrecyclerView;
    DatabaseReference giftpackdatabaseuser;
    GiftpackUserAdapter giftpackadapteruser;
    ArrayList<giftpack> giftpackListuser;
    EditText searchET;
    String str="";

    String username = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftpacks);


        giftpackrecyclerView = findViewById(R.id.giftpacklistrecycleruser);
        giftpackdatabaseuser = FirebaseDatabase.getInstance().getReference("Giftpacks");


        Intent giftpackIntent = getIntent();
        String UserName1 = giftpackIntent.getStringExtra("userName");


        username = UserName1;

        giftpackrecyclerView.setHasFixedSize(true);
        giftpackrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        giftpackListuser = new ArrayList<>();
        giftpackadapteruser = new GiftpackUserAdapter(this,giftpackListuser);
        giftpackrecyclerView.setAdapter(giftpackadapteruser);

        searchET = findViewById(R.id.search_giftpack_text_user);



        giftpackdatabaseuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    giftpack giftpacks = datasnapshot.getValue(giftpack.class);
                    giftpackListuser.add(giftpacks);
                }
                giftpackadapteruser.notifyDataSetChanged();

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
                    giftpackrecyclerView = findViewById(R.id.giftpacklistrecycleruser);
                    giftpackdatabaseuser = FirebaseDatabase.getInstance().getReference("Giftpacks");

                    searchET = findViewById(R.id.search_giftpack_text_user);

                    giftpackrecyclerView.setHasFixedSize(true);
                    giftpackrecyclerView.setLayoutManager(new LinearLayoutManager(Giftpacks.this));

                    giftpackListuser = new ArrayList<>();
                    giftpackadapteruser = new GiftpackUserAdapter(Giftpacks.this,giftpackListuser);
                    giftpackrecyclerView.setAdapter(giftpackadapteruser);

                    giftpackdatabaseuser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot datasnapshot : snapshot.getChildren()){

                                giftpack giftpacks = datasnapshot.getValue(giftpack.class);
                                giftpackListuser.add(giftpacks);
                            }
                            giftpackadapteruser.notifyDataSetChanged();

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
            options = new FirebaseRecyclerOptions.Builder<giftpack>().setQuery(giftpackdatabaseuser,giftpack.class).build();
        }else{
            options = new FirebaseRecyclerOptions.Builder<giftpack>().setQuery(giftpackdatabaseuser.orderByChild("name").startAt(str).endAt(str + "\uf8ff"),giftpack.class).build();
        }
        FirebaseRecyclerAdapter<giftpack, GiftpackUserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<giftpack, GiftpackUserViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull GiftpackUserViewHolder giftpackUserViewHolder, int i, @NonNull giftpack giftpacks) {
                giftpackUserViewHolder.name.setText(giftpacks.getName());
                giftpackUserViewHolder.price.setText(giftpacks.getPrice());

                Picasso.get().load(giftpacks.getImageurl()).placeholder(R.mipmap.ic_launcher).into(giftpackUserViewHolder.giftpackImage);

                giftpackUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Giftpacks.this, com.warmdelightapp.GiftpackDetailsUser.class);
                        intent.putExtra("giftpackname",giftpacks.getName());
                        intent.putExtra("username",username);

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public GiftpackUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.giftpack_card_user, parent, false);
                GiftpackUserViewHolder viewHolder = new GiftpackUserViewHolder(view);
                return viewHolder;
            }
        };
        giftpackrecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class GiftpackUserViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView name,price;
        ImageView giftpackImage;


        public GiftpackUserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.usergiftpack_name);
            price = itemView.findViewById(R.id.usergiftpack_price);
            giftpackImage = itemView.findViewById(R.id.usergiftpackimage);
            cardView = itemView.findViewById(R.id.giftpackusercard);
        }
    }
}