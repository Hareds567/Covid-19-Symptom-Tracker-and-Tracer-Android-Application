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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MySocialCircle_MyCovidData extends Fragment {
    private static Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(oswego)\\.edu$") ;
    private final String getSocialURL = "https://covidtrackerdev.herokuapp.com/get_social_circle";
<<<<<<< HEAD
    private final String postSocialUrl = "https://covidtrackerdev.herokuapp.com/post_social_circle";
=======
    private final String postSocialURL = "https://covidtrackerdev.herokuapp.com/post_social_circle";
>>>>>>> db0c8867632d27903461df3399e87e58db717ca6
    private CheckBox box1;CheckBox box2;CheckBox box3;CheckBox box4;CheckBox box5;CheckBox box6;CheckBox box7;CheckBox box8;CheckBox box9;
    private Button remove;Button add;
    private TextView newGmail;
    private ArrayList<String>gmails = new ArrayList<>();
    private ArrayList<CheckBox> DisplayedBoxes = new ArrayList<>();
    private String Gmail;




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
        remove = screen.findViewById(R.id.button);
        add = screen.findViewById(R.id.button2);
        //initalise text field
        newGmail = screen.findViewById(R.id.newGmail);



        //sets up volley queue
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.start();
        //set up the gmail
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(getActivity());
        Gmail = acc.getEmail();
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
                    displayEmails();
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
        //==========================================================================================
        //Add and update Social Circle
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = newGmail.getText().toString();
                if(!validateEmail(inputEmail)){
                    Toast.makeText(getActivity(),"Please Enter an School email", Toast.LENGTH_LONG).show();
                }else if(gmails.size() + 1 > 9 ){
                    Toast.makeText(getActivity(),"Social Circle is Full", Toast.LENGTH_LONG).show();
                }else {
                    if (gmails.size() < 9) {
                        gmails.add(inputEmail);
                    }
                    queue.add(updateSocial());
                    displayEmails();
                }
            }
        });
        //==========================================================================================
        //Remove and update Social Circle
        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                for(int i = 0; i < DisplayedBoxes.size(); i++){
                    if(DisplayedBoxes.get(i).isChecked()){
                        gmails.remove(i);
                    }
                }
                queue.add(updateSocial());
            }
        });
<<<<<<< HEAD

        return screen;
    }

    //==============================================================================================

    public JsonObjectRequest updateSocial (){
        JSONObject o = new JSONObject();
        JSONArray array = new JSONArray(); // create a JSON array

        for (int i =0; i < gmails.size(); i++){ // Put elements of the array into the JSONArray
            array.put(gmails.get(i));
        }
        try {
            o.put("CircleUser", Gmail);
            o.put("SocialCircle",array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postSocialUrl,
                o, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Unable to get Social Circle",Toast.LENGTH_LONG);
            }
        });
        return jsonObjectRequest;

    }


    public void displayEmails() {
        for(int i =0 ; i< gmails.size();i++){
            String s = gmails.get(i);
=======
        Submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
              JSONObject o = new JSONObject();
                try {
                    o.put("CircleUser",Gmail);
                    o.put("SocialCircle",GetEmails());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getSocialURL,
                        o, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Unable to get Social Circle",Toast.LENGTH_LONG);
                    }
                });
                queue.add(jsonObjectRequest);
            }
        });
        return screen;
    }
    public ArrayList<String> GetEmails(){
        ArrayList<String>out = new ArrayList<>();
        for(CheckBox d: DisplayedBoxes){
            out.add(d.getText().toString());
        }
        return out;
    }
    public void displayEmails(ArrayList<String> emails) {
        for(int i =0 ; i<emails.size();i++){
            String s = emails.get(i);
>>>>>>> db0c8867632d27903461df3399e87e58db717ca6
            DisplayedBoxes.get(i).setText(s);
        }
    }

    public static boolean validateEmail(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}