package com.onleetosh.pluralsight.dealership;


import com.onleetosh.pluralsight.datamanager.DataManager;

import java.util.List;

public class Dealership {


    private String name;
    private String address;
    private String phone;
    private int dealershipID;

    private DataManager dataManager;

    public Dealership(DataManager dataManager, int dealershipID, String name, String address, String phone) {
        this.dealershipID = dealershipID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.dataManager = dataManager;
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public List<Vehicle> getInventory() {
        return dataManager.getVehiclesFromDealership(dealershipID);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *  add vehicle to database
     */
    public void addVehicleToInventory(Vehicle vehicle){
        dataManager.insertVehicleToInventory(this.dealershipID, vehicle);
    }



    /**
     * remove vehicle from database
     */
    public void removeVehicleFromInventory(Vehicle vehicle){
        dataManager.removeVehicleFromInventory(this.dealershipID, vehicle);
    }


    /**
     * get  by  price range
     */
    public List<Vehicle> getVehiclesByPrice(double min, double max){
        return dataManager.getVehicleFromDatabaseByPriceRange(this.dealershipID, min, max);

    }

    /**
     * get car by color
     */
    public List<Vehicle> getVehicleByColor(String color){
        return dataManager.getVehicleFromDatabaseByColor(this.dealershipID, color);
    }

    /**
     * get car by vehicle type
     */
    public List<Vehicle> getVehiclesByType(String vehicleType){
        return dataManager.getVehicleFromDatabaseByVehicleType(this.dealershipID, vehicleType);

    }

    /**
     * get car by year
     */
    public List<Vehicle> getVehicleByYear(int min, int max) {
        return dataManager.getVehicleFromDatabaseByYear(this.dealershipID, min, max);
    }

    /**
     * get car by mileage
     */
    public List<Vehicle> getVehiclesByMileage(int min, int max){
        return dataManager.getVehicleFromDatabaseByMileage(this.dealershipID, min, max);
    }
    /**
     * get car by make or model
     */
    public List<Vehicle> getVehiclesByMakeModel(String makeModel) {
        return dataManager.getVehicleFromDatabaseByMakeModel(this.dealershipID, makeModel);

    }




}
