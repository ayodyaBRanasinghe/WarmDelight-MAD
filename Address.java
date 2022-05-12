package com.warmdelightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Address extends AppCompatActivity {

    TextView number,street,suburb,town,district;
    Button add,edit,delete;

    DatabaseReference deleteaddressref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        number = findViewById(R.id.addressNo);
        street = findViewById(R.id.addressStreet);
        suburb = findViewById(R.id.addressSuburb);
        town = findViewById(R.id.addressTown);
        district = findViewById(R.id.addressDistrict);

        add = findViewById(R.id.addressaddbtn);
        edit = findViewById(R.id.addressEditbtn);
        delete = findViewById(R.id.addressdeletebtn);

        Intent OrderIntent = getIntent();
        String UserName1 = OrderIntent.getStringExtra("userName");

        Query addressRef = FirebaseDatabase.getInstance().getReference("Address").child(UserName1);

        if (addressRef == null){
            number.setText("add Address");
            street.setText("add Address");
            suburb.setText("add Address");
            town.setText("add Address");
            district.setText("add Address");

            edit.setEnabled(false);
        }else{

            addressRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String numberaddress = snapshot.child("number").getValue(String.class);
                        String streetaddress = snapshot.child("street").getValue(String.class);
                        String suburbaddress = snapshot.child("suburb").getValue(String.class);
                        String townaddress = snapshot.child("town").getValue(String.class);
                        String districtaddress= snapshot.child("district").getValue(String.class);

                        number.setText(numberaddress);
                        street.setText(streetaddress);
                        suburb.setText(suburbaddress);
                        town.setText(townaddress);
                        district.setText(districtaddress);

                        edit.setEnabled(true);


                    }else{
                        Toast.makeText(Address.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numberaddress = number.getText().toString().trim();
                String streetaddress = street.getText().toString().trim();
                String suburbaddress = suburb.getText().toString().trim();
                String townaddress = town.getText().toString().trim();
                String districtaddress= district.getText().toString().trim();
                Intent editintent = new Intent(Address.this,EditAddress.class);
                editintent.putExtra("userName",UserName1);
                editintent.putExtra("number",numberaddress);
                editintent.putExtra("street",streetaddress);
                editintent.putExtra("suburb",suburbaddress);
                editintent.putExtra("town",townaddress);
                editintent.putExtra("district",districtaddress);
                startActivity(editintent);
            }

        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addintent = new Intent(Address.this,AddAddress.class);
                addintent.putExtra("userName",UserName1);

                startActivity(addintent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteaddressref = FirebaseDatabase.getInstance().getReference("Address").child(UserName1);
                deleteaddressref.removeValue();
                Intent deleteintent = new Intent(Address.this, com.warmdelightapp.HomePage.class);
                startActivity(deleteintent);
            }
        });





    }
}