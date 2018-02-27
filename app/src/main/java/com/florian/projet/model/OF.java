package com.florian.projet.model;

import java.util.Date;

public class OF {
    private String numOF;
    private Site site;
    private Machine machine;
    private Article article;
    private Person person;
    private Date dateDeclarationProduction;
    private Date dateStartPlanned;
    private Date dateEndPlanned;
    private double qtAsked;
    private double volume;
    private double waste;
    private double cadence;

    public OF() {

    }

    public OF(String numOF, Site site, Machine machine, Article article, Person person, Date dateDeclarationProduction, Date dateStartPlanned, Date dateEndPlanned, double qtAsked, double volume, double waste, double cadence) {
        this.numOF = numOF;
        this.site = site;
        this.machine = machine;
        this.article = article;
        this.person = person;
        this.dateDeclarationProduction = dateDeclarationProduction;
        this.dateStartPlanned = dateStartPlanned;
        this.dateEndPlanned = dateEndPlanned;
        this.qtAsked = qtAsked;
        this.volume = volume;
        this.waste = waste;
        this.cadence = cadence;
    }

    public String getNumOF() {
        return numOF;
    }

    public void setNumOF(String numOF) {
        this.numOF = numOF;
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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getWaste() {
        return waste;
    }

    public void setWaste(double waste) {
        this.waste = waste;
    }

    public double getCadence() {
        return cadence;
    }

    public void setCadence(double cadence) {
        this.cadence = cadence;
    }
}
