package com.example.rajuk_expensemanager;

public class FlatOwner {
    String date;
    String name,flatNo,email,password,idOwner;

    public FlatOwner(){

    }

    public FlatOwner(String idOwner,String email, String date, String name, String flatNo, String password) {
        this.idOwner=idOwner;
        this.date = date;
        this.name = name;
        this.flatNo = flatNo;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
