package com.florian.projet.viewModel;

import com.florian.projet.asyncTasks.DropboxDownloadFileTask;
import com.florian.projet.asyncTasks.ParseQuantityFileTask;
import com.florian.projet.asyncTasks.ParseMachinePerfFileTask;
import com.florian.projet.manager.ApplicationManager;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.model.QuantityFileLine;
import com.florian.projet.tools.ArticleWithDataCallback;
import com.florian.projet.tools.MachineCallback;
import com.florian.projet.tools.PresseWithDataCallback;
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

    public void parseArticlePerfXlsFile(File file, ParseQuantityFileTask.Callback callback) throws IOException{
        applicationManager.parseArticlePerfXlsFile(file, callback);
    }

    public ArrayList<Machine> initMachineListWithSite(ArrayList<Machine> allMachineInMESFile) {
        return applicationManager.getMachineListWithSite(allMachineInMESFile);
    }

    public void refreshAllMachineInDatabase(ArrayList<Machine> machineArrayList, SimpleCallback callback) {
        applicationManager.refreshAllMachine(machineArrayList, callback);
    }

    public void refreshAllArticleInDatabase(ArrayList<QuantityFileLine> quantityFileLineArrayList, SimpleCallback callback) {
        applicationManager.refreshAllArticle(quantityFileLineArrayList, callback);
    }

    /**
     * @param callback Callback pour récupérer la réponse
     *
     * @see ApplicationManager
     */
    public void getMachineLocalData(MachineCallback callback) {
        applicationManager.getAllMachine(callback);
    }

    /**
     * @param callback Callback pour récupérer la réponse
     *
     * @see ApplicationManager
     */
    public void getArticleLocalData(ArticleWithDataCallback callback) {
        applicationManager.getAllArticleWithData(callback);
    }

    /**
     * @param callback Callback pour récupérer la réponse
     *
     * @see ApplicationManager
     */
    public void getPresseLocalData(PresseWithDataCallback callback) {
        applicationManager.getAllPresseWithData(callback);
    }
}
