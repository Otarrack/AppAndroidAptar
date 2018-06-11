package com.florian.projet.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.florian.projet.bdd.database.FavMachineDataBase;
import com.florian.projet.manager.FavoriteMachineManager;
import com.florian.projet.model.Machine;
import com.florian.projet.model.SiteEnum;

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


public class ParseMESFileTask extends AsyncTask<File, Void, ArrayList<Machine>> {
    private Callback callback;
    private Context context;
    private Exception mException;

    public interface Callback {
        void onSuccess(ArrayList<Machine> dataLineList);
        void onFailed(Exception e);
    }

    public ParseMESFileTask(Context context, Callback callback) {
        this.callback = callback;
        this.context = context;
    }

    @Override
    protected ArrayList<Machine> doInBackground(File... files) {
        ArrayList<Machine> dataLineList = new ArrayList<>();

        try {
            InputStream excelFile;

            excelFile = new FileInputStream(files[0]);
            HSSFWorkbook wb = new HSSFWorkbook(excelFile);

            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;

            Iterator rows = sheet.rowIterator();
            Machine machine;

            while (rows.hasNext()) {
                row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                machine = new Machine();

                if (row.getRowNum() >= 9) {
                    while (cells.hasNext()) {
                        cell = (HSSFCell) cells.next();

                        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            Log.d("[1] CELL : [" + cell.getColumnIndex() + ";" + cell.getRowIndex() + "] ", cell.getStringCellValue() + " ");

                            addStringValue(cell, machine);

                        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            Log.d("[2] CELL : [" + cell.getColumnIndex() + ";" + cell.getRowIndex() + "] ", cell.getNumericCellValue() + " ");

                            addNumericalValue(cell, machine);

                        } else {
                            Log.d("AUTRE CELL : [" + cell.getColumnIndex() + ";" + cell.getRowIndex() + "] ", cell.getNumericCellValue() + " ");
                            //U Can Handel Boolean, Formula, Errors
                        }
                    }

                    if (machine.getMachineName() != null) {
                        dataLineList.add(machine);
                    }
                }
            }

            initFavoriteMachine(context);

        } catch (IOException e) {
            Log.d("ERREUR ", e.getMessage());
            mException = e;
        }
        return dataLineList;
    }

    @Override
    protected void onPostExecute(ArrayList<Machine> machines) {
        if (mException == null) {
            callback.onSuccess(machines);
        } else {
            callback.onFailed(mException);
        }
    }

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

    private void initFavoriteMachine(Context context) {
        FavoriteMachineManager favoriteMachineManager = FavoriteMachineManager.getInstance();
        favoriteMachineManager.setAppQuestionDatabase(FavMachineDataBase.getInstance(context));

        SiteEnum.FAV.setMachineMESList(favoriteMachineManager.getAllFavMachine());
    }
}
