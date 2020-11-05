package com.example.logindemo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;


public class MainActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        BottomNavigationView mBottomNavigationView  = findViewById(R.id.bottomNavigationView);
        //MongoDB Setup
       /* Realm.init(this);
        String appID = "application-1-ykqdi";
        app = new App(new AppConfiguration.Builder(appID).build());
        Credentials credentials = Credentials.anonymous();
        app.loginAsync(credentials, result -> {
            if(result.isSuccess()){
                Log.v("QUICKSTART", "Successfully authenticated anonymously.");
                User user = app.currentUser();
                String partitionValue = "Gmail";
                SyncConfiguration config = new SyncConfiguration.Builder(user, partitionValue).build();
                uiThreadRealm = Realm.getInstance(config);

            }else {
                Log.e("QUICKSTART", "Failed to log in. Error: " + result.getError());
            }
        });
        */


        //Navigation Setup
        AppBarConfiguration appBarConfiguration  = new AppBarConfiguration.Builder(R.id.covidData, R.id.myCovidData, R.id.appSettings).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(mBottomNavigationView, navController);
    }

}