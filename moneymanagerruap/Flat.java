package com.example.moneymanagerruap;

public class Flat {
    String date,name,flatNo;

    public Flat(String date, String name, String flatNo) {
        this.date = date;
        this.name = name;
        this.flatNo = flatNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }
}
