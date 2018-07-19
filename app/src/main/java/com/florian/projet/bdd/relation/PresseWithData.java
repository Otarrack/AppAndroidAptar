package com.florian.projet.bdd.relation;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.OFData;
import com.florian.projet.bdd.entity.Presse;

import java.util.List;

/**
 * Classe de relation entre les presses et leurs données
 *
 * @author Florian
 */
public class PresseWithData {

    @Embedded
    private Presse presse;

    @Relation(parentColumn = "id", entityColumn = "idPresse")
    private List<OFData> dataList;

    public Presse getPresse() {
        return presse;
    }

    public void setPresse(Presse presse) {
        this.presse = presse;
    }

    public List<OFData> getDataList() {
        return dataList;
    }

    public void setDataList(List<OFData> dataList) {
        this.dataList = dataList;
    }

    /**
     * Methode qui retourne la quantité total des données de la presse
     *
     * @return Quantité totale
     */
    public double getTotalQuantity() {
        Double totalQuantity = 0.0;

        for (OFData OFData : dataList) {
            totalQuantity += OFData.getQuantity();
        }

        return totalQuantity;
    }
}
