package com.example.stockmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Products signUpResponsesData;


    Products listItems;
    private Context context;
    private ProgressDialog dialog;


    MyAdapter(Products listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView Barcode,
                Pname,
                Supplier,
                Category,
                Quantity,
                OriginalPrice,
                SellingPrice,
                Date;
        ImageView imageView;
        CardView card_view;

        ViewHolder(View itemView) {
            super(itemView);
            Barcode = itemView.findViewById(R.id.barcode);
            Pname = itemView.findViewById(R.id.pname);
            Supplier = itemView.findViewById(R.id.supplier);
            Category = itemView.findViewById(R.id.category);
            Quantity = itemView.findViewById(R.id.quantity);
            OriginalPrice = itemView.findViewById(R.id.originalPrice);
            SellingPrice = itemView.findViewById(R.id.sellingPrice);
            Date = itemView.findViewById(R.id.date);
            imageView = itemView.findViewById(R.id.image);


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
        holder.Barcode.setText("barcode: "+listItems.getData().get(position).getBarcode());
        holder.Pname.setText("product name: "+listItems.getData().get(position).getPname());
        holder.Supplier.setText("supplier: "+listItems.getData().get(position).getSupplier());
        holder.Category.setText("category: "+listItems.getData().get(position).getCategory());
        holder.Quantity.setText("quantity: "+listItems.getData().get(position).getQuantity());
        holder.OriginalPrice.setText("original price: "+listItems.getData().get(position).getOriginalPrice());
        holder.SellingPrice.setText("selling price: "+listItems.getData().get(position).getSellingPrice());
        holder.Date.setText("date purchased: "+listItems.getData().get(position).getDate());
        Picasso.get().load("http://192.168.1.4/eshopper.local/images/"+listItems.getData().get(position).getPname()+".jpg").into(holder.imageView);



        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final ProgressDialog dialog = new ProgressDialog(view.getContext());
                dialog.setMessage("Loading Delete Data");
                final CharSequence[] dialogitem = {"View Data", "Edit Data", "Delete Data"};
                builder.setTitle(listItems.getData().get(position).getPname());
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(view.getContext(), DetailData.class);
                                intent.putExtra("Barcode", listItems.getData().get(position).getBarcode());
                                intent.putExtra("Pname", listItems.getData().get(position).getPname());
                                intent.putExtra("Supplier", listItems.getData().get(position).getSupplier());
                                intent.putExtra("Category", listItems.getData().get(position).getCategory());
                                intent.putExtra("Quantity", listItems.getData().get(position).getQuantity());
                                intent.putExtra("OriginalPrice", listItems.getData().get(position).getOriginalPrice());
                                intent.putExtra("SellingPrice", listItems.getData().get(position).getSellingPrice());
                                intent.putExtra("Date", listItems.getData().get(position).getDate());

                                view.getContext().startActivity(intent);
                                break;
                            case 1:

                                Intent intent2 = new Intent(view.getContext(), EditActivity.class);
                                intent2.putExtra("Barcode", listItems.getData().get(position).getBarcode());
                                intent2.putExtra("Pname", listItems.getData().get(position).getPname());
                                intent2.putExtra("Supplier", listItems.getData().get(position).getSupplier());
                                intent2.putExtra("Category", listItems.getData().get(position).getCategory());
                                intent2.putExtra("Quantity", listItems.getData().get(position).getQuantity());
                                intent2.putExtra("OriginalPrice", listItems.getData().get(position).getOriginalPrice());
                                intent2.putExtra("SellingPrice", listItems.getData().get(position).getSellingPrice());
                                intent2.putExtra("Date", listItems.getData().get(position).getDate());

                                view.getContext().startActivity(intent2);
                                break;
                            case 2:

                                AlertDialog.Builder builderDel = new AlertDialog.Builder(view.getContext());
                                builderDel.setTitle(listItems.getData().get(position).getPname());
                                builderDel.setMessage("Are You Sure, You Want to Delete Data?");
                                builderDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.show();

                                        (Api.getClient().delete(listItems.getData().get(position).getBarcode())).enqueue(new Callback<Products>() {
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