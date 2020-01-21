package com.example.cvrecommendation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LogIn extends AppCompatActivity {

    EditText emailEditText;
    EditText passEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailEditText = findViewById(R.id.emailEditText);
        passEditText = findViewById(R.id.passEditText);
    }

    public void letsGetStarted(View view){
        String email_pass_json = "{'username': {}, 'password': {}}".format(emailEditText.getText().toString(), passEditText.getText().toString());
        String api_response = MainActivity.retrofitLibrary("email_pass_json");
        System.out.println(api_response);
        if(api_response=="{\n" +
                "    \"access_token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1Nzk1OTk4MTUsImlhdCI6MTU3OTU5OTUxNSwibmJmIjoxNTc5NTk5NTE1LCJpZGVudGl0eSI6MX0.MgYo4_SKrR-I22IAXPo06Wkpq1KiN1mh3u9kwMHYIAU\"\n" +
                "}") {
            Intent intent = new Intent(getApplicationContext(), LetsGetStarted.class);
            startActivity(intent);
        } else if(api_response=="{\n" +
                "    \"description\": \"Invalid credentials\",\n" +
                "    \"error\": \"Bad Request\",\n" +
                "    \"status_code\": 401\n" +
                "}") {
            Toast.makeText(getApplicationContext(),"Invalid User Id or Password", Toast.LENGTH_SHORT).show();
        }
        }
    }

