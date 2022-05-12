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

public class GiftpackDetailsUser extends AppCompatActivity {
    String cardgiftpackNameuser = "",user="";
    ImageView giftpackimageuser;
    TextView nameuser,priceuser;
    Button buybutton;
    DatabaseReference orderref;
    EditText qtygiftpack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftpack_details_user);



        nameuser = findViewById(R.id.giftpackdetailsNameuser);
        priceuser = findViewById(R.id.giftpackdetailspriceuser);
        giftpackimageuser = findViewById(R.id.giftpackdetailsimageuser);
        buybutton = findViewById(R.id.giftpackbuy);
        qtygiftpack = findViewById(R.id.qty);

        cardgiftpackNameuser = getIntent().getExtras().get("giftpackname").toString();
        user = getIntent().getExtras().get("username").toString();
        Query giftpackref = FirebaseDatabase.getInstance().getReference().child("Giftpacks").orderByChild("name").equalTo(cardgiftpackNameuser);

        giftpackref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namedb = snapshot.child(cardgiftpackNameuser).child("name").getValue(String.class);
                String pricedb = snapshot.child(cardgiftpackNameuser).child("price").getValue(String.class);
                String descriptiondb = snapshot.child(cardgiftpackNameuser).child("description").getValue(String.class);
                String imagedb = snapshot.child(cardgiftpackNameuser).child("imageurl").getValue(String.class);
                nameuser.setText(namedb);
                priceuser.setText(pricedb);
                Picasso.get().load(imagedb).into(giftpackimageuser);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        buybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                giftpackref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String namedb = snapshot.child(cardgiftpackNameuser).child("name").getValue(String.class);
                        String pricedb = snapshot.child(cardgiftpackNameuser).child("price").getValue(String.class);
                        String imagedb = snapshot.child(cardgiftpackNameuser).child("imageurl").getValue(String.class);

                        String quantity = qtygiftpack.getText().toString().trim();

                        int priceforone = Integer.parseInt(pricedb);
                        int qty = Integer.parseInt(quantity);

                        int total = priceforone * qty;

                        String Total = String.valueOf(total);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("Order");

                        Order order = new Order(namedb,Total,imagedb,quantity);
                        ref.child(user).child(namedb).setValue(order);


                        Toast.makeText(GiftpackDetailsUser.this,"Added to Orders",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}