package com.example.stockmanager;


import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/android/select.php")
        // API's endpoints
    Call<Products> getUsersList();

// UserListResponse is POJO class to get the data from API, we use List<UserListResponse> in callback because the data in our API is starting from JSONArray
}
