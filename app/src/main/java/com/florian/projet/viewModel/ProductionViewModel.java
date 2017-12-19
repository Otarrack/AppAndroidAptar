package com.florian.projet.viewModel;

import com.florian.projet.manager.ArticleManager;
import com.florian.projet.manager.MachineManager;
import com.florian.projet.manager.SiteManager;
import com.florian.projet.model.Article;
import com.florian.projet.model.Machine;
import com.florian.projet.model.Site;

import java.util.ArrayList;

public class ProductionViewModel {
    private ArrayList<Site> siteList;
    private ArrayList<Article> articleList;
    private ArrayList<Machine> machineList;
    private static ProductionViewModel instance;

    public static ProductionViewModel getInstance() {
        if(instance == null) {
            instance = new ProductionViewModel();
        }
        return instance;
    }

    private ProductionViewModel() {
        siteList = new ArrayList<>(SiteManager.getInstance().getListSite());
        machineList = new ArrayList<>(MachineManager.getInstance().getListMachine());
        articleList = new ArrayList<>(ArticleManager.getInstance().getListArticle());
    }

    public ArrayList<Site> getSiteList() {
        return siteList;
    }

    public ArrayList<Article> getArticleList() {
        return articleList;
    }

    public ArrayList<Machine> getMachineList() {
        return machineList;
    }

    public Site getSite(int position) {
        return siteList.get(position);
    }

    public Article getArticle(int position) {
        return articleList.get(position);
    }

    public Machine getMachine(int position) {
        return machineList.get(position);
    }

}
