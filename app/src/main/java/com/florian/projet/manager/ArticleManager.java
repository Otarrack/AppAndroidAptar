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

    public List<Article> getAllArticle() {
        return listArticle;
    }

    private void setListArticle() {
        listArticle = new ArrayList<>();
        Article article;
        for (int i = 1; i < 15; i++) {
            article = new Article(i,i * 103,i,i + 13*i);
            listArticle.add(article);
            //TODO: Récupèration des données à partir du manager qui récupère du fichier
        }
    }

    public Article getArticleAt(int position) {
        if (position < listArticle.size()) {
            return listArticle.get(position);
        } else {
            return null;
        }
    }


}
