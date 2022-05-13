package com.warmdelightapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.warmdelightapp.Model.cake;

public class CakeDetailsEdit extends AppCompatActivity {

    TextView name;
    EditText price,cost;
    Button update;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference cakeref = database.getReference("Cakes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake_details_edit);

        String detailscakeName = getIntent().getExtras().get("cakename").toString();
        String detailscakeprice = getIntent().getExtras().get("cakeprice").toString();
        String detailscakeCost = getIntent().getExtras().get("cakecost").toString();
        String detailscakeimage = getIntent().getExtras().get("cakeimageurl").toString();

        name = findViewById(R.id.updatecakeName);
        price = findViewById(R.id.updatecakePrice);
        cost = findViewById(R.id.updatecakeCost);
        update = findViewById(R.id.updatecakedata);

        name.setText(detailscakeName);
        price.setText(detailscakeprice);
        cost.setText(detailscakeCost);




        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameupdateedittext = detailscakeName;
                String imageupdateedittext = detailscakeimage;
                String priceupdatededittext = price.getText().toString().trim();
                String costupdatededittext = cost.getText().toString().trim();
                cake cakes = new cake(nameupdateedittext,priceupdatededittext,costupdatededittext,imageupdateedittext);
                cakeref.child(detailscakeName).setValue(cakes);
                Toast.makeText(CakeDetailsEdit.this,"updated!",Toast.LENGTH_SHORT).show();

            }
        });




    }
}