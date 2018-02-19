package com.florian.projet.manager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ApplicationManager {
    private static ApplicationManager instance;
    private Calendar calendar;
    private ArticleManager articleManager;
    private MachineManager machineManager;
    private SiteManager siteManager;
    private Date defaultDate;
    private Date fromDate;
    private Date toDate;

    private ApplicationManager() {

        calendar = Calendar.getInstance();

        machineManager = MachineManager.getInstance();
        articleManager = ArticleManager.getInstance();
        siteManager = SiteManager.getInstance();

        setDefaultDate();
    }

    public static ApplicationManager getInstance() {
        if(instance == null) {
            instance = new ApplicationManager();
        }
        return instance;
    }

    private Date getDefaultDate() {
        return defaultDate;
    }

    private void setDefaultDate() {
        defaultDate = new Date();
        calendar.setTime(defaultDate);
        calendar.add(Calendar.DATE, -1);
        defaultDate = calendar.getTime();
        calendar.clear();

        resetFromDateToDefault();
        resetToDateToDefault();
    }

    public void resetFromDateToDefault() {
        setFromDate(getDefaultDate());
    }

    public void resetToDateToDefault() {
        setToDate(getDefaultDate());
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

}
