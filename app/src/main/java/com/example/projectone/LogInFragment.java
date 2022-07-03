package com.example.projectone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LogInFragment extends Fragment {

    TextView createNewAccount;
    FirebaseAuth mAuth;
    Button loginButton;
    TextView email;
    TextView password;

    public LogInFragment() {
    }

    public static LogInFragment newInstance() {
        LogInFragment fragment = new LogInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        getActivity().setTitle("Login Fragment");

        mAuth = FirebaseAuth.getInstance();

        password = view.findViewById(R.id.editTextTextPassword);
        email = view.findViewById(R.id.editTextEmail);

        //setting button to go to create account page
        createNewAccount = view.findViewById(R.id.loginCreateNewAccountTextView);
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.MainLayout, new CreateAccountFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        //login button
        loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (email.getText().toString().isEmpty() || email.getText().toString() == null){
                    Toast.makeText(getActivity(), "Email Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String emailString = email.getText().toString();

                if (password.getText().toString().isEmpty() || password.getText().toString() == null){
                    Toast.makeText(getActivity(), "Password Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String passwordString = email.getText().toString();

                mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.MainLayout, new AccountScreenFragment())
                                .commit();
                    }
                });
            }
        });

        return view;
    }
}