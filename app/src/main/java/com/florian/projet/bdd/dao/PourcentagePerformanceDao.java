package com.florian.projet.bdd.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.florian.projet.bdd.entity.Machine;

import java.util.List;

/**
 * Interface qui regroupe les requêtes et appels à la base de données des machines
 *
 * @author Florian
 */
@Dao
public interface PourcentagePerformanceDao {

    /**
     * Insère toutes les machines de la liste dans la base
     *
     * @param machineList Liste des machines à ajouter
     *
     * @return Liste des Id des machines ajoutées
     */
    @Insert
    List<Long> insertAll(List<Machine> machineList);

    /**
     * Sélectionne tous les machines
     *
     * @return Liste des machines avec les données associées
     */
    @Query("SELECT * FROM machines")
    List<Machine> getAll();

    /**
     * Sélectionne tous les machines du/des site(s)
     *
     * @param siteList Liste des sites
     *
     * @return Liste des machines avec les données associées du/des site(s)
     */
    @Query("SELECT * FROM machines WHERE site IN(:siteList)")
    List<Machine> getBySite(List<Integer> siteList);
}
