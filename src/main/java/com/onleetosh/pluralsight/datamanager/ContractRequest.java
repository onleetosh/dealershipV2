package com.onleetosh.pluralsight.datamanager;


import com.onleetosh.pluralsight.contract.*;
import com.onleetosh.pluralsight.dealership.*;
import com.onleetosh.pluralsight.util.Console;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.onleetosh.pluralsight.datamanager.DataManager.*;
import static com.onleetosh.pluralsight.datamanager.VehicleRequest.*;
import static com.onleetosh.pluralsight.dealership.Dealership.*;

public class ContractRequest {

    private static List<Contract> contracts = new ArrayList<>();

    public static Contract contractToAdd = null;
    public static void getContracts(String[] args){
        try (BasicDataSource dataSource = processConfiguredDataSource(args)) {
            DataManager dm = new DataManager(dataSource);
            List<Contract> contracts = dm.getContractsFromDatabase();
            if (!contracts.isEmpty()) {
                for (Contract contract : contracts) {
                    System.out.println(contract);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: ", e);
        }
    }

    public static void getSalesContract(String[] args){
        try (BasicDataSource dataSource = processConfiguredDataSource(args)) {
            DataManager dm = new DataManager(dataSource);
            List<SalesContract> salesContracts = dm.getSalesContractsFromDatabase();
            if (!salesContracts.isEmpty()) {
                for (SalesContract sales : salesContracts) {
                    System.out.println(sales);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: ", e);
        }

    }

    public static void getLeaseContract(String[] args){

        try (BasicDataSource dataSource = processConfiguredDataSource(args)) {
            DataManager dm = new DataManager(dataSource);
            List<LeaseContract> leaseContracts = dm.getLeaseContractsFromDatabase();
            if (!leaseContracts.isEmpty()) {
                for (LeaseContract lease : leaseContracts) {
                    System.out.println(lease);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: ", e);
        }
    }



    /**
     * processSellOrLeaseRequest() method with helper methods
     */
    public static void processSellOrLeaseRequest2() {



        int vin = getVinFromUser();
        if (vin == 0) return; // VIN input was cancelled

        Vehicle vehicle = currentDealership.getVehicleByVin(vin);
        if (vehicle == null) {
            System.out.println("Vehicle not found. Returning to the main menu.");
            return;
        }

        String contractType = getContractTypeFromUser();
        if (contractType == null) return; // Contract input was cancelled

        String customerName = getInput("Enter customer name (or 'q' to cancel): ");
        if (customerName == null) return; // User ends process

        String customerEmail = getInput("Enter customer email (or 'q' to cancel): ");
        if (customerEmail == null) return; // User ends process

        Customer customer = new Customer(customerName, customerEmail);

        String date = getDateFromUser();
        if (date == null) return; //User end process

        Contract newContract;
        if (contractType.equalsIgnoreCase("sale")) {
            boolean isFinanced = getFinancingOption();
            newContract = new SalesContract(date, customerName, customerEmail, vehicle, isFinanced);
        }
        else {
            newContract = new LeaseContract(date, customerName, customerEmail, vehicle);
        }

        // Add new contract to ArrayList
        contracts.add(newContract);


        System.out.println("Adding contract >>>> " + newContract);
        //remove vehicle sold from inventory
        currentDealership.removeVehicleFromInventory(vehicle);

    }



    public static void processSellOrLeaseRequest() {


        int vin = getVinFromUser();
        if (vin == 0) return;

        Vehicle vehicle = currentDealership.getVehicleByVin(vin);
        if (vehicle == null) {
            System.out.println("Vehicle not found. Returning to the main menu.");
            return;
        }

        String contractType = getContractTypeFromUser();
        if (contractType == null) return;

        String customerName = getInput("Enter customer name (or 'q' to cancel): ");
        if (customerName == null) return;

        String customerEmail = getInput("Enter customer email (or 'q' to cancel): ");
        if (customerEmail == null) return;

        String customerPhone = getInput("Enter Phone Number");


        //TODO: update constructor to match database column..
        Customer customer = new Customer(customerName, customerEmail);
        String date = getDateFromUser();
        if (date == null) return;

        Contract newContract;
        if (contractType.equalsIgnoreCase("sale")) {
            boolean isFinanced = getFinancingOption();
            newContract = new SalesContract(date, customer, vehicle, isFinanced);

        } else {
           LeaseContract lease = new LeaseContract(date, customer, vehicle);

            contractToAdd.addLeaseContract( lease, customer, vehicle);


        }

       // contracts.add(newContract);

        //System.out.println("Adding contract >>>> " + newContract);
        //currentDealership.removeVehicleFromInventory(vehicle);
    }

    /***
     * Helper methods designed to prompt user for information
     */

    private static int getVinFromUser() {
        String input;
        do {
            input = Console.PromptForString("Enter VIN of the vehicle to sell/lease (or 'v' to view all vehicles or 'q' to cancel): ");
            if (input.equalsIgnoreCase("q"))
                return 0;
            if (input.equalsIgnoreCase("v")) {
                processGetAllVehiclesRequest(); // View all vehicles
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid VIN. Please enter a valid number.");
            }
        } while (true);
    }

    private static String getContractTypeFromUser() {
        String input;
        do {
            input = Console.PromptForString("Enter contract type (sale/lease) (or 'q' to cancel): ");
            if (input.equalsIgnoreCase("q"))
                return null; // User ends request
            if (input.equalsIgnoreCase("sale") || input.equalsIgnoreCase("lease"))
                return input; // return and set contract type to sale or lease
            System.out.println("Invalid contract type. Please enter 'sale' or 'lease'.");
        } while (true);
    }

    private static String getInput(String prompt) {
        String input;
        do {
            input = Console.PromptForString(prompt);
            if (input.equalsIgnoreCase("q"))
                return null; // User ends request
        } while (input.isEmpty());
        return input;
    }

    private static String getDateFromUser() {
        String date;
        do {
            date = Console.PromptForString("Enter date (YYYYMMDD) (or 'q' to cancel): ");
            if (date.equalsIgnoreCase("q"))
                return null; // User ends request
            if (date.length() != 8 || !date.matches("\\d{8}")) { // ensure format
                System.out.println("Invalid date format. Please use YYYYMMDD (e.g., 20210928).");
                continue;
            }
            return date;
        } while (true);
    }

    private static boolean getFinancingOption() {
        String input;
        do {
            input = Console.PromptForString("Will this be financed? (yes/no) (or 'q' to cancel): ");
            if (input.equalsIgnoreCase("q"))
                return false; // User ends request
            if ("yes".equalsIgnoreCase(input))
                return true;    // return and set finance to Yes
            if ("no".equalsIgnoreCase(input))
                return false;    //return and set finance to No
            System.out.println("Please enter 'yes' or 'no'.");
        } while (true);
    }


}
