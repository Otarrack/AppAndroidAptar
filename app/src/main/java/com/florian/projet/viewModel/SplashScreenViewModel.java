package com.florian.projet.viewModel;

import android.content.Context;

import com.florian.projet.asyncTasks.DropboxDownloadDataFileTask;
import com.florian.projet.asyncTasks.GetCurrentAccountTask;
import com.florian.projet.asyncTasks.ParseMESFileTask;
import com.florian.projet.manager.ApplicationManager;
import com.florian.projet.manager.DatabaseManager;
import com.florian.projet.model.Machine;
import com.florian.projet.tools.SimpleCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public void downloadDataFile(File path, DropboxDownloadDataFileTask.Callback callback) {
        applicationManager.downloadDataFile(path, callback);
    }

    public void parseXlsFile(Context context, File file, ParseMESFileTask.Callback callback) throws IOException{
        applicationManager.parseXLSFile(context, file, callback);
    }

    public ArrayList<Machine> initMachineListWithFav(ArrayList<Machine> allMachineInMESFile, ArrayList<Machine> allMachineInDatabase) {
        return applicationManager.getMachineListWithFav(allMachineInMESFile, allMachineInDatabase);
    }

    public void initSiteEnum(ArrayList<Machine> machineArrayList) {
        applicationManager.initSiteEnum(machineArrayList);
    }

    public void refreshAllMachineInDatabase(ArrayList<Machine> machineArrayList, SimpleCallback callback) {
        applicationManager.refreshAllMachine(machineArrayList, callback);
    }

    public void getLocalData(DatabaseManager.GetAllTask.Callback callback) {
        applicationManager.getAllMachine(callback);
    }
}
