package com.example.lahcen.finalproject;


import java.text.DateFormat;
import java.util.Calendar;

public class Item {

    private String description;
    private String category;
    private String date;
    private String amount;
    private String id;

    private Calendar c = Calendar.getInstance();
    private String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
    private String currentDateSubString = currentDateString.substring(0, currentDateString.length() - 6);

    public Item(String description, String category, String date, String amount, String id) {
        this.description = description;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.id= id;
    }


    public Item() {
        this.description = "description";
        this.category = "category";
        this.date = currentDateSubString;
        this.amount = "amount";
        this.id= "";

    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
