package com.example.logindemo2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCovidData_WorkPlace#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCovidData_WorkPlace extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView lvProgram;
    String[] workplaces = {"Scales Hall" , "Waterbury Hall", "Sheldon Hall", "Hart Hall", "Riggs Hall",
            "Johnson Hall", "Oneida Hall", "Seneca Hall", "Onondaga Hall", "Cayuga Hall", "Shineman Center", "Marano Campus Center", "Wilbur Hall", "Park Hall", "Penfield Library",
            "Hewitt Union", "Mahar Hall", "Lanigan Hall", "Cooper Dining Center", "Lakeside Dining Center", "Littlepage Dining Center",
            "Pathfinder Dining Center"};

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
        final View root = inflater.inflate(R.layout.fragment_work_place__my_covid_data, container, false);
        lvProgram = root.findViewById(R.id.lvProgram);
        ProgramAdapter programAdapater = new ProgramAdapter(getActivity(), workplaces);
        lvProgram.setAdapter(programAdapater);
        return root;
    }
}