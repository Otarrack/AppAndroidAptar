package com.florian.projet.viewModel;

import com.florian.projet.asyncTasks.DropboxDownloadDataFileTask;
import com.florian.projet.asyncTasks.GetCurrentAccountTask;
import com.florian.projet.asyncTasks.ParseMESFileTask;
import com.florian.projet.manager.ApplicationManager;

import java.io.File;
import java.io.IOException;

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

    public void parseXlsFile(File file, ParseMESFileTask.Callback callback) throws IOException{
        applicationManager.parseXLSFile(file, callback);
    }
}
