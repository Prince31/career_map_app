package com.example.cvrecommendation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    static String api_response="";

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

//    public static String retrofitLibrary(String json){
//
//        System.out.println("json string inside retrofit"+json);
//        //Retrofit Class defining base url where app needs to post data
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://job-recommendation-api.herokuapp.com/")
//                .build();
//        //Retrofit class generates an implementation of Api interface
//        Api api = retrofit.create(Api.class);
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
//        //This method POST request and we receive return data in response
//        api.postUser(requestBody).enqueue(new Callback<ResponseBody>() {
//            //Method executed if we receive response from server
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                System.out.println("onresponse body ran");
//                try {
//                    System.out.println("got response");
//                    System.out.println("response try : "+response.body().string());
//                    api_response = response.body().string();
//
//                } catch (Exception e) {
//                    System.out.println("response catch exception : "+ e);
//                    e.printStackTrace();
//
//                }
//            }
//            //Method executed if request fails
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                if(t.toString().equals("java.net.UnknownHostException:" +
//                        " Unable to resolve host \"prediction1-rest-api.herokuapp.com\": No address associated with hostname")) {
//                    System.out.print("onfailure no internet exception : ");
//                    api_response = "Please Check Your Network Connection";
////                    Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    System.out.print("onfailure server exception : ");
//                    api_response="Server Not Responding. Please Try Again, Later.";
////                    Toast.makeText(getApplicationContext(), "Server Not Responding. Please Try Again, Later.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        return api_response;
//    }
}
