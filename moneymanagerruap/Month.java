package com.example.moneymanagerruap;

public class Month {
    String date,amount,month,year,MRH;

    public Month(String date, String amount, String month,String year,String MRH) {
        this.date = date;
        this.amount = amount;
        this.MRH=MRH;
        this.month = month;
        this.year=year;
    }

    public String getMRH() {
        return MRH;
    }

    public void setMRH(String MRH) {
        this.MRH = MRH;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
