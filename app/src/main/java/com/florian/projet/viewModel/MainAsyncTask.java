package com.florian.projet.viewModel;

import android.os.AsyncTask;

import com.florian.projet.manager.ArticleManager;
import com.florian.projet.manager.MachineManager;
import com.florian.projet.manager.SiteManager;

import java.util.concurrent.atomic.AtomicBoolean;


public class MainAsyncTask extends AsyncTask<Void, Integer, Boolean> {
    /** * Le AtomicBoolean pour lancer et stopper la Thread */
    public AtomicBoolean isThreadRunnning = new AtomicBoolean();
    /** * Le AtomicBoolean pour mettre en pause et relancer la Thread */
    public AtomicBoolean isThreadPausing = new AtomicBoolean();

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            while (isThreadRunnning.get()) {
                if (isThreadPausing.get()) {
                    Thread.sleep(2000);
                } else {
                    Thread.sleep(100);
                    ArticleManager.getInstance();
                    SiteManager.getInstance();
                    MachineManager.getInstance();

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}

