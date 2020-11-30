package com.example.logindemo2;

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

public class MyCovidData_UpdateClasses extends Fragment {


    public static MyCovidData_UpdateClasses newInstance() {
        return new MyCovidData_UpdateClasses();
    }

    final String postCourseList = "https://covidtrackerdev.herokuapp.com/post_courselist";
    final String getCourseList = "https://covidtrackerdev.herokuapp.com/get_courselist";

    ArrayList<CheckBox> boxes;
    String Gmail;
    ArrayList<String> CIDS;
    private static Pattern VALID_CRN_REGEX = Pattern.compile("^[A-Z]{3}\\d{3}(HY1|HY2|HY3|HY4|HY5|\\d{3})$");


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View screen = inflater.inflate(R.layout.fragment_my_covid_data_update_classes, container, false);
        CIDS = new ArrayList<>();
        //==========================================================================================
        // Initalises starting variables
        //==========================================================================================
        boxes = new ArrayList<>();

        boxes.add(screen.findViewById(R.id.cid1));
        boxes.add(screen.findViewById(R.id.cid2));
        boxes.add(screen.findViewById(R.id.cid3));
        boxes.add(screen.findViewById(R.id.cid4));
        boxes.add(screen.findViewById(R.id.cid5));
        boxes.add(screen.findViewById(R.id.cid6));
        boxes.add(screen.findViewById(R.id.cid7));
        boxes.add(screen.findViewById(R.id.cid8));
        boxes.add(screen.findViewById(R.id.cid9));
        boxes.add(screen.findViewById(R.id.cid10));
        Button add = screen.findViewById(R.id.Add);
        Button remove = screen.findViewById(R.id.button14);

        TextView newClass = screen.findViewById(R.id.newclass);
        //sets up volley queue
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.start();

        //set up the gmail
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(getActivity());
        Gmail = acc.getEmail();
        System.out.println("My user gmail: " + Gmail);
        //==========================================================================================
        //fill checkboxes on startup
        //==========================================================================================
        JSONObject o = new JSONObject();
        try {
            o.put("studentEmail", Gmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST, getCourseList, o, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jarray = response.getJSONArray("CourseId");
                    System.out.println(jarray.toString());
                    for (int i = 0; i < jarray.length(); i++) {
                        CIDS.add(jarray.get(i).toString());
                    }
                    displayEmails(CIDS);
                } catch (JSONException e) {
                    System.out.println("error");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Unable to get Classes", Toast.LENGTH_LONG);
            }
        });
        queue.add(jsonReq);
        //==========================================================================================
        //Add and update Classes
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputCrn = newClass.getText().toString().toUpperCase();
                if(CIDS.contains(inputCrn)) {
                    Toast.makeText(getActivity(), "The Class is Already in your List", Toast.LENGTH_LONG).show();
                }else if (!validateCrn(inputCrn)) {
                    Toast.makeText(getActivity(), "Please Add a valid CRN", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Class to be added: "+ inputCrn);
                    if(inputCrn.contains("-")){

                    }
                    if (CIDS.size() + 1 > 10) {
                        Toast.makeText(getActivity(), "Class list is Full", Toast.LENGTH_LONG).show();
                    } else {
                        if (CIDS.size() < 10) {
                            CIDS.add(inputCrn);
                        }
                        queue.add(updateClasses());
                        displayEmails(CIDS);
                        newClass.setText("");
                    }
                }
            }
        });
        //==========================================================================================
        //Remove and update Social Circle
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> tempList = new ArrayList<>();
                for (CheckBox b : boxes) {
                    if (b.isChecked()) {
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

    public JsonObjectRequest updateClasses() {
        JSONObject o = new JSONObject();
        JSONArray array = new JSONArray(); // create a JSON array

        for (int i = 0; i < CIDS.size(); i++) { // Put elements of the array into the JSONArrayfdsfsa
            array.put(CIDS.get(i));
        }
        try {
            o.put("studentEmail", Gmail);
            o.put("CourseId", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(array.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postCourseList,
                o, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Unable to get classes", Toast.LENGTH_LONG);
            }
        });
        return jsonObjectRequest;

    }

    public void displayEmails(ArrayList<String> emails) {
        for (int i = 0; i < emails.size(); i++) {
            String s = emails.get(i);
            boxes.get(i).setText(s);
        }
    }

    public static boolean validateCrn(String crn) {
        Matcher matcher = VALID_CRN_REGEX.matcher(crn);
        return matcher.find();
    }
}

