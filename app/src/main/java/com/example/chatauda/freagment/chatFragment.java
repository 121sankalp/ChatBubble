package com.example.chatauda.freagment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.chatauda.R;
import com.example.chatauda.adaptor.useradaptor;
import com.example.chatauda.databinding.FragmentChatBinding;
import com.example.chatauda.model.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class chatFragment extends Fragment {



    public chatFragment() {





    }
 FragmentChatBinding binding ;
    ArrayList<user> list= new ArrayList<>() ;
    FirebaseDatabase database ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentChatBinding. inflate(inflater, container, false);
        database= FirebaseDatabase.getInstance() ;
        useradaptor adaptor = new useradaptor(list,getContext()) ;
        binding.chatrecyclerview.setAdapter(adaptor);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) ;
        binding.chatrecyclerview.setLayoutManager(layoutManager) ;
        database.getReference().child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                     user users = dataSnapshot.getValue(user.class);

                     users.setUserId(dataSnapshot.getKey());
                     if(!users.getUserId().equals(FirebaseAuth.getInstance().getUid()))
                      list .add(users)  ;
                 }


                adaptor.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;



        return  binding.getRoot() ;



    }
}