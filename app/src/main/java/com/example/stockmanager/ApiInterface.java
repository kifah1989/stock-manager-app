package com.example.stockmanager;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("/android/select.php")
        // API's endpoints
    Call<Products> getUsersList();

    @FormUrlEncoded
    @POST("/android/add.php")
        // API's endpoints
    Call<Products> add(@Field("id") String id,
                       @Field("pqty") String quantity,
                       @Field("pname") String name,
                       @Field("pprice") String price,
                       @Field("pdescription") String description);

    @FormUrlEncoded
    @POST("/android/update.php")
        // API's endpoints
    Call<Products> update(@Field("id") String id,
                          @Field("pqty") String quantity,
                          @Field("pname") String name,
                          @Field("pprice") String price,
                          @Field("pdescription") String description);

    @FormUrlEncoded
    @POST("/android/delete.php")
        // API's endpoints
    Call<Products> delete(@Field("id") String id);


// UserListResponse is POJO class to get the data from API, we use List<UserListResponse> in callback because the data in our API is starting from JSONArray
}
