package com.example.stockmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Model> listItems;
    private Context context;
    private ProgressDialog dialog;


    MyAdapter(List<Model> listItems, Context context) {
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
        final Model listItem = listItems.get(position);
        holder.id.setText(listItem.getId());
        holder.pqty.setText(listItem.getpqty());
        holder.pname.setText(listItem.getpname());
        holder.pdescription.setText(listItem.getpdescription());
        holder.pprice.setText(listItem.getpprice());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final ProgressDialog dialog = new ProgressDialog(view.getContext());
                dialog.setMessage("Loading Delete Data");
                final CharSequence[] dialogitem = {"View Data","Edit Data","Delete Data"};
                builder.setTitle(listItem.getpname());
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                Intent intent = new Intent(view.getContext(), DetailData.class);
                                intent.putExtra("id", listItem.getId());
                                intent.putExtra("pqty",listItem.getpqty());
                                intent.putExtra("pname",listItem.getpname());
                                intent.putExtra("pdescription",listItem.getpdescription());
                                intent.putExtra("pprice", listItem.getpprice());
                                view.getContext().startActivity(intent);
                                break;
                            case 1 :

                                Intent intent2 = new Intent(view.getContext(), EditActivity.class);
                                intent2.putExtra("id", listItem.getId());
                                intent2.putExtra("pqty",listItem.getpqty());
                                intent2.putExtra("pname",listItem.getpname());
                                intent2.putExtra("pdescription",listItem.getpdescription());
                                intent2.putExtra("pprice", listItem.getpprice());
                                view.getContext().startActivity(intent2);
                                break;
                            case 2 :

                                AlertDialog.Builder builderDel = new AlertDialog.Builder(view.getContext());
                                builderDel.setTitle(listItem.getpname());
                                builderDel.setMessage("Are You Sure, You Want to Delete Data?");
                                builderDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.show();

                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_DELETE, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                dialog.hide();
                                                dialog.dismiss();
                                                Toast.makeText(view.getContext(),"Successfully Deleted Data "+ listItem.getpname(),Toast.LENGTH_LONG).show();
                                                ListActivity.ma.refresh_list();

                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                dialog.hide();
                                                dialog.dismiss();
                                            }
                                        }){
                                            protected HashMap<String, String> getParams() {
                                                HashMap<String, String> params= new HashMap<>();
                                                params.put("id",listItem.getId());
                                                return params;

                                            }
                                        };
                                        RequestHandler.getInstance(view.getContext()).addToRequestQueue(stringRequest);
                                        dialogInterface.dismiss();
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
        return listItems.size();
    }
}