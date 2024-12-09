package com.onleetosh.pluralsight.dealership;

import com.onleetosh.pluralsight.util.*;
public class Vehicle {
    private int vin;
    private int year;
    private String make;
    private String model;
    private String vehicleType;
    private String color;
    private int odometer;
    private double price;

    public Vehicle(int vin,
                   int year,
                   String make,
                   String model,
                   String vehicleType,
                   String color,
                   int odometer,
                   double price) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
    }

    // Getters for all fields
    public int getVin() {
        return vin;
    }

    public int getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getColor() {
        return color;
    }

    public int getOdometer() {
        return odometer;
    }

    public double getPrice() {
        return price;
    }


    public static Vehicle getVehicleDetails() {
        System.out.println("Enter vehicle details:");
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

    @Override
    public String toString() {
        String colorString;
        if (color.equalsIgnoreCase("Red"))
            colorString = ColorCodes.RED + color + ColorCodes.RESET;
        else if (color.equalsIgnoreCase("Blue"))
            colorString = ColorCodes.BLUE + color + ColorCodes.RESET;
        else if (color.equalsIgnoreCase("Yellow"))
            colorString = ColorCodes.YELLOW + color + ColorCodes.RESET;
        else if (color.equalsIgnoreCase("Green"))
            colorString = ColorCodes.GREEN + color + ColorCodes.RESET;
        else
            colorString = color;

        return String.format(" %5d | %5d | %10s | %10s | %7s | %15s | %8d | $%3.2f ",
                this.vin,
                this.year,
                this.make,
                this.model,
                this.vehicleType,
                colorString,
                this.odometer,
                this.price
        );

    }
}
