package com.florian.projet.bdd.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Classe qui permet de convertir les dates pour les enregistrer en base de données
 *
 * @author Florian
 */
public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}