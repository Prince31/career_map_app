package com.example.cvrecommendation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.widget.Toast;

public class LetsGetStarted extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_get_started);
    }

    //to enable app exit on double back press only
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
////            super.onBackPressed();
////            return;
//            this.finishAffinity();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//        //Handler().postDelayed will call run after 2 seconds to set doubleBackToExitPressedOnce = false
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }

}
