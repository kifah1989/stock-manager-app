package com.example.stockmanager;

class Model {
    private String id, pqty,pname,pprice,pdescription;

    Model(String id, String pqty, String pname, String pprice, String pdescription) {
        this.id = id;
        this.pqty = pqty;
        this.pname = pname;
        this.pprice = pprice;
        this.pdescription = pdescription;
    }

    String getId(){
        return id;
    }
    String getpqty(){
        return pqty;
    }
    String getpname(){
        return pname;
    }

    String getpprice(){
        return pprice;
    }

    String getpdescription(){
        return pdescription;
    }
}
