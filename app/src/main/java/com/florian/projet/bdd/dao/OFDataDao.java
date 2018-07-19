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

import java.util.List;

/**
 * Interface qui regroupe les requêtes et appels à la base de données du fichier des quantités
 *
 * @author Florian
 */
@Dao
public interface OFDataDao {

    /**
     * Inserer toutes les données de la liste
     *
     * @param ofDataList Liste des données à ajouter
     */
    @Insert
    void insertAllData(List<OFData> ofDataList);
}
