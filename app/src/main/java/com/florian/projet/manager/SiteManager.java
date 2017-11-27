package com.florian.projet.manager;

import com.florian.projet.model.Site;

import java.util.ArrayList;
import java.util.List;

public class SiteManager {
    private static SiteManager instance;
    private List<Site> listSite;

    private SiteManager() {
        setListSite();
    }

    public static SiteManager getInstance() {
        if(instance == null) {
            instance = new SiteManager();
        }
        return instance;
    }

    public List<Site> getListSite() {
        return listSite;
    }

    private void setListSite() {
        listSite = new ArrayList<>();
        Site site;
        for (int i = 1; i < 10; i++) {
            site = new Site(i,"Oyo" + i,i * 103,i,i + 13 * i);
            listSite.add(site);
            //TODO: Récupèration des données à partir du manager qui récupère du serveur
        }
    }

}
