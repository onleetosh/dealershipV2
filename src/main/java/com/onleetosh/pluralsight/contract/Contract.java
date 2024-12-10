package com.onleetosh.pluralsight.contract;

import com.onleetosh.pluralsight.datamanager.ContractManager;
import com.onleetosh.pluralsight.datamanager.DataManager;
import com.onleetosh.pluralsight.dealership.Vehicle;

import java.awt.*;
import java.util.List;

public abstract class Contract {

    protected int contractID;
    protected String date;
    protected String customerName;
    protected String customerEmail;
    protected Vehicle vehicleSold;


    private int customerId;

    static List<Contract> contractList;

    protected double totalPrice;
    protected double monthlyPayment;

    private ContractManager contractManager;


    // with Customer class

    protected Customer customerInformation;
    public Contract(String date,
                       Customer customerInformation,
                       Vehicle vehicleSold) {
        this.date = date;
        this.customerInformation = customerInformation;
        this.vehicleSold = vehicleSold;
    }

    public Contract(ContractManager contractManager,
                    String date,
                    Customer customerInformation,
                    Vehicle vehicleSold) {
        this.contractManager = contractManager;
        this.date = date;
        this.customerInformation = customerInformation;
        this.vehicleSold = vehicleSold;
    }



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

    public List<Contract> getContractList(){
        return contractManager.getContractsFromDatabase();

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


    public int getCustomerId(){
        return customerId;
    }
    public void addLeaseContract(LeaseContract contract, Customer customer, Vehicle vehicle){
        contractManager.processInsertLeaseContract(contract, customer, vehicle);
    }
    public abstract double getTotalPrice();
    public abstract double getMonthlyPayment();
}
