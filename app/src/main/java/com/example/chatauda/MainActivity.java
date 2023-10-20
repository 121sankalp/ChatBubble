package com.example.chatauda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chatauda.adaptor.fragmentsAdaptor;
import com.example.chatauda.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
  ActivityMainBinding binding ;
  FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance() ;
        DatabaseReference myref =database.getReference("message") ;
        myref.setValue("hello ") ;
        binding.viewPager.setAdapter(new fragmentsAdaptor(getSupportFragmentManager())) ;
        binding.tablelayout.setupWithViewPager(binding.viewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.setting:
                Intent intenttt=  new Intent(MainActivity.this, settingact.class);
                startActivity(intenttt);
                break ;

            case R.id.logout:
                auth.signOut();
                Intent intent=  new Intent(MainActivity.this, signinactivity.class);
                 startActivity(intent);
                Toast.makeText(this, "Logout!", Toast.LENGTH_LONG).show();
                break;

            case R.id.groupchat:
                Intent intentt=  new Intent(MainActivity.this, groupchatactivity.class);
                startActivity(intentt);
                break ;


        }


        return true ;
    }
}