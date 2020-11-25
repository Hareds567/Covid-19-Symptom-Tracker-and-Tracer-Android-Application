package com.example.logindemo2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateInfo_AppSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateInfo_AppSetting extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String WEBSITE_URL = "https://covidtrackerdev.herokuapp.com/";


    private String mParam1;
    private String mParam2;
    private Button updateCourseCSV;

    public UpdateInfo_AppSetting() {
        // Required empty public constructor
    }

    public static UpdateInfo_AppSetting newInstance(String param1, String param2) {
        UpdateInfo_AppSetting fragment = new UpdateInfo_AppSetting();
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
        final View rootView =inflater.inflate(R.layout.fragment_update_info__app_setting, container, false);
        //Goes to Website to Upload Class Roster
        updateCourseCSV = rootView.findViewById(R.id.button7);
        updateCourseCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(WEBSITE_URL));
                startActivity(intent);
            }
        });


        return rootView;
    }
}