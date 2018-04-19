package com.florian.projet.manager;

import android.os.AsyncTask;
import android.util.Log;

import com.florian.projet.asyncTasks.ParseMESFileTask;
import com.florian.projet.model.MachineMESFile;
import com.florian.projet.model.SiteMESFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MESFileManager {
    private static MESFileManager instance;

    private MachineMESFile totalMachine;
    private ArrayList<MachineMESFile> allMachineMESFilesList;

    private SiteMESFile siteMESFile1;
    private SiteMESFile siteMESFile2;
    private SiteMESFile siteMESFile3;
    private SiteMESFile siteMESFile4;
    private SiteMESFile siteMESFile5;

    private ArrayList<SiteMESFile> siteList;

    private MESFileManager() {
        allMachineMESFilesList = new ArrayList<>();

        siteMESFile1 = new SiteMESFile("Oyo - 1", new ArrayList<MachineMESFile>());
        siteMESFile2 = new SiteMESFile("Marti - 2", new ArrayList<MachineMESFile>());
        siteMESFile3 = new SiteMESFile("Groi - 3", new ArrayList<MachineMESFile>());
        siteMESFile4 = new SiteMESFile("Evron - 4", new ArrayList<MachineMESFile>());
        siteMESFile5 = new SiteMESFile("5", new ArrayList<MachineMESFile>());
    }

    public static MESFileManager getInstance() {
        if(instance == null) {
            instance = new MESFileManager();
        }
        return instance;
    }

    void parseXLSFile(File file, final ParseMESFileTask.Callback callback) throws IOException {
        new ParseMESFileTask(new ParseMESFileTask.Callback() {
            @Override
            public void onSuccess(ArrayList<MachineMESFile> dataLineList) {

                for (MachineMESFile machineMESFile: dataLineList) {
                    Log.d("MACHINE", "" + machineMESFile.getMachineName());

                    if (machineMESFile.getMachineName().equals("Machine")) {
                        Log.d("CA PASSE", machineMESFile.getMachineName());
                        totalMachine = machineMESFile;
                    } else {

                        allMachineMESFilesList.add(machineMESFile);

                        char siteChar = machineMESFile.getMachineName().charAt(0);

                        switch (String.valueOf(siteChar)) {
                            case "1":
                                siteMESFile1.addMachineToList(machineMESFile);
                                break;
                            case "2":
                                siteMESFile2.addMachineToList(machineMESFile);
                                break;
                            case "3":
                                siteMESFile3.addMachineToList(machineMESFile);
                                break;
                            case "4":
                                siteMESFile4.addMachineToList(machineMESFile);
                                break;
                            case "5":
                                siteMESFile5.addMachineToList(machineMESFile);
                                break;
                            default:
                                Log.d("CA PASSE PO", machineMESFile.getMachineName());

                        }
                    }
                }

                setSiteList();
                callback.onSuccess(dataLineList);
            }

            @Override
            public void onFailed(Exception e) {
                callback.onFailed(e);

            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, file);
    }

    public ArrayList<MachineMESFile> getAllMachineMESFilesList() {
        return allMachineMESFilesList;
    }

    public MachineMESFile getTotalMachine() {
        return totalMachine;
    }

    public SiteMESFile getSiteMESFile1() {
        return siteMESFile1;
    }

    public void addMachineToSiteMESFile1(SiteMESFile siteMESFile1) {
        this.siteMESFile1 = siteMESFile1;
    }

    public SiteMESFile getSiteMESFile2() {
        return siteMESFile2;
    }

    public void addMachineToSiteMESFile2(SiteMESFile siteMESFile2) {
        this.siteMESFile2 = siteMESFile2;
    }

    public SiteMESFile getSiteMESFile3() {
        return siteMESFile3;
    }

    public void addMachineToSiteMESFile3(SiteMESFile siteMESFile3) {
        this.siteMESFile3 = siteMESFile3;
    }

    public SiteMESFile getSiteMESFile4() {
        return siteMESFile4;
    }

    public void addMachineToSiteMESFile4(SiteMESFile siteMESFile4) {
        this.siteMESFile4 = siteMESFile4;
    }

    public SiteMESFile getSiteMESFile5() {
        return siteMESFile5;
    }

    public void addMachineToSiteMESFile5(SiteMESFile siteMESFile5) {
        this.siteMESFile5 = siteMESFile5;
    }

    private void setSiteList() {

        ArrayList<MachineMESFile> machineList1Et2 = getSiteMESFile1().getMachineList();
        machineList1Et2.addAll(getSiteMESFile2().getMachineList());

        this.siteList = new ArrayList<>();

        siteList.add(new SiteMESFile("Tous", getAllMachineMESFilesList()));
        siteList.add(getSiteMESFile1());
        siteList.add(getSiteMESFile2());
        siteList.add(new SiteMESFile("1 et 2", machineList1Et2));
        siteList.add(getSiteMESFile3());
        siteList.add(getSiteMESFile4());
        siteList.add(getSiteMESFile5());
    }

    public ArrayList<SiteMESFile> getSiteList() {
        return siteList;
    }

    public SiteMESFile getSiteAt(int position) {
        return getSiteList().get(position);
    }
}

