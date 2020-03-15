package com.example.stockmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    public static ListActivity ma;
    List<Model> listItems;
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
        refresh_list();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refresh_list();
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
            Intent tes = new Intent(ListActivity.this, Scanner.class);
            startActivity(tes);
        }
        return super.onOptionsItemSelected(item);
    }


    public void refresh_list(){
        listItems.clear();
        adapter = new MyAdapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SELECT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{

                    progressDialog.hide();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    Toast.makeText(ListActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject o = jsonArray.getJSONObject(i);
                       Model item = new Model(
                                o.getString("id"),
                                o.getString("pqty"),
                                o.getString("pname"),
                                o.getString("pprice"),
                                o.getString("pdescription")

                        );
                        listItems.add(item);

                        adapter = new MyAdapter(listItems,getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(ListActivity.this, "Failed",Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String , String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("pname", "kl");
                return params;
            }
        };
        RequestHandler.getInstance(ListActivity.this).addToRequestQueue(stringRequest);
    }




}
