package com.florian.projet.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.florian.projet.asyncTasks.ParseMESFileTask;
import com.florian.projet.model.Machine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MESFileManager {
    private static MESFileManager instance;

    private Machine totalMachine;
    private ArrayList<Machine> allMachineMESFilesList;

    private MESFileManager() {
        allMachineMESFilesList = new ArrayList<>();
    }

    public static MESFileManager getInstance() {
        if(instance == null) {
            instance = new MESFileManager();
        }
        return instance;
    }

    void parseXLSFile(final Context context, File file, final ParseMESFileTask.Callback callback) throws IOException {
        new ParseMESFileTask(context, new ParseMESFileTask.Callback() {
            @Override
            public void onSuccess(ArrayList<Machine> dataLineList) {

                for (Machine machine : dataLineList) {

                    if (machine.getMachineName().equals("Machine")) {
                        totalMachine = machine;
                    } else {
                        allMachineMESFilesList.add(machine);
                    }
                }

                callback.onSuccess(dataLineList);
            }

            @Override
            public void onFailed(Exception e) {
                callback.onFailed(e);

            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, file);
    }

    public ArrayList<Machine> getAllMachineMES() {
        return allMachineMESFilesList;
    }

}

