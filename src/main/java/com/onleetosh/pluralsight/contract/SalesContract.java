package com.onleetosh.pluralsight.contract;


import com.onleetosh.pluralsight.dealership.Vehicle;
import com.onleetosh.pluralsight.util.Calculation;

public class SalesContract extends Contract {

    /**
     * Sales Information
     */

    private final double salesTaxPercentage = 0.05;
    private double salesTax; // %5
    private double recordingFee; // $100.00
    private double processingFee; // a fee of $295 if vehicle under $10k, else $495
    private boolean isFinance; // do they want to finance (yes/no)


    /**
     * Constructor One: used to initialize a Sales contract; requires minimal information and the missing
     * values are computed based on the vehicle's price.
     */

    public SalesContract(String date,
                         String customerName,
                         String customerEmail,
                         Vehicle vehicleSold,
                         double salesTaxes,
                         double recordingFee,
                         double processingFee,
                         double totalPrice,
                         double monthlyPayment,
                         boolean isFinance)
    {
        super(date, customerName, customerEmail, vehicleSold);
        this.salesTax = vehicleSold.getPrice() * salesTaxPercentage;
        this.recordingFee = 100;
        this.processingFee = (vehicleSold.getPrice() < 10000) ? 295 : 495;
        this.isFinance = isFinance;
    }

    /**
     * Constructor Two: used initialize a Sales contract; requires all contract details
     */

    public SalesContract(String date,
                         String customerName,
                         String customerEmail,
                         Vehicle vehicleSold,
                         double salesTax,
                         double recordingFee,
                         double processingFee,
                         boolean isFinance) {
        super( date, customerName, customerEmail, vehicleSold);
        this.salesTax = salesTax;
        this.isFinance = isFinance;
        this.processingFee = processingFee;
        this.recordingFee = recordingFee;
    }

    /**
     * Getters
     */

    public double getRecordingFee() {
        return recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public boolean isFinance() {
        return isFinance;
    }

    public double getSalesTax() {
        return salesTax;
    }

    /**
     * Setters
     */

    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    public void setRecordingFee(double recordingFee) {
        this.recordingFee = recordingFee;
    }

    public void setFinance(boolean finance) {
        isFinance = finance;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    /**
     * Override to calculate and return the value amount for total price and monthly payment
     */

    // 4.25% for 48mos if the price is $10k+ , else 5.25% for 24mos
    //TODO : confirm all calculations are precise

    @Override
    public double getTotalPrice(){
        return getVehicleSold().getPrice() + salesTax + processingFee + recordingFee;
    }

    @Override
    public double getMonthlyPayment(){
        if (isFinance) {
            double financeRate = (getVehicleSold().getPrice() < 10000) ? 0.0525 : 0.0425;
            double financeTerm = (getVehicleSold().getPrice() < 10000) ? 24 : 48;
            return Calculation.calculateLoanPayment(getTotalPrice(), financeRate, financeTerm);
        }
        else {
            return 0;
        }
    }

    /**
     * Override to convert and return Sales contract as a custom string
     */

    @Override
    public String toString() {
        String financeDecision = isFinance ? "YES" : "NO";
        // 0  1  2  3  4  5  6  7  8  9  10  11   12   13   14  15   16  17
        return String.format("SALES|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f",
                this.date, //1
                this.customerName,//2
                this.customerEmail, //3
                this.vehicleSold.getVin(),//4
                this.vehicleSold.getYear(),//5
                this.vehicleSold.getMake(),//6
                this.vehicleSold.getModel(),//7
                this.vehicleSold.getVehicleType(),//8
                this.vehicleSold.getColor(),//9
                this.vehicleSold.getOdometer(),//10
                this.vehicleSold.getPrice(), //11
                this.salesTax, //12
                this.recordingFee, //13
                this.processingFee, //14
                getTotalPrice(), //15
                financeDecision, //16
                getMonthlyPayment()); //17
    }

}