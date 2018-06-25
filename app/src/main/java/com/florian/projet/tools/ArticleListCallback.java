package com.florian.projet.tools;

import com.florian.projet.bdd.entity.Article;

import java.util.List;

public interface ArticleListCallback {
    //TODO Améliorations : Factoriser les Callback comme ça
    void onSuccess(List<Article> articleList);
    void onFailed(Exception e);
}
