package com.warmdelightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    Button cake,flower,delivery,orders,address,logout;
    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        cake = findViewById(R.id.cakeuserhomepage);
        flower = findViewById(R.id.floweruserhomepage);
        delivery = findViewById(R.id.deliveryuserhomepage);
        orders = findViewById(R.id.ordersuserhomepage);
        address = findViewById(R.id.addressuserhomepage);
        logout = findViewById(R.id.userlogout);
        name = findViewById(R.id.name);

        Intent homepageIntent = getIntent();

        String UserName = homepageIntent.getStringExtra("userName");
        String Email = homepageIntent.getStringExtra("email");
        String MobileNumber = homepageIntent.getStringExtra("mobileNumber");

        name.setText(UserName);


        flower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent giftpackIntent = new Intent(HomePage.this, Cakes.class);
                giftpackIntent.putExtra("userName",UserName);
                startActivity(giftpackIntent);

            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent = new Intent(HomePage.this, com.warmdelightapp.Delivery.class);
                deliveryIntent.putExtra("userName",UserName);
                startActivity(deliveryIntent);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent flowerIntent = new Intent(HomePage.this,Orders.class);
                flowerIntent.putExtra("userName",UserName);
                startActivity(flowerIntent);

            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent flowerIntent = new Intent(HomePage.this,Address.class);
                flowerIntent.putExtra("userName",UserName);
                startActivity(flowerIntent);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutIntent = new Intent(HomePage.this,Login.class);
                startActivity(logoutIntent);

            }
        });



    }
}