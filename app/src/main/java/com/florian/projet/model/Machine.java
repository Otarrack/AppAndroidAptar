package com.florian.projet.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "machines")
public class Machine {
    @ColumnInfo(name = "id_question")
    @PrimaryKey(autoGenerate = true)
    private int idQuestion;
    @ColumnInfo(name = "name")
    private String machineName;
    @ColumnInfo(name = "max_time_openned")
    private String maxTimeOpenned;
    @ColumnInfo(name = "holidays")
    private String holidays;
    @ColumnInfo(name = "planned_stop")
    private String plannedStop;
    @ColumnInfo(name = "break_time")
    private String breakTime;
    @ColumnInfo(name = "preventive_maintenance")
    private String preventiveMaintenance;
    @ColumnInfo(name = "missing_of")
    private String missingOF;
    @ColumnInfo(name = "sample")
    private String sample;
    @ColumnInfo(name = "actual_productive_time")
    private String actualProductiveTime;
    @ColumnInfo(name = "mcu")
    private double mcu;
    @ColumnInfo(name = "setup_time")
    private String setupTime;
    @ColumnInfo(name = "micro_stop_time")
    private String microStopTime;
    @ColumnInfo(name = "other_stop_time")
    private String otherStopTime;
    @ColumnInfo(name = "scrap_rate")
    private double scrapRate;
    @ColumnInfo(name = "scrap_loss_efficiency")
    private String scrapLossEfficiency;
    @ColumnInfo(name = "cavity_loss_efficiency")
    private String cavityLossEfficiency;
    @ColumnInfo(name = "cycle_time_loss_efficiency")
    private String cycleTimeLossEfficiency;
    @ColumnInfo(name = "speed_lost_efficiency")
    private String speedLostEfficiency;
    @ColumnInfo(name = "net_productive_time")
    private String netProductiveTime;
    @ColumnInfo(name = "net_productive_time_qme")
    private String netProductiveTimeQME;
    @ColumnInfo(name = "good_quality_produced")
    private double goodQualityProduced;
    @ColumnInfo(name = "qme")
    private double qme;
    @ColumnInfo(name = "average_ome")
    private double averageOME;

    public Machine() {

    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMaxTimeOpenned() {
        return maxTimeOpenned;
    }

    public void setMaxTimeOpenned(String maxTimeOpenned) {
        this.maxTimeOpenned = maxTimeOpenned;
    }

    public String getHolidays() {
        return holidays;
    }

    public void setHolidays(String holidays) {
        this.holidays = holidays;
    }

    public String getPlannedStop() {
        return plannedStop;
    }

    public void setPlannedStop(String plannedStop) {
        this.plannedStop = plannedStop;
    }

    public String getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
    }

    public String getPreventiveMaintenance() {
        return preventiveMaintenance;
    }

    public void setPreventiveMaintenance(String preventiveMaintenance) {
        this.preventiveMaintenance = preventiveMaintenance;
    }

    public String getMissingOF() {
        return missingOF;
    }

    public void setMissingOF(String missingOF) {
        this.missingOF = missingOF;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getActualProductiveTime() {
        return actualProductiveTime;
    }

    public void setActualProductiveTime(String actualProductiveTime) {
        this.actualProductiveTime = actualProductiveTime;
    }

    public double getMcu() {
        return mcu;
    }

    public void setMcu(double mcu) {
        this.mcu = mcu;
    }

    public String getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(String setupTime) {
        this.setupTime = setupTime;
    }

    public String getMicroStopTime() {
        return microStopTime;
    }

    public void setMicroStopTime(String microStopTime) {
        this.microStopTime = microStopTime;
    }

    public String getOtherStopTime() {
        return otherStopTime;
    }

    public void setOtherStopTime(String otherStopTime) {
        this.otherStopTime = otherStopTime;
    }

    public double getScrapRate() {
        return scrapRate;
    }

    public void setScrapRate(double scrapRate) {
        this.scrapRate = scrapRate;
    }

    public String getScrapLossEfficiency() {
        return scrapLossEfficiency;
    }

    public void setScrapLossEfficiency(String scrapLossEfficiency) {
        this.scrapLossEfficiency = scrapLossEfficiency;
    }

    public String getCavityLossEfficiency() {
        return cavityLossEfficiency;
    }

    public void setCavityLossEfficiency(String cavityLossEfficiency) {
        this.cavityLossEfficiency = cavityLossEfficiency;
    }

    public String getCycleTimeLossEfficiency() {
        return cycleTimeLossEfficiency;
    }

    public void setCycleTimeLossEfficiency(String cycleTimeLossEfficiency) {
        this.cycleTimeLossEfficiency = cycleTimeLossEfficiency;
    }

    public String getSpeedLostEfficiency() {
        return speedLostEfficiency;
    }

    public void setSpeedLostEfficiency(String speedLostEfficiency) {
        this.speedLostEfficiency = speedLostEfficiency;
    }

    public String getNetProductiveTime() {
        return netProductiveTime;
    }

    public void setNetProductiveTime(String netProductiveTime) {
        this.netProductiveTime = netProductiveTime;
    }

    public String getNetProductiveTimeQME() {
        return netProductiveTimeQME;
    }

    public void setNetProductiveTimeQME(String netProductiveTimeQME) {
        this.netProductiveTimeQME = netProductiveTimeQME;
    }

    public double getGoodQualityProduced() {
        return goodQualityProduced;
    }

    public void setGoodQualityProduced(double goodQualityProduced) {
        this.goodQualityProduced = goodQualityProduced;
    }

    public double getQme() {
        return qme;
    }

    public void setQme(double qme) {
        this.qme = qme;
    }

    public double getAverageOME() {
        return averageOME;
    }

    public void setAverageOME(double averageOME) {
        this.averageOME = averageOME;
    }
}
