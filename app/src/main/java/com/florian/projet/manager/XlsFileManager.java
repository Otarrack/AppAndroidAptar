package com.florian.projet.manager;

import android.os.AsyncTask;

import com.florian.projet.asyncTasks.ParseArticlePerfFileTask;
import com.florian.projet.asyncTasks.ParseMachinePerfFileTask;

import java.io.File;
import java.io.IOException;

public class XlsFileManager {
    private static XlsFileManager instance;

    /**
     * Singleton qui permet de récupérer l'instance en cours si elle a déjà été créée
     *
     * @return Instance du manager
     */
    public static XlsFileManager getInstance() {
        if(instance == null) {
            instance = new XlsFileManager();
        }
        return instance;
    }

    void parseMachinePerfXlsFile(File file, ParseMachinePerfFileTask.Callback callback) {
        new ParseMachinePerfFileTask(callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, file);
    }

    void parseArticlePerfXlsFile(File file, ParseArticlePerfFileTask.Callback callback) {
        new ParseArticlePerfFileTask(callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, file);
    }

}

