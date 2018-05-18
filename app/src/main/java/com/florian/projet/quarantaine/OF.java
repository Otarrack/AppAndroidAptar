package com.florian.projet.quarantaine;

import com.florian.projet.model.Person;

import java.util.Date;

public class OF {
    private String numOF;
    private String nameSite;
    private String nameMachine;
    private String numArticle;
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

    public OF(String numOF, String nameSite, String nameMachine, String numArticle, Person person, Date dateDeclarationProduction, Date dateStartPlanned, Date dateEndPlanned, double qtAsked, double volume, double waste, double cadence) {
        this.numOF = numOF;
        this.nameSite = nameSite;
        this.nameMachine = nameMachine;
        this.numArticle = numArticle;
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

    public String getNameSite() {
        return nameSite;
    }

    public void setNameSite(String nameSite) {
        this.nameSite = nameSite;
    }

    public String getNameMachine() {
        return nameMachine;
    }

    public void setNameMachine(String nameMachine) {
        this.nameMachine = nameMachine;
    }

    public String getNumArticle() {
        return numArticle;
    }

    public void setNumArticle(String numArticle) {
        this.numArticle = numArticle;
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
