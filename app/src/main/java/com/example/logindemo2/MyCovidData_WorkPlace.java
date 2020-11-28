package com.example.logindemo2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class MyCovidData_WorkPlace extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FragmentManager fragmentManager;
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
        fragmentManager = getFragmentManager();

        //handles incoming information from other fragments
        Bundle bundle = (Bundle) getArguments();
        if(bundle != null) {

            System.out.println("Tag from workplace: " + this.getTag());
            System.out.println("Fragment from Workplace: \n" + fragmentManager.getFragments());
            System.out.println("Workplace Arguments from the adapter: " + bundle);
            System.out.println("Workplace Bundle from the adapter: " + bundle.get("WORKPLACE"));
            MyCovidData_UpdateWorkplace updateWorkplace = new MyCovidData_UpdateWorkplace();
            updateWorkplace.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, updateWorkplace ,updateWorkplace.getTag()).commit();
        }



        //inflates the view using the program adapter
        listView = root.findViewById(R.id.lvProgram);
        ProgramAdapter programAdapter = new ProgramAdapter(getActivity(), workplaces);
        listView.setAdapter(programAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.println("Test for onClick");
                String workplace = (String) adapterView.getItemAtPosition(position);
                if(workplace != null){
                    Toast.makeText(getContext(), workplace, Toast.LENGTH_LONG).show();
                    MyCovidData_UpdateWorkplace workPlace = new MyCovidData_UpdateWorkplace();
                    Bundle bundle = new Bundle();
                    bundle.putString("WORKPLACE", workplace);
                    workPlace.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, workPlace, workPlace.getTag()).commit();
                }
            }

        });

        return root;
    }
}