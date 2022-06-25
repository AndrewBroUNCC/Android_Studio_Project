package com.example.projectone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class LogInFragment extends Fragment {

    TextView createNewAccount;
    FirebaseAuth mAuth;

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

        return view;
    }
}