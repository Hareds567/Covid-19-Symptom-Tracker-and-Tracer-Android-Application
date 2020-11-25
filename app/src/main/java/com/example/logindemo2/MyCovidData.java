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
 * Use the {@link MyCovidData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCovidData extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button residenceHallData_Btn;
    private Button mySocialCircle_Btn;
    private Button classes_Btn;
    private Button workplace_Btn;

    public MyCovidData() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserCovidData.
     */
    // TODO: Rename and change types and number of parameters
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
        final View rootView  = inflater.inflate(R.layout.fragment_user_covid_data, container, false);

        //Goes to ResidenceHallData_MyCovidData
        residenceHallData_Btn = rootView.findViewById(R.id.residenceHalldata_Btn);
        mySocialCircle_Btn = rootView.findViewById(R.id.mySocialCircle_Btn);
        classes_Btn = rootView.findViewById(R.id.classes_btn);
        workplace_Btn = rootView.findViewById(R.id.myWorkplace_btn);

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
        return rootView;
    }
}