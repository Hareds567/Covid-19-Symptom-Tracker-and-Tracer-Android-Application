package com.example.logindemo2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CovidData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CovidData extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Test" ;
    private static final String WEBSITE_SURVEY_URL ="https://oswego.medicatconnect.com/login.aspx";
    private static final String WEBSITE_INFO_URL ="https://ww1.oswego.edu/oswego-forward/covid-19-dashboard";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GoogleSignInAccount mGoogleSignInAccount;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView test_textView;
    private Button dailySurveyBtn;
    private Button covidInfoBtn;

    public CovidData() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CovidData.
     */
    // TODO: Rename and change types and number of parameters
    public static CovidData newInstance(String param1, String param2) {
        CovidData fragment = new CovidData();
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
            final View rootView = inflater.inflate(R.layout.fragment_covid_data, null);

            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),gso);
            test_textView = rootView.findViewById(R.id.test_textView);
            dailySurveyBtn = rootView.findViewById(R.id.DailySurveyBtn_CovidData);
            covidInfoBtn = rootView.findViewById(R.id.CovidInfoBtn_CovidData);

             dailySurveyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(WEBSITE_SURVEY_URL));
                startActivity(intent);
            }
        });
            covidInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(WEBSITE_INFO_URL));
                startActivity(intent);
            }
        });


            return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(getActivity());
        //String text = acc.getDisplayName() + " " + acc.getEmail();
        //test_textView.setText(text);

    }
}