package com.florian.projet.manager;

import com.florian.projet.model.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleManager {
    private static ArticleManager instance;
    private List<Article> listArticle;

    private ArticleManager() {
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

    private void setListArticle() {
        listArticle = new ArrayList<>();
        Article site;
        for (int i = 1; i < 5; i++) {
            site = new Article(i,i * 103,i,i + 13*i);
            listArticle.add(site);
            //TODO: Récupèration des données à partir du manager qui récupère du serveur
        }
    }
}
