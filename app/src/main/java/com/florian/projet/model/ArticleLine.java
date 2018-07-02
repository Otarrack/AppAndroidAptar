package com.florian.projet.model;

import java.util.Date;

public class ArticleLine {
    private String name;
    private Date date;
    private String customer;
    private double quantity;
    private String type;
    private String numOf;

    public ArticleLine() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumOf() {
        return numOf;
    }

    public void setNumOf(String numOf) {
        this.numOf = numOf;
    }
}
