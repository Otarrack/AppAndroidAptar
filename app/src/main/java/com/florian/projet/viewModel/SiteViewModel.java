package com.florian.projet.viewModel;

import com.florian.projet.manager.SiteManager;
import com.florian.projet.model.Site;

public class SiteViewModel {
    private static SiteViewModel instance;
    private SiteManager siteManager;
    private Site currentSite;

    public static SiteViewModel getInstance() {
        if(instance == null) {
            instance = new SiteViewModel();
        }
        return instance;
    }

    private SiteViewModel() {
        siteManager = SiteManager.getInstance();
    }

    public void setCurrentSite(int id) {
        currentSite = siteManager.getSiteAt(id);
    }

    public void delCurrentSite() {
        currentSite = null;
    }
}
