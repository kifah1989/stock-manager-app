package com.example.stockmanager;

public class DataItem{

    private String pdescription;

    private String pqty;

    private String pname;

    private String pprice;

    private String id;

    public void setPdescription(String pdescription){
        this.pdescription = pdescription;
    }

    public String getPdescription(){
        return pdescription;
    }

    public void setPqty(String pqty){
        this.pqty = pqty;
    }

    public String getPqty(){
        return pqty;
    }

    public void setPname(String pname){
        this.pname = pname;
    }

    public String getPname(){
        return pname;
    }

    public void setPprice(String pprice){
        this.pprice = pprice;
    }

    public String getPprice(){
        return pprice;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    @Override
    public String toString(){
        return
                "DataItem{" +
                        "pdescription = '" + pdescription + '\'' +
                        ",pqty = '" + pqty + '\'' +
                        ",pname = '" + pname + '\'' +
                        ",pprice = '" + pprice + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}