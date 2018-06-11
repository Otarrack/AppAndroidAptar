package com.florian.projet.manager;

import android.content.Context;

import com.dropbox.core.v2.DbxClientV2;
import com.florian.projet.asyncTasks.DropboxDownloadDataFileTask;
import com.florian.projet.asyncTasks.GetCurrentAccountTask;
import com.florian.projet.asyncTasks.ParseMESFileTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class ApplicationManager {
    private static ApplicationManager instance;

    public static final String FILE_MES_NAME = "fichiermes.xls";

    private DropboxManager dropboxManager;
    private MESFileManager dataMESFileManager;

    private Calendar calendar;
    private Date defaultDate;
    private Date fromDate;
    private Date toDate;

    private ApplicationManager() {
        calendar = Calendar.getInstance();
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


    public void parseXLSFile(Context context, File file, final ParseMESFileTask.Callback callback) throws IOException {
        if (file.getName().equals(FILE_MES_NAME)) {
            dataMESFileManager.parseXLSFile(context, file, callback);
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
