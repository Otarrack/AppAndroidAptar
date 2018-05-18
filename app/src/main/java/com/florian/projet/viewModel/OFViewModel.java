package com.florian.projet.viewModel;

import com.florian.projet.manager.OFManager;
import com.florian.projet.quarantaine.OF;
import com.florian.projet.model.Person;

import java.text.DateFormat;

public class OFViewModel {
    public static OFViewModel instance;
    private OFManager ofManager;
    private OF currentOF;

    public static OFViewModel getInstance() {
        if(instance == null) {
            instance = new OFViewModel();
        }
        return instance;
    }

    private OFViewModel() {
        ofManager = OFManager.getInstance();
    }

    public void setCurrentOF(String numOF) {
        currentOF = ofManager.getOFByNum(numOF);
    }

    public OF getCurrentOF() {
        return currentOF;
    }

    public void delCurrentArticle() {
        currentOF = null;
    }

    public String getNumOF() {
        return currentOF.getNumOF();
    }

    public String getNameSite() {
        return currentOF.getNameSite();
    }

    public String getNameMachine() {
        return currentOF.getNameMachine();
    }

    public String getNumArticle() {
        return currentOF.getNumArticle();
    }

    public Person getPerson() {
        return currentOF.getPerson();
    }

    public String getDeclarationDate() {

        if (currentOF.getDateDeclarationProduction() == null) {
            return "";
        }
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentOF.getDateDeclarationProduction());
    }

    public String getStartingDate() {

        if (currentOF.getDateStartPlanned() == null) {
            return "";
        }
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentOF.getDateStartPlanned());
    }

    public String getEndingDate() {

        if (currentOF.getDateEndPlanned() == null) {
            return "";
        }
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentOF.getDateEndPlanned());
    }

    public double getQtAsked() {
        return currentOF.getQtAsked();
    }

    public double getVolume() {
        return currentOF.getVolume();
    }

    public double getWaste() {
        return currentOF.getWaste();
    }

    public double getCadence() {
        return currentOF.getCadence();
    }
}
