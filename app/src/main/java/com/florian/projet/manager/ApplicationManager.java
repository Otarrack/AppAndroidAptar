package com.florian.projet.manager;

import com.florian.projet.MyApplication;
import com.florian.projet.asyncTasks.DropboxDownloadFileTask;
import com.florian.projet.asyncTasks.GetCurrentAccountTask;
import com.florian.projet.asyncTasks.ParseArticlePerfFileTask;
import com.florian.projet.asyncTasks.ParseMachinePerfFileTask;
import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.model.ArticleLine;
import com.florian.projet.tools.ArticleWithDataCallback;
import com.florian.projet.tools.SimpleCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ApplicationManager {
    private static ApplicationManager instance;

    public static final String FILE_PERF_MACHINE_NAME = "fichiermes.xls";
    public static final String FILE_PERF_ARTICLE_NAME = "par site valorisation des produits finis et semi-finis produits.csv";

    public static boolean failLoadingMachine = false;
    public static boolean failLoadingArticle = false;

    private MyApplication myApplication;
    private DropboxManager dropboxManager;
    private XlsFileManager xlsFileManager;
    private MachineDatabaseManager machineDatabaseManager;
    private ArticleDatabaseManager articleDatabaseManager;

    private Calendar calendar;
    private Date defaultDate;
    private Date fromDate;
    private Date toDate;

    private ApplicationManager() {
        myApplication = MyApplication.getInstance();
        calendar = Calendar.getInstance();
        xlsFileManager = XlsFileManager.getInstance();
        machineDatabaseManager = MachineDatabaseManager.getInstance();
        articleDatabaseManager = ArticleDatabaseManager.getInstance();

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
        calendar.setTime(fromDate);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),0,0,0);
        fromDate = calendar.getTime();
        calendar.clear();

        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        calendar.setTime(toDate);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),23,59,59);
        toDate = calendar.getTime();
        calendar.clear();

        this.toDate = toDate;
    }

    public void getAllMachine(MachineDatabaseManager.GetAllTask.Callback callback) {
        machineDatabaseManager.getAllMachine(callback);
    }

    public void getAllArticle(ArticleWithDataCallback callback) {
        articleDatabaseManager.getAllArticle(callback);
    }

    public void init(GetCurrentAccountTask.Callback callback) {
        dropboxManager = DropboxManager.getInstance();
        dropboxManager.init(callback);

        setDefaultDate();
    }

    public void downloadDataFile(File path, String fileName, DropboxDownloadFileTask.Callback callback) {
        dropboxManager.downloadFile(path, fileName, callback);
    }

    public void parseMachinePerfXlsFile(File file, final ParseMachinePerfFileTask.Callback callback) throws IOException {
        xlsFileManager.parseMachinePerfXlsFile(file, callback);
    }

    public void parseArticlePerfXlsFile(File file, final ParseArticlePerfFileTask.Callback callback) throws IOException {
        xlsFileManager.parseArticlePerfXlsFile(file, callback);
    }

    public ArrayList<Machine> getMachineListWithFavAndSite(ArrayList<Machine> allMachineInMESFile, ArrayList<Machine> allMachineInDatabase) {

        ArrayList<String> machineFavNameList = getMachineFavNameList(allMachineInDatabase);
        ArrayList<Machine> finalMachineList = new ArrayList<>();

        for (Machine machine : allMachineInMESFile) {
            if (machineFavNameList.contains(machine.getMachineName())) {
                machine.setFavorite(true);
            }

            char siteChar = machine.getMachineName().charAt(0);

            machine.setSite(Integer.parseInt(String.valueOf(siteChar)));

            finalMachineList.add(machine);
        }

        return finalMachineList;

    }

    private ArrayList<String> getMachineFavNameList(List<Machine> machineList) {
        ArrayList<String> machineFavNameList = new ArrayList<>();

        if (machineList != null) {
            for (Machine machine : machineList) {
                if (machine.isFavorite()) {
                    machineFavNameList.add(machine.getMachineName());
                }
            }
        }

        return machineFavNameList;
    }

    public ArrayList<ArticleWithData> getArticleListWithFav(ArrayList<ArticleWithData> allArticleInFile, ArrayList<ArticleWithData> allArticleInDatabase) {
        ArrayList<String> articleFavNameList = getArticleFavNameList(allArticleInDatabase);

        for (ArticleWithData articleWithData : allArticleInFile) {
            if (articleFavNameList.contains(articleWithData.getArticle().getName())) {
                articleWithData.getArticle().setFavorite(true);
            }
        }

        return allArticleInFile;
    }

    private ArrayList<String> getArticleFavNameList(List<ArticleWithData> articleList) {
        ArrayList<String> articleFavNameList = new ArrayList<>();

        if (articleList != null) {
            for (ArticleWithData articleWithData : articleList) {
                if (articleWithData.getArticle().isFavorite()) {
                    articleFavNameList.add(articleWithData.getArticle().getName());
                }
            }
        }

        return articleFavNameList;
    }

    public void refreshAllMachine(ArrayList<Machine> machineArrayList, SimpleCallback callback) {
        machineDatabaseManager.refreshAllMachine(machineArrayList, callback);
    }

    public void refreshAllArticle(ArrayList<ArticleWithData> articleArrayList, SimpleCallback callback) {
        articleDatabaseManager.refreshAllArticle(articleArrayList, callback);
    }
}
