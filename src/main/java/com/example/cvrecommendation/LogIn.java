package com.example.cvrecommendation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LogIn extends AppCompatActivity {

    EditText emailEditText;
    EditText passEditText;
    static Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailEditText = findViewById(R.id.emailEditText);
        passEditText = findViewById(R.id.passEditText);
        loginButton = findViewById(R.id.loginScreenButton);

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
            MainActivity.loginScreenButton.setClickable(true);
        } catch (Exception e){
            e.printStackTrace();
        }
        super.onBackPressed();
        return;
    }


    //Function To check if all editText fields have valid values before posting data to server
    public boolean inputFieldValidation(String email_address, String password){
        if (email_address.equals("")) {
            emailEditText.requestFocus();
            Toast.makeText(this, "Please fill all fields and press login", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            passEditText.requestFocus();
            Toast.makeText(this, "Please fill all fields and press login", Toast.LENGTH_SHORT).show();
        } else if (!email_address.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            emailEditText.requestFocus();
            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        loginButton.setClickable(true);
        return false;
    }

    //**Another location of login at SignUp.java file
    public void login(View view){
        loginButton.setClickable(false);
        String email_address= emailEditText.getText().toString();
        String password= passEditText.getText().toString();
        boolean inputFieldCheck = inputFieldValidation(email_address, password);
        if(inputFieldCheck) {
            String sign_in_json = "{\"email_address\": \"" + email_address + "\", \"password\": \"" + password + "\"}";
            System.out.println(sign_in_json);
//        String sign_in_json = "{'email_address': {}, 'password': {}}".format(emailEditText.getText().toString(), passEditText.getText().toString());


            //Retrofit Class defining base url where app needs to post data
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://job-recommendation-api.herokuapp.com/")
                    .build();
            //Retrofit class generates an implementation of Api interface
            Api api = retrofit.create(Api.class);

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), sign_in_json);
            //This method POST request and we receive return data in response
            api.postUser(requestBody).enqueue(new Callback<ResponseBody>() {
                //Method executed if we receive response from server
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
////
                        if (response.isSuccessful()) {
                            String api_response = response.body().string();
                            System.out.println("api_response"+api_response);
                            JSONObject jsonOb = new JSONObject(api_response);
                            String access_token = jsonOb.optString("access_token");
                            System.out.println(access_token);
                            String email_address_received = jsonOb.optString("email_address");
                            System.out.println(email_address_received);
                            // Storing data into SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            // Creating an Editor object to edit(write to the file)
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            // Storing the key and its value as the data fetched from edittext
                            myEdit.putString("access_token", access_token);
                            myEdit.putString("email_address", email_address_received);
                            // Once the changes have been made, we need to commit to apply those changes made, otherwise, it will throw an error
                            myEdit.commit();

                            String name = jsonOb.optString("name");
                            System.out.println(name);
                            String email_address = jsonOb.optString("email_address");

                            Intent intent = new Intent(getApplicationContext(), LetsGetStarted.class);  //go with access token verification
                            intent.putExtra("user_name", name);
                            intent.putExtra("access_token", access_token);
                            intent.putExtra("email_address", email_address);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid email_address or Password", Toast.LENGTH_SHORT).show();
                            loginButton.setClickable(true);
                        }

                    } catch (Exception e) {
                        System.out.println("response catch exception : " + e);
                        e.printStackTrace();
                        loginButton.setClickable(true);

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
                    loginButton.setClickable(true);
                }
            });
        }
    }
}

