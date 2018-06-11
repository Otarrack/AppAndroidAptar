package com.florian.projet.viewModel;

import com.florian.projet.manager.SiteManager;
import com.florian.projet.model.SiteEnum;

import java.util.ArrayList;

public class SiteViewModel {
    private static SiteViewModel instance;
    private SiteManager siteManager;

    public static SiteViewModel getInstance() {
        if(instance == null) {
            instance = new SiteViewModel();
        }
        return instance;
    }

    private SiteViewModel() {
        siteManager = SiteManager.getInstance();
    }

    public ArrayList<SiteEnum> getSiteList() {
        return siteManager.getAllSite();
    }
}
