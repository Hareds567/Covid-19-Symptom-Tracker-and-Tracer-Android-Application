package com.example.logindemo2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkPlace_MyCovidData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkPlace_MyCovidData extends Fragment {


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

    int[] images = {R.drawable.reshall, R.drawable.reshall2, R.drawable.reshall3, R.drawable.reshall4, R.drawable.reshall5,
            R.drawable.reshall7, R.drawable.reshall8, R.drawable.reshall9, R.drawable.reshall10,
            R.drawable.reshall11,  R.drawable.workplace, R.drawable.workplace1, R.drawable.workplace2, R.drawable.workplace3,
            R.drawable.workplace4, R.drawable.workplace5,  R.drawable.workplace6,  R.drawable.workplace7,  R.drawable.workplace8,
            R.drawable.workplace9};

    public WorkPlace_MyCovidData() {
        // Required empty public constructor
    }


    public static WorkPlace_MyCovidData newInstance(String param1, String param2) {
        WorkPlace_MyCovidData fragment = new WorkPlace_MyCovidData();
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
        ProgramAdapter programAdapater = new ProgramAdapter(getActivity(), workplaces, images);
        lvProgram.setAdapter(programAdapater);
        return root;
    }
}