package com.florian.projet.manager;

import android.os.AsyncTask;

import com.florian.projet.asyncTasks.ParseMESFileTask;
import com.florian.projet.model.MachineMESFile;
import com.florian.projet.model.SiteEnum;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MESFileManager {
    private static MESFileManager instance;

    private MachineMESFile totalMachine;
    private ArrayList<MachineMESFile> allMachineMESFilesList;

    private MESFileManager() {
        allMachineMESFilesList = new ArrayList<>();
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

                    if (machineMESFile.getMachineName().equals("Machine")) {
                        totalMachine = machineMESFile;
                    } else {

                        allMachineMESFilesList.add(machineMESFile);

                        char siteChar = machineMESFile.getMachineName().charAt(0);

                        for (SiteEnum siteEnum: SiteEnum.values()) {
                            for (int num : siteEnum.getSiteNum()) {
                                if (String.valueOf(siteChar).equals(String.valueOf(num))){
                                    siteEnum.addMachineMESToList(machineMESFile);
                                }
                            }
                        }

                        /*switch (String.valueOf(siteChar)) {
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

                        }*/
                    }
                }

                callback.onSuccess(dataLineList);
            }

            @Override
            public void onFailed(Exception e) {
                callback.onFailed(e);

            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, file);
    }

    public ArrayList<MachineMESFile> getAllMachineMES() {
        return allMachineMESFilesList;
    }

    public MachineMESFile getTotalMachine() {
        return totalMachine;
    }

   /* private void setSiteList() {

        ArrayList<MachineMESFile> machineList1Et2 = new ArrayList<>();
        machineList1Et2.addAll(getSiteMESFile1().getMachineList());
        machineList1Et2.addAll(getSiteMESFile2().getMachineList());

        this.siteList = new ArrayList<>();

        if (getAllMachineMES().size() > 0) {
            siteList.add(new SiteMESFile("Tous", getAllMachineMES()));
        }

        if (getSiteMESFile1().getMachineList().size() > 0) {
            siteList.add(getSiteMESFile1());
        }

        if (getSiteMESFile2().getMachineList().size() > 0) {
            siteList.add(getSiteMESFile2());
        }

        if (machineList1Et2.size() > 0) {
            siteList.add(new SiteMESFile("Oyo - 1 et Marti - 2", machineList1Et2));
        }

        if (getSiteMESFile3().getMachineList().size() > 0) {
            siteList.add(getSiteMESFile3());
        }

        if (getSiteMESFile4().getMachineList().size() > 0) {
            siteList.add(getSiteMESFile4());
        }

        if (getSiteMESFile5().getMachineList().size() > 0) {
            siteList.add(getSiteMESFile5());
        }
    }

    public ArrayList<SiteMESFile> getSiteList() {
        return siteList;
    }
    public SiteMESFile getSiteAt(int position) {
        return getSiteList().get(position);
    }
*/
}

