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
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUp extends AppCompatActivity {
    EditText nameEditText;
    EditText emailEditText;
    EditText phoneEditText;
    EditText passEditText;
    EditText zipCodeEditText;
    RadioButton tcRadioButton;
    static Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passEditText = findViewById(R.id.passEditText);
        zipCodeEditText = findViewById(R.id.zipCodeEditText);
        tcRadioButton= findViewById(R.id.tcRadioButton);
        signupButton = findViewById(R.id.signupButton);

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
            MainActivity.signupScreenButton.setClickable(true);
        } catch (Exception e){
            e.printStackTrace();
        }
        super.onBackPressed();
        return;
    }


    public static boolean isValidPassword(String password) {
        Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{4,20})").matcher(password);
        return matcher.matches();
    }


    //Function To check if all editText fields have valid values before posting data to server
    public boolean inputFieldValidation(String email_address, String password, String name, String phone_number, String zip_code){
        if (email_address.equals("")) {
            emailEditText.requestFocus();
            Toast.makeText(this, "Please fill all fields and press login", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            passEditText.requestFocus();
            Toast.makeText(this, "Please fill all fields and press login", Toast.LENGTH_SHORT).show();
        }
        else if (name.equals("")) {
            nameEditText.requestFocus();
            Toast.makeText(this, "Please fill all fields and press login", Toast.LENGTH_SHORT).show();
        }
        else if (phone_number.equals("")) {
            phoneEditText.requestFocus();
            Toast.makeText(this, "Please fill all fields and press login", Toast.LENGTH_SHORT).show();
        }
        else if (zip_code.equals("")) {
            zipCodeEditText.requestFocus();
            Toast.makeText(this, "Please fill all fields and press login", Toast.LENGTH_SHORT).show();
        } else if (!email_address.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            emailEditText.requestFocus();
            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
        } else if (!isValidPassword(password)) {
            passEditText.requestFocus();
            Toast.makeText(this, "Password must contain mix of upper and lower case letters as well as digits and one special character (length: 4-20)", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        signupButton.setClickable(true);
        return false;
    }


    public void signup(View view){
        signupButton.setClickable(false);
        final String email_address = emailEditText.getText().toString();
        final String password = passEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String phone_number = phoneEditText.getText().toString();
        String zip_code = zipCodeEditText.getText().toString();

        boolean inputFieldCheck = inputFieldValidation(email_address, password, name, phone_number, zip_code);
        if(inputFieldCheck) {
            String sign_up_json = String.format("{\"email_address\": \"%1$s\", \"password\": \"%2$s\", " +
                    "\"name\": \"%3$s\", \"phone_number\": \"%4$s\", \"zip_code\": \"%5$s\"}",
                    email_address, password, name, phone_number, zip_code);
            //Retrofit Class defining base url where app needs to post data
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://job-recommendation-api.herokuapp.com/")
                    .build();
            //Retrofit class generates an implementation of Api interface
            Api api = retrofit.create(Api.class);

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), sign_up_json);
            //This method POST request and we receive return data in response
            api.registerUser(requestBody).enqueue(new Callback<ResponseBody>() {
                //Method executed if we receive response from server
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
////
                        if (response.isSuccessful()) {
                            String api_response = response.body().string();
                            JSONObject jsonOb = new JSONObject(api_response);
                            String response_message = jsonOb.optString("message");
                            System.out.println(response_message);
                            Toast.makeText(getApplicationContext(),"User Registered Successfully.", Toast.LENGTH_SHORT).show();
                            loginAfterSignup(email_address, password);

                        } else {
                            Toast.makeText(getApplicationContext(), "User Already registered with this Email Address", Toast.LENGTH_SHORT).show();
                            signupButton.setClickable(true);
                        }

                    } catch (Exception e) {
                        System.out.println("response catch exception : " + e);
                        e.printStackTrace();
                        signupButton.setClickable(true);
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
                    signupButton.setClickable(true);
                }
            });


        }
    }


    public void loginAfterSignup(String email_address, String password){

        String sign_in_json = "{\"email_address\": \"" + email_address + "\", \"password\": \"" + password + "\"}";
        System.out.println(sign_in_json);

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
                        System.out.println(api_response);
                        JSONObject jsonOb = new JSONObject(api_response);
                        String access_token = jsonOb.optString("access_token");
                        System.out.println(access_token);

                        // Storing data into SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        // Creating an Editor object to edit(write to the file)
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        // Storing the key and its value as the data fetched from edittext
                        myEdit.putString("access_token", access_token);
                        // Once the changes have been made, we need to commit to apply those changes made, otherwise, it will throw an error
                        myEdit.commit();

                        String name = jsonOb.optString("name");
                        System.out.println(name);

                        Intent intent = new Intent(getApplicationContext(), LetsGetStarted.class);  //go with access token verification
                        intent.putExtra("user_name", name);
                        startActivity(intent);

                    } else {
//                        Toast.makeText(getApplicationContext(), "Invalid email_address or Password", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LogIn.class);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    Intent intent = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(intent);
                    e.printStackTrace();

                }
            }

            //Method executed if request fails
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t.toString().equals("java.net.UnknownHostException:" +
                        " Unable to resolve host \"prediction1-rest-api.herokuapp.com\": No address associated with hostname")) {
                    Intent intent = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(intent);
//                    Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(intent);
//                    Toast.makeText(getApplicationContext(), "Server Not Responding. Please Try Again, Later.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
