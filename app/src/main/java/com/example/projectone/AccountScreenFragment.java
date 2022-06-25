package com.example.projectone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AccountScreenFragment extends Fragment {

    FirebaseAuth mAuth;
    String userName;
    Button logoutButton;

    public AccountScreenFragment() {
    }

    public static AccountScreenFragment newInstance() {
        AccountScreenFragment fragment = new AccountScreenFragment();
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
        View view = inflater.inflate(R.layout.fragment_account_screen, container, false);

        logoutButton = view.findViewById(R.id.accountScreenLogoutButton);
        mAuth = FirebaseAuth.getInstance();
        userName = mAuth.getCurrentUser().getDisplayName();

        getActivity().setTitle(userName + "'s Profile");

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.MainLayout, new LogInFragment())
                        .commit();
            }
        });

        return view;
    }
}