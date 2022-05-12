package com.warmdelightapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.warmdelightapp.Model.Order;

public class OrderDetailsEdit extends AppCompatActivity {

    TextView name,priceorder;
    EditText qty;
    Button update;
    String str="";
    String newPriceupdated;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference orderref = database.getReference("Order");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_edit);

        String detailsorderName = getIntent().getExtras().get("ordername").toString();
        String detailsorderprice = getIntent().getExtras().get("orderprice").toString();
        String detailsorderqty = getIntent().getExtras().get("orderqty").toString();
        String detailsorderimage = getIntent().getExtras().get("orderimageurl").toString();
        String user = getIntent().getExtras().get("username").toString();

        name = findViewById(R.id.updateorderName);
        priceorder = findViewById(R.id.updateorderPrice);
        qty = findViewById(R.id.updateorderqty);
        update = findViewById(R.id.updateorderdata);


        name.setText(detailsorderName);
        priceorder.setText(detailsorderprice);
        qty.setText(detailsorderqty);

        int firstTotal = Integer.parseInt(detailsorderprice);
        int firstQty = Integer.parseInt(detailsorderqty);

        int unitPrice = firstTotal/firstQty;

        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(qty.getText().toString().equals("")){
                    Toast.makeText(OrderDetailsEdit.this,"Enter Quantity",Toast.LENGTH_SHORT).show();
                }else{

                    str = s.toString();
                    if(str.equals("")){
                        Toast.makeText(OrderDetailsEdit.this,"Enter Quantity",Toast.LENGTH_SHORT).show();
                    }else{
                        String qtyupdatededittext = qty.getText().toString().trim();

                        int newQty = Integer.parseInt(qtyupdatededittext);

                        int newPrice = unitPrice * newQty;

                        newPriceupdated = String.valueOf(newPrice);
                    }

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameupdateedittext = detailsorderName;
                String imageupdateedittext = detailsorderimage;
                String priceupdatededittext = newPriceupdated;
                String qtyupdatededittext = qty.getText().toString().trim();

                Order orders = new Order(nameupdateedittext,priceupdatededittext,imageupdateedittext,qtyupdatededittext);
                orderref.child(user).child(detailsorderName).setValue(orders);
                Toast.makeText(OrderDetailsEdit.this,"updated!",Toast.LENGTH_SHORT).show();
                Intent updateintent  = new Intent (OrderDetailsEdit.this,Orders.class);
                startActivity(updateintent);
                finish();
            }
        });
    }
}