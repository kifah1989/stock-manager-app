package com.example.stockmanager;


import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface ApiInterface {
    @GET("/select.php")
        // API's endpoints
    Call<Products> getUsersList();

    @FormUrlEncoded
    @POST("/add.php")
        // API's endpoints
    Call<Products> add(@Field("barcode") String barcode,
                       @Field("pname") String pname,
                       @Field("supplier") String supplier,
                       @Field("category") String category,
                       @Field("quantity") String quantity,
                       @Field("originalPrice") String originalPrice,
                       @Field("sellingPrice") String sellingPrice,
                       @Field("date") String date);

    @FormUrlEncoded
    @POST("/update.php")
        // API's endpoints
    Call<Products> update(@Field("barcode") String barcode,
                          @Field("pname") String pname,
                          @Field("supplier") String supplier,
                          @Field("category") String category,
                          @Field("quantity") String quantity,
                          @Field("originalPrice") String originalPrice,
                          @Field("sellingPrice") String sellingPrice,
                          @Field("date") String date);

    @FormUrlEncoded
    @POST("/delete.php")
        // API's endpoints
    Call<Products> delete(@Field("barcode") String barcode);

    @Multipart
    @POST("eshopper.local/image_upload.php")
    Call<Products> upload(
            @Header("Authorization") String authorization,
            @PartMap Map<String, RequestBody> map
    );


// UserListResponse is POJO class to get the data from API, we use List<UserListResponse> in callback because the data in our API is starting from JSONArray
}
