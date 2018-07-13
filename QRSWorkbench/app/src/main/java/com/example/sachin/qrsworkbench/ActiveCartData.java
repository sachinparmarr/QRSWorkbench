package com.example.sachin.qrsworkbench;

public class ActiveCartData {
    String cust_id;
    String amount;

    public ActiveCartData(String cust_id, String amount) {
        this.cust_id = cust_id;
        this.amount = amount;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
