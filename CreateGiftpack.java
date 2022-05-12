package com.warmdelightapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.warmdelightapp.Model.giftpack;

public class CreateGiftpack extends AppCompatActivity {

    ImageView giftpackImage;
    Button select,save;
    EditText name,price,cost;
    Uri imageUri;

    StorageReference imageStorage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference giftpackref = database.getReference("Giftpacks");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_giftpack);

        giftpackImage = findViewById(R.id.giftpackImage);
        select =findViewById(R.id.selectgiftpackImage);
        name = findViewById(R.id.giftpackName);
        price = findViewById(R.id.giftpackPrice);
        save = findViewById(R.id.uploadgiftpackdata);
        cost=findViewById(R.id.giftpackCost);


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


        String giftpackName = name.getText().toString().trim();
        String giftpackPrice = price.getText().toString().trim();
        String giftpackDescription = cost.getText().toString().trim();


        imageStorage = FirebaseStorage.getInstance().getReference(giftpackName);

        imageStorage.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                giftpackImage.setImageURI(imageUri);


                imageStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        giftpack giftpackdetails = new giftpack(giftpackName,giftpackPrice,giftpackDescription,uri.toString());
                        giftpackref.child(giftpackName).setValue(giftpackdetails);


                    }
                });
                Toast.makeText(CreateGiftpack.this,"giftpack data uploaded",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateGiftpack.this,"Error!",Toast.LENGTH_SHORT).show();
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
            giftpackImage.setImageURI(imageUri);

        }

    }
}