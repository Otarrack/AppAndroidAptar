package com.florian.projet.bdd.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.Machine;

import java.util.List;

@Dao
public interface ArticleDao {

    @Insert
    long insert(Article article);

    @Insert
    List<Long> insertAll(List<Article> articleList);

    @Update
    int update(Article article);

    @Delete
    int delete(Article article);

    @Query("SELECT * FROM articles")
    List<Article> getAll();

    @Query("SELECT * FROM articles WHERE name LIKE :name")
    List<Article> getByName(String name);
}
