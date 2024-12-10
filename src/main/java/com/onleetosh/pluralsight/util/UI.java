package com.onleetosh.pluralsight.util;

import com.onleetosh.pluralsight.datamanager.*;
import com.onleetosh.pluralsight.dealership.*;
import org.apache.commons.dbcp2.BasicDataSource;

import static com.onleetosh.pluralsight.dealership.Dealership.*;


public class UI {



    private int currentDealershipId = 2;

    private DataManager dataManager;
    /**
     * UI Constructor initializes and loads the file content to ensure the program starts with
     * a dealership with inventory and a list of contract (objects)
     */
    public UI(DataManager dataManager){
        this.dataManager = dataManager;
        currentDealership= dataManager.getDealershipFromDatabase(currentDealershipId);
    }


    /**
     * Display user request options
     */

    public void display(BasicDataSource dataSource, String[] args){
        // option menu represented as a String
        String choices = """
                Please select from the following choices:
                1 - Find vehicles within a price range
                2 - Find vehicles by make / model
                3 - Find vehicles by year range
                4 - Find vehicles by color
                5 - Find vehicles by mileage range
                6 - Find vehicles by type (car, truck, SUV, van)
                7 - List ALL vehicles
                8 - Add a vehicle
                9 - Remove a vehicle
                10 - Sell/Lease a vehicle
                11 - Display Lease Contracts
                12 - Display Sales Contracts
                13 - Display Contracts
                
                0 - Quit

                >>>\s""";

        int request;

        // User Interface Loop
        while (true){
            System.out.println("Welcome to " + currentDealership.getName() + "!");
            request = Console.PromptForInt(choices);
            switch (request) {
                case 1 -> VehicleRequest.processGetByPriceRequest();
                case 2 -> VehicleRequest.processGetByMakeModelRequest();
                case 3 -> VehicleRequest.processGetByYearRequest();
                case 4 -> VehicleRequest.processGetByColorRequest();
                case 5 -> VehicleRequest.processGetByMileageRequest();
                case 6 -> VehicleRequest.processGetByVehicleTypeRequest();
                case 7 -> VehicleRequest.processGetAllVehiclesRequest();
                case 8 -> VehicleRequest.processAddVehicleRequest();
                case 9 -> VehicleRequest.processRemoveVehicleRequest();
                case 10 -> ContractRequest.processSellOrLeaseRequest();
                case 11 -> ContractRequest.getLeaseContract(args);
                case 12 -> ContractRequest.getSalesContract(args);
                case 13 -> ContractRequest.getContracts(args);
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid selection. Please try again.");
            }
        }
    }

}
