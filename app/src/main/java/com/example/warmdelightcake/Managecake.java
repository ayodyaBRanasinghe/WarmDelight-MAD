package com.example.warmdelightcake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class Managecake extends AppCompatActivity {

    RecyclerView recyclerView;
    ManagecakeApadter Managecake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managecake);


        recyclerView = (RecyclerView) findViewById(R.id.recview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Addcake> options =
                new FirebaseRecyclerOptions.Builder<Addcake>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("CakeDetails"), Addcake.class)
                .build();

        Managecake = new ManagecakeApadter(options);
        recyclerView.setAdapter(Managecake);

        @Override
            protected void onStart(){
            super.onStart();
            Managecake.startListening();

            protected void onStop(){
            super.onStop();
            Managecake.stopListening();
        }


    }
}}