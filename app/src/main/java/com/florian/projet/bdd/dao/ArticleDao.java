package com.florian.projet.bdd.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.ArticleData;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.bdd.relation.ArticleWithData;

import java.util.Date;
import java.util.List;

@Dao
public interface ArticleDao {

    @Insert
    List<Long> insertAllArticle(List<Article> articleList);

    @Insert
    List<Long> insertAllData(List<ArticleData> articleList);

    @Update
    int updateArticle(Article article);

    @Update
    int updateData(ArticleData articleData);

    @Transaction
    @Query("SELECT * FROM articles")
    List<ArticleWithData> getAll();

    @Transaction
    @Query("SELECT * FROM articles WHERE name LIKE :name")
    List<ArticleWithData> getByName(String name);

    @Transaction
    @Query("SELECT * FROM articles, articles_data WHERE date BETWEEN :startDate AND :endDate")
    List<ArticleWithData> getByPeriod(Date startDate, Date endDate);

    @Transaction
    @Query("SELECT * FROM articles WHERE favorite = 1")
    List<ArticleWithData> getAllFav();
}
