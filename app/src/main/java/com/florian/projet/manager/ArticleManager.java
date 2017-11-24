package com.florian.projet.manager;

import com.florian.projet.model.Article;

import java.util.List;

public class ArticleManager {
    private static ArticleManager instance;
    private List<Article> listArticle;

    public ArticleManager() {
        setListArticle();
    }

    public static ArticleManager getInstance() {
        if(instance == null) {
            instance = new ArticleManager();
        }
        return instance;
    }

    public List<Article> getListArticle() {
        return listArticle;
    }

    public void setListArticle() {
        for (int i = 1; i < 5; i++) {
            Article site = new Article(i,i * 103,i,i + 13*i);
            listArticle.add(site);
        }
    }
}
