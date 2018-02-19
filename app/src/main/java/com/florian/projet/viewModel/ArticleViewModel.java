package com.florian.projet.viewModel;

import com.florian.projet.manager.ArticleManager;
import com.florian.projet.model.Article;

public class ArticleViewModel {
    private static ArticleViewModel instance;
    private ArticleManager articleManager;
    private Article currentArticle;

    public static ArticleViewModel getInstance() {
        if(instance == null) {
            instance = new ArticleViewModel();
        }
        return instance;
    }

    private ArticleViewModel() {
        articleManager = ArticleManager.getInstance();
    }

    public void setCurrentArticle(int position) {
        currentArticle = articleManager.getArticleAt(position);
    }

    public void delCurrentArticle() {
        currentArticle = null;
    }
}
