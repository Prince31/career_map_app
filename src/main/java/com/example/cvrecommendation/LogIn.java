package com.example.cvrecommendation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailEditText = findViewById(R.id.emailEditText);
        passEditText = findViewById(R.id.passEditText);
    }

    public void letsGetStarted(View view){
        String username= emailEditText.getText().toString();
        String password= passEditText.getText().toString();
        String sign_in_json = "{\"username\": \""+username+"\", \"password\": \""+password+"\"}";
        System.out.println(sign_in_json);
//        String sign_in_json = "{'username': {}, 'password': {}}".format(emailEditText.getText().toString(), passEditText.getText().toString());


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
                        Intent intent = new Intent(getApplicationContext(), LetsGetStarted.class);
                        startActivity(intent);
                    } else{
                        Toast.makeText(getApplicationContext(), "Invalid UserName or Password", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    System.out.println("response catch exception : "+ e);
                    e.printStackTrace();

                }
            }
            //Method executed if request fails
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(t.toString().equals("java.net.UnknownHostException:" +
                        " Unable to resolve host \"prediction1-rest-api.herokuapp.com\": No address associated with hostname")) {
                    Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Server Not Responding. Please Try Again, Later.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        }
    }

