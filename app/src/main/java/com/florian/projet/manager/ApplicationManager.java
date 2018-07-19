package com.florian.projet.manager;

import com.florian.projet.MyApplication;
import com.florian.projet.asyncTasks.DropboxDownloadFileTask;
import com.florian.projet.asyncTasks.ParseQuantityFileTask;
import com.florian.projet.asyncTasks.ParseMachinePerfFileTask;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.model.QuantityFileLine;
import com.florian.projet.tools.ArticleWithDataCallback;
import com.florian.projet.tools.MachineCallback;
import com.florian.projet.tools.PresseWithDataCallback;
import com.florian.projet.tools.SimpleCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Manager global de l'application
 *
 * @author Florian
 */
public class ApplicationManager {
    private static ApplicationManager instance;

    public static final String FILE_PERF_MACHINE_NAME = "fichiermes.xls";
    public static final String FILE_PERF_ARTICLE_NAME = "par site valorisation des produits finis et semi-finis produits.csv";

    public static boolean failLoadingMachine = false;
    public static boolean failLoadingArticle = false;

    private DropboxManager dropboxManager;
    private XlsFileManager xlsFileManager;
    private PourcentagePerfDatabaseManager pourcentagePerfDatabaseManager;
    private QuantityDatabaseManager quantityDatabaseManager;

    private Calendar calendar;
    private Date defaultDate;
    private Date fromDate;
    private Date toDate;

    private ApplicationManager() {
        MyApplication.getInstance();
        calendar = Calendar.getInstance();
        xlsFileManager = XlsFileManager.getInstance();
        pourcentagePerfDatabaseManager = PourcentagePerfDatabaseManager.getInstance();
        quantityDatabaseManager = QuantityDatabaseManager.getInstance();

        setDefaultDate();
    }

    /**
     * Singleton qui permet de récupérer l'instance en cours si elle a déjà été créée
     *
     * @return Instance du manager
     */
    public static ApplicationManager getInstance() {
        if(instance == null) {
            instance = new ApplicationManager();
        }
        return instance;
    }

    /**
     * Méthode pour récupérer la date par défaut
     *
     * @return Date par défaut
     */
    private Date getDefaultDate() {
        return defaultDate;
    }

    /**
     * Méthode d'initialisation de la date par défaut
     */
    private void setDefaultDate() {
        //Récupère la date de la veille pour initialiser la date par défaut
        defaultDate = new Date();
        calendar.setTime(defaultDate);
        calendar.add(Calendar.DATE, -1);
        defaultDate = calendar.getTime();
        calendar.clear();

        resetFromDateToDefault();
        resetToDateToDefault();
    }

    /**
     * Méthode pour réinitialiser la date de début
     */
    public void resetFromDateToDefault() {
        setFromDate(getDefaultDate());
    }

    /**
     * Méthode pour réinitialiser la date de fin
     */
    public void resetToDateToDefault() {
        setToDate(getDefaultDate());
    }

    /**
     * Renvoie la date de début de la période
     *
     * @return Date de début
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * Inscrit la date de début de la période
     *
     * @param fromDate Date de début
     */
    public void setFromDate(Date fromDate) {
        calendar.setTime(fromDate);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),0,0,0);
        fromDate = calendar.getTime();
        calendar.clear();

        this.fromDate = fromDate;
    }

    /**
     * Renvoie la date de fin de la période
     *
     * @return Date de fin
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * Inscrit la date de fin de la période
     *
     * @param toDate Date de fin
     */
    public void setToDate(Date toDate) {
        calendar.setTime(toDate);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),23,59,59);
        toDate = calendar.getTime();
        calendar.clear();

        this.toDate = toDate;
    }

    /**
     * Initialise l'application et la connexion au Dropbox
     */
    public void init() {
        dropboxManager = DropboxManager.getInstance();
        dropboxManager.init();

        setDefaultDate();
    }

    /**
     * @param callback Callback pour récupérer la réponse
     *
     * @see PourcentagePerfDatabaseManager
     */
    public void getAllMachine(MachineCallback callback) {
        pourcentagePerfDatabaseManager.getAllMachine(callback);
    }

    /**
     * @param callback Callback pour récupérer la réponse
     *
     * @see QuantityDatabaseManager
     */
    public void getAllArticleWithData(ArticleWithDataCallback callback) {
        quantityDatabaseManager.getAllArticleWithData(callback);
    }

    /**
     * @param callback Callback pour récupérer la réponse
     *
     * @see QuantityDatabaseManager
     */
    public void getAllPresseWithData(PresseWithDataCallback callback) {
        quantityDatabaseManager.getAllPresseWithData(callback);
    }

    /**
     * @param path Chemin où télécharger le fichier
     * @param fileName Nom du fichier
     * @param callback Callback pour récupérer la réponse
     */
    public void downloadDataFile(File path, String fileName, DropboxDownloadFileTask.Callback callback) {
        dropboxManager.downloadFile(path, fileName, callback);
    }

    /**
     * @param file Fichier à lire
     * @param callback Callback pour récupérer la réponse
     */
    public void parseMachinePerfXlsFile(File file, final ParseMachinePerfFileTask.Callback callback) {
        xlsFileManager.parseMachinePerfXlsFile(file, callback);
    }

    /**
     * @param file Fichier à lire
     * @param callback Callback pour récupérer la réponse
     */
    public void parseArticlePerfXlsFile(File file, final ParseQuantityFileTask.Callback callback) {
        xlsFileManager.parseArticlePerfXlsFile(file, callback);
    }

    /**
     * Initialise les favoris et le site pour la mise à jour des données
     *
     * @param allMachineInFile Données venant du fichier
     *
     * @return La liste des machines initialisées
     */
    public ArrayList<Machine> getMachineListWithSite(ArrayList<Machine> allMachineInFile) {

        ArrayList<Machine> finalMachineList = new ArrayList<>();

        for (Machine machine : allMachineInFile) {
            //Récupère le numéro du site sur le nom de la machine
            machine.setSite(Integer.parseInt(String.valueOf(machine.getMachineName().charAt(0))));

            finalMachineList.add(machine);
        }

        return finalMachineList;

    }

    /**
     * Remplace la base de données par la la liste passée en paramètre
     *
     * @param machineArrayList Liste de machines à mettre dans la base
     * @param callback Callback pour récupérer la réponse
     *
     * @see PourcentagePerfDatabaseManager
     */
    public void refreshAllMachine(ArrayList<Machine> machineArrayList, SimpleCallback callback) {
        pourcentagePerfDatabaseManager.refreshAllMachine(machineArrayList, callback);
    }

    /**
     * Remplace la base de données par la la liste passée en paramètre
     *
     * @param quantityFileLineArrayList Liste de données à mettre dans la base
     * @param callback Callback pour récupérer la réponse
     *
     * @see QuantityDatabaseManager
     */
    public void refreshAllArticle(ArrayList<QuantityFileLine> quantityFileLineArrayList, SimpleCallback callback) {
        quantityDatabaseManager.refreshAllFromFile(quantityFileLineArrayList, callback);
    }

}
