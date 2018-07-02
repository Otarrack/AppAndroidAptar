package com.florian.projet.bdd.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "articles_data")
public class ArticleData {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "idArticle")
    private long idArticle;
    @ColumnInfo(name = "numOf")
    private String numOf;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "quantity")
    private double quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(long idArticle) {
        this.idArticle = idArticle;
    }

    public String getNumOf() {
        return numOf;
    }

    public void setNumOf(String numOf) {
        this.numOf = numOf;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
