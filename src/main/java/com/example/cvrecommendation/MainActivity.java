package com.example.cvrecommendation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signupScreen(View view){
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }

    public void loginScreen(View view){
        Intent intent = new Intent(getApplicationContext(), LogIn.class);
        startActivity(intent);



    }

    public void retrofitLibrary(String json){
        //Retrofit Class defining base url where app needs to post data
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://prediction1-rest-api.herokuapp.com/")
                .build();
        //Retrofit class generates an implementation of Api interface
        Api api = retrofit.create(Api.class);
//        json = "{}";

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        //This method POST request and we receive return data in response
        api.postUser(requestBody).enqueue(new Callback<ResponseBody>() {
            //Method executed if we receive response from server
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    ;
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            //Method executed if request fails
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(t.toString().equals("java.net.UnknownHostException:" +
                        " Unable to resolve host \"prediction1-rest-api.herokuapp.com\": No address associated with hostname"))
                    Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Server Not Responding. Please Try Again, Later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
