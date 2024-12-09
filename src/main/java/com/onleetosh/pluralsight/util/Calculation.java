package com.onleetosh.pluralsight.util;

public class Calculation {

    public static double calculateLoanPayment(double borrowedAmount, double loanRate, double months){
        return borrowedAmount * (loanRate/12 * Math.pow(1 + loanRate/12, months)) / (Math.pow(1 + loanRate/12, months) - 1);
    }
}
