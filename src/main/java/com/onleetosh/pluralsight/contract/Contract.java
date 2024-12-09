package com.onleetosh.pluralsight.contract;

import com.onleetosh.pluralsight.dealership.Vehicle;

public abstract class Contract {

    protected int contractID;
    protected String date;
    protected String customerName;
    protected String customerEmail;
    protected Vehicle vehicleSold;

    protected double totalPrice;
    protected double monthlyPayment;

    public Contract(String date,
                    String customerName,
                    String customerEmail,
                    Vehicle vehicleSold) {
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleSold = vehicleSold;
    }

    /**
     * Getters
     */



    public String getDate(){
        return date;
    }
    public String getCustomerName(){
        return customerName;
    }

    public String getCustomerEmail(){
        return customerEmail;
    }

    public Vehicle getVehicleSold() {
        return vehicleSold;
    }



    /**
     * Setters
     */


    public void setDate(String date) {
        this.date = date;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setVehicleSold(Vehicle vehicleSold) {
        this.vehicleSold = vehicleSold;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setMonthlyPayment(float monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }


    public abstract double getTotalPrice();
    public abstract double getMonthlyPayment();
}
