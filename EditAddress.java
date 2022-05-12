package com.warmdelightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.warmdelightapp.Model.address;

public class EditAddress extends AppCompatActivity {

    EditText number,street,suburb,town,district;
    Button edit;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference addressref = database.getReference("Address");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        number = findViewById(R.id.ETaddressupdateNo);
        street = findViewById(R.id.ETaddressupdateStreet);
        suburb = findViewById(R.id.ETaddressupdateSuburb);
        town = findViewById(R.id.ETaddressupdateTown);
        district = findViewById(R.id.ETaddressupdateDistrict);

        edit = findViewById(R.id.addressupdatebtn);

        String name = getIntent().getExtras().get("userName").toString();
        String numberaddress = getIntent().getExtras().get("number").toString();
        String streetaddress = getIntent().getExtras().get("street").toString();
        String suburbaddress = getIntent().getExtras().get("suburb").toString();
        String townaddress = getIntent().getExtras().get("town").toString();
        String districtaddress = getIntent().getExtras().get("district").toString();

        number.setText(numberaddress);
        street.setText(streetaddress);
        suburb.setText(suburbaddress);
        town.setText(townaddress);
        district.setText(districtaddress);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numberaddressET = number.getText().toString().trim();
                String streetaddressET = street.getText().toString().trim();
                String suburbaddressET = suburb.getText().toString().trim();
                String townaddressET = town.getText().toString().trim();
                String districtaddressET = district.getText().toString().trim();

                address addresses = new address(numberaddressET,streetaddressET,suburbaddressET,townaddressET,districtaddressET);
                addressref.child(name).setValue(addresses);
                Toast.makeText(EditAddress.this,"updated!",Toast.LENGTH_SHORT).show();
                Intent backintent = new Intent(EditAddress.this, com.warmdelightapp.HomePage.class);
                startActivity(backintent);
                finish();

            }
        });
    }
}