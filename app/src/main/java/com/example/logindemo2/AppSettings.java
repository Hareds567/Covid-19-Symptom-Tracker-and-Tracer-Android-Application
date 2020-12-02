package com.example.logindemo2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class AppSettings extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button myCovidData_Btn;
    private Button updateInfo_Btn;
    private Button helpImprove_btn;
    private Button privacyPolicy_btn;
    private Button licenses_btn;

    public AppSettings() {
        // Required empty public constructor
    }

    public static AppSettings newInstance(String param1, String param2) {
        AppSettings fragment = new AppSettings();
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
        final View rootView = inflater.inflate(R.layout.fragment_app_settings, container, false);
        //Connect Buttons and their xml content using ID
        myCovidData_Btn = rootView.findViewById(R.id.myCovidData_Btn);
        helpImprove_btn = rootView.findViewById(R.id.helpImprove_btn);
        privacyPolicy_btn = rootView.findViewById(R.id.privacyPolicy_btn);
        licenses_btn = rootView.findViewById(R.id.licenses_btn);

        //Set click listeners for buttons: When a button gets clicked do something that is inside the onclick method
        licenses_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Licenses myCovidDataFragment = new Licenses();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment, myCovidDataFragment, myCovidDataFragment.getTag()).commit();
            }
        });
        myCovidData_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppSettings_Profile myCovidDataFragment = new AppSettings_Profile();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment, myCovidDataFragment, myCovidDataFragment.getTag()).commit();
            }
        });

        helpImprove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppSettings_SubmitFeedback app_settingSubmitFeedback = new AppSettings_SubmitFeedback();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment, app_settingSubmitFeedback, app_settingSubmitFeedback.getTag()).commit();
            }
        });

        privacyPolicy_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AppSettings_PrivacyPolicy appSettingsPolicy = new  AppSettings_PrivacyPolicy();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment, appSettingsPolicy, appSettingsPolicy.getTag()).commit();
            }
        });
        //build the view
        return rootView;
    }
}