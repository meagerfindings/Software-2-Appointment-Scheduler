/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reports;

import java.util.Collections;

/**
 *
 * @author matgreten
 */
public class Month {
    
    private String monthName;
    
    /**
     * @return the monthName
     */
    public String getMonthName() {
        return monthName;
    }

    /**
     * @param monthName the monthName to set
     */
    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
    
    private int demo;
    
    /**
     * @return the demo
     */
    public int getDemo() {
        return demo;
    }

    /**
     * @param demo the demo to set
     */
    public void setDemo(int demo) {
        this.demo = demo;
    }
    
    public void addDemo() {
        this.demo += 1;
    }
    
    private int troubleshooting;

    /**
     * @return the troubleshooting
     */
    public int getTroubleshooting() {
        return troubleshooting;
    }

    /**
     * @param troubleshooting the troubleshooting to set
     */
    public void setTroubleshooting(int troubleshooting) {
        this.troubleshooting = troubleshooting;
    }
    
    public void addTroubleshooting() {
        this.troubleshooting += 1;
    }
    
    private int intialConsultation;

    /**
     * @return the intialConsultation
     */
    public int getIntialConsultation() {
        return intialConsultation;
    }

    /**
     * @param intialConsultation the intialConsultation to set
     */
    public void setIntialConsultation(int intialConsultation) {
        this.intialConsultation = intialConsultation;
    }
    
    public void addIntialConsultation() {
        this.intialConsultation += 1;
    }
    
    private int checkIn;

    /**
     * @return the checkIn
     */
    public int getCheckIn() {
        return checkIn;
    }

    /**
     * @param checkIn the checkIn to set
     */
    public void setCheckIn(int checkIn) {
        this.checkIn = checkIn;
    }
    
    public void addCheckIn() {
        this.checkIn += 1;
    }
    
    private int cusCalib;

    /**
     * @return the cusCalib
     */
    public int getCusCalib() {
        return cusCalib;
    }

    /**
     * @param cusCalib the cusCalib to set
     */
    public void setCusCalib(int cusCalib) {
        this.cusCalib = cusCalib;
    }
    
    public void addCusCalib() {
        this.cusCalib += 1;
    }
    
    private int emergency;

    /**
     * @return the emergency
     */
    public int getEmergency() {
        return emergency;
    }

    /**
     * @param emergency the emergency to set
     */
    public void setEmergency(int emergency) {
        this.emergency = emergency;
    }
    
    public void addEmergency() {
        this.emergency += 1;
    }
    
    
    public Month(){
        this.monthName = "";
        this.checkIn = 0;
        this.cusCalib = 0;
        this.demo = 0;
        this.intialConsultation = 0;
        this.troubleshooting = 0;
    }
    
    public Month(String monthName){
        this.monthName = monthName;
        this.checkIn = 0;
        this.cusCalib = 0;
        this.demo = 0;
        this.intialConsultation = 0;
        this.troubleshooting = 0;
    }
    
    public String getMonthData(){
       
        String dashes = "--";
        
        for(int i = 0; i < getMonthName().length();  i++){
            dashes += "-";
        }
        
        String result = "\n" + getMonthName() + "\n" + dashes + "\n" +
                "Customer Calibration: " + getCusCalib() + "\n" +
                "Demo: " + getDemo() + "\n" + 
                "Emergency Customer Assistance: " + getEmergency() + "\n" +
                "Initial Consultation: " + getIntialConsultation() + "\n" +
                "Routine Check-In: " + getCheckIn() + "\n" + 
                "Troubleshooting: " + getTroubleshooting() + "\n";
        
        return result;
    }
    
}
