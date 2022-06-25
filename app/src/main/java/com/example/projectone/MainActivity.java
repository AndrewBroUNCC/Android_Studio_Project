package com.example.projectone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LOG IN CHECK--------------------------------------------------IMPORTANT-------
        mAuth = FirebaseAuth.getInstance();
        //FirebaseAuth.getInstance().signOut();
        if (mAuth.getCurrentUser() == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.MainLayout, new LogInFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.MainLayout, new AccountScreenFragment())
                    .commit();
        }
        ///////////////////////////////////////////////////////////////////////////////
    }
}