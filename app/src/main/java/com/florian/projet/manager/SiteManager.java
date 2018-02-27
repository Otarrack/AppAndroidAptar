package com.florian.projet.manager;

import com.florian.projet.model.Site;

import java.util.ArrayList;
import java.util.List;

public class SiteManager {
    private static SiteManager instance;
    private List<Site> listSite;

    private SiteManager() {
        listSite = new ArrayList<>();
    }

    public static SiteManager getInstance() {
        if(instance == null) {
            instance = new SiteManager();
        }
        return instance;
    }

    public List<Site> getAllSite() {
        return listSite;
    }

    public void addSite(Site site) {
        listSite.add(site);
    }

    public Site getSiteAt(int position) {
        if (position < listSite.size()) {
            return listSite.get(position);
        } else {
            return null;
        }
    }

}
