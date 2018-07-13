package com.example.sachin.qrsworkbench;

public class CurrentCartData {
    String pname;
    String quantity;
    String price;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public CurrentCartData(String pname, String quantity, String price) {
        this.pname = pname;
        this.quantity = quantity;
        this.price = price;
    }
}
