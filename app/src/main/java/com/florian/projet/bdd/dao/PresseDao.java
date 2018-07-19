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
public interface PresseDao {

    /**
     * Inserer les presses dans la base
     *
     * @param presseArrayList Presses à ajouter
     *
     * @return Id des presses ajoutées
     */
    @Insert
    List<Long> insertAllPresse(ArrayList<Presse> presseArrayList);

    /**
     * Modifie la presse
     *
     * @param presse Presse à modifier
     *
     * @return Id de la presse modifiée
     */
    @Update
    int updatePresse(Presse presse);

    /**
     * Sélectionne toutes les presses et leurs données associées
     *
     * @return Liste des presses avec les données associées ({@link PresseWithData})
     */
    @Transaction
    @Query("SELECT * FROM presses")
    List<PresseWithData> getAllPresseWithData();

    /**
     * Sélectionne toutes les presses en favoris
     *
     * @return Liste des presses en favoris
     */
    @Transaction
    @Query("SELECT * FROM presses WHERE favorite = 1")
    List<Presse> getAllPresseFav();

    /**
     * Sélectionne toutes les presses en favoris avec leurs données associées
     *
     * @return Liste des presses en favoris avec les données associées ({@link PresseWithData})
     */
    @Transaction
    @Query("SELECT * FROM presses WHERE favorite = 1")
    List<PresseWithData> getAllPresseFavWithData();
}
