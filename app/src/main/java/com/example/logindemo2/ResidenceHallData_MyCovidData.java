package com.example.logindemo2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

import org.json.JSONException;
import org.json.JSONObject;


public class ResidenceHallData_MyCovidData extends Fragment {

    private final String getSocialURL = "https://covidtrackerdev.herokuapp.com/testcsv";
    private final String sendalertURL = "https://covidtrackerdev.herokuapp.com/post_send_alert";

    private TextView displayResidence_TextView;

    //==============================================================================================
    //Google Sign in
    private String userGmail;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleSignInAccount;

    //==============================================================================================
    private RadioButton PositiveRes;
    private RadioButton NegRes;
    private TextView LastRes;
    private Button Confirm;
    private String Res;
    private RadioGroup options;

    public ResidenceHallData_MyCovidData() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //=============================================================================================
    //Set Up for the screen
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_residence_hall_data__my_covid_data, container, false);
        //=========================================================================================
        //Processes the google login
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),gso);
        mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
        userGmail  = mGoogleSignInAccount.getEmail();

        //==========================================================================================
        // sets up volley
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.start();

        //sets up buttons and text fields
        PositiveRes = rootView.findViewById(R.id.radioButton3);
        NegRes =  rootView.findViewById(R.id.radioButton4);
        Confirm = rootView.findViewById(R.id.button6);
        LastRes = rootView.findViewById(R.id.textView11);
        options = rootView.findViewById(R.id.radioGroup);

        //==========================================================================================
        //Get new test results



        //==========================================================================================
        //React to buttons
        Confirm.setVisibility(View.GONE);
        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Confirm.setVisibility(View.VISIBLE);
                Confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checkedId == R.id.radioButton3){
                            JSONObject o = new JSONObject();
                            try {
                                o.put("Email",userGmail);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, sendalertURL,
                                    o, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity(),"Unable to get Send Alert",Toast.LENGTH_LONG);
                                }
                            });
                            queue.add(jsonObjectRequest);

                        }else {
                            //send neg
                        }
                        Confirm.setVisibility(View.GONE);
                    }
                });

            }
        });
        return rootView;
    }


}