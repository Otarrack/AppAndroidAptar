package com.florian.projet.viewModel;

import com.florian.projet.asyncTasks.DropboxDownloadFileTask;
import com.florian.projet.asyncTasks.GetCurrentAccountTask;
import com.florian.projet.asyncTasks.ParseArticlePerfFileTask;
import com.florian.projet.asyncTasks.ParseMachinePerfFileTask;
import com.florian.projet.bdd.entity.Article;
import com.florian.projet.manager.ApplicationManager;
import com.florian.projet.manager.ArticleDatabaseManager;
import com.florian.projet.manager.MachineDatabaseManager;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.tools.SimpleCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SplashScreenViewModel {
    private static SplashScreenViewModel instance;
    private ApplicationManager applicationManager;

    public SplashScreenViewModel() {
        applicationManager = ApplicationManager.getInstance();
    }

    public static SplashScreenViewModel getInstance() {
        if (instance == null) {
            instance = new SplashScreenViewModel();
        }
        return instance;
    }

    public void initApp(GetCurrentAccountTask.Callback callback) {
        applicationManager.init(callback);
    }

    public void downloadDataFile(File path, String fileName, DropboxDownloadFileTask.Callback callback) {
        applicationManager.downloadDataFile(path, fileName, callback);
    }

    public void parseMachinePerfXlsFile(File file, ParseMachinePerfFileTask.Callback callback) throws IOException{
        applicationManager.parseMachinePerfXlsFile(file, callback);
    }

    public void parseArticlePerfXlsFile(File file, ParseArticlePerfFileTask.Callback callback) throws IOException{
        applicationManager.parseArticlePerfXlsFile(file, callback);
    }

    public ArrayList<Machine> initMachineListWithFav(ArrayList<Machine> allMachineInMESFile, ArrayList<Machine> allMachineInDatabase) {
        return applicationManager.getMachineListWithFavAndSite(allMachineInMESFile, allMachineInDatabase);
    }

    public void refreshAllMachineInDatabase(ArrayList<Machine> machineArrayList, SimpleCallback callback) {
        applicationManager.refreshAllMachine(machineArrayList, callback);
    }

    public void refreshAllArticleInDatabase(ArrayList<Article> articleArrayList, SimpleCallback callback) {
        applicationManager.refreshAllArticle(articleArrayList, callback);
    }

    public void getMachineLocalData(MachineDatabaseManager.GetAllTask.Callback callback) {
        applicationManager.getAllMachine(callback);
    }

    public void getArticleLocalData(ArticleDatabaseManager.GetAllTask.Callback callback) {
        applicationManager.getAllArticle(callback);
    }
}
