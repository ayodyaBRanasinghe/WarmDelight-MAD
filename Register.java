package com.warmdelightapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.warmdelightapp.Model.User;

public class Register extends AppCompatActivity {

    EditText SignUpEmail,SignUpUsername,SignUpMobileNumber,SignUpPassword;
    TextView signIn;
    Button Signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SignUpEmail = findViewById(R.id.SignUpemail);
        SignUpPassword = findViewById(R.id.SignUppassword);
        SignUpUsername = findViewById(R.id.SignUpusername);
        SignUpMobileNumber = findViewById(R.id.SignUpmobileNumber);


        signIn = findViewById(R.id.SignUpsignin);
        Signup = findViewById(R.id.SignUpsignup);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String UserName = SignUpUsername.getText().toString().trim();
                String Email = SignUpEmail.getText().toString().trim();
                String MobileNumber = SignUpMobileNumber.getText().toString().trim();
                String Password = SignUpPassword.getText().toString().trim();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("User");

                User user = new User(UserName,Email,MobileNumber,Password);
                ref.child(UserName).setValue(user);

                SignUpEmail.setText("");
                SignUpPassword.setText("");
                SignUpMobileNumber.setText("");
                SignUpUsername.setText("");

                Toast.makeText(Register.this,"User Created!",Toast.LENGTH_SHORT).show();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinIntent = new Intent(Register.this,Login.class);
                startActivity(signinIntent);
            }
        });
    }
}