package com.florian.projet.viewModel;

import com.florian.projet.manager.ArticleManager;
import com.florian.projet.manager.MachineManager;
import com.florian.projet.manager.SiteManager;
import com.florian.projet.model.Article;
import com.florian.projet.model.Machine;
import com.florian.projet.model.Site;

import java.util.ArrayList;

public class ProductionViewModel {
    private ArticleManager articleManager;
    private MachineManager machineManager;
    private SiteManager siteManager;
    private static ProductionViewModel instance;

    public static ProductionViewModel getInstance() {
        if(instance == null) {
            instance = new ProductionViewModel();
        }
        return instance;
    }

    private ProductionViewModel() {
        siteManager = SiteManager.getInstance();
        machineManager = MachineManager.getInstance();
        articleManager = ArticleManager.getInstance();
    }

    public ArrayList<Site> getSiteList() {
        return new ArrayList<>(siteManager.getAllSite());
    }

    public ArrayList<Article> getArticleList() {
        return new ArrayList<>(articleManager.getAllArticle());
    }

    public ArrayList<Machine> getMachineList() {
        return new ArrayList<>(machineManager.getAllMachine());
    }

    public Site getSite(int position) {
        return siteManager.getSiteAt(position);
    }

    public Article getArticle(int position) {
        return articleManager.getArticleAt(position);
    }

    public Machine getMachine(int position) {
        return machineManager.getMachineAt(position);
    }

}
