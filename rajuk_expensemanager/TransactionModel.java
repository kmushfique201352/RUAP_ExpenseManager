package com.example.rajuk_expensemanager;

public class TransactionModel {
    private String id,amount,date,purpose,type;

    public TransactionModel(){

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TransactionModel(String id, String amount, String date, String purpose, String type) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.purpose = purpose;
        this.type=type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
