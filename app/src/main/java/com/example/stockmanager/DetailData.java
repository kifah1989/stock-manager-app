package com.example.stockmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailData extends AppCompatActivity {
    TextView Barcode,
            Pname,
            Supplier,
            Category,
            Quantity,
            OriginalPrice,
            SellingPrice,
            Date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);

        Barcode          = findViewById(R.id.barcode);
        Pname            = findViewById(R.id.pname);
        Supplier         = findViewById(R.id.supplier);
        Category         = findViewById(R.id.category);
        Quantity         = findViewById(R.id.quantity);
        OriginalPrice    = findViewById(R.id.originalPrice);
        SellingPrice     = findViewById(R.id.sellingPrice);
        Date             = findViewById(R.id.date);

        Barcode.setText(getIntent().getStringExtra("Barcode"));
        Pname.setText(getIntent().getStringExtra("Pname"));
        Supplier.setText(getIntent().getStringExtra("Supplier "));
        Category.setText(getIntent().getStringExtra("Category"));
        Quantity.setText(getIntent().getStringExtra("Quantity "));
        OriginalPrice.setText(getIntent().getStringExtra("OriginalPrice"));
        SellingPrice.setText(getIntent().getStringExtra("SellingPrice"));
        Date.setText(getIntent().getStringExtra("Date"));





    }
}
