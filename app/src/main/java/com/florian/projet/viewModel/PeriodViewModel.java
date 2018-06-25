package com.florian.projet.viewModel;

import com.florian.projet.manager.ApplicationManager;

import java.util.Date;

public class PeriodViewModel {
    private static PeriodViewModel instance;
    private ApplicationManager applicationManager;

    public static PeriodViewModel getInstance() {
        if(instance == null) {
            instance = new PeriodViewModel();
        }
        return instance;
    }

    private PeriodViewModel() {
        this.applicationManager = ApplicationManager.getInstance();
    }

    public Date getFromDate() {
        return applicationManager.getFromDate();
    }

    public Date getToDate() {
        return applicationManager.getToDate();
    }

    public void resetFromDateToDefault() {
        applicationManager.resetFromDateToDefault();
    }

    public void resetToDateToDefault() {
        applicationManager.resetToDateToDefault();
    }

    public void setFromDate(Date fromDate) {
        applicationManager.setFromDate(fromDate);
    }

    public void setToDate(Date toDate) {
        applicationManager.setToDate(toDate);
    }
}
