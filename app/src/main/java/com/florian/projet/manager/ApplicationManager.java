package com.florian.projet.manager;

import com.florian.projet.model.Article;
import com.florian.projet.model.Machine;
import com.florian.projet.model.OF;
import com.florian.projet.model.Person;
import com.florian.projet.model.Site;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ApplicationManager {
    private static ApplicationManager instance;
    private Calendar calendar;
    private ArticleManager articleManager;
    private MachineManager machineManager;
    private SiteManager siteManager;
    private List<OF> listOF;
    private Date defaultDate;
    private Date fromDate;
    private Date toDate;

    private ApplicationManager() {

        calendar = Calendar.getInstance();

        machineManager = MachineManager.getInstance();
        articleManager = ArticleManager.getInstance();
        siteManager = SiteManager.getInstance();

        setDefaultDate();
        setAllOF();
    }

    public static ApplicationManager getInstance() {
        if(instance == null) {
            instance = new ApplicationManager();
        }
        return instance;
    }

    private void setAllOF() {
        listOF = new ArrayList<>();
        OF of;
        String numOf;
        Site site;
        Article article;
        Machine machine;
        Person person;
        Date dateDeclarationProduction;
        Date dateStartPlanned;
        Date dateEndPlanned;
        double qtAsked;
        double volume;
        double waste;
        double cadence;

        for (int i = 1; i < 15; i++) {

            numOf = "OF : " + 1;
            dateDeclarationProduction = new Date();
            dateStartPlanned = new Date();
            dateEndPlanned = new Date();
            qtAsked = i;
            volume = i * 2;
            waste = i * 3;
            cadence = i * 4;
            site = new Site("Site " + i,volume,waste);
            machine = new Machine("Machine " + i,volume,waste);
            article = new Article("Article " + i,qtAsked,volume,waste,cadence);
            person = new Person();

            siteManager.addSite(site);
            machineManager.addMachine(machine);
            articleManager.addArticle(article);

            of = new OF(numOf, site, machine, article, person, dateDeclarationProduction,
                    dateStartPlanned, dateEndPlanned, qtAsked, volume, waste, cadence);

            listOF.add(of);
            //TODO: Récupèration des données à partir du manager qui récupère du fichier
        }
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
