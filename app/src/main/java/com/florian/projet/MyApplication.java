package com.florian.projet;

import android.app.Application;
import android.widget.Toast;

import com.florian.projet.bdd.database.MachineDataBase;
import com.florian.projet.manager.DatabaseManager;
import com.florian.projet.tools.SimpleCallback;


public class MyApplication extends Application {
    private static MyApplication instance;
    private DatabaseManager databaseManager;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        MachineDataBase.setInstance(getApplicationContext());

        databaseManager = DatabaseManager.getInstance();

        /* //RESET DATABASE
        databaseManager.deleteAllData(new SimpleCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailed(Exception e) {

            }
        });*/
    }

}