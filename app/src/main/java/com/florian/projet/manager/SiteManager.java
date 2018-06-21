package com.florian.projet.manager;

import com.florian.projet.model.SiteEnum;

import java.util.ArrayList;
import java.util.Arrays;

public class SiteManager {
    private static SiteManager instance;
    private ArrayList<SiteEnum> siteList;

    private SiteManager() {
        siteList = new ArrayList<>(Arrays.asList(SiteEnum.values()));
    }

    public static SiteManager getInstance() {
        if(instance == null) {
            instance = new SiteManager();
        }
        return instance;
    }

    public ArrayList<SiteEnum> getAllSite() {
        return siteList;
    }

}
