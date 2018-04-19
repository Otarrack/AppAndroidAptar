package com.florian.projet.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.florian.projet.model.MachineMESFile;

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


public class ParseMESFileTask extends AsyncTask<File, Void, ArrayList<MachineMESFile>> {
    private Callback callback;
    private Exception mException;

    public interface Callback {
        void onSuccess(ArrayList<MachineMESFile> dataLineList);
        void onFailed(Exception e);
    }

    public ParseMESFileTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected ArrayList<MachineMESFile> doInBackground(File... files) {
        ArrayList<MachineMESFile> dataLineList = new ArrayList<>();

        try {
            InputStream excelFile;

            excelFile = new FileInputStream(files[0]);
            HSSFWorkbook wb = new HSSFWorkbook(excelFile);

            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;

            Iterator rows = sheet.rowIterator();
            MachineMESFile machineMESFile;

            while (rows.hasNext()) {
                row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                machineMESFile = new MachineMESFile();

                if (row.getRowNum() >= 9) {
                    while (cells.hasNext()) {
                        cell = (HSSFCell) cells.next();

                        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            Log.d("[1] CELL : [" + cell.getColumnIndex() + ";" + cell.getRowIndex() + "] ", cell.getStringCellValue() + " ");

                            addStringValue(cell, machineMESFile);

                        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            Log.d("[2] CELL : [" + cell.getColumnIndex() + ";" + cell.getRowIndex() + "] ", cell.getNumericCellValue() + " ");

                            addNumericalValue(cell, machineMESFile);

                        } else {
                            //U Can Handel Boolean, Formula, Errors
                        }
                    }

                    if (machineMESFile.getMachineName() != null) {
                        dataLineList.add(machineMESFile);
                    }
                }
            }

        } catch (IOException e) {
            Log.d("ERREUR ", e.getMessage());
            mException = e;
        }
        return dataLineList;
    }

    @Override
    protected void onPostExecute(ArrayList<MachineMESFile> machineMESFiles) {
        if (mException == null) {
            callback.onSuccess(machineMESFiles);
        } else {
            callback.onFailed(mException);
        }
    }

    private void addStringValue(HSSFCell cell, MachineMESFile machineMESFile) {
        switch (cell.getColumnIndex() + 1) {
            case 1: // COLUMN_MACHINE_NAME;
                machineMESFile.setMachineName(cell.getStringCellValue());
                break;

            case 2: // COLUMN_TEMPS_MAX_OUVERTURE
                machineMESFile.setTempsMaximumOuverture(cell.getStringCellValue());
                break;

            case 3: // COLUMN_VACANCES
                machineMESFile.setVacances(cell.getStringCellValue());
                break;

            case 4: // COLUMN_ARRET_PLANNIFIE
                machineMESFile.setArretPlanifie(cell.getStringCellValue());
                break;

            case 5: // COLUMN_TEMPS_PAUSE
                machineMESFile.setTempsPause(cell.getStringCellValue());
                break;

            case 6: // COLUMN_MAINTENANCE_PREVENTIVE
                machineMESFile.setMaintenancePreventive(cell.getStringCellValue());
                break;

            case 7: // COLUMN_ABSENCE_OF
                machineMESFile.setAbsenceOF(cell.getStringCellValue());
                break;

            case 8: // COLUMN_PRELEVEMENT
                machineMESFile.setPrelevement(cell.getStringCellValue());
                break;

            case 9: // COLUMN_TEMPS_PRODUCTIF_EFFECTIF
                machineMESFile.setTempsProductifEffectif(cell.getStringCellValue());
                break;

            case 12: // COLUMN_TEMPS_SETUP
                machineMESFile.setTempsSetup(cell.getStringCellValue());
                break;

            case 13: // COLUMN_MICRO_ARRET
                machineMESFile.setMicroArret(cell.getStringCellValue());
                break;

            case 14: // COLUMN_AUTRE_TEMPS_ARRET
                machineMESFile.setAutreTempsArret(cell.getStringCellValue());
                break;

            case 16: // COLUMN_PERTE_EFFICACITE_REBUT
                machineMESFile.setPerteEfficaciteRebut(cell.getStringCellValue());
                break;

            case 17: // COLUMN_PERTE_EFFICACITE_CAVITE
                machineMESFile.setPerteEfficaciteCavite(cell.getStringCellValue());
                break;

            case 18: // COLUMN_PERTE_EFFICACITE_TEMPS_CYCLE
                machineMESFile.setPerteEfficaciteTempsCycle(cell.getStringCellValue());
                break;

            case 19: // COLUMN_EFFICACITE_VITESSE_PERDUE
                machineMESFile.setEfficaciteVitessePerdue(cell.getStringCellValue());
                break;

            case 20: // COLUMN_TEMPS_PRODUCTIF_NET
                machineMESFile.setTempsProductifNet(cell.getStringCellValue());
                break;

            case 21: // COLUMN_TEMPS_PRODUCTIF_NET_QME
                machineMESFile.setTempsProductifNetQME(cell.getStringCellValue());
                break;

            case 22: // COLUMN_QUALITE_BONNE_PRODUITE
                machineMESFile.setQualiteBonneProduite(cell.getStringCellValue());
                break;

            case 28: // COLUMN_AQUIRED_PRODTIME
                machineMESFile.setAquiredProdTime(cell.getStringCellValue());
                break;
            default:
                break;
        }
    }

    private void addNumericalValue(HSSFCell cell, MachineMESFile machineMESFile) {
        switch (cell.getColumnIndex() + 1) {

            case 10: // COLUMN_MCU
                machineMESFile.setMcu(cell.getNumericCellValue());
                break;

            case 15: // COLUMN_TAUX_REBUT
                machineMESFile.setTauxRebut(cell.getNumericCellValue());
                break;

            case 24: // COLUMN_QME
                machineMESFile.setQme(cell.getNumericCellValue());
                break;

            case 25: // COLUMN_OME_MOYENNE
                machineMESFile.setOmeMoyenne(cell.getNumericCellValue());
                break;

            case 26: // COLUMN_OME_PLANIFIE
                machineMESFile.setOmePlanifie(cell.getNumericCellValue());
                break;

            case 27: // COLUMN_OME_MOYENNE_SUR_PLANIFIE
                machineMESFile.setOmeMoyenneSurPlanifie(cell.getNumericCellValue());
                break;
            default:
                break;
        }
    }
}
