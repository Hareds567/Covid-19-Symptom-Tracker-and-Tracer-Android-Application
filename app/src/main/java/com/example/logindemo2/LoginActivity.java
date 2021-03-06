package com.example.logindemo2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    //Regex that checks for Oswego school email format
    private static Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(oswego)\\.edu$") ;
    //Static variables used for Google Login
    private static final int SIGN_IN = 1;
    private static final String TAG = "Fail";
    //Variables that are used to connect to Google API
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleSignInAccount;
    //Setting up Button
    private SignInButton signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        //Connect Buttons and their xml content using ID
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        //Add click listeners to buttons
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.sign_in_button:
                        signIn();
                        break;

                }
            }
        });
    }

    // WHen the app launches the program checks if the user has already LogIn.
    //If the user has the program proceeds to the HomeActivity.
    //Else the user stays on the Login Activity
    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account !=null){
            goToHomeActivity();
        }else{

        }
    }

    //Creates an Intent from Google API to LogIn and store it into a Intent that calls onActivityResult
    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,SIGN_IN);
    }
    // If The request code is SIGN_IN
    // Creates a task to handle all User information when the user Sign in
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if(task.isSuccessful()) {
                handleSignInResult(task);
            }
        }
    }
    //Handles SignIn Results, after login do something
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            mGoogleSignInAccount = completedTask.getResult(ApiException.class);
            if(!validateEmail(mGoogleSignInAccount.getEmail())){ //if email does not match the regex sign out and display a message on the screen
                Toast.makeText(this,"Please Login with the School Email", Toast.LENGTH_LONG).show();
                signOut();
            }else{
                goToHomeActivity();
            }
        }catch (ApiException e){
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
    //Change to Main Activity and end the life cycle of LoginActivity
    private void goToHomeActivity(){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
    //SignOut the user
    private void signOut(){
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
    //Checks if emails format matches with the regular expression
    public static boolean validateEmail(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}
