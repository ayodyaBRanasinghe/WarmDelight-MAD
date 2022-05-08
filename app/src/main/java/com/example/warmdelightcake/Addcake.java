package com.example.warmdelightcake;

import static android.os.Build.ID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import java.text.BreakIterator;

public class Addcake extends AppCompatActivity {

    private static final String CAKE_PREFIX = ;
    private String ProductHighlight;
    private Object Producthighlight;
    private DataSnapshot reference;
    private Intent packageContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcake);
    }

    public void AddCake(View view){
        String caketype = CakeType.getText().toString();
        String cakename = CakeName.getText().toString();
        Stirng price = price.getText().toString();
        String weight = weight.getChars().toString();
        String producthighlight = Producthighlight.getClass().toString();

        if (CakeType.isEmpty() || caketype == null) {
            CakeType.setError("Cake type must be filled.");
        }
        if(CakeName.isEmpty() || cakename == null) {
            CakeName.setError("Cake name must be filled");
        }
        if(price.isEmpty() || price == null) {
            price.setError("Cake price must be filled");
        }
        if(weight.isEmpty() || weight == null) {
            weight.split("Cake weight must be filled");
        }
        if(Producthighlight.wait() || Producthighlight == null) {
            ProductHighlight.split("Cake producthighlight must be filled");
        }else {
            String cID = String.valueOf(ID);

            String id = CAKE_PREFIX + ID;
            System.out.println("id" + ID);
            Addcake addcake = new Addcake(caketype, cakename, price, weight, producthighlight);
            reference.child(id).getValue(addcake);
            Toast.makeText(context:this, ID "Cake is added successfully.", Toast.LENGTH_SHORT).
            show();

            Intent intent = new Intent(packageContext:Addcake.this, Addcake.class);
            startActivity(intent);
        }

            caketype.setText(model.getCakeType());
            cakename.setText(model.getCakeName());
            price.setText(model.getPrice());
            weight.setText(model.getweight());
            producthighlight.setText(model.getProducthighlight());

            dialogPlus.show();

            submit.setOnclickLIstener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Map<String, Object> map = new HasMap<>();
                    map.put("cakeType", cType.getChars().toString());
                    map.put("cakeName", cName.getChars().toString());
                    map.put("price", price.getText().toString());
                    map.put("weight", weight.getChars().toString());
                    map.put("productheighlight", producthighlight.getChars().toString());

                    FirebaseDatabase.getInstance().getReference().child("Managecake")
                            .child(getRef(position).getKey()).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>(){
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dialogPlus.dismiss();
                                    Toast.makeText(myview.getContext(), ID: "Upadate Successfully.", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e){
                                    dialogPlus.dismiss();
                                }
                            });

                }
            });
        }

    }

    private void show() {

    }

}





