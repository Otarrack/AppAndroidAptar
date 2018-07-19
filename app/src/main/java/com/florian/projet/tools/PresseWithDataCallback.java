package com.florian.projet.tools;

import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.bdd.relation.PresseWithData;

import java.util.List;

public interface PresseWithDataCallback {
    void onSuccess(List<PresseWithData> presseList);
    void onFailed(Exception e);
}
