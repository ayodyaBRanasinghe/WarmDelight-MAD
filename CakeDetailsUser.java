package com.warmdelightapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.warmdelightapp.Model.Order;
import com.squareup.picasso.Picasso;

public class CakeDetailsUser extends AppCompatActivity {
    String cardcakeNameuser = "",user="";
    ImageView cakeimageuser;
    TextView nameuser,priceuser;
    Button buybutton;
    DatabaseReference orderref;
    EditText qtycake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake_details_user);



        nameuser = findViewById(R.id.cakedetailsNameuser);
        priceuser = findViewById(R.id.cakedetailspriceuser);
        cakeimageuser = findViewById(R.id.cakedetailsimageuser);
        buybutton = findViewById(R.id.cakebuy);
        qtycake = findViewById(R.id.qty);

        cardcakeNameuser = getIntent().getExtras().get("cakename").toString();
        user = getIntent().getExtras().get("username").toString();
        Query cakeref = FirebaseDatabase.getInstance().getReference().child("Cakes").orderByChild("name").equalTo(cardcakeNameuser);

        cakeref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namedb = snapshot.child(cardcakeNameuser).child("name").getValue(String.class);
                String pricedb = snapshot.child(cardcakeNameuser).child("price").getValue(String.class);
                String descriptiondb = snapshot.child(cardcakeNameuser).child("description").getValue(String.class);
                String imagedb = snapshot.child(cardcakeNameuser).child("imageurl").getValue(String.class);
                nameuser.setText(namedb);
                priceuser.setText(pricedb);
                Picasso.get().load(imagedb).into(cakeimageuser);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        buybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cakeref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String namedb = snapshot.child(cardcakeNameuser).child("name").getValue(String.class);
                        String pricedb = snapshot.child(cardcakeNameuser).child("price").getValue(String.class);
                        String imagedb = snapshot.child(cardcakeNameuser).child("imageurl").getValue(String.class);

                        String quantity = qtycake.getText().toString().trim();

                        int priceforone = Integer.parseInt(pricedb);
                        int qty = Integer.parseInt(quantity);

                        int total = priceforone * qty;

                        String Total = String.valueOf(total);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("Order");

                        Order order = new Order(namedb,Total,imagedb,quantity);
                        ref.child(user).child(namedb).setValue(order);


                        Toast.makeText(CakeDetailsUser.this,"Added to Orders",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}