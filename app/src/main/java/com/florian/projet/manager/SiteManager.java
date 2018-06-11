package com.florian.projet.manager;

import com.florian.projet.model.SiteEnum;

import java.util.ArrayList;
import java.util.Arrays;

public class SiteManager {
    private static SiteManager instance;
    private ArrayList<SiteEnum> siteList;

    private SiteManager() {
        siteList = new ArrayList<>(Arrays.asList(SiteEnum.values()));
        siteList.remove(SiteEnum.FAV);
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

    public SiteEnum getSiteAt(int position) {
        if (position < siteList.size()) {
            return siteList.get(position);
        } else {
            return null;
        }
    }

}
