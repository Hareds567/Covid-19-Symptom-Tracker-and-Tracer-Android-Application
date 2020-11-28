package com.example.logindemo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class AppSettings_Profile extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private GoogleSignInAccount acc;
    private GoogleSignInClient mGoogleSignInClient;

    public AppSettings_Profile() { }

    public static AppSettings_Profile newInstance(String param1, String param2) {
        AppSettings_Profile fragment = new AppSettings_Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_app_settings_profile, container, false);
        //Get User Account
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),gso);
        acc = GoogleSignIn.getLastSignedInAccount(getActivity());
        //Setting up UI elements
        Button signOut_Btn = rootView.findViewById(R.id.Sign_out_Button_Setting);
        TextView email_TextView = rootView.findViewById(R.id.textView_Email);
        TextView userName_TextView = rootView.findViewById(R.id.textView_userName);
        //
        email_TextView.setText(acc.getEmail());
        userName_TextView.setText(acc.getDisplayName());

        signOut_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        return rootView;
    }

    private void  signOut() {
        if (getActivity() != null) {
            mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    goToLoginActivity();
                }
            });
        }
    }
    private void goToLoginActivity(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
        ((Activity) getActivity()).overridePendingTransition(0,0);
    }
}