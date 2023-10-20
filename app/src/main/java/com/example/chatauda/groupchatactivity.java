package com.example.chatauda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chatauda.adaptor.chatadapter;
import com.example.chatauda.databinding.ActivityGroupchatactivityBinding;
import com.example.chatauda.model.messagemodel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class groupchatactivity extends AppCompatActivity {
    ActivityGroupchatactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupchatactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(groupchatactivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<messagemodel> messagemodel = new ArrayList<>();
        final String senderid = FirebaseAuth.getInstance().getUid();
        binding.username.setText("chit-chat");

        final chatadapter adapter = new chatadapter(messagemodel, this);
        binding.chatrecycleview.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatrecycleview.setLayoutManager(layoutManager);


        database.getReference().child("groupchat")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                messagemodel.clear();
                                 for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                                      messagemodel  model = dataSnapshot.getValue(com.example.chatauda.model.messagemodel.class);
                                      messagemodel.add(model);

                                 }
                                 adapter.notifyDataSetChanged();



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }) ;




        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  String message = binding.etmessage.getText().toString();
                final  messagemodel model =new messagemodel(senderid,message) ;
                model.setTimestamp(new Date().getTime());
                binding.etmessage.setText("");
                database.getReference().child("groupchat")
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {



                            }
                        });
            }
        });

    }
}