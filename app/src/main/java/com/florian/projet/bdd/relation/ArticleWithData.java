package com.florian.projet.bdd.relation;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.OFData;

import java.util.List;

/**
 * Classe de relation entre les articles et leurs données
 *
 * @author Florian
 */
public class ArticleWithData {

    @Embedded
    private Article article;

    @Relation(parentColumn = "id", entityColumn = "idArticle")
    private List<OFData> dataList;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<OFData> getDataList() {
        return dataList;
    }

    public void setDataList(List<OFData> dataList) {
        this.dataList = dataList;
    }

    /**
     * Methode qui retourne la quantité total de l'article
     *
     * @return Quantité
     */
    public double getTotalQuantity() {
        Double totalQuantity = 0.0;

        for (OFData OFData : dataList) {
            totalQuantity += OFData.getQuantity();
        }

        return totalQuantity;
    }
}
