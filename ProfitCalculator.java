package com.warmdelightapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfitCalculator extends AppCompatActivity {

    EditText cost,sellprice,qty;
    TextView profit;
    Button calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_calculator);

        cost = findViewById(R.id.costcalc);
        sellprice = findViewById(R.id.sellingcalc);
        profit = findViewById(R.id.profittotalcalc);
        qty = findViewById(R.id.qtycalc);

        calc = findViewById(R.id.calcbtn);



        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String costcal = cost.getText().toString().trim();
                String sellcal = sellprice.getText().toString();
                String qtycal = qty.getText().toString().trim();

                int c = Integer.parseInt(costcal);
                int s = Integer.parseInt(sellcal);
                int q = Integer.parseInt(qtycal);

                int t = (s - c) * q;

                String profitcal = String.valueOf(t);

                profit.setText(profitcal);


            }
        });

    }
}