package com.example.stockmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    Products signUpResponsesData;


    Products listItems;
    private Context context;
    private ProgressDialog dialog;


    MyAdapter(Products listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView pqty;
        public TextView pname;
        public TextView pdescription;
        public TextView pprice;
        CardView card_view;
        ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.barcode);
            pqty = itemView.findViewById(R.id.pqty);
            pname = itemView.findViewById(R.id.pname);
            pdescription = itemView.findViewById(R.id.pdescription);
            pprice = itemView.findViewById(R.id.pprice);
            card_view = itemView.findViewById(R.id.card_view);
        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.id.setText(listItems.getData().get(position).getId());
        holder.pqty.setText(listItems.getData().get(position).getPqty());
        holder.pname.setText(listItems.getData().get(position).getPname());
        holder.pdescription.setText(listItems.getData().get(position).getPdescription());
        holder.pprice.setText(listItems.getData().get(position).getPprice());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final ProgressDialog dialog = new ProgressDialog(view.getContext());
                dialog.setMessage("Loading Delete Data");
                final CharSequence[] dialogitem = {"View Data","Edit Data","Delete Data"};
                builder.setTitle(listItems.getData().get(position).getPname());
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                Intent intent = new Intent(view.getContext(), DetailData.class);
                                intent.putExtra("id", listItems.getData().get(position).getId());
                                intent.putExtra("pqty",listItems.getData().get(position).getPqty());
                                intent.putExtra("pname",listItems.getData().get(position).getPname());
                                intent.putExtra("pdescription",listItems.getData().get(position).getPdescription());
                                intent.putExtra("pprice", listItems.getData().get(position).getPprice());
                                view.getContext().startActivity(intent);
                                break;
                            case 1 :

                                Intent intent2 = new Intent(view.getContext(), EditActivity.class);
                                intent2.putExtra("id", listItems.getData().get(position).getId());
                                intent2.putExtra("pqty",listItems.getData().get(position).getPqty());
                                intent2.putExtra("pname",listItems.getData().get(position).getPname());
                                intent2.putExtra("pdescription",listItems.getData().get(position).getPdescription());
                                intent2.putExtra("pprice", listItems.getData().get(position).getPprice());
                                view.getContext().startActivity(intent2);
                                break;
                            case 2 :

                                AlertDialog.Builder builderDel = new AlertDialog.Builder(view.getContext());
                                builderDel.setTitle(listItems.getData().get(position).getPname());
                                builderDel.setMessage("Are You Sure, You Want to Delete Data?");
                                builderDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.show();

                                        (Api.getClient().delete(listItems.getData().get(position).getId())).enqueue(new Callback<Products>() {
                                            @Override
                                            public void onResponse(Call<Products> call, Response<Products> response) {
                                                signUpResponsesData = response.body();
                                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                ListActivity.ma.getProductListData();

                                                dialog.cancel();

                                            }

                                            @Override
                                            public void onFailure(Call<Products> call, Throwable t) {
                                                Log.d("response", t.getStackTrace().toString());

                                            }
                                        });

                                    }
                                });

                                builderDel.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override

                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });


                                builderDel.create().show();
                                break;
                        }
                    }
                });

                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.getData().size();
    }
}