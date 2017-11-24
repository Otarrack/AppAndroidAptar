package com.florian.projet.manager;

import com.florian.projet.model.Site;

import java.util.List;

public class SiteManager {
    private static SiteManager instance;
    private List<Site> listSite;

    public SiteManager() {
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
        for (int i = 1; i < 5; i++) {
            Site site = new Site(i,i * 103,i,i + 13 * i);
            listSite.add(site);
        }
    }

}
