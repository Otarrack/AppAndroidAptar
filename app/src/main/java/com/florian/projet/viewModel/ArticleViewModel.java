package com.florian.projet.viewModel;

import android.content.Context;
import android.graphics.Color;

import com.florian.projet.R;
import com.florian.projet.manager.ApplicationManager;
import com.florian.projet.manager.ArticleManager;
import com.florian.projet.model.Article;
import com.florian.projet.model.OF;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArticleViewModel {
    private static ArticleViewModel instance;
    private ArticleManager articleManager;
    private ApplicationManager applicationManager;
    private Article currentArticle;

    public static ArticleViewModel getInstance() {
        if(instance == null) {
            instance = new ArticleViewModel();
        }
        return instance;
    }

    private ArticleViewModel() {
        articleManager = ArticleManager.getInstance();
        applicationManager = ApplicationManager.getInstance();
    }

    public void setCurrentArticle(int position) {
        currentArticle = articleManager.getArticleAt(position);
    }

    public void delCurrentArticle() {
        currentArticle = null;
    }

    public String getNumArticle() {
        return currentArticle.getNumArticle();
    }

    public String getNomMachine() {
        return currentArticle.getMachine().getName();
    }

    public String getNomSite() {
        return currentArticle.getSite().getName();
    }

    public String getDeclarationDate() {
        return currentArticle.getDateDeclarationProduction().toString();
    }

    public String getStartingDate() {
        return currentArticle.getDateStartPlanned().toString();
    }

    public String getEndingDate() {
        return currentArticle.getDateEndPlanned().toString();
    }

    public double getVolumeInPeriod() {
        Calendar fromCalendar = Calendar.getInstance();
        Calendar toCalendar = Calendar.getInstance();
        Calendar declarationProdCalendar = Calendar.getInstance();

        fromCalendar.setTime(applicationManager.getFromDate());
        toCalendar.setTime(applicationManager.getToDate());
        declarationProdCalendar.setTime(currentArticle.getDateDeclarationProduction());

        return 0;
    }

    public double getWasteQtInPeriod() {
        Calendar fromCalendar = Calendar.getInstance();
        Calendar toCalendar = Calendar.getInstance();
        Calendar declarationProdCalendar = Calendar.getInstance();

        fromCalendar.setTime(applicationManager.getFromDate());
        toCalendar.setTime(applicationManager.getToDate());
        declarationProdCalendar.setTime(currentArticle.getDateDeclarationProduction());

        return 0;
    }

    public double getWastePercentInPeriod() {
        Calendar fromCalendar = Calendar.getInstance();
        Calendar toCalendar = Calendar.getInstance();
        Calendar declarationProdCalendar = Calendar.getInstance();

        fromCalendar.setTime(applicationManager.getFromDate());
        toCalendar.setTime(applicationManager.getToDate());
        declarationProdCalendar.setTime(currentArticle.getDateDeclarationProduction());

        return 0;
    }

    public double getCadenceInPeriod() {
        Calendar fromCalendar = Calendar.getInstance();
        Calendar toCalendar = Calendar.getInstance();
        Calendar declarationProdCalendar = Calendar.getInstance();

        fromCalendar.setTime(applicationManager.getFromDate());
        toCalendar.setTime(applicationManager.getToDate());
        declarationProdCalendar.setTime(currentArticle.getDateDeclarationProduction());

        return 0;
    }

    private int getColorForCadence(Context context){
        int color;
        if (getCadenceInPeriod() < currentArticle.getCadence()) {
            color = context.getResources().getColor(R.color.red);
        } else {
            color = context.getResources().getColor(R.color.green);
        }
        return color;
    }

    private double getTrendWasteOverTime() {

        return 0;
    }

    private double getAverageSizeOF() {

        return 0;
    }

    private double getTrendSizeOFOverTime() {

        return 0;
    }

    private List<OF> getListOF() {
        return currentArticle.getOfList();
    }


}
