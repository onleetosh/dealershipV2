package com.onleetosh.pluralsight.datamanager;

import com.onleetosh.pluralsight.dealership.Vehicle;
import com.onleetosh.pluralsight.util.Console;

import static com.onleetosh.pluralsight.util.UI.*;

public class VehicleRequest {
    /**
     * Helper method designed to remove a vehicle from the inventory by vin and save changes to inventory.csv
     */
    public static void processRemoveVehicleRequest() {
        System.out.println("Processing vehicle remove request");
        int vin = Console.PromptForInt("Enter Vin: ");
        currentDealership.removeVehicleFromInventory(getVehicleByVin(vin));
    }

    /**
     * get a vehicle by vin number
     */
    private static Vehicle getVehicleByVin(int vin) {
        for(Vehicle v : currentDealership.getInventory()){
            if(v.getVin() == vin){
                return v;
            }
        }
        return null;
    }

    /**
     * Helper method designed to process adding a vehicle to the inventory and save changes to inventory.csv
     */
    public static void processAddVehicleRequest() {
        Vehicle newVehicle = promptForVehicleDetails();
        currentDealership.addVehicleToInventory(newVehicle);
    }

    /**
     * prompt user for  details when adding a vehicle
     */
    private static Vehicle promptForVehicleDetails() {
        int vin = Console.PromptForInt("Enter Vin: ");
        int year = Console.PromptForInt("Enter year: ");
        String make = Console.PromptForString("Enter make: ");
        String model = Console.PromptForString("Enter model: ");
        String vehicleType = Console.PromptForString("Enter vehicle type: ");
        String color = Console.PromptForString("Enter color: ");
        int odometer = Console.PromptForInt("Enter odometer: ");
        double price = Console.PromptForDouble("Enter price: ");

        return new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
    }

    /**
     * Helper methods designed to process search request, by vehicle by type, mileage, color, year, make, model, and price
     */
    public static void processGetByVehicleTypeRequest() {
        String vehicleType = Console.PromptForString("Enter vehicle type (Sedan, Truck): ");
        displayHeader();
        for(Vehicle vehicle : currentDealership.getVehiclesByType(vehicleType)){
            displayVehicle(vehicle);
        }
    }

    /**
     * get car by mileage
     */
    public static void processGetByMileageRequest() {
        int min = Console.PromptForInt("Enter min: ");
        int max = Console.PromptForInt("Enter max: ");
        displayHeader();
        for(Vehicle vehicle : currentDealership.getVehiclesByMileage(min, max)){
            displayVehicle(vehicle);
        }
    }

    /**
     * get car by color
     */
    public static void processGetByColorRequest() {
        String color = Console.PromptForString("Enter color for vehicle: ");
        displayHeader();
        for (Vehicle vehicle : currentDealership.getVehicleByColor(color)){
            displayVehicle(vehicle);
        }
    }

    /**
     * get car by year
     */
    public static void processGetByYearRequest() {
        int min = Console.PromptForInt("Enter min: ");
        int max = Console.PromptForInt("Enter max: ");
        displayHeader();
        for(Vehicle vehicle : currentDealership.getVehicleByYear(min, max)){
            displayVehicle(vehicle);
        }
    }

    /**
     * get car by make or model
     */
    public static void processGetByMakeModelRequest() {
        String makeModel = Console.PromptForString("Enter make for vehicle: ");
        displayHeader();
        for (Vehicle vehicle : currentDealership.getVehiclesByMakeModel(makeModel)) {
            displayVehicle(vehicle);
        }
    }

    /**
     * get car by price range
     */
    public static void processGetByPriceRequest() {
        double min = Console.PromptForDouble("Enter min: ");
        double max = Console.PromptForDouble("Enter max: ");
        displayHeader();
        for(Vehicle vehicle : currentDealership.getVehiclesByPrice(min, max)){
            displayVehicle(vehicle);
        }
    }

    /**
     * Helper method designed to loop through an ArrayList and display all objects
     */
    public static void processGetAllVehiclesRequest(){
        displayHeader();
        for(Vehicle vehicle : currentDealership.getInventory()){
            displayVehicle(vehicle);
        }
    }

    /**
     * Helper method used to print a single vehicle object
     */
    public static void displayVehicle(Vehicle vehicle){
        System.out.println(vehicle);
    }

    public static void displayHeader() {
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("                                  VEHICLES");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.printf("%6s | %5s | %10s | %10s | %7s | %6s | %7s | %6s \n",
                "VIN","YEAR","MAKE", "MODEL","TYPE", "COLOR","ODOMETER","PRICE");
        System.out.println("-------------------------------------------------------------------------------------");
    }

}
