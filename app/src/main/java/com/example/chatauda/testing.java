package com.example.chatauda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chatauda.databinding.ActivityChatdetailactivityBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class testing extends AppCompatActivity {
    ActivityChatdetailactivityBinding binding ;
    FirebaseDatabase database ;
    FirebaseAuth auth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        //setContentView(binding.getRoot());
        auth =FirebaseAuth.getInstance() ;
        database=FirebaseDatabase.getInstance() ;
        String senderid = auth.getUid()  ;
        String reciveid = getIntent().getStringExtra("userid" );
        String userName = getIntent().getStringExtra("username") ;
        String profilepic = getIntent().getStringExtra("profilepic") ;
         //binding.username.setText("katy");
        //Picasso.get().load(profilepic).placeholder(R.drawable.avatar).into(binding.profileImage);


    }
}