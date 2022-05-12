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
import com.warmdelightapp.Model.delivery;
import com.squareup.picasso.Picasso;

public class OrderDetails extends AppCompatActivity {

    String cardOrderName = "",cardOrderuser = "";
    ImageView orderimage;
    TextView ordername,orderprice,orderqty;
    Button editbtn,deletebtn,buy;

    DatabaseReference deleteorderref,deliveryref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        editbtn = findViewById(R.id.orderdetailsedit);
        deletebtn = findViewById(R.id.orderdetailsdelete);

        ordername = findViewById(R.id.orderdetailsName);
        orderprice = findViewById(R.id.orderdetailsprice);
        orderqty = findViewById(R.id.orderdetailsqty);
        orderimage = findViewById(R.id.orderdetailsimage);
        buy = findViewById(R.id.orderdetailsbuy);

        cardOrderName = getIntent().getExtras().get("giftpackname").toString();
        cardOrderuser = getIntent().getExtras().get("username").toString();

        Query orderref = FirebaseDatabase.getInstance().getReference().child("Order").child(cardOrderuser).orderByChild("name").equalTo(cardOrderName);

        orderref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namedb = snapshot.child(cardOrderName).child("name").getValue(String.class);
                String pricedb = snapshot.child(cardOrderName).child("price").getValue(String.class);
                String qtydb = snapshot.child(cardOrderName).child("qty").getValue(String.class);
                String imagedb = snapshot.child(cardOrderName).child("imageurl").getValue(String.class);
                ordername.setText(namedb);
                orderprice.setText(pricedb);
                orderqty.setText(qtydb);
                Picasso.get().load(imagedb).into(orderimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String namedb = snapshot.child(cardOrderName).child("name").getValue(String.class);
                        String pricedb = snapshot.child(cardOrderName).child("price").getValue(String.class);
                        String qtydb = snapshot.child(cardOrderName).child("qty").getValue(String.class);
                        String imagedb = snapshot.child(cardOrderName).child("imageurl").getValue(String.class);
                        ordername.setText(namedb);
                        orderprice.setText(pricedb);
                        orderqty.setText(qtydb);
                        Picasso.get().load(imagedb).into(orderimage);

                        Intent editIntent = new Intent(OrderDetails.this, com.warmdelightapp.OrderDetailsEdit.class);
                        editIntent.putExtra("ordername",namedb);
                        editIntent.putExtra("orderprice",pricedb);
                        editIntent.putExtra("orderqty",qtydb);
                        editIntent.putExtra("orderimageurl",imagedb);
                        editIntent.putExtra("username",cardOrderuser);
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
                deleteorderref = FirebaseDatabase.getInstance().getReference().child("Order").child(cardOrderuser).child(cardOrderName);
                deleteorderref.removeValue();
                Toast.makeText(OrderDetails.this,"Deleted!",Toast.LENGTH_SHORT).show();
                Intent deleteintent = new Intent(OrderDetails.this,Orders.class);
                startActivity(deleteintent);

            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String namedb = snapshot.child(cardOrderName).child("name").getValue(String.class);
                        String pricedb = snapshot.child(cardOrderName).child("price").getValue(String.class);
                        String qtydb = snapshot.child(cardOrderName).child("qty").getValue(String.class);
                        String imagedb = snapshot.child(cardOrderName).child("imageurl").getValue(String.class);
                        ordername.setText(namedb);
                        orderprice.setText(pricedb);
                        orderqty.setText(qtydb);
                        Picasso.get().load(imagedb).into(orderimage);


                        deliveryref = FirebaseDatabase.getInstance().getReference().child("Delivery").child(cardOrderuser).child(cardOrderName);
                        delivery deliverys = new delivery(namedb,pricedb,imagedb,qtydb);
                        deliveryref.setValue(deliverys);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





                deleteorderref = FirebaseDatabase.getInstance().getReference().child("Order").child(cardOrderuser).child(cardOrderName);
                deleteorderref.removeValue();

                Intent buyintent = new Intent(OrderDetails.this, com.warmdelightapp.HomePage.class);
                startActivity(buyintent);
                finish();

            }
        });

    }
}