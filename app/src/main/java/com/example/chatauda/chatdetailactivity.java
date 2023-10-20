package com.example.chatauda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatauda.adaptor.chatadapter;
import com.example.chatauda.databinding.ActivityChatdetailactivityBinding;
import com.example.chatauda.databinding.ActivityMainBinding;
import com.example.chatauda.model.messagemodel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class chatdetailactivity extends AppCompatActivity {
    ActivityChatdetailactivityBinding binding ;
    FirebaseDatabase database ;
    FirebaseAuth auth ;
    TextView textView  ;
    ImageView imageView;
    ImageView imageView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatdetailactivity);
        binding= ActivityChatdetailactivityBinding.inflate(getLayoutInflater());
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
        auth =FirebaseAuth.getInstance() ;
        database=FirebaseDatabase.getInstance() ;
         final  String senderid = auth.getUid()  ;
        String reciveid = getIntent().getStringExtra("userid" );
        String userName = getIntent().getStringExtra("username") ;
        String profilepic = getIntent().getStringExtra("profilepic") ;
        textView= findViewById(R.id.username) ;

        imageView = findViewById(R.id.profileimage);
        imageView1 = findViewById(R.id.backarrow );

        binding.username.setText(userName);
        Picasso.get().load(profilepic).placeholder(R.drawable.avatar).into(imageView);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(chatdetailactivity.this,MainActivity.class) ;
                startActivity(intent) ;
            }
        });


        final ArrayList<messagemodel>messagemodels= new ArrayList<>() ;
        final  chatadapter chatadapters =new chatadapter(messagemodels,this,reciveid);
        binding.chatrecycleview.setAdapter(chatadapters);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) ;
        binding.chatrecycleview.setLayoutManager(layoutManager);
        final String  senderRoom = senderid+ reciveid ;
        final String reciverRoom = reciveid +senderid ;

        database.getReference().child("chat")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   messagemodels.clear();
                           for(DataSnapshot snapshot1: snapshot.getChildren()){
                               messagemodel model = snapshot1 .getValue(messagemodel.class);
                                model.setMessageid(snapshot1.getKey());
                               messagemodels.add(model) ;

                           }
                                chatadapters.notifyDataSetChanged();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }) ;




       binding.send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

              String message =   binding.etmessage.getText().toString() ;
              final  messagemodel   model  = new messagemodel(senderid, message) ;
              model. setTimestamp(new Date().getTime());
              binding.etmessage.setText("");
              database.getReference().child("chat")
                      .child(senderRoom)
                      .push()
                      .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void unused) {
                              database.getReference().child("chat")
                                      .child(reciverRoom)
                                      .push()
                                      .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                          @Override
                                          public void onSuccess(Void aVoid) {

                                          }

                                      }) ;
                          }
                      });
           }
       });


    }
}