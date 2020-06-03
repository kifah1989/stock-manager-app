package com.example.stockmanager;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {
    Products signUpResponsesData;

    EditText Barcode,
            Pname,
            Supplier,
            Category,
            Quantity,
            OriginalPrice,
            SellingPrice,
            Date;
    Button button;
    Boolean valid = true;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
            Barcode       = (EditText) findViewById(R.id.barcode);
           Pname          = (EditText) findViewById(R.id.pname);
           Supplier      = (EditText) findViewById(R.id.supplier);
           Category       = (EditText) findViewById(R.id.category);
           Quantity       = (EditText) findViewById(R.id.quantity);
           OriginalPrice   = (EditText) findViewById(R.id.originalPrice);
           SellingPrice    = (EditText) findViewById(R.id.sellingPrice);
           Date            = (EditText) findViewById(R.id.date);

        progressDialog = new ProgressDialog(this);
        button = (Button) findViewById(R.id.button);

        Barcode.setText(getIntent().getStringExtra("Barcode"));
        Pname.setText(getIntent().getStringExtra("Pname"));
        Supplier.setText(getIntent().getStringExtra("Supplier"));
        Category.setText(getIntent().getStringExtra("Category"));
        Quantity.setText(getIntent().getStringExtra("Quantity"));
        OriginalPrice.setText(getIntent().getStringExtra("OriginalPrice"));
        SellingPrice.setText(getIntent().getStringExtra("SellingPrice"));
        Date.setText(getIntent().getStringExtra("Date"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validate the fields and call sign method to implement the api
                if (
                        validate(Barcode) &&
                        validate(Pname) &&
                        validate(Supplier) &&
                        validate(Category) &&
                        validate(Quantity) &&
                        validate(OriginalPrice) &&
                        validate(SellingPrice) &&
                        validate(Date)) {
                    updateData();
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

    private void updateData() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(EditActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        // Api is a class in which we define a method getClient() that returns the API Interface class object
        // registration is a POST request type method in which we are sending our field's data
        // enqueue is used for callback response and error

        (Api.getClient().update(
       Barcode.getText().toString().trim(),
      Pname.getText().toString().trim(),
      Supplier.getText().toString().trim(),
      Category.getText().toString().trim(),
      Quantity.getText().toString().trim()  ,
      OriginalPrice.getText().toString().trim(),
      SellingPrice.getText().toString().trim(),
      Date.getText().toString().trim()

        )).enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                signUpResponsesData = response.body();
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                ListActivity.ma.getProductListData();

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Log.d("response", t.getStackTrace().toString());
                progressDialog.dismiss();

            }
        });
    }
}
