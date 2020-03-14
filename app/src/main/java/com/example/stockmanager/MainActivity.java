package com.example.stockmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText id, pqty, pname, pdescription, price;
    String Pid, Pqty, Pname, Pdescription, Pprice;
    Button scan, button;
    Boolean valid = true;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.barcode);
        pqty = findViewById(R.id.pqty);
        pname = findViewById(R.id.pname);
        pdescription = findViewById(R.id.pdescription);
        price = findViewById(R.id.pprice);
        progressDialog = new ProgressDialog(this);
        button = findViewById(R.id.button);

        Bundle extras = getIntent().getExtras();
        String barcode = extras.getString("barcode");
        id.setText(barcode);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pid = id.getText().toString();
                Pqty = pqty.getText().toString();
                Pname = pname.getText().toString();
                Pdescription = pdescription.getText().toString();
                Pprice = price.getText().toString();

                if(TextUtils.isEmpty(Pid)){
                    id.setError("id Cannot be Empty");
                    valid = false;
                }else {
                    valid = true;
                    if (TextUtils.isEmpty(Pqty)) {
                        pqty.setError("qty Cannot be Empty");
                        valid = false;
                    } else {
                        valid = true;

                        if (TextUtils.isEmpty(Pname)) {
                            pname.setError("name Cannot be Empty");
                            valid = false;
                        } else {
                            valid = true;

                            if (TextUtils.isEmpty(Pdescription)) {
                                pdescription.setError("descryption Cannot be Empty");
                                valid = false;
                            } else {
                                valid = true;

                                if (TextUtils.isEmpty(Pprice)) {
                                    price.setError("Contact Number Cannot be Empty");
                                    valid = false;
                                } else {
                                    valid = true;
                                }
                            }

                        }
                    }
                }

                if(valid){
                    progressDialog.setMessage("Loading");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                if(jsonObject.getString("message").equals("Data Added Successfully")){
                                    ListActivity.ma.refresh_list();
                                    finish();
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Toast.makeText(MainActivity.this, "Failed to Data",Toast.LENGTH_SHORT).show();
                        }
                    }){
                        protected Map<String , String> getParams() {
                            Map<String , String> params = new HashMap<>();
                            params.put("id", Pid);
                            params.put("pname", Pname);
                            params.put("pqty", Pqty);
                            params.put("pprice", Pprice);
                            params.put("pdescription",Pdescription);
                            return params;
                        }
                    };
                    RequestHandler.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

                }
            }
        });



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Bundle extras = getIntent().getExtras();
        String barcode = extras.getString("barcode");
        id.setText(barcode);
    }
}
