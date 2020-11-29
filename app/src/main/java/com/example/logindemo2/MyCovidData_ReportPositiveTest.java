package com.example.logindemo2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.json.JSONException;
import org.json.JSONObject;


public class MyCovidData_ReportPositiveTest extends Fragment {
    private final String get_isAllowed = "https://covidtrackerdev.herokuapp.com/get_allowed_to_report";
    private final String post_self_report = "https://covidtrackerdev.herokuapp.com/post_self_report";

    private final String sendAlertSocial = "https://covidtrackerdev.herokuapp.com/post_send_alert";
    private final String sendAlertClass = "https://covidtrackerdev.herokuapp.com/post_class_alert";
    private final String sendAlertWorkplace = "https://covidtrackerdev.herokuapp.com/post_alert_workplace";

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
    private Boolean success;

    public MyCovidData_ReportPositiveTest() {
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
        final View rootView = inflater.inflate(R.layout.fragment_my_covid_data_report_positive_test, container, false);
        success = false;
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
        //Get new test results availability
        JSONObject allowed = new JSONObject();
        try {
            allowed.put("ReportUser", userGmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String requestAllowNewResult = allowed.toString();
        BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, get_isAllowed,requestAllowNewResult, new Response.Listener<Boolean>(){
            @Override
            public void onResponse(Boolean response) {
                System.out.println("First Test Result Response: " + response);
                success = response;
                System.out.println("Inside Boolean Request: "+ success);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error: " + error);
            }
        });
        queue.add(booleanRequest);

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


                            if (checkedId == R.id.radioButton3) {
                                if(success) {
                                    JSONObject jsonUserGmail = new JSONObject();
                                    try {
                                        jsonUserGmail.put("Email", userGmail);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //Post request to notify user's to user's social circle
                                    JsonObjectRequest requestAlertSocial = new JsonObjectRequest(Request.Method.POST, sendAlertSocial,
                                            jsonUserGmail, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getActivity(), "Unable to get Send Alert", Toast.LENGTH_LONG);
                                        }
                                    });
                                    //Post request to notify user's classmates
                                    JsonObjectRequest requestAlertCourse = new JsonObjectRequest(Request.Method.POST, sendAlertClass,
                                            jsonUserGmail, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getActivity(), "Unable to get Send Alert", Toast.LENGTH_LONG);
                                        }
                                    });
                                    //Post request to notify user's coo-workers.
                                    JsonObjectRequest requestAlertWorkplace = new JsonObjectRequest(Request.Method.POST, sendAlertWorkplace,
                                            jsonUserGmail, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getActivity(), "Unable to get Send Alert", Toast.LENGTH_LONG);
                                        }
                                    });

                                    queue.add(requestAlertSocial);
                                    queue.add(requestAlertCourse);
                                    queue.add(requestAlertWorkplace);
                                    queue.add(updateSelfReportModel());
                                    MyCovidData_ReportPositiveTest currentFragment = new MyCovidData_ReportPositiveTest();
                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.nav_host_fragment, currentFragment,currentFragment.getTag());
                                    fragmentTransaction.commit();
                                    Toast.makeText(getActivity(), "Thank You for Submitting Your Test Results!", Toast.LENGTH_LONG);
                                } else {
                                    System.out.println("You already submitted a Positive Test Result");
                                    Toast.makeText(getActivity(), "You already submitted a Positive Test Result", Toast.LENGTH_LONG);
                                }
                            } else {
                                //send neg
                            }
                            Confirm.setVisibility(View.GONE);
                    }
                });

            }
        });
        return rootView;
    }

    private JsonObjectRequest updateSelfReportModel(){
        JSONObject reportUser = new JSONObject();
        try {
            reportUser.put("ReportUser", userGmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, post_self_report, reportUser, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        return request;
    }
}