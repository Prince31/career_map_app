package com.example.cvrecommendation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

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
        }
        else {
            return true;
        }
        return false;
    }


    public void signup(View view){
        String email_address = emailEditText.getText().toString();
        String password = passEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String phone_number = phoneEditText.getText().toString();
        String zip_code = zipCodeEditText.getText().toString();

        boolean inputFieldCheck = inputFieldValidation(email_address, password, name, phone_number, zip_code);
        if(inputFieldCheck) {
            String sign_up_json = String.format("{\"email_address\": \"%1$s\", \"password\": \"%2$s\", " +
                    "\"name\": \"%3$s\", \"phone_number\": \"%4$s\", \"zip_code\": \"%5$s\"}",
                    email_address, password, name, phone_number, zip_code);
            System.out.println(sign_up_json);
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
                            System.out.println(api_response);
                            JSONObject jsonOb = new JSONObject(api_response);
                            String response_message = jsonOb.optString("message");
                            System.out.println(response_message);
                            Intent intent = new Intent(getApplicationContext(), LetsGetStarted.class);
                            startActivity(intent);


                        } else {
                            Toast.makeText(getApplicationContext(), "User Already registered with this Email Address", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        System.out.println("response catch exception : " + e);
                        e.printStackTrace();

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
                }
            });


        }
    }

}
