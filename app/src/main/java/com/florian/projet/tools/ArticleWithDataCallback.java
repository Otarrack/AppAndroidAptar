package com.florian.projet.tools;

import com.florian.projet.bdd.relation.ArticleWithData;

import java.util.List;

public interface ArticleWithDataCallback {
    void onSuccess(List<ArticleWithData> articleList);
    void onFailed(Exception e);
}
