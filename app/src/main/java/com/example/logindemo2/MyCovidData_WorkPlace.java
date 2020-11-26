package com.example.logindemo2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class MyCovidData_WorkPlace extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ListView listView;
    String[] workplaces = {"Scales Hall" , "Waterbury Hall", "Sheldon Hall", "Hart Hall", "Riggs Hall",
            "Johnson Hall", "Oneida Hall", "Seneca Hall", "Onondaga Hall", "Cayuga Hall", "Shineman Center", "Marano Campus Center", "Wilbur Hall", "Park Hall", "Penfield Library",
            "Hewitt Union", "Mahar Hall", "Lanigan Hall", "Cooper Dining Center", "Lakeside Dining Center", "Littlepage Dining Center",
            "Pathfinder Dining Center","END"};

    public MyCovidData_WorkPlace() {
        // Required empty public constructor
    }


    public static MyCovidData_WorkPlace newInstance(String param1, String param2) {
        MyCovidData_WorkPlace fragment = new MyCovidData_WorkPlace();
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
        final View root = inflater.inflate(R.layout.fragment_my_covid_data_work_place, container, false);
        listView = root.findViewById(R.id.lvProgram);
        ProgramAdapter programAdapter = new ProgramAdapter(getActivity(), workplaces);
        listView.setAdapter(programAdapter);
        //==========================================================================================
        //Receives Bundle From the Adapter so it can send it to Upload_Workplace fragment
        System.out.println("Hello from Workplace Fragment");
        System.out.println("New Arguments: " + getArguments());

        return root;
    }
}