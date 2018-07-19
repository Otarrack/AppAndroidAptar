package com.florian.projet.model;

import java.util.Date;

public class QuantityFileLine {
    private String nameArticle;
    private String namePresse;
    private Date date;
    private String customer;
    private double quantity;
    private String type;
    private String numOf;

    public QuantityFileLine() {

    }

    public String getNameArticle() {
        return nameArticle;
    }

    public void setNameArticle(String nameArticle) {
        this.nameArticle = nameArticle;
    }

    public String getNamePresse() {
        return namePresse;
    }

    public void setNamePresse(String namePresse) {
        this.namePresse = namePresse;
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
