package com.example.cvrecommendation;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface Api {
    //HTTP annotation providing request method and relative URL
    @POST("/auth")
    Call<ResponseBody> postUser(@Body RequestBody requestBody);

    @POST("/register")
    Call<ResponseBody> registerUser(@Body RequestBody requestBody);
}
