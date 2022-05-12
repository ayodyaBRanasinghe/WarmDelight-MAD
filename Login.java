package com.warmdelightapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {


    EditText userNameSignIn,PasswordSignIn;
    TextView SignUpinSignIn,forgotpasswordSignIn;
    Button signIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameSignIn = findViewById(R.id.email);
        PasswordSignIn = findViewById(R.id.password);

        signIn = findViewById(R.id.signIn);
        forgotpasswordSignIn = findViewById(R.id.forgotpassword);
        SignUpinSignIn = findViewById(R.id.signUp);




        SignUpinSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupintent = new Intent(Login.this,Register.class);
                startActivity(signupintent);
            }


        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                letLogin();
            }
        });


    }

    public void letLogin(){
        String userName = userNameSignIn.getText().toString().trim();
        String Password = PasswordSignIn.getText().toString().trim();

        Query checkUser = FirebaseDatabase.getInstance().getReference("User").orderByChild("userName").equalTo(userName);



        if(userName.equals("admin")&& Password.equals("admin123")){
            Intent adminIntent = new Intent(Login.this,AdminDashboard.class);
            startActivity(adminIntent);
        }else{
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {




                    if (snapshot.exists()){
                        userNameSignIn.setError(null);

                        String passwordDB = snapshot.child(userName).child("password").getValue(String.class);
                        if (passwordDB.equals(Password)){

                            String UserName = snapshot.child(userName).child("userName").getValue(String.class);
                            String email = snapshot.child(userName).child("email").getValue(String.class);
                            String mobileNumber = snapshot.child(userName).child("contactNo").getValue(String.class);

                            Intent loginIntent = new Intent(Login.this,HomePage.class);
                            loginIntent.putExtra("userName",UserName);
                            loginIntent.putExtra("email",email);
                            loginIntent.putExtra("mobileNumber",mobileNumber);
                            startActivity(loginIntent);
                            finish();

                        }else{
                            Toast.makeText(Login.this, "Password not correct!", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(Login.this, "No user Exist!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Login.this, "Database Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}