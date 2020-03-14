package com.example.stockmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailData extends AppCompatActivity {
    TextView pqty, pname, pdescription, pprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);

        pqty = findViewById(R.id.pqty);
        pname = findViewById(R.id.pname);
        pdescription = findViewById(R.id.pdescription);
        pprice = findViewById(R.id.pprice);

        pqty.setText(getIntent().getStringExtra("pqty"));
        pname.setText(getIntent().getStringExtra("pname"));
        pdescription.setText(getIntent().getStringExtra("pdescription"));
        pprice.setText(getIntent().getStringExtra("pprice"));
    }
}
