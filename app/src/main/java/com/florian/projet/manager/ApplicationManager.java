package com.florian.projet.manager;

import android.content.Context;

import com.dropbox.core.v2.DbxClientV2;
import com.florian.projet.MyApplication;
import com.florian.projet.asyncTasks.DropboxDownloadDataFileTask;
import com.florian.projet.asyncTasks.GetCurrentAccountTask;
import com.florian.projet.asyncTasks.ParseMESFileTask;
import com.florian.projet.model.Machine;
import com.florian.projet.model.SiteEnum;
import com.florian.projet.tools.SimpleCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ApplicationManager {
    private static ApplicationManager instance;

    public static final String FILE_MES_NAME = "fichiermes.xls";

    private MyApplication myApplication;
    private DropboxManager dropboxManager;
    private MESFileManager dataMESFileManager;
    private DatabaseManager databaseManager;

    private Calendar calendar;
    private Date defaultDate;
    private Date fromDate;
    private Date toDate;

    private ApplicationManager() {
        myApplication = MyApplication.getInstance();
        calendar = Calendar.getInstance();
        dataMESFileManager = MESFileManager.getInstance();
        databaseManager = DatabaseManager.getInstance();
    }

    public static ApplicationManager getInstance() {
        if(instance == null) {
            instance = new ApplicationManager();
        }
        return instance;
    }


    public void getAllMachine(DatabaseManager.GetAllTask.Callback callback) {
        databaseManager.getAllMachine(callback);
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

    public ArrayList<Machine> getMachineListWithFav(ArrayList<Machine> allMachineInMESFile, ArrayList<Machine> allMachineInDatabase) {

        ArrayList<String> machineFavNameList = getMachineFavNameList(allMachineInDatabase);
        ArrayList<Machine> finalMachineList = new ArrayList<>();

        for (Machine machine : allMachineInMESFile) {
            if (machineFavNameList.contains(machine.getMachineName())) {
                machine.setFavorite(true);
            }

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

        return  machineFavNameList;
    }

    public void initSiteEnum(ArrayList<Machine> machineArrayList) {
        for (Machine machine : machineArrayList) {
            char siteChar = machine.getMachineName().charAt(0);

            for (SiteEnum siteEnum: SiteEnum.values()) {
                for (int num : siteEnum.getSiteNum()) {
                    if (String.valueOf(siteChar).equals(String.valueOf(num))){
                        siteEnum.addMachineToList(machine);
                    }
                }
            }
        }
    }

    public void refreshAllMachine(ArrayList<Machine> machineArrayList, SimpleCallback callback) {
        databaseManager.refreshAllMachine(machineArrayList, callback);
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

/*    public static class InitFavMachineList extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseManager.getInstance().getAllFavMachine(new DatabaseManager.GetAllFavTask.Callback() {
                @Override
                public void onSuccess(List<Machine> machineList) {
                    SiteEnum.FAV.setMachineList(new ArrayList<>(machineList));
                }

                @Override
                public void onFailed(Exception e) {
                    Log.d("FAIL Load Fav", e.getMessage());
                }
            });
            return null;
        }
    }*/

}
