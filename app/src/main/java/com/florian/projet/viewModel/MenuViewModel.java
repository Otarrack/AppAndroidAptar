package com.florian.projet.viewModel;

import com.florian.projet.manager.ArticleManager;
import com.florian.projet.manager.MESFileManager;
import com.florian.projet.manager.MachineManager;
import com.florian.projet.manager.SiteManager;
import com.florian.projet.model.MachineMESFile;
import com.florian.projet.quarantaine.Article;
import com.florian.projet.quarantaine.Machine;
import com.florian.projet.model.SiteMESFile;

import java.util.ArrayList;

public class MenuViewModel {
    private static MenuViewModel instance;
    private ArticleManager articleManager;
    private MachineManager machineManager;
    private SiteManager siteManager;
    private MESFileManager mesFileManager;

    public static MenuViewModel getInstance() {
        if(instance == null) {
            instance = new MenuViewModel();
        }
        return instance;
    }

    private MenuViewModel() {
        siteManager = SiteManager.getInstance();
        machineManager = MachineManager.getInstance();
        articleManager = ArticleManager.getInstance();

        mesFileManager = MESFileManager.getInstance();
    }

    public ArrayList<SiteMESFile> getSiteList() {

        //return mesFileManager.getSiteList();


        // return new ArrayList<>(siteManager.getAllSite());
        return null;
    }

    public ArrayList<Article> getArticleList() {
        return new ArrayList<>(articleManager.getAllArticle());
    }

    public ArrayList<Machine> getMachineList() {
        return new ArrayList<>(machineManager.getAllMachine());
    }

    public ArrayList<MachineMESFile> getMESMachineList() {
        return new ArrayList<>(mesFileManager.getAllMachineMES());
    }

    public SiteMESFile getSite(int position) {
        //return mesFileManager.getSiteAt(position);

        //return siteManager.getSiteAt(position);
        return null;
    }

    public Article getArticle(int position) {
        return articleManager.getArticleAt(position);
    }

    public Machine getMachine(int position) {
        return machineManager.getMachineAt(position);
    }

}
