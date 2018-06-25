package com.florian.projet.viewModel;

import android.util.Log;

import com.florian.projet.bdd.entity.Article;
import com.florian.projet.manager.ApplicationManager;
import com.florian.projet.manager.ArticleDatabaseManager;
import com.florian.projet.tools.ArticleListCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ArticleViewModel {
    private static ArticleViewModel instance;

    private ArticleDatabaseManager articleDatabaseManager;
    private ApplicationManager applicationManager;

    public static ArticleViewModel getInstance() {
        if(instance == null) {
            instance = new ArticleViewModel();
        }
        return instance;
    }

    private ArticleViewModel() {
        articleDatabaseManager = ArticleDatabaseManager.getInstance();
        applicationManager = ApplicationManager.getInstance();
    }

    public void getArticleByPeriod(final ArticleListCallback callback) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(applicationManager.getFromDate());
        calendar.add(Calendar.SECOND, -1);


        Date fromDate = calendar.getTime();
        Date toDate = applicationManager.getToDate();

        calendar.clear();

        articleDatabaseManager.getArticleByPeriod(fromDate, toDate, new ArticleListCallback() {
            @Override
            public void onSuccess(List<Article> articleList) {
                List<Article> finalArticleList = new ArrayList<>();
                boolean add = false;

                for (Article article : articleList) {
                    Log.d("Date -> " + article.getName(), article.getDate().toString());
                    Log.d("FAV -> " + article.getName(), article.isFavorite() + "");
                    if (finalArticleList.size() > 0) {
                        for (Article articleInFinalList : finalArticleList) {
                            if (articleInFinalList.getName().equals(article.getName())) {
                                articleInFinalList.setQuantity(articleInFinalList.getQuantity() + article.getQuantity());
                                add = true;
                            }
                        }
                    }

                    if (add) {
                        add = false;
                    } else {
                        finalArticleList.add(article);
                    }
                }

                callback.onSuccess(finalArticleList);
            }

            @Override
            public void onFailed(Exception e) {
                callback.onFailed(e);
            }
        });
    }

}
