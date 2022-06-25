package com.example.projectone;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CreateAccountFragment extends Fragment {

    TextView cancelButton;
    String name, email, password;
    EditText nameEditText, emailEditText, passwordEditText;
    Button submitButton;
    FirebaseAuth mAuth;

    String TAG = "demo";
    public CreateAccountFragment() {
    }

    public static CreateAccountFragment newInstance() {
        CreateAccountFragment fragment = new CreateAccountFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        //setting title
        getActivity().setTitle("Create New Account");

        nameEditText = view.findViewById(R.id.createAcctNameEditText);
        emailEditText = view.findViewById(R.id.createAcctEmailEditText);
        passwordEditText = view.findViewById(R.id.createAcctPasswordEditText);
        submitButton = view.findViewById(R.id.createAcctSubmitButton);

        cancelButton = view.findViewById(R.id.createAcctCancelTextView);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nameEditText.getText().toString().isEmpty()) {
                    validationAlert("User Name");
                }
                else if(emailEditText.getText().toString().isEmpty()) {
                    validationAlert("User Email");
                }
                else if(passwordEditText.getText().toString().isEmpty()) {
                    validationAlert("User Password");
                }
                else{
                    name = nameEditText.getText().toString();
                    email = emailEditText.getText().toString();
                    password = passwordEditText.getText().toString();

                    Log.d(TAG, "onClick: " + name + " " + email + " " + password);
                   registerUser(name, email, password);
                }
            }
        });

        return view;
    }

    public void validationAlert(String alert){
        new AlertDialog.Builder(getContext())
                .setTitle("Error")
                .setMessage(alert + " field is empty")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    public void registerUser(String userName, String userEmail, String userPassword){

        Log.d(TAG, "onClick: " + userName + " " + userEmail + " " + userPassword);

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            String userID = mAuth.getCurrentUser().getUid();

                           FirebaseUser user = mAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userName)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Log.d(TAG, "User Profile Updated.");

                                                getParentFragmentManager().beginTransaction()
                                                        .replace(R.id.MainLayout, new AccountScreenFragment())
                                                        .commit();
                                            }
                                        }
                                    });

                        }else{
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Error")
                                    .setMessage(task.getException().getMessage())
                                    .show();
                        }
                    }
                });
    }
}