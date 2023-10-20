package com.example.chatauda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chatauda.databinding.ActivitySignupactivityBinding;
import com.example.chatauda.model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signupactivity extends AppCompatActivity {
Button button;
    private FirebaseAuth Auth;
    private Button butt;
    ActivitySignupactivityBinding binding;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);
        binding= ActivitySignupactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Auth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(signupactivity.this);
        progressDialog.setTitle("wait!!! creating  account");
        progressDialog.setMessage("we 're creating your account");
  binding.btnSignup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          progressDialog.show();
          Toast.makeText(signupactivity.this, "hii", Toast.LENGTH_SHORT).show();
          Auth.createUserWithEmailAndPassword
                  (binding.etEmail.getText().toString(),binding.etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                  progressDialog.dismiss();
                  if(task.isSuccessful()){
                      user users=new user(binding.etUsername.getText().toString(),binding.etEmail.getText().toString(),binding.etPassword.getText().toString());
                      String id = task.getResult().getUser().getUid();
                      database.getReference().child("user").child(id).setValue(users);


                      Toast.makeText(signupactivity.this, "user created sucessfully", Toast.LENGTH_SHORT).show();
                  }
                  else {
                      Toast.makeText(signupactivity.this, task.getException().getMessage()    , Toast.LENGTH_SHORT).show();
                  }
              }
          });





      }

  });
        binding.tvalreadyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(signupactivity.this,signinactivity.class) ;
                startActivity(intent);
            }
        });
    }
}