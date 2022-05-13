package com.warmdelightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.squareup.picasso.Picasso;

public class CakeDetails extends AppCompatActivity {

    String cardcakeName = "";
    ImageView cakeimage;
    TextView name,price,cost;
    Button editbtn,deletebtn;

    DatabaseReference deletecakeref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake_details);

        editbtn = findViewById(R.id.cakedetailsedit);
        deletebtn = findViewById(R.id.cakedetailsdelete);

        name = findViewById(R.id.cakedetailsName);
        price = findViewById(R.id.cakedetailsprice);
        cost = findViewById(R.id.cakedetailscost);
        cakeimage = findViewById(R.id.cakedetailsimage);



        cardcakeName = getIntent().getExtras().get("cakename").toString();
        Query cakeref = FirebaseDatabase.getInstance().getReference().child("Cakes").orderByChild("name").equalTo(cardcakeName);


        cakeref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namedb = snapshot.child(cardcakeName).child("name").getValue(String.class);
                String pricedb = snapshot.child(cardcakeName).child("price").getValue(String.class);
                String costdb = snapshot.child(cardcakeName).child("cost").getValue(String.class);
                String imagedb = snapshot.child(cardcakeName).child("imageurl").getValue(String.class);
                name.setText(namedb);
                price.setText(pricedb);
                cost.setText(costdb);
                Picasso.get().load(imagedb).into(cakeimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cakeref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String namedb = snapshot.child(cardcakeName).child("name").getValue(String.class);
                        String pricedb = snapshot.child(cardcakeName).child("price").getValue(String.class);
                        String costdb = snapshot.child(cardcakeName).child("cost").getValue(String.class);
                        String imagedb = snapshot.child(cardcakeName).child("imageurl").getValue(String.class);
                        name.setText(namedb);
                        price.setText(pricedb);
                        cost.setText(costdb);
                        Picasso.get().load(imagedb).into(cakeimage);

                        Intent editIntent = new Intent(CakeDetails.this, CakeDetailsEdit.class);
                        editIntent.putExtra("cakename",namedb);
                        editIntent.putExtra("cakeprice",pricedb);
                        editIntent.putExtra("cakecost",costdb);
                        editIntent.putExtra("cakeimageurl",imagedb);
                        startActivity(editIntent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletecakeref = FirebaseDatabase.getInstance().getReference().child("Cakes").child(cardcakeName);
                deletecakeref.removeValue();
                Toast.makeText(CakeDetails.this,"Deleted!",Toast.LENGTH_SHORT).show();
                Intent deleteintent = new Intent(CakeDetails.this, CakesAdmin.class);
                startActivity(deleteintent);

            }
        });



    }
}