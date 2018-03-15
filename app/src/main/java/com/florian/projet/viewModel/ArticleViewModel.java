package com.florian.projet.viewModel;

import android.content.Context;

import com.florian.projet.R;
import com.florian.projet.manager.ApplicationManager;
import com.florian.projet.manager.ArticleManager;
import com.florian.projet.model.Article;
import com.florian.projet.model.OF;

import java.text.DateFormat;
import java.util.Calendar;
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

    public List<OF> getOfList() {
        return currentArticle.getOfList();
    }

    public OF getOfAt(int position) {
        if (currentArticle.getOfList() == null) {
            return null;
        }
        return currentArticle.getOfList().get(position);
    }

    public String getNumArticle() {
        return currentArticle.getNumArticle();
    }

    public String getNomMachine() {
        if (currentArticle.getMachine() == null) {
            return "";
        }
        return currentArticle.getMachine().getMachineName();
    }

    public String getNomSite() {
        if (currentArticle.getSite() == null) {
            return "";
        }
        return currentArticle.getSite().getSiteName();
    }

    public String getDeclarationDate() {

        if (currentArticle.getDateDeclarationProduction() == null) {
            return "";
        }
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentArticle.getDateDeclarationProduction());
    }

    public String getStartingDate() {

        if (currentArticle.getDateStartPlanned() == null) {
            return "";
        }
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentArticle.getDateStartPlanned());
    }

    public String getEndingDate() {

        if (currentArticle.getDateEndPlanned() == null) {
            return "";
        }
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentArticle.getDateEndPlanned());
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
