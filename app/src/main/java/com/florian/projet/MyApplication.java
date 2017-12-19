package com.florian.projet;

import android.app.Application;

import com.florian.projet.tools.MainAsyncTask;


public class MyApplication extends Application {
    private static MyApplication instance;
    private static MainAsyncTask mainAsyncTask;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        mainAsyncTask = new MainAsyncTask();
        mainAsyncTask.execute();
        mainAsyncTask.isThreadRunnning.set(true);
    }

    public static MainAsyncTask getMainAsyncTask() {
        return mainAsyncTask;
    }
}
