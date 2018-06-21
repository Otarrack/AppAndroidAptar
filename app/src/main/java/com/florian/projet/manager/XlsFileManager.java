package com.florian.projet.manager;

import android.os.AsyncTask;

import com.florian.projet.asyncTasks.ParseArticlePerfFileTask;
import com.florian.projet.asyncTasks.ParseMachinePerfFileTask;

import java.io.File;
import java.io.IOException;

public class XlsFileManager {
    private static XlsFileManager instance;

    public static XlsFileManager getInstance() {
        if(instance == null) {
            instance = new XlsFileManager();
        }
        return instance;
    }

    void parseMachinePerfXlsFile(File file, ParseMachinePerfFileTask.Callback callback) throws IOException {
        new ParseMachinePerfFileTask(callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, file);
    }

    void parseArticlePerfXlsFile(File file, ParseArticlePerfFileTask.Callback callback) throws IOException {
        new ParseArticlePerfFileTask(callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, file);
    }

}

