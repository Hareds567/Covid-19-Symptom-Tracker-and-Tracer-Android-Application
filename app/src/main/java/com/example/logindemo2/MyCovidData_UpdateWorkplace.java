package com.example.logindemo2;

import android.app.VoiceInteractor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;


public class MyCovidData_UpdateWorkplace extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String get_Workplace = "https://covidtrackerdev.herokuapp.com/get_workplace";
    private static final String post_Workplace = "https://covidtrackerdev.herokuapp.com/post_workplace";

    private String mParam1;
    private String mParam2;
    private String userEmail;
    private Button selectWorkplace_btn;
    private Button deleteButton;
    private Button addWorkplace_btn;
    private Button cancelWorkplace_btn;
    private TextView addWorkplace_TextV;
    private String workplace;
    private String workplace_tobe_added;
    private TextView workplace_TextView;
    private FragmentManager fragmentManager;

    public MyCovidData_UpdateWorkplace() {
        // Required empty public constructor
    }

    public static MyCovidData_UpdateWorkplace newInstance(String param1, String param2) {
        MyCovidData_UpdateWorkplace fragment = new MyCovidData_UpdateWorkplace();
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
        View root = inflater.inflate(R.layout.fragment_my_covid_data__update_workplace, container, false);
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        //Variables
        JSONObject email = new JSONObject();
        fragmentManager = getFragmentManager();
        //Setting up buttons
        selectWorkplace_btn = root.findViewById(R.id.select_workplace_btn);
        deleteButton = root.findViewById(R.id.delete_workplace_btn);
        addWorkplace_btn = root.findViewById(R.id.addWorkPlace_btn);
        cancelWorkplace_btn = root.findViewById(R.id.cancelWorkplace_btn);
        //Setting up Text Views
        workplace_TextView = root.findViewById(R.id.editTextTextPersonName);
        addWorkplace_TextV = root.findViewById(R.id.workPlaceSelected_textview);
        //Setting up the user's google account
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(getActivity());
        userEmail = acc.getEmail();
        try {
            email.put("WorkUser", userEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //==========================================================================================
        //Display workplace
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, get_Workplace,
                email, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println("Workplace test " + response);
                    workplace = response.get("Workplace").toString();
                    if (!(workplace.compareTo("null") == 0)) {
                        workplace_TextView.setText(workplace);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Unable to get Workplace", Toast.LENGTH_LONG);
            }
        });
        queue.add(jsonObjectRequest);
        //==========================================================================================
        //Get the workplace selected from the list view of workplaces and fix visibility of UI elements
        //Handles can the user select a new workplace
        if (getArguments() != null) {
            System.out.println("New Arguments: " + getArguments());
            String response = (String) getArguments().get("WORKPLACE");
            System.out.println("New Response: " + response);
            //handles UI
            addWorkplace_btn.setVisibility(View.VISIBLE);
            cancelWorkplace_btn.setVisibility(View.VISIBLE);
            //set workplace
            workplace_tobe_added = response;
            addWorkplace_TextV.setText(workplace_tobe_added);
            System.out.println("When argument is not null, Workplace: " + workplace_tobe_added);
        }
        System.out.println("Workplace: " + workplace);
        //==========================================================================================
        //BUTTON ON_CLICK LISTENERS
        // Select the workplace to be updated or added
        selectWorkplace_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCovidData_WorkPlace workplace = new MyCovidData_WorkPlace();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, workplace, workplace.getTag()).commit();
            }
        });
        //delete the WorkPlace
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workplace_tobe_added = null;
                queue.add(updateWorkplace());
                workplace_TextView.setText("No Workplace");
            }
        });
        //Add or Update new Workplace
        addWorkplace_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Handles Http Requests
                queue.add(updateWorkplace());

                //Handles UI
                displayWorkplace(workplace_tobe_added);
                addWorkplace_TextV.setText("");
                addWorkplace_btn.setVisibility(View.GONE);
                cancelWorkplace_btn.setVisibility(View.GONE);

            }
        });

        cancelWorkplace_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWorkplace_TextV.setText("Select a Work Place");
                addWorkplace_btn.setVisibility(View.GONE);
                cancelWorkplace_btn.setVisibility(View.GONE);
            }
        });
        return root;
    }

    private JsonObjectRequest updateWorkplace() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("WorkUser", userEmail);
            jsonObject.put("Workplace", workplace_tobe_added);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, post_Workplace, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Unable to get Workplace", Toast.LENGTH_LONG);
            }
        });
        return jsonObjectRequest;
    }

    private void displayWorkplace(String workplace) {
        workplace_TextView.setText(workplace);
    }
}