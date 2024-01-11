package com.example.moneymanagerruap;

public class ExpensesJava {
    String amount,purpose,date;

    public ExpensesJava(String date,String purpose, String amount) {
        this.purpose = purpose;
        this.amount=amount;
        this.date = date;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
