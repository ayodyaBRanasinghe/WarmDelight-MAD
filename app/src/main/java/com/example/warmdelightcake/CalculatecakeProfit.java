package com.example.warmdelightcake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CalculatecakeProfit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculatecake_profit);
    }

//add cake price to calculate the sum

    public void ADD(View v){
        EditText ed1 = (EditText) findViewById(R.id.value1);
        EditText ed2 = (EditText) findViewById(R.id.value2);
        EditText ed3 = (EditText) findViewById(R.id.value3);

        int n1 = Integer.parseInt(ed1.getText().toString());
        int n2 = Integer.parseInt(ed2.getText().toString());
        int result = n1+n2;

        ed3.setText("Total Value"+result);
    }

//subtract the cake price to calculate the profit

    public void SUBTRACT(View v){
        EditText ed1 = (EditText) findViewById(R.id.value1);
        EditText ed2 = (EditText) findViewById(R.id.value2);
        EditText ed3 = (EditText) findViewById(R.id.value3);

        int n1 = Integer.parseInt(ed1.getText().toString());
        int n2 = Integer.parseInt(ed2.getText().toString());
        int result = n1-n2;

        ed3.setText("SUBTRACT Value"+result);
    }

//multiply the cake price to calculate the profit

    public void MULTIPLY(View v){
        EditText ed1 = (EditText) findViewById(R.id.value1);
        EditText ed2 = (EditText) findViewById(R.id.value2);
        EditText ed3 = (EditText) findViewById(R.id.value3);

        int n1 = Integer.parseInt(ed1.getText().toString());
        int n2 = Integer.parseInt(ed2.getText().toString());
        int result = n1*n2;

        ed3.setText("MULTIPLY Value"+result);
    }

//divide the total to calculate the price

    public void DIVIDE(View v){
        EditText ed1 = (EditText) findViewById(R.id.value1);
        EditText ed2 = (EditText) findViewById(R.id.value2);
        EditText ed3 = (EditText) findViewById(R.id.value3);

        int n1 = Integer.parseInt(ed1.getText().toString());
        int n2 = Integer.parseInt(ed2.getText().toString());
        int result = n1/n2;

        ed3.setText("MULTIPLY Value"+result);
    }
}
