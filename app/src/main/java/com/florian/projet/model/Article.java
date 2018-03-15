package com.florian.projet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Article {
    private String numArticle;
    private List<OF> ofList;
    private Site site;
    private Machine machine;
    private Date dateDeclarationProduction;
    private Date dateStartPlanned;
    private Date dateEndPlanned;
    private double qtAsked;
    private double volume;
    private double waste;
    private double cadence;

    public Article(String numArticle, Site site, Machine machine, Date dateDeclarationProduction, Date dateStartPlanned, Date dateEndPlanned, double qtAsked, double volume, double waste, double cadence) {
        this.numArticle = numArticle;
        this.site = site;
        this.machine = machine;
        this.dateDeclarationProduction = dateDeclarationProduction;
        this.dateStartPlanned = dateStartPlanned;
        this.dateEndPlanned = dateEndPlanned;
        this.qtAsked = qtAsked;
        this.volume = volume;
        this.waste = waste;
        this.cadence = cadence;
        this.ofList = new ArrayList<>();
    }

    public Article(String numArticle) {
        this.numArticle = numArticle;
        this.ofList = new ArrayList<>();
    }

    public void addOfToList(OF of) {
        ofList.add(of);
    }

    public List<OF> getOfList() {
        return ofList;
    }


    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public String getNumArticle() {
        return numArticle;
    }

    public void setNumArticle(String numArticle) {
        this.numArticle = numArticle;
    }

    public Date getDateDeclarationProduction() {
        return dateDeclarationProduction;
    }

    public void setDateDeclarationProduction(Date dateDeclarationProduction) {
        this.dateDeclarationProduction = dateDeclarationProduction;
    }

    public Date getDateStartPlanned() {
        return dateStartPlanned;
    }

    public void setDateStartPlanned(Date dateStartPlanned) {
        this.dateStartPlanned = dateStartPlanned;
    }

    public Date getDateEndPlanned() {
        return dateEndPlanned;
    }

    public void setDateEndPlanned(Date dateEndPlanned) {
        this.dateEndPlanned = dateEndPlanned;
    }

    public double getQtAsked() {
        return qtAsked;
    }

    public void setQtAsked(double qtAsked) {
        this.qtAsked = qtAsked;
    }

    public double getWaste() {
        return waste;
    }

    public void setWaste(double waste) {
        this.waste = waste;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getCadence() {
        return cadence;
    }

    public void setCadence(double cadence) {
        this.cadence = cadence;
    }
}
