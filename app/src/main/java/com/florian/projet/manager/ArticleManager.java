package com.florian.projet.manager;

import com.florian.projet.model.Article;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ArticleManager {
    private static ArticleManager instance;
    private List<Article> listArticle;

    private ArticleManager() {
        listArticle = new ArrayList<>();
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

    public void addArticle(Article article) {
        listArticle.add(article);
    }

    public Article getArticleAt(int position) {
        if (position < listArticle.size()) {
            return listArticle.get(position);
        } else {
            return null;
        }
    }
}