package com.florian.projet.viewModel;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.users.FullAccount;
import com.florian.projet.asyncTasks.DropboxDownloadFileTask;
import com.florian.projet.asyncTasks.ParseArticlePerfFileTask;
import com.florian.projet.asyncTasks.ParseMachinePerfFileTask;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.manager.ApplicationManager;
import com.florian.projet.manager.MachineDatabaseManager;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.tools.ArticleWithDataCallback;
import com.florian.projet.tools.MachineCallback;
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

    public void initApp() {
        applicationManager.init();
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

    public ArrayList<ArticleWithData> initArticleListWithFav(ArrayList<ArticleWithData> allArticleInFile, ArrayList<ArticleWithData> allArticleInDatabase) {
        return applicationManager.getArticleListWithFav(allArticleInFile, allArticleInDatabase);
    }

    public void refreshAllMachineInDatabase(ArrayList<Machine> machineArrayList, SimpleCallback callback) {
        applicationManager.refreshAllMachine(machineArrayList, callback);
    }

    public void refreshAllArticleInDatabase(ArrayList<ArticleWithData> articleArrayList, SimpleCallback callback) {
        applicationManager.refreshAllArticle(articleArrayList, callback);
    }

    public void getMachineLocalData(MachineCallback callback) {
        applicationManager.getAllMachine(callback);
    }

    public void getArticleLocalData(ArticleWithDataCallback callback) {
        applicationManager.getAllArticle(callback);
    }
}
