package com.warmdelightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboard extends AppCompatActivity {

    Button giftpack,cake,logout,profit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        giftpack = findViewById(R.id.giftpackadmindashboard);
        cake = findViewById(R.id.cakeadmindashboard);
        profit = findViewById(R.id.profitadmindashboard);
        logout = findViewById(R.id.adminlogout);


        giftpack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent giftpackadmindashboardIntent = new Intent(AdminDashboard.this, CakesAdmin.class);
                startActivity(giftpackadmindashboardIntent);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutadmindashboardIntent = new Intent(AdminDashboard.this,Login.class);
                startActivity(logoutadmindashboardIntent);
            }
        });


        profit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profitadmindashboardIntent = new Intent(AdminDashboard.this,ProfitCalculator.class);
                startActivity(profitadmindashboardIntent);
            }
        });
    }
}