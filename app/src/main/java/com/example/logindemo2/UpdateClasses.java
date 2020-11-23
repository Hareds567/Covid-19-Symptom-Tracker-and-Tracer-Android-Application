package com.example.logindemo2;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateClasses extends Fragment {


    public static UpdateClasses newInstance() {
        return new UpdateClasses();
    }
    final String post_courselist ="https://covidtrackerdev.herokuapp.com/post_courselist";
    final String get_courselist ="https://covidtrackerdev.herokuapp.com/get_courselist";

    ArrayList<CheckBox> boxes;
    String Gmail;
    ArrayList<String>CIDS;
    private static Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(oswego)\\.edu$");


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       final View screen = inflater.inflate(R.layout.update_classes_fragment, container, false);
        CIDS = new ArrayList<>();
        //==========================================================================================
        // Initalises starting variables
        //==========================================================================================
        boxes = new ArrayList<>();
        boxes.add(screen.findViewById(R.id.cid10));boxes.add(screen.findViewById(R.id.cid9));
        boxes.add(screen.findViewById(R.id.cid8));boxes.add(screen.findViewById(R.id.cid7));
        boxes.add(screen.findViewById(R.id.cid6));boxes.add(screen.findViewById(R.id.cid5));
        boxes.add(screen.findViewById(R.id.cid4));boxes.add(screen.findViewById(R.id.cid3));
        boxes.add(screen.findViewById(R.id.cid2));boxes.add(screen.findViewById(R.id.cid1));

        Button add = screen.findViewById(R.id.Add);
        Button remove = screen.findViewById(R.id.button14);

        TextView newClass = screen.findViewById(R.id.newclass);
        //sets up volley queue
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.start();

        //set up the gmail
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(getActivity());
        Gmail = acc.getEmail();

        //==========================================================================================
        //fill checkboxes on startup
        //==========================================================================================
        JSONObject o = new JSONObject();
        try {
            o.put("StudentEmail",Gmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST, get_courselist, o, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jarray = response.getJSONArray("CourseID");
                    for(int i = 0;i<jarray.length();i++){
                        CIDS.add(jarray.get(i).toString());
                    }
                    displayEmails(CIDS);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Unable to get Classes",Toast.LENGTH_LONG);
            }
        });
        queue.add(jsonReq);
        //==========================================================================================
        //Add and update Social Circle
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = newClass.getText().toString();
                if(!validateEmail(inputEmail)){
                    Toast.makeText(getActivity(),"Please Enter an School email", Toast.LENGTH_LONG).show();
                }else if(CIDS.size() + 1 > 9 ){
                    Toast.makeText(getActivity(),"Class list is Full", Toast.LENGTH_LONG).show();
                }else {
                    if (CIDS.size() < 9) {
                        CIDS.add(inputEmail);
                    }
                    queue.add(updateClasses());
                    displayEmails(CIDS);
                }
            }
        });
        //==========================================================================================
        //Remove and update Social Circle
        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ArrayList<String>tempList = new ArrayList<>();
                for(CheckBox b : boxes){
                    if(b.isChecked()){
                        tempList.add(b.getText().toString());
                        b.setText("");
                        b.toggle();
                    }//else tempList.add(b.getText().toString());
                }
                CIDS.removeAll(tempList);
                queue.add(updateClasses());
                displayEmails(CIDS);
            }
        });

        return screen;
    }

    public JsonObjectRequest updateClasses (){
        JSONObject o = new JSONObject();
        JSONArray array = new JSONArray(); // create a JSON array

        for (int i =0; i < CIDS.size(); i++){ // Put elements of the array into the JSONArray
            array.put(CIDS.get(i));
        }
        try {
            o.put("StudentEmail", Gmail);
            o.put("CourseId",array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, post_courselist,
                o, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Unable to get classes",Toast.LENGTH_LONG);
            }
        });
        return jsonObjectRequest;

    }


    public void displayEmails(ArrayList<String> emails) {
        for(int i =0 ; i< emails.size();i++){
            String s = emails.get(i);
           boxes.get(i).setText(s);
        }
    }

    public static boolean validateEmail(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}

