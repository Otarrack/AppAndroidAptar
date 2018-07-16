package com.florian.projet.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.florian.projet.bdd.entity.Machine;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Une classe qui lit en asynchrone le fichier de performance machine.
 *
 * @author Florian
 */

public class ParseMachinePerfFileTask extends AsyncTask<File, Void, ArrayList<Machine>> {
    private Callback callback;
    private Exception mException;

    /**
     * Une interface qui permet de signaler la fin de la lecture du fichier.
     */
    public interface Callback {
        /**
         * Une méthode pour envoyer la liste des lignes machines en cas de réussite.
         *
         * @param dataLineList La liste des lignes récupérées
         */
        void onSuccess(ArrayList<Machine> dataLineList);

        /**
         * Une méthode pour envoyer les détails en cas d'erreur
         *
         * @param e L'exception générée lors du traitement
         */
        void onFailed(Exception e);
    }

    /**
     * @param callback Le callback permettant de récupérer le résultat du traitement
     */
    public ParseMachinePerfFileTask(Callback callback) {
        this.callback = callback;
    }

    /**
     * Méthode contenant le traitement de lecture du fichier
     *
     * @param files Le fichier à lire
     *
     * @return Renvoie les lignes du fichier en ArrayList de {@link Machine}
     */
    @Override
    protected ArrayList<Machine> doInBackground(File... files) {
        ArrayList<Machine> dataLineList = new ArrayList<>();

        try {
            //Récupère le fichier et initialise le lecteur
            InputStream excelFile = new FileInputStream(files[0]);
            HSSFWorkbook wb = new HSSFWorkbook(excelFile);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;

            Iterator rows = sheet.rowIterator();
            Machine machine;

            //Execute la boucle tant qu'il y a des lignes de données
            while (rows.hasNext()) {
                row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                machine = new Machine();

                if (row.getRowNum() >= 10) {
                    while (cells.hasNext()) {
                        cell = (HSSFCell) cells.next();

                        //On vérifie les types de valeurs pour éviter les exceptions
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            //Si il s'agit d'une valeur de type texte
                            addStringValue(cell, machine);

                        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            //Si il s'agit d'une valeur de type numérique
                            addNumericalValue(cell, machine);

                        }
                    }

                    if (machine.getMachineName() != null) {
                        dataLineList.add(machine);
                    }
                }
            }

        } catch (IOException e) {
            Log.d("Parse Machine", e.getMessage());
            mException = e;
        }
        return dataLineList;
    }

    /**
     * Méthode appelée automatiquement après doInBackgroud qui gère le callback
     *
     * @param machines La liste des lignes renvoyée par le traitement
     */
    @Override
    protected void onPostExecute(ArrayList<Machine> machines) {
        if (mException == null) {
            callback.onSuccess(machines);
        } else {
            callback.onFailed(mException);
        }
    }

    /**
     * Méthode gérant l'ajout dans la machine des données de type texte
     *
     * @param cell Cellule en cours de lecture
     * @param machine Machine en cours de récupération
     */
    private void addStringValue(HSSFCell cell, Machine machine) {
        switch (cell.getColumnIndex()) {
            case 0: // COLUMN_MACHINE_NAME;
                machine.setMachineName(cell.getStringCellValue());
                break;

            case 1: // COLUMN_TEMPS_MAX_OUVERTURE
                machine.setMaxTimeOpenned(cell.getStringCellValue());
                break;

            case 2: // COLUMN_VACANCES
                machine.setHolidays(cell.getStringCellValue());
                break;

            case 3: // COLUMN_ARRET_PLANNIFIE
                machine.setPlannedStop(cell.getStringCellValue());
                break;

            case 4: // COLUMN_TEMPS_PAUSE
                machine.setBreakTime(cell.getStringCellValue());
                break;

            case 5: // COLUMN_MAINTENANCE_PREVENTIVE
                machine.setPreventiveMaintenance(cell.getStringCellValue());
                break;

            case 6: // COLUMN_ABSENCE_OF
                machine.setMissingOF(cell.getStringCellValue());
                break;

            case 7: // COLUMN_PRELEVEMENT
                machine.setSample(cell.getStringCellValue());
                break;

            case 8: // COLUMN_TEMPS_PRODUCTIF_EFFECTIF
                machine.setActualProductiveTime(cell.getStringCellValue());
                break;

            case 11: // COLUMN_TEMPS_SETUP
                machine.setSetupTime(cell.getStringCellValue());
                break;

            case 12: // COLUMN_MICRO_ARRET
                machine.setMicroStopTime(cell.getStringCellValue());
                break;

            case 13: // COLUMN_AUTRE_TEMPS_ARRET
                machine.setOtherStopTime(cell.getStringCellValue());
                break;

            case 15: // COLUMN_PERTE_EFFICACITE_REBUT
                machine.setScrapLossEfficiency(cell.getStringCellValue());
                break;

            case 16: // COLUMN_PERTE_EFFICACITE_CAVITE
                machine.setCavityLossEfficiency(cell.getStringCellValue());
                break;

            case 17: // COLUMN_PERTE_EFFICACITE_TEMPS_CYCLE
                machine.setCycleTimeLossEfficiency(cell.getStringCellValue());
                break;

            case 18: // COLUMN_EFFICACITE_VITESSE_PERDUE
                machine.setSpeedLostEfficiency(cell.getStringCellValue());
                break;

            case 19: // COLUMN_TEMPS_PRODUCTIF_NET
                machine.setNetProductiveTime(cell.getStringCellValue());
                break;

            case 20: // COLUMN_TEMPS_PRODUCTIF_NET_QME
                machine.setNetProductiveTimeQME(cell.getStringCellValue());
                break;

            default:
                break;
        }
    }

    /**
     * Méthode gérant l'ajout dans la machine des données de type numérique
     *
     * @param cell Cellule en cours de lecture
     * @param machine Machine en cours de récupération
     */
    private void addNumericalValue(HSSFCell cell, Machine machine) {
        switch (cell.getColumnIndex()) {

            case 9: // COLUMN_MCU
                machine.setMcu(cell.getNumericCellValue());
                break;

            case 14: // COLUMN_TAUX_REBUT
                machine.setScrapRate(cell.getNumericCellValue());
                break;

            case 22: // COLUMN_QUALITE_BONNE_PRODUITE
                machine.setGoodQualityProduced(cell.getNumericCellValue());
                break;

            case 23: // COLUMN_QME
                machine.setQme(cell.getNumericCellValue());
                break;

            case 24: // COLUMN_OME_MOYENNE
                machine.setAverageOME(cell.getNumericCellValue());
                break;

            default:
                break;
        }
    }
}
