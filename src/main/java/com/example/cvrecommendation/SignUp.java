package com.example.cvrecommendation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUp extends AppCompatActivity {
    View signup2ConstraintLoayout;
    View signup1ConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signup1ConstraintLayout = findViewById(R.id.signup1ConstraintLayout);
        signup2ConstraintLoayout = findViewById(R.id.signup2ConstraintLayout);
    }


    public void signup(View view){
        signup1ConstraintLayout.setVisibility(View.INVISIBLE);
        signup2ConstraintLoayout.setVisibility(View.VISIBLE);
    }


    public void letsGetStarted(View view){
        Intent intent = new Intent(getApplicationContext(), LetsGetStarted.class);
        startActivity(intent);
    }
}
