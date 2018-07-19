package com.florian.projet.viewModel;

import android.util.Log;

import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.OFData;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.manager.ApplicationManager;
import com.florian.projet.manager.QuantityDatabaseManager;
import com.florian.projet.tools.ArticleWithDataCallback;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ArticleViewModel {
    private static ArticleViewModel instance;

    private QuantityDatabaseManager quantityDatabaseManager;
    private ApplicationManager applicationManager;

    private ArticleWithData currentArticle;

    public static ArticleViewModel getInstance() {
        if(instance == null) {
            instance = new ArticleViewModel();
        }
        return instance;
    }

    private ArticleViewModel() {
        quantityDatabaseManager = QuantityDatabaseManager.getInstance();
        applicationManager = ApplicationManager.getInstance();
    }

    public void getAllFavArticleByPeriod(final ArticleWithDataCallback callback) {

        quantityDatabaseManager.getAllArticleFavWithData(new ArticleWithDataCallback() {
            @Override
            public void onSuccess(List<ArticleWithData> articleList) {

                callback.onSuccess(getArticleByPeriod(articleList,false));
            }

            @Override
            public void onFailed(Exception e) {
                callback.onFailed(e);
            }
        });
    }

    public void getAllArticleByPeriod(final ArticleWithDataCallback callback) {

        quantityDatabaseManager.getAllArticleWithData(new ArticleWithDataCallback() {
            @Override
            public void onSuccess(List<ArticleWithData> articleList) {

                callback.onSuccess(getArticleByPeriod(articleList, true));
            }

            @Override
            public void onFailed(Exception e) {
                callback.onFailed(e);
            }
        });
    }

    private List<ArticleWithData> getArticleByPeriod(List<ArticleWithData> articleList, boolean removeArticleIfNoData) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(applicationManager.getFromDate());
        calendar.add(Calendar.SECOND, -1);


        final Date fromDate = calendar.getTime();
        final Date toDate = applicationManager.getToDate();

        calendar.clear();

        for (Iterator<ArticleWithData> iteratorAWD = articleList.iterator(); iteratorAWD.hasNext();) {
            ArticleWithData articleWithData = iteratorAWD.next();

            for (Iterator<OFData> iteratorArticleData = articleWithData.getDataList().iterator(); iteratorArticleData.hasNext();) {
                OFData OFData = iteratorArticleData.next();

                if (!(OFData.getDate().compareTo(fromDate) >= 0 && OFData.getDate().compareTo(toDate) <= 0)) {
                    iteratorArticleData.remove();
                }
            }

            if (articleWithData.getDataList().size() == 0 && removeArticleIfNoData) {
                iteratorAWD.remove();
            }
        }

        return articleList;
    }


    public void updateFavArticle(Article article) {
        try {
            quantityDatabaseManager.updateArticle(article);
        } catch (Exception e) {
            Log.d("Fav Article View Model", e.getMessage());
        }
    }

    public ArticleWithData getCurrentArticle() {
        return currentArticle;
    }

    public void setCurrentArticle(ArticleWithData currentArticle) {
        this.currentArticle = currentArticle;
    }

    public void delCurrentArticle() {
        currentArticle = null;
    }
}
