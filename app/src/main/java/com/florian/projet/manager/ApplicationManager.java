package com.florian.projet.manager;

import com.dropbox.core.v2.DbxClientV2;
import com.florian.projet.asyncTasks.DropboxDownloadDataFileTask;
import com.florian.projet.asyncTasks.GetCurrentAccountTask;
import com.florian.projet.asyncTasks.ParseMESFileTask;
import com.florian.projet.quarantaine.Article;
import com.florian.projet.quarantaine.Machine;
import com.florian.projet.quarantaine.Site;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ApplicationManager {
    private static ApplicationManager instance;

    public static final String FILE_MES_NAME = "fichiermes.xls";

    private DropboxManager dropboxManager;
    private MESFileManager dataMESFileManager;

    private Calendar calendar;
    private ArticleManager articleManager;
    private MachineManager machineManager;
    private Date defaultDate;
    private Date fromDate;
    private Date toDate;

    private ApplicationManager() {
        calendar = Calendar.getInstance();
        machineManager = MachineManager.getInstance();
        articleManager = ArticleManager.getInstance();
        dataMESFileManager = MESFileManager.getInstance();
    }

    public static ApplicationManager getInstance() {
        if(instance == null) {
            instance = new ApplicationManager();
        }
        return instance;
    }

    public void init(GetCurrentAccountTask.Callback callback) {
        dropboxManager = DropboxManager.getInstance();
        dropboxManager.init(callback);

        setDefaultDate();
    }

    private DbxClientV2 getDbxClient() {
        return dropboxManager.getClient();
    }

    public void downloadDataFile(File path, DropboxDownloadDataFileTask.Callback callback) {
        dropboxManager.downloadFile(path, callback);
    }


    public void parseXLSFile(File file, final ParseMESFileTask.Callback callback) throws IOException {
        if (file.getName().equals(FILE_MES_NAME)) {
            dataMESFileManager.parseXLSFile(file, callback);
        }
    }

    /*private void setAllOF() {
        OF of;
        String numOf;
        String nameSite;
        String nameMachine;
        String numArticle;
        Person person;
        Date dateDeclarationProduction;
        Date dateStartPlanned;
        Date dateEndPlanned;
        double qtAsked;
        double volume;
        double waste;
        double cadence;

        Site site;
        Article article;
        Machine machine;

        for (int i = 1; i <= 15; i++) {

            dateDeclarationProduction = new Date();
            dateStartPlanned = new Date();
            dateEndPlanned = new Date();

            numOf = "OF : " + i;

            if (i < 6) {
                numArticle = "12099EI1293";
                nameSite = "Oyonnax";
                nameMachine = "293ZEE";
            } else if (i < 9) {
                numArticle = "12J3I310SDK";
                nameSite = "Groissiat";
                nameMachine = "123RAR";
            } else {
                numArticle = "213NFNEO2L1";
                nameSite = "Martignat";
                nameMachine = "KZEA21";
            }

            if (i < 2) {
                calendar.setTime(dateStartPlanned);
                calendar.add(Calendar.DAY_OF_YEAR, -10);
                dateStartPlanned = calendar.getTime();

                calendar.setTime(dateEndPlanned);
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                dateEndPlanned = calendar.getTime();
            } else if (i < 4) {
                calendar.setTime(dateStartPlanned);
                calendar.add(Calendar.DAY_OF_YEAR, -10);
                dateStartPlanned = calendar.getTime();

                calendar.setTime(dateEndPlanned);
                calendar.add(Calendar.DAY_OF_YEAR, 5);
                dateEndPlanned = calendar.getTime();
            } else {
                calendar.setTime(dateStartPlanned);
                calendar.add(Calendar.DAY_OF_YEAR, 4);
                dateStartPlanned = calendar.getTime();

                calendar.setTime(dateEndPlanned);
                calendar.add(Calendar.DAY_OF_YEAR, 12);
                dateEndPlanned = calendar.getTime();
            }

            qtAsked = i;
            volume = i * 2;
            waste = i * 3;
            cadence = i * 4;
            person = new Person();

            of = new OF(numOf, nameSite, nameMachine, numArticle, person, dateDeclarationProduction,
                    dateStartPlanned, dateEndPlanned, qtAsked, volume, waste, cadence);

            site = new Site(nameSite);
            machine = new Machine(nameMachine);
            article = new Article(numArticle);

            site = getExistingSite(site);
            site.setVolume(site.getVolume() + volume);
            site.setWaste(site.getWaste() + waste);

            machine = getExistingMachine(machine);
            machine.setVolume(machine.getVolume() + volume);
            machine.setWaste(machine.getWaste() + waste);

            article = getExistingArticle(article);
            article.addOfToList(of);

            article.setQtAsked(article.getQtAsked() + qtAsked);
            article.setVolume(article.getVolume() + volume);
            article.setWaste(article.getWaste() + waste);
            article.setCadence(article.getCadence() + cadence);
            article.setSite(site);
            article.setMachine(machine);
            article.setDateDeclarationProduction(dateDeclarationProduction);
            article.setDateStartPlanned(dateStartPlanned);
            article.setDateEndPlanned(dateEndPlanned);

            ofManager.addOf(of);

            //TODO: Récupèration des données à partir du manager qui récupère du fichier
        }
    }*/

    private Article getExistingArticle(Article newArticle) {
        List<Article> articleList = articleManager.getAllArticle();

        for (Article article : articleList) {
            if (newArticle.getNumArticle().equals(article.getNumArticle())) {
                return article;
            }
        }

        articleManager.addArticle(newArticle);
        return newArticle;
    }

    private Machine getExistingMachine(Machine newMachine) {
        List<Machine> machineList = machineManager.getAllMachine();

        for (Machine machine : machineList) {
            if (newMachine.getMachineName().equals(machine.getMachineName())) {
                return machine;
            }
        }

        machineManager.addMachine(newMachine);
        return newMachine;
    }

    /*private Site getExistingSite(Site newSite) {
        List<Site> siteList = siteManager.getAllSite();

        for (Site site : siteList) {
            if (newSite.getSiteName().equals(site.getSiteName())) {
                return site;
            }
        }

        siteManager.addSite(newSite);
        return newSite;
    }*/

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
