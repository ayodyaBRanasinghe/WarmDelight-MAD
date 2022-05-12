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
import com.warmdelightapp.Model.giftpack;

public class GiftpackDetailsEdit extends AppCompatActivity {

    TextView name;
    EditText price,cost;
    Button update;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference giftpackref = database.getReference("Giftpacks");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftpack_details_edit);

        String detailsgiftpackName = getIntent().getExtras().get("giftpackname").toString();
        String detailsgiftpackprice = getIntent().getExtras().get("giftpackprice").toString();
        String detailsgiftpackCost = getIntent().getExtras().get("giftpackcost").toString();
        String detailsgiftpackimage = getIntent().getExtras().get("giftpackimageurl").toString();

        name = findViewById(R.id.updategiftpackName);
        price = findViewById(R.id.updategiftpackPrice);
        cost = findViewById(R.id.updategiftpackCost);
        update = findViewById(R.id.updategiftpackdata);

        name.setText(detailsgiftpackName);
        price.setText(detailsgiftpackprice);
        cost.setText(detailsgiftpackCost);




        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameupdateedittext = detailsgiftpackName;
                String imageupdateedittext = detailsgiftpackimage;
                String priceupdatededittext = price.getText().toString().trim();
                String costupdatededittext = cost.getText().toString().trim();
                giftpack giftpacks = new giftpack(nameupdateedittext,priceupdatededittext,costupdatededittext,imageupdateedittext);
                giftpackref.child(detailsgiftpackName).setValue(giftpacks);
                Toast.makeText(GiftpackDetailsEdit.this,"updated!",Toast.LENGTH_SHORT).show();

            }
        });




    }
}