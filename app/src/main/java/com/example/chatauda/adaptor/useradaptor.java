package com.example.chatauda.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatauda.R;
import com.example.chatauda.chatdetailactivity;
import com.example.chatauda.model.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class useradaptor extends  RecyclerView.Adapter<useradaptor.ViewHolder> {
   ArrayList<user> list;
   Context context ;
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image ;
        TextView username,lastmessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.profileimage);
            username= itemView.findViewById(R.id.usernamelist);
            lastmessage = itemView.findViewById(R.id.lastmessage);



        }
    }

    public useradaptor(ArrayList<user> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false) ;

        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            user users =list.get(position);
        Picasso .get().load(users.getProfilepic()).placeholder(R.drawable.avatar).into(holder.image);
       holder.username.setText(users.getUsername()) ;
        FirebaseDatabase.getInstance().getReference().child("chat")
                .child(FirebaseAuth.getInstance().getUid()+users.getUserId())
                .orderByChild("timestamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(snapshot.hasChildren()){

                                                            for (DataSnapshot snapshot1: snapshot.getChildren())
                                                            {holder.lastmessage.setText(snapshot1.child("message").getValue().toString());



                                                            }
                                                        }


                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });


     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent=new Intent(context, chatdetailactivity.class) ;
             intent.putExtra("userid", users.getUserId());
             intent.putExtra("username",users.getUsername());
             intent.putExtra("profilepic",users.getProfilepic());
             context.startActivity(intent);
         }
     });


    }

    @Override
    public int getItemCount() {
        return list.size() ;
    }




}
