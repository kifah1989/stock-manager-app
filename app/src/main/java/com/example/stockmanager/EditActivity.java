package com.example.stockmanager;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    EditText pqty, pname, pdescription, pprice;
    String Pqty, Pname, Pdescription, Pprice, Id;
    Button button;
    Boolean valid = true;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        pqty = (EditText) findViewById(R.id.pqty);
        pname = (EditText) findViewById(R.id.pname);
        pdescription = (EditText) findViewById(R.id.pdescription);
        pprice = (EditText) findViewById(R.id.pprice);
        progressDialog = new ProgressDialog(this);
        button = (Button) findViewById(R.id.button);

        Id = getIntent().getStringExtra("id");
        pqty.setText(getIntent().getStringExtra("pqty"));
        pname.setText(getIntent().getStringExtra("pname"));
        pdescription.setText(getIntent().getStringExtra("pdescription"));
        pprice.setText(getIntent().getStringExtra("pprice"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pqty = pqty.getText().toString();
                Pname = pname.getText().toString();
                Pdescription = pdescription.getText().toString();
                Pprice = pprice.getText().toString();

                if(TextUtils.isEmpty(Pqty)){
                    pqty.setError("quantity Cannot be Empty");
                    valid = false;
                }else {
                    valid = true;

                    if(TextUtils.isEmpty(Pname)){
                        pname.setError("product name Cannot be Empty");
                        valid = false;
                    }else {
                        valid = true;

                        if(TextUtils.isEmpty(Pdescription)){
                            pdescription.setError("description Cannot be Empty");
                            valid = false;
                        }else {
                            valid = true;

                            if(TextUtils.isEmpty(Pprice)){
                                pprice.setError("price Cannot be Empty");
                                valid = false;
                            }else {
                                valid = true;
                            }
                        }

                    }
                }

                if(valid){
                    progressDialog.setMessage("Loading");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(EditActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                if(jsonObject.getString("message").equals("Edit Data Successful")){
                                    ListActivity.ma.getUserListData();
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
                            Toast.makeText(EditActivity.this, "Failed",Toast.LENGTH_SHORT).show();
                        }
                    }){
                        protected Map<String , String> getParams() throws AuthFailureError {
                            Map<String , String> params = new HashMap<>();
                            params.put("id", Id);
                            params.put("pname", Pname);
                            params.put("pqty", Pqty);
                            params.put("pprice", Pprice);
                            params.put("pdescription",Pdescription);
                            return params;
                        }
                    };
                    RequestHandler.getInstance(EditActivity.this).addToRequestQueue(stringRequest);

                }
            }
        });
    }
}
