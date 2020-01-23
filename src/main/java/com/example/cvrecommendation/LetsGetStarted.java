package com.example.cvrecommendation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LetsGetStarted extends AppCompatActivity {

    TextView welcomeTextView;
    Intent file_choose_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_get_started);

        welcomeTextView = findViewById(R.id.welcomeTextView);

        welcomeScreen();
    }

    //Method to hide keyboard when we touch outside editText fields in Android App
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

        @Override
    public void onBackPressed() {
            try {
                LogIn.loginButton.setClickable(true);
            } catch (Exception e){
                e.printStackTrace();
            }
            try {
                SignUp.signupButton.setClickable(true);
            } catch (Exception e){
                e.printStackTrace();
            }
            super.onBackPressed();
            return;
    }

    public void welcomeScreen(){


        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        String user_name="";

        if (extras != null) {
            user_name = intent.getExtras().getString("user_name", "guest");
        }
        welcomeTextView.setText("Welcome "+user_name);
    }

    public void chooseFile(View view){
        file_choose_intent = new Intent(Intent.ACTION_GET_CONTENT);
        file_choose_intent.setType("application/pdf");
        startActivityForResult(file_choose_intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String file_path="";
        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    file_path = Objects.requireNonNull(data.getData()).getPath();
                    welcomeTextView.setText(file_path);
                }

                break;
        }
        Uri file_uri= Uri.fromFile(new File(file_path));
//        upload_file(file_uri);
    }

//    public void upload_file(Uri file_uri){
////            // create upload service client
////            FileUploadService service =
////                    ServiceGenerator.createService(FileUploadService.class);
////
////            // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
////            // use the FileUtils to get the actual file by uri
////            File file = FileUtils.getFile(this, fileUri);
//
//            // create RequestBody instance from file
//            RequestBody requestFile =
//                    RequestBody.create(
//                            MediaType.parse(getContentResolver().getType(file_uri)),
//                            file_uri
//                    );
//
//            // MultipartBody.Part is used to send also the actual file name
//            MultipartBody.Part body =
//                    MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
//
//            // add another part within the multipart request
//            String descriptionString = "hello, this is description speaking";
//            RequestBody description =
//                    RequestBody.create(
//                            okhttp3.MultipartBody.FORM, descriptionString);
//
//            // finally, execute the request
//            Call<ResponseBody> call = service.upload(description, body);
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call,
//                                       Response<ResponseBody> response) {
//                    Log.v("Upload", "success");
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Log.e("Upload error:", t.getMessage());
//                }
//            });
//
//    }

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
