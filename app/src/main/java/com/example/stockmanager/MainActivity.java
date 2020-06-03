package com.example.stockmanager;

import android.app.ProgressDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Products signUpResponsesData;

    EditText barcode, pname, supplier, category, quantity, originalPrice, sellingPrice, date;
    String Barcode, Pname, Supplier, Category, Quantity, OriginalPrice, SellingPrice, Date;
    Button image, button, scanbutton;
    Boolean valid = true;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barcode = findViewById(R.id.barcode);
        pname = findViewById(R.id.pname);
        supplier = findViewById(R.id.supplier);
        category = findViewById(R.id.category);
        quantity = findViewById(R.id.quantity);
        originalPrice = findViewById(R.id.originalPrice);
        sellingPrice = findViewById(R.id.sellingPrice);
        date = findViewById(R.id.date);
        image = findViewById(R.id.button2);
        scanbutton = findViewById(R.id.scanbutton);

        progressDialog = new ProgressDialog(this);
        button = findViewById(R.id.button);
        image = findViewById(R.id.button2);


        barcode.setText(getIntent().getStringExtra("barcode"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validate the fields and call sign method to implement the api
                if (validate(supplier) && validate(category) && validate(supplier) && validate(quantity)) {
                    submit();
                }
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent tes = new Intent(MainActivity.this, ImageActivity.class);
                                          tes.putExtra("Pname", pname.getText().toString());
                                          startActivity(tes);
                                      }
                                  });
        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tes = new Intent(MainActivity.this, Scanner.class);
                startActivity(tes);
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
        Barcode = barcode.getText().toString().trim();
        Pname = pname.getText().toString().trim();
        Supplier = supplier.getText().toString().trim();
        Category = category.getText().toString().trim();
        Quantity = quantity.getText().toString().trim();
        OriginalPrice = originalPrice.getText().toString().trim();
        SellingPrice = sellingPrice.getText().toString().trim();
        Date = date.getText().toString().trim();


        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        // Api is a class in which we define a method getClient() that returns the API Interface class object
        // registration is a POST request type method in which we are sending our field's data
        // enqueue is used for callback response and error
        (Api.getClient().add(
                Barcode,
                Pname,
                Supplier,
                Category,
                Quantity,
                OriginalPrice,
                SellingPrice,
                Date))
                .enqueue(new Callback<Products>() {
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
        this.barcode.setText(barcode);
    }
}
