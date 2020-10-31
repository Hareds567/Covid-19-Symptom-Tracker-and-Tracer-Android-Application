package com.example.logindemo2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppSettings extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button myCovidData_Btn;
    private Button updateInfo_Btn;
    private Button helpImprove_btn;

    public AppSettings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppSettings.
     */
    // TODO: Rename and change types and number of parameters
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

        myCovidData_Btn = rootView.findViewById(R.id.myCovidData_Btn);
        updateInfo_Btn = rootView.findViewById(R.id.updateInfoBtn_Setting);
        helpImprove_btn = rootView.findViewById(R.id.helpImprove_btn);


        myCovidData_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCovidData_AppSetting myCovidDataFragment = new MyCovidData_AppSetting();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment, myCovidDataFragment, myCovidDataFragment.getTag()).commit();
            }
        });

        updateInfo_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateInfo_AppSetting updateInfo = new UpdateInfo_AppSetting();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment, updateInfo, updateInfo.getTag()).commit();
            }
        });

        helpImprove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitFeedback_Setting submitFeedback_setting = new SubmitFeedback_Setting();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment, submitFeedback_setting, submitFeedback_setting.getTag()).commit();
            }
        });

        return rootView;
    }
}