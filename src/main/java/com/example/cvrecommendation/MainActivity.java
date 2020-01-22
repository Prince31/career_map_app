package com.example.cvrecommendation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen_check();

    }

    public void screen_check(){
        // Retrieving the value using its keys the file name must be same in both saving and retrieving the data
//        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_APPEND);
//        // The value will be default as empty string because for the very first time when the app is opened, there is nothing to show
//        String access_token = sharedPreferences.getString("access_token", "");
//        if (access_token==""){
//
//        }
    }

    public void signupScreen(View view){
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }

    public void loginScreen(View view){
        Intent intent = new Intent(getApplicationContext(), LogIn.class);
        startActivity(intent);



    }

}
