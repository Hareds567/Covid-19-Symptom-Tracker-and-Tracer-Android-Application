package com.example.logindemo2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;


public class ResidenceHallData_MyCovidData extends Fragment {

    private final String getSocialURL = "https://covidtrackerdev.herokuapp.com/testcsv";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    private TextView displayResidence_TextView;
    private String userGmail;

    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleSignInAccount;

    public ResidenceHallData_MyCovidData() {
    }


    public static ResidenceHallData_MyCovidData newInstance(String param1, String param2) {
        ResidenceHallData_MyCovidData fragment = new ResidenceHallData_MyCovidData();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_residence_hall_data__my_covid_data, container, false);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),gso);
        mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
        userGmail  = mGoogleSignInAccount.getEmail();

        //displayResidence_TextView.setText(userGmail);
        return rootView;
    }


}