package com.example.logindemo2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyCovidData_UpdateWorkplace extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String get_Workplace = "https://covidtrackerdev.herokuapp.com/get_workplace";

    private String mParam1;
    private String mParam2;
    private String userEmail;
    private Button addButton;
    private Button deleteButton;
    private TextView workplace_TextView;

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
        final View root = inflater.inflate(R.layout.fragment_my_covid_data__update_workplace, container, false);
        addButton = root.findViewById(R.id.add_workplace_btn);
        deleteButton = root.findViewById(R.id.delete_workplace_btn);
        workplace_TextView = root.findViewById(R.id.editTextTextPersonName);
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(getActivity());
        userEmail = acc.getEmail();

        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        JSONObject email = new JSONObject();
        try {
            email.put("WorkUser", userEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, get_Workplace,
                email, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               try {
                    System.out.println("Workplace test " + response);
                    String workplace = response.get("Workplace").toString();
                    workplace_TextView.setText(workplace);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Unable to get Social Circle", Toast.LENGTH_LONG);
            }
        });
        queue.add(jsonObjectRequest);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return root;
    }
}