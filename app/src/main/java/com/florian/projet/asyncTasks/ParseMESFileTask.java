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
                            Log.d("AUTRE CELL : [" + cell.getColumnIndex() + ";" + cell.getRowIndex() + "] ", cell.getNumericCellValue() + " ");
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
        switch (cell.getColumnIndex()) {
            case 0: // COLUMN_MACHINE_NAME;
                machineMESFile.setMachineName(cell.getStringCellValue());
                break;

            case 1: // COLUMN_TEMPS_MAX_OUVERTURE
                machineMESFile.setMaxTimeOpenned(cell.getStringCellValue());
                break;

            case 2: // COLUMN_VACANCES
                machineMESFile.setHolidays(cell.getStringCellValue());
                break;

            case 3: // COLUMN_ARRET_PLANNIFIE
                machineMESFile.setPlannedStop(cell.getStringCellValue());
                break;

            case 4: // COLUMN_TEMPS_PAUSE
                machineMESFile.setBreakTime(cell.getStringCellValue());
                break;

            case 5: // COLUMN_MAINTENANCE_PREVENTIVE
                machineMESFile.setPreventiveMaintenance(cell.getStringCellValue());
                break;

            case 6: // COLUMN_ABSENCE_OF
                machineMESFile.setMissingOF(cell.getStringCellValue());
                break;

            case 7: // COLUMN_PRELEVEMENT
                machineMESFile.setSample(cell.getStringCellValue());
                break;

            case 8: // COLUMN_TEMPS_PRODUCTIF_EFFECTIF
                machineMESFile.setActualProductiveTime(cell.getStringCellValue());
                break;

            case 11: // COLUMN_TEMPS_SETUP
                machineMESFile.setSetupTime(cell.getStringCellValue());
                break;

            case 12: // COLUMN_MICRO_ARRET
                machineMESFile.setMicroStopTime(cell.getStringCellValue());
                break;

            case 13: // COLUMN_AUTRE_TEMPS_ARRET
                machineMESFile.setOtherStopTime(cell.getStringCellValue());
                break;

            case 15: // COLUMN_PERTE_EFFICACITE_REBUT
                machineMESFile.setScrapLossEfficiency(cell.getStringCellValue());
                break;

            case 16: // COLUMN_PERTE_EFFICACITE_CAVITE
                machineMESFile.setCavityLossEfficiency(cell.getStringCellValue());
                break;

            case 17: // COLUMN_PERTE_EFFICACITE_TEMPS_CYCLE
                machineMESFile.setCycleTimeLossEfficiency(cell.getStringCellValue());
                break;

            case 18: // COLUMN_EFFICACITE_VITESSE_PERDUE
                machineMESFile.setSpeedLostEfficiency(cell.getStringCellValue());
                break;

            case 19: // COLUMN_TEMPS_PRODUCTIF_NET
                machineMESFile.setNetProductiveTime(cell.getStringCellValue());
                break;

            case 20: // COLUMN_TEMPS_PRODUCTIF_NET_QME
                machineMESFile.setNetProductiveTimeQME(cell.getStringCellValue());
                break;

            default:
                break;
        }
    }

    private void addNumericalValue(HSSFCell cell, MachineMESFile machineMESFile) {
        switch (cell.getColumnIndex()) {

            case 9: // COLUMN_MCU
                machineMESFile.setMcu(cell.getNumericCellValue());
                break;

            case 14: // COLUMN_TAUX_REBUT
                machineMESFile.setScrapRate(cell.getNumericCellValue());
                break;

            case 22: // COLUMN_QUALITE_BONNE_PRODUITE
                machineMESFile.setGoodQualityProduced(cell.getNumericCellValue());
                break;

            case 23: // COLUMN_QME
                machineMESFile.setQme(cell.getNumericCellValue());
                break;

            case 24: // COLUMN_OME_MOYENNE
                machineMESFile.setAverageOME(cell.getNumericCellValue());
                break;

            default:
                break;
        }
    }
}
