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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResidenceHallData_MyCovidData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResidenceHallData_MyCovidData extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView displayResidence_TextView;
    private String userGmail;

    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleSignInAccount;

    public ResidenceHallData_MyCovidData() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResidenceHallData_MyCovidData.
     */
    // TODO: Rename and change types and number of parameters
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
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

        //MongoReader mongoReader = new MongoReader(userGmail);
        //mongoReader.AddToStudentCollection();



        //String residence = mongoReader.Residence;

        displayResidence_TextView = rootView.findViewById(R.id.displayResidence_TextView);
        displayResidence_TextView.setText(userGmail);
        return rootView;
    }


}