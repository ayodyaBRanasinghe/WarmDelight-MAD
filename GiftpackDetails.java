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

public class GiftpackDetails extends AppCompatActivity {

    String cardgiftpackName = "";
    ImageView giftpackimage;
    TextView name,price,cost;
    Button editbtn,deletebtn;

    DatabaseReference deletegiftpackref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftpack_details);

        editbtn = findViewById(R.id.giftpackdetailsedit);
        deletebtn = findViewById(R.id.giftpackdetailsdelete);

        name = findViewById(R.id.giftpackdetailsName);
        price = findViewById(R.id.giftpackdetailsprice);
        cost = findViewById(R.id.giftpackdetailscost);
        giftpackimage = findViewById(R.id.giftpackdetailsimage);



        cardgiftpackName = getIntent().getExtras().get("giftpackname").toString();
        Query giftpackref = FirebaseDatabase.getInstance().getReference().child("Giftpacks").orderByChild("name").equalTo(cardgiftpackName);


        giftpackref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namedb = snapshot.child(cardgiftpackName).child("name").getValue(String.class);
                String pricedb = snapshot.child(cardgiftpackName).child("price").getValue(String.class);
                String costdb = snapshot.child(cardgiftpackName).child("cost").getValue(String.class);
                String imagedb = snapshot.child(cardgiftpackName).child("imageurl").getValue(String.class);
                name.setText(namedb);
                price.setText(pricedb);
                cost.setText(costdb);
                Picasso.get().load(imagedb).into(giftpackimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                giftpackref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String namedb = snapshot.child(cardgiftpackName).child("name").getValue(String.class);
                        String pricedb = snapshot.child(cardgiftpackName).child("price").getValue(String.class);
                        String costdb = snapshot.child(cardgiftpackName).child("cost").getValue(String.class);
                        String imagedb = snapshot.child(cardgiftpackName).child("imageurl").getValue(String.class);
                        name.setText(namedb);
                        price.setText(pricedb);
                        cost.setText(costdb);
                        Picasso.get().load(imagedb).into(giftpackimage);

                        Intent editIntent = new Intent(GiftpackDetails.this, com.warmdelightapp.GiftpackDetailsEdit.class);
                        editIntent.putExtra("giftpackname",namedb);
                        editIntent.putExtra("giftpackprice",pricedb);
                        editIntent.putExtra("giftpackcost",costdb);
                        editIntent.putExtra("giftpackimageurl",imagedb);
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
                deletegiftpackref = FirebaseDatabase.getInstance().getReference().child("Giftpacks").child(cardgiftpackName);
                deletegiftpackref.removeValue();
                Toast.makeText(GiftpackDetails.this,"Deleted!",Toast.LENGTH_SHORT).show();
                Intent deleteintent = new Intent(GiftpackDetails.this, com.warmdelightapp.GiftpacksAdmin.class);
                startActivity(deleteintent);

            }
        });



    }
}