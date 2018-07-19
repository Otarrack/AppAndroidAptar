package com.florian.projet.viewModel;

import android.util.Log;

import com.florian.projet.bdd.entity.OFData;
import com.florian.projet.bdd.entity.Presse;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.bdd.relation.PresseWithData;
import com.florian.projet.manager.ApplicationManager;
import com.florian.projet.manager.QuantityDatabaseManager;
import com.florian.projet.tools.ArticleWithDataCallback;
import com.florian.projet.tools.PresseWithDataCallback;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PresseViewModel {
    private static PresseViewModel instance;

    private QuantityDatabaseManager quantityDatabaseManager;
    private ApplicationManager applicationManager;

    private PresseWithData currentPresse;

    public static PresseViewModel getInstance() {
        if(instance == null) {
            instance = new PresseViewModel();
        }
        return instance;
    }

    private PresseViewModel() {
        quantityDatabaseManager = QuantityDatabaseManager.getInstance();
        applicationManager = ApplicationManager.getInstance();
    }

    public void getAllFavPresseByPeriod(final PresseWithDataCallback callback) {

        quantityDatabaseManager.getAllPresseFavWithData(new PresseWithDataCallback() {
            @Override
            public void onSuccess(List<PresseWithData> presseWithDataList) {

                callback.onSuccess(getPresseByPeriod(presseWithDataList,false));
            }

            @Override
            public void onFailed(Exception e) {
                callback.onFailed(e);
            }
        });
    }

    public void getAllPresseByPeriod(final PresseWithDataCallback callback) {

        quantityDatabaseManager.getAllPresseWithData(new PresseWithDataCallback() {
            @Override
            public void onSuccess(List<PresseWithData> presseWithDataList) {
                callback.onSuccess(getPresseByPeriod(presseWithDataList, true));
            }

            @Override
            public void onFailed(Exception e) {
                callback.onFailed(e);
            }
        });
    }

    private List<PresseWithData> getPresseByPeriod(List<PresseWithData> presseWithDataList, boolean removePresseIfNoData) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(applicationManager.getFromDate());
        calendar.add(Calendar.SECOND, -1);


        final Date fromDate = calendar.getTime();
        final Date toDate = applicationManager.getToDate();

        calendar.clear();

        for (Iterator<PresseWithData> iteratorPWD = presseWithDataList.iterator(); iteratorPWD.hasNext();) {
            PresseWithData presseWithData = iteratorPWD.next();

            for (Iterator<OFData> iteratorArticleData = presseWithData.getDataList().iterator(); iteratorArticleData.hasNext();) {
                OFData OFData = iteratorArticleData.next();

                if (!(OFData.getDate().compareTo(fromDate) >= 0 && OFData.getDate().compareTo(toDate) <= 0)) {
                    iteratorArticleData.remove();
                }
            }

            if (presseWithData.getDataList().size() == 0 && removePresseIfNoData) {
                iteratorPWD.remove();
            }
        }

        return presseWithDataList;
    }

    public void updateFavPresse(Presse presse) {
        try {
            quantityDatabaseManager.updatePresse(presse);
        } catch (Exception e) {
            Log.d("Fav Presse View Model", e.getMessage());
        }
    }

    public void delCurrentPresse() {
        currentPresse = null;
    }

    public PresseWithData getCurrentPresse() {
        return currentPresse;
    }

    public void setCurrentPresse(PresseWithData currentPresse) {
        this.currentPresse = currentPresse;
    }
}
