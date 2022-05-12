package com.warmdelightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.warmdelightapp.Model.address;

public class AddAddress extends AppCompatActivity {

    EditText number,street,suburb,town,district;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        number = findViewById(R.id.ETaddressNo);
        street = findViewById(R.id.ETaddressStreet);
        suburb = findViewById(R.id.ETaddressSuburb);
        town = findViewById(R.id.ETaddressTown);
        district = findViewById(R.id.ETaddressDistrict);
        add = findViewById(R.id.addressAddbtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberAdd = number.getText().toString().trim();
                String streetAdd = street.getText().toString().trim();
                String suburbAdd = suburb.getText().toString().trim();
                String townAdd = town.getText().toString().trim();
                String districtAdd = district.getText().toString().trim();

                Intent homepageIntent = getIntent();
                String UserName = homepageIntent.getStringExtra("userName");

                FirebaseDatabase addressDatabase = FirebaseDatabase.getInstance();
                DatabaseReference ref = addressDatabase.getReference("Address");

                address addresses = new address(numberAdd,streetAdd,suburbAdd,townAdd,districtAdd);
                ref.child(UserName).setValue(addresses);

                Intent backintent = new Intent(AddAddress.this, com.warmdelightapp.Address.class);
                startActivity(backintent);
            }
        });


    }
}