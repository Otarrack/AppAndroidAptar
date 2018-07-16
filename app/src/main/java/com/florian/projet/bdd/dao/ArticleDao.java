package com.florian.projet.bdd.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.ArticleData;
import com.florian.projet.bdd.relation.ArticleWithData;

import java.util.List;

/**
 * Interface qui regroupe les requêtes et appels à la base de données des articles
 *
 * @author Florian
 */
@Dao
public interface ArticleDao {

    /**
     * Inserer tout les articles de la liste
     *
     * @param articleList Liste d'article à ajouter
     *
     * @return Liste des Id des articles ajoutés
     */
    @Insert
    List<Long> insertAllArticle(List<Article> articleList);

    /**
     * Inserer tous les articles de la liste
     *
     * @param articleList Liste des données d'article à ajouter
     */
    @Insert
    void insertAllData(List<ArticleData> articleList);

    /**
     * Modifie l'article
     *
     * @param article Article à modifier
     *
     * @return Id de l'article ajouté
     */
    @Update
    int updateArticle(Article article);

    /**
     * Sélectionne tous les articles
     *
     * @return Liste des articles avec les données associées ({@link ArticleWithData})
     */
    @Transaction
    @Query("SELECT * FROM articles")
    List<ArticleWithData> getAll();

    /**
     * Sélectionne tous les articles en favoris
     *
     * @return Liste des articles en favoris avec les données associées ({@link ArticleWithData})
     */
    @Transaction
    @Query("SELECT * FROM articles WHERE favorite = 1")
    List<ArticleWithData> getAllFav();
}
