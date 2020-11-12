package com.example.logindemo2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;




public class MySocialCircle_MyCovidData extends Fragment {
    private final String getSocialURL = "https://covidtrackerdev.herokuapp.com/get_social_circle";
    private CheckBox box1;CheckBox box2;CheckBox box3;CheckBox box4;CheckBox box5;CheckBox box6;CheckBox box7;CheckBox box8;CheckBox box9;
    private Button Submit;Button Enter;
    private TextView newGmail;
    private ArrayList<String>gmails = new ArrayList<>();
    private ArrayList<CheckBox> DisplayedBoxes = new ArrayList<>();

    public MySocialCircle_MyCovidData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View screen = inflater.inflate(R.layout.fragment_my_social_circle__my_covid_data, container, false);
        box1 = screen.findViewById(R.id.checkBox);
        box2 = screen.findViewById(R.id.checkBox2);
        box3 = screen.findViewById(R.id.checkBox3);
        box4 = screen.findViewById(R.id.checkBox4);
        box5 = screen.findViewById(R.id.checkBox5);
        box6 = screen.findViewById(R.id.checkBox6);
        box7 = screen.findViewById(R.id.checkBox7);
        box8 = screen.findViewById(R.id.checkBox8);
        box9 = screen.findViewById(R.id.checkBox9);
        //Sets up an array of check boxes
        //ArrayList<CheckBox> DisplayedBoxes = new ArrayList<>();
        //add checkboxes to arraylist
        DisplayedBoxes.add(box1);DisplayedBoxes.add(box2);DisplayedBoxes.add(box3);DisplayedBoxes.add(box4);DisplayedBoxes.add(box5);DisplayedBoxes.add(box6);DisplayedBoxes.add(box7);DisplayedBoxes.add(box8);DisplayedBoxes.add(box9);
        //initalises both buttons
        Enter = screen.findViewById(R.id.button);
        Submit = screen.findViewById(R.id.button2);
        //initalise text field
        newGmail = screen.findViewById(R.id.newGmail);



        //sets up volley queue
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.start();
        //set up the gmail
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(getActivity());
        String Gmail = acc.getEmail();
        System.out.println("User Gmail: "+ Gmail);

        //makes a Json object containg the gmail we want to search for
        HashMap<String, String> param = new HashMap<>();
        param.put("CircleUser", Gmail);
        //==========================================================================================
        //sends a Request to node for the social circle array associated with the gmail
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getSocialURL,
                new JSONObject(param), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jArray = response.getJSONArray("SocialCircle");
                    System.out.println(jArray.toString());
                    for(int i = 0; i < jArray.length();i++){
                        gmails.add(jArray.get(i).toString());
                        //DisplayedBoxes.get(i).setText(gmails.get(i));
                    }
                    displayEmails(gmails);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Unable to get Social Circle",Toast.LENGTH_LONG);
            }
        });
        queue.add(jsonObjectRequest);

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String>tempList = new ArrayList<>();
                for(CheckBox b : DisplayedBoxes){
                    if(b.isChecked()){
                        b.setText("");
                    }else tempList.add(b.getText().toString());
                }
                if(tempList.size()<9){
                    tempList.add(newGmail.getText().toString());
                }
                displayEmails(tempList);
            }
        });
        return screen;
    }

    public void displayEmails(ArrayList<String> emails) {
        for(int i =0 ; i<emails.size();i++){
            String s = emails.get(i);
            DisplayedBoxes.get(i).setText(s);
        }
    }
}