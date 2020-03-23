package com.example.stockmanager;

import java.util.List;

public class Products{

    private int total;

    private List<DataItem> data;

    private String success;

    private String message;

    public void setTotal(int total){
        this.total = total;
    }

    public int getTotal(){
        return total;
    }

    public void setData(List<DataItem> data){
        this.data = data;
    }

    public List<DataItem> getData(){
        return data;
    }

    public void setSuccess(String success){
        this.success = success;
    }

    public String getSuccess(){
        return success;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString(){
        return
                "Products{" +
                        "total = '" + total + '\'' +
                        ",data = '" + data + '\'' +
                        ",success = '" + success + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}