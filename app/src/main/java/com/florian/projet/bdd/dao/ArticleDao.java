package com.florian.projet.bdd.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.OFData;
import com.florian.projet.bdd.entity.Presse;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.bdd.relation.PresseWithData;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface qui regroupe les requêtes et appels à la base de données des articles
 *
 * @author Florian
 */
@Dao
public interface ArticleDao {

    /**
     * Inserer les articles dans la base
     *
     * @param articleList Articles à ajouter
     *
     * @return Id des articles ajoutés
     */
    @Insert
    List<Long> insertAllArticle(ArrayList<Article> articleList);

    /**
     * Modifie l'article
     *
     * @param article Article à modifier
     *
     * @return Id de l'article modifié
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
    List<ArticleWithData> getAllArticleWithData();

    /**
     * Sélectionne tous les articles en favoris
     *
     * @return Liste des articles en favoris
     */
    @Transaction
    @Query("SELECT * FROM articles WHERE favorite = 1")
    List<Article> getAllArticleFav();

    /**
     * Sélectionne tous les articles en favoris avec les données associées
     *
     * @return Liste des articles en favoris avec les données associées ({@link ArticleWithData})
     */
    @Transaction
    @Query("SELECT * FROM articles WHERE favorite = 1")
    List<ArticleWithData> getAllArticleFavWithData();
}
