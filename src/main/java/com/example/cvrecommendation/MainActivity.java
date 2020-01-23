package com.example.cvrecommendation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    ConstraintLayout losignConstraintLayout;
    ConstraintLayout loadingConstraintLayout;
    Button loginScreenButton;
    Button signupScreenButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        losignConstraintLayout = findViewById(R.id.losignConstraintLayout);
        loadingConstraintLayout = findViewById(R.id.loadingConstraintLayout);
        loginScreenButton = findViewById(R.id.loginScreenButton);
        signupScreenButton = findViewById(R.id.signupScreenButton);

        screen_check();
    }

    public void screen_check(){
         //Retrieving the value using its keys the file name must be same in both saving and retrieving the data
        SharedPreferences sharedPreferences = this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        // The value will be default as empty string because for the very first time when the app is opened, there is nothing to show
        String access_token = sharedPreferences.getString("access_token", null);
        if (access_token==null){
            loadingConstraintLayout.setVisibility(View.INVISIBLE);
            losignConstraintLayout.setVisibility(View.VISIBLE);
        } else{
            //to send token to identity to verify before opening third screen
            System.out.println(access_token);
            loadingConstraintLayout.setVisibility(View.INVISIBLE);      //remove it
            losignConstraintLayout.setVisibility(View.VISIBLE);         //remove it
        }
    }

    public void signupScreen(View view){
        signupScreenButton.setClickable(false);
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }

    public void loginScreen(View view){
        loginScreenButton.setClickable(false);
        Intent intent = new Intent(getApplicationContext(), LogIn.class);
        startActivity(intent);
    }

}
