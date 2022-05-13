package com.warmdelightapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.warmdelightapp.Model.cake;

public class CreateCake extends AppCompatActivity {

    ImageView cakeImage;
    Button select,save;
    EditText name,price,cost;
    Uri imageUri;

    StorageReference imageStorage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference cakeref = database.getReference("Cakes");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cake);

        cakeImage = findViewById(R.id.cakeImage);
        select =findViewById(R.id.selectcakeImage);
        name = findViewById(R.id.cakeName);
        price = findViewById(R.id.cakePrice);
        save = findViewById(R.id.uploadcakedata);
        cost=findViewById(R.id.cakeCost);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();
            }
        });



    }

    public void uploadImage() {


        String cakeName = name.getText().toString().trim();
        String cakePrice = price.getText().toString().trim();
        String cakeCost = cost.getText().toString().trim();


        imageStorage = FirebaseStorage.getInstance().getReference(cakeName);

        imageStorage.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                cakeImage.setImageURI(imageUri);


                imageStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        cake cakedetails = new cake(cakeName,cakePrice,cakeCost,uri.toString());
                        cakeref.child(cakeName).setValue(cakedetails);


                    }
                });
                Toast.makeText(CreateCake.this,"Cake data uploaded",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateCake.this,"Error!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void selectImage() {

        Intent selectimageIntent = new Intent();
        selectimageIntent.setType("image/*");
        selectimageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(selectimageIntent,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            cakeImage.setImageURI(imageUri);

        }

    }



}