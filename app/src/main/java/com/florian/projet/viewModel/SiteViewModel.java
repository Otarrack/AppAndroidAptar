package com.florian.projet.viewModel;

import com.florian.projet.manager.SiteManager;
import com.florian.projet.model.SiteEnum;
import com.florian.projet.quarantaine.Site;

import java.util.ArrayList;
import java.util.Arrays;

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
