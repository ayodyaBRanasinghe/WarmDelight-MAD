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


    public void ADD(View v){
        EditText ed1 = (EditText) findViewById(R.id.value1);
        EditText ed2 = (EditText) findViewById(R.id.value2);
        EditText ed3 = (EditText) findViewById(R.id.value3);

        int n1 = Integer.parseInt(ed1.getText().toString());
        int n2 = Integer.parseInt(ed2.getText().toString());
        int result = n1+n2;

        ed3.setText("Total Value"+result);
    }

    public void SUBTRACT(View v){
        EditText ed1 = (EditText) findViewById(R.id.value1);
        EditText ed2 = (EditText) findViewById(R.id.value2);
        EditText ed3 = (EditText) findViewById(R.id.value3);

        int n1 = Integer.parseInt(ed1.getText().toString());
        int n2 = Integer.parseInt(ed2.getText().toString());
        int result = n1-n2;

        ed3.setText("SUBTRACT Value"+result);
    }

    public void MULTIPLY(View v){
        EditText ed1 = (EditText) findViewById(R.id.value1);
        EditText ed2 = (EditText) findViewById(R.id.value2);
        EditText ed3 = (EditText) findViewById(R.id.value3);

        int n1 = Integer.parseInt(ed1.getText().toString());
        int n2 = Integer.parseInt(ed2.getText().toString());
        int result = n1*n2;

        ed3.setText("MULTIPLY Value"+result);
    }

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
