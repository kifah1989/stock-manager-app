package com.example.stockmanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
    Products productListData;

    public static ListActivity ma;
    List<Products> listItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setTitle("Product List");
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
        progressDialog = new ProgressDialog(this);
        listItems = new ArrayList<>();
        ma = this;
        getUserListData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getUserListData();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {

            Intent tes = new Intent(ListActivity.this, Scanner.class);
            startActivity(tes);

        }
        else if (id == R.id.sell) {
            //Intent tes = new Intent(ListActivity.this, Scanner.class);
            //startActivity(tes);
        }
        return super.onOptionsItemSelected(item);
    }


    public void getUserListData() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog((Context) ListActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog


        (Api.getClient().getUsersList()).enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                Log.d("responseGET", response.body().toString());
                progressDialog.dismiss(); //dismiss progress dialog
                JsonObject json = new JsonObject();
                productListData = response.body();
                setDataInRecyclerView();
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                // if error occurs in network transaction then we can get the error in this method.
                Log.d("responseError", t.toString());

                Toast.makeText((Context) ListActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss(); //dismiss progress dialog
            }
        });
    }

    private void setDataInRecyclerView() {
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // call the constructor of UsersAdapter to send the reference and data to Adapter
        MyAdapter usersAdapter = new MyAdapter(productListData, ListActivity.this);
        recyclerView.setAdapter(usersAdapter); // set the Adapter to RecyclerView
    }




}
