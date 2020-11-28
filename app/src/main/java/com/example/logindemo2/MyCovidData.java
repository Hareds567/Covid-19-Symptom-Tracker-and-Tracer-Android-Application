package com.example.logindemo2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyCovidData extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String WEBSITE_URL = "https://covidtrackerdev.herokuapp.com/";
    private static Pattern VALID_FACULTY_EMAIL_ADDRESS_REGEX = Pattern.compile("^[a-z0-9]+\\.[a-z0-9]+@(oswego)\\.edu$");

    private String mParam1;
    private String mParam2;
    private Button residenceHallData_Btn;
    private Button mySocialCircle_Btn;
    private Button classes_Btn;
    private Button workplace_Btn;
    private Button uploadClassList_Btn;
    private String userEmail;

    public MyCovidData() {
        // Required empty public constructor
    }

    public static MyCovidData newInstance(String param1, String param2) {
        MyCovidData fragment = new MyCovidData();
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
        // Inflate the layout for this fragment
        final View rootView  = inflater.inflate(R.layout.fragment_my_covid_data, container, false);
        //Get User Account
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(getActivity());
        userEmail = acc.getEmail();

        //Goes to ResidenceHallData_MyCovidData
        residenceHallData_Btn = rootView.findViewById(R.id.residenceHalldata_Btn);
        mySocialCircle_Btn = rootView.findViewById(R.id.mySocialCircle_Btn);
        classes_Btn = rootView.findViewById(R.id.classes_btn);
        workplace_Btn = rootView.findViewById(R.id.myWorkplace_btn);
        uploadClassList_Btn = rootView.findViewById(R.id.uploadClasses_btn);

        if(!validateProfessor(userEmail)){
            uploadClassList_Btn.setVisibility(View.GONE);
        }

        residenceHallData_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCovidData_ReportPositiveTest residenceHallDataMyCovidData = new MyCovidData_ReportPositiveTest();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, residenceHallDataMyCovidData, residenceHallDataMyCovidData.getTag()).commit();
            }
        });

        classes_Btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                MyCovidData_UpdateClasses updateClasses = new MyCovidData_UpdateClasses();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, updateClasses, updateClasses.getTag()).commit();
            }
        });

        mySocialCircle_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MyCovidData_MySocialCircle _myCovidDataMySocialCircle = new MyCovidData_MySocialCircle();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, _myCovidDataMySocialCircle, _myCovidDataMySocialCircle.getTag()).commit();
            }
        });

        workplace_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MyCovidData_UpdateWorkplace MyCovidData_UpdateClasses = new MyCovidData_UpdateWorkplace();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, MyCovidData_UpdateClasses, MyCovidData_UpdateClasses.getTag()).commit();
            }
        });

        uploadClassList_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(WEBSITE_URL));
                startActivity(intent);
            }
        });
        return rootView;
    }

    private static boolean validateProfessor(String email){
        Matcher matcher = VALID_FACULTY_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}