package com.example.cvrecommendation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        upload_file(file_path);
    }

    public void upload_file(String file_path){
//            //---------------------------------------------
        //Retrofit Class defining base url where app needs to post data
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://job-recommendation-api.herokuapp.com/")
                .build();
        //Retrofit class generates an implementation of Api interface
        Api api = retrofit.create(Api.class);

            //pass it like this
            File file = new File(file_path);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // MultipartBody.Part is used to send also the actual file name
                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("pdf", file.getName(), requestFile);

            // add another part within the multipart request
                    RequestBody description =
                            RequestBody.create(MediaType.parse("multipart/form-data"), "myPdf");

        //This method POST request and we receive return data in response
        api.uploadcv(description, body).enqueue(new Callback<ResponseBody>() {        //add access token
            //Method executed if we receive response from server
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
////
                    if (response.isSuccessful()) {
                        String api_response = response.body().string();
                        System.out.println(api_response);
                        JSONObject jsonOb = new JSONObject(api_response);
//                        String access_token = jsonOb.optString("access_token");
//                        System.out.println(access_token);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid email_address or Password", Toast.LENGTH_SHORT).show();
//                        loginButton.setClickable(true);
                    }

                } catch (Exception e) {
                    System.out.println("response catch exception : " + e);
                    e.printStackTrace();
//                    loginButton.setClickable(true);

                }

            }

            //Method executed if request fails
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t.toString().equals("java.net.UnknownHostException:" +
                        " Unable to resolve host \"prediction1-rest-api.herokuapp.com\": No address associated with hostname")) {
                    Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Server Not Responding. Please Try Again, Later.", Toast.LENGTH_SHORT).show();
                }
//                loginButton.setClickable(true);
            }
        });


        //---------------------------------------------------

        // create upload service client
//            FileUploadService service =
//                    ServiceGenerator.createService(FileUploadService.class);
//
//            // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
//            // use the FileUtils to get the actual file by uri
//            File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
//            RequestBody requestFile =
//                    RequestBody.create(
//                            MediaType.parse(getContentResolver().getType(file_uri)),
//                            file_uri
//                    );

//            // MultipartBody.Part is used to send also the actual file name
//            MultipartBody.Part body =
//                    MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
//
//            // add another part within the multipart request
//            String descriptionString = "hello, this is description speaking";
//            RequestBody description =
//                    RequestBody.create(
//                            okhttp3.MultipartBody.FORM, descriptionString);

//            // finally, execute the request
//            Call<ResponseBody> call = Api.upload(description, body);
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

    }






}
