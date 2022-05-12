package com.warmdelightapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.warmdelightapp.Adapters.OrderUserAdapter;
import com.warmdelightapp.Model.Order;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Orders extends AppCompatActivity {

    RecyclerView orderrecyclerView;
    DatabaseReference orderdatabaseuser;
    OrderUserAdapter orderadapteruser;
    ArrayList<Order> orderListuser;
    EditText searchET;
    String str="";

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Intent OrderIntent = getIntent();
        String UserName1 = OrderIntent.getStringExtra("userName");
        username = UserName1;

        orderrecyclerView = findViewById(R.id.Orderlistrecycleruser);
        orderdatabaseuser = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Order").child(username);







        orderrecyclerView.setHasFixedSize(true);
        orderrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderListuser = new ArrayList<>();
        orderadapteruser = new OrderUserAdapter(this,orderListuser);
        orderrecyclerView.setAdapter(orderadapteruser);

        searchET = findViewById(R.id.search_Order_text_user);

        orderdatabaseuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    Order orders = datasnapshot.getValue(Order.class);
                    orderListuser.add(orders);
                }
                orderadapteruser.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(searchET.getText().toString().equals("")){
                    orderrecyclerView = findViewById(R.id.Orderlistrecycleruser);
                    orderdatabaseuser = FirebaseDatabase.getInstance().getReference("Order").child(username);

                    searchET = findViewById(R.id.search_Order_text_user);

                    orderrecyclerView.setHasFixedSize(true);
                    orderrecyclerView.setLayoutManager(new LinearLayoutManager(Orders.this));

                    orderListuser = new ArrayList<>();
                    orderadapteruser = new OrderUserAdapter(Orders.this,orderListuser);
                    orderrecyclerView.setAdapter(orderadapteruser);

                    orderdatabaseuser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot datasnapshot : snapshot.getChildren()){

                                Order orders = datasnapshot.getValue(Order.class);
                                orderListuser.add(orders);
                            }
                            orderadapteruser.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else {
                    str = s.toString();
                    onStart();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Order> options = null;
        if (str.equals("")){
            options = new FirebaseRecyclerOptions.Builder<Order>().setQuery(orderdatabaseuser, Order.class).build();
        }else{
            options = new FirebaseRecyclerOptions.Builder<Order>().setQuery(orderdatabaseuser.orderByChild("name").startAt(str).endAt(str + "\uf8ff"),Order.class).build();
        }
        FirebaseRecyclerAdapter<Order, OrderUserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Order, OrderUserViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull OrderUserViewHolder orderUserViewHolder, int i, @NonNull Order orders) {
                orderUserViewHolder.name.setText(orders.getName());
                orderUserViewHolder.price.setText(orders.getPrice());
                orderUserViewHolder.qty.setText(orders.getQty());

                Picasso.get().load(orders.getImageurl()).placeholder(R.mipmap.ic_launcher).into(orderUserViewHolder.giftpackImage);

                orderUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Orders.this, com.warmdelightapp.OrderDetails.class);
                        intent.putExtra("giftpackname",orders.getName());
                        intent.putExtra("username",username);

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public OrderUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card_user, parent, false);
                OrderUserViewHolder viewHolder = new OrderUserViewHolder(view);
                return viewHolder;
            }
        };
        orderrecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class OrderUserViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView name,price,qty;
        ImageView giftpackImage;


        public OrderUserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userOrdername);
            price = itemView.findViewById(R.id.userOrderprice);
            qty = itemView.findViewById(R.id.userOrderqty);
            giftpackImage = itemView.findViewById(R.id.userOrderimage);
            cardView = itemView.findViewById(R.id.orderusercard);
        }
    }
}