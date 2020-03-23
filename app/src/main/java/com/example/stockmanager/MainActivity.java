package com.example.stockmanager;

import android.app.ProgressDialog;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Products signUpResponsesData;

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
                // validate the fields and call sign method to implement the api
                if (validate(pname) && validate(pdescription) && validate(pqty) && validate(price)) {
                    submit();
                }
            }
        });
    }


    private boolean validate(EditText editText) {
        // check the lenght of the enter data in EditText and give error if its empty
        if (editText.getText().toString().trim().length() > 0) {
            return true; // returns true if field is not empty
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    private void submit() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        // Api is a class in which we define a method getClient() that returns the API Interface class object
        // registration is a POST request type method in which we are sending our field's data
        // enqueue is used for callback response and error
        (Api.getClient().add(
                id.getText().toString().trim(),
                pname.getText().toString().trim(),
                pdescription.getText().toString().trim(),
                pqty.getText().toString().trim(),
                price.getText().toString().trim())).enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                signUpResponsesData = response.body();
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Log.d("response", t.getStackTrace().toString());
                progressDialog.dismiss();

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
