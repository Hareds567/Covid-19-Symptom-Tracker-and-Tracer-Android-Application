package com.example.logindemo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {
    private GoogleSignInOptions gso;
    private GoogleSignInAccount mGoogleSignInAccount;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInResult mGoogleSignInResult;

    private TextView name_TextView;
    private TextView email_TextView;
    private TextView userType_TextView;
    private Button signOut_Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        name_TextView = findViewById(R.id.name_TextView);
        email_TextView = findViewById(R.id.email_Textview);
        userType_TextView = findViewById(R.id.userType_TextView);
        signOut_Btn = findViewById(R.id.singOut_Button);

        //Set up the gso to get the user information.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestProfile().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        signOut_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.singOut_Button:
                        signOut();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        name_TextView.setText(acct.getDisplayName());
        email_TextView.setText(acct.getEmail());

    }

    private void handleSignInResult(GoogleSignInResult result){
        mGoogleSignInAccount = result.getSignInAccount();

    }
    private void signOut(){
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                goToLoginActivity();
            }
        });
    }
    private void goToLoginActivity(){
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        finish();
    }
}