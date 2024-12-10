package com.onleetosh.pluralsight.util;

public class Queries {




    /**
     * this method returns a query statement used to get vehicles by color in a specific dealership
     */
    public static String selectVehicleByColor(){
        return """
                SELECT Vehicle.vin, year, make, model, vehicleType, color, odometer, price
                FROM vehicle
                JOIN inventory ON vehicle.vin = inventory.vin
                WHERE inventory.dealershipID = ? 
                AND vehicle.color = ?;
                """;
    }


    public static String selectVehicleByYear(){
        return """
                SELECT Vehicle.vin, year, make, model, vehicleType, color, odometer, price
                FROM vehicle
                JOIN inventory ON vehicle.vin = inventory.vin
                WHERE inventory.dealershipID = ? 
                AND vehicle.year >= ? AND vehicle.year <= ?;
                """;    }

    public static String selectVehicleByType(){
        return """
                SELECT Vehicle.vin, year, make, model, vehicleType, color, odometer, price
                FROM vehicle
                JOIN inventory ON vehicle.vin = inventory.vin
                WHERE inventory.dealershipID = ? 
                AND vehicle.vehicleType = ?;
                """;
    }


    public static String selectVehicleByMakeModel(){
        return "SELECT * FROM Vehicle WHERE Make = ? AND Model = ?";
    }

    /**
     * this method returns a query statement used to get vehicles by price range in a specific dealership
     */
    public static String selectVehicleByPriceRange(){
        return """
                SELECT Vehicle.vin, year, make, model, vehicleType, color, odometer, price
                FROM vehicle
                JOIN inventory ON vehicle.vin = inventory.vin
                WHERE inventory.dealershipID = ? 
                AND vehicle.price >= ? AND vehicle.price <= ?;
                """;
    }



    public static String selectVehicleByMileage(){
        return """
                SELECT Vehicle.vin, year, make, model, vehicleType, color, odometer, price
                FROM vehicle
                JOIN inventory ON vehicle.vin = inventory.vin
                WHERE inventory.dealershipID = ? 
                AND vehicle.odometer >= ? and vehicle.odometer <= ?;
                """;
    }

    public static String selectAllVehicles(){
        return "SELECT * FROM Vehicle";
    }


    /**
     * SQL string for inserting new data into table
     */
    public static String insertNewVehicle(){
        return """
                    INSERT INTO vehicle
                    (`vin`,`year`,`make`,`model`,`vehicleType`,`color`,`odometer`,`price`)
                    VALUES
                    ( ?, -- <{vin: }>,
                      ?, -- <{year: }>,
                      ?, -- <{make: }>,
                      ?, -- <{model: }>,
                      ?, -- <{vehicleType: }>,
                      ?, -- <{color: }>,
                      ?, -- <{odometer: }>,
                      ? -- <{price: }>
                      );
                     """;
    }
    public static String insertVehicleToDealershipInventory(){
        return """
                     INSERT INTO `inventory` (`dealershipID`, `vin`)
                     VALUES (?, ?);
                     """;
    }

    /**
     * SQL string for removing  data from table
     */
    public static String deleteVehicle(){
        return """
                DELETE Inventory, Vehicle
                FROM Inventory
                JOIN Vehicle ON Inventory.Vin = Vehicle.Vin
                WHERE Inventory.Vin = ?;
                """;
    }


    /**
     *  SQL query all contract
     */
    public static String selectForContract() {

        return """
                SELECT
                    LeaseContract.ContractDate
                    Vehicle.Vin, Year, Make, Model, VehicleType, Color, Odometer, Price,
                    CONCAT(Customer.FirstName, ' ', Customer.LastName) AS name,
                    Customer.Email,
                    LeaseContract.ExpectedFinalValue,
                    LeaseContract.LeaseFee,
                    NULL AS SalesTaxes,
                    NULL AS RecordingFee,
                    NULL AS ProcessingFee,
                    LeaseContract.TotalPrice,
                    LeaseContract.MonthlyPayment,
                    'Lease' AS ContractType,
                    NULL AS isFinance
                FROM LeaseContract
                JOIN Vehicle ON LeaseContract.Vin = Vehicle.Vin
                JOIN Customer ON LeaseContract.CustomerID = Customer.CustomerID
                
                UNION ALL
                
                SELECT
                    SalesContract.ContractDate 
                    Vehicle.Vin, Year, Make, Model, VehicleType, Color, Odometer, Price,
                    CONCAT(Customer.FirstName, ' ', Customer.LastName) AS name,
                    Customer.Email,
                    NULL AS EndValue,
                    NULL AS LeaseFee,
                    SalesContract.SalesTaxes,
                    SalesContract.RecordingFee,
                    SalesContract.ProcessingFee, 
                    SalesContract.TotalPrice,
                    SalesContract.MonthlyPayment,
                    'Sales' AS ContractType,
                    SalesContract.isFinance
                FROM SalesContract
                JOIN Vehicle ON SalesContract.Vin = Vehicle.Vin
                JOIN Customer ON SalesContract.CustomerID = Customer.CustomerID;
                """;
    }


    public static String selectContracts() {

        return """
                SELECT
                           LeaseContract.ContractDate AS date,
                           Vehicle.Vin,
                           Vehicle.Year,
                           Vehicle.Make,
                           Vehicle.Model,
                           Vehicle.VehicleType,
                           Vehicle.Color,
                           Vehicle.Odometer,
                           Vehicle.Price,
                           CONCAT(Customer.FirstName, ' ', Customer.LastName) AS name,
                           Customer.Email,
                           NULL AS tax,
                           NULL AS recording,
                           NULL AS processing,
                           LeaseContract.ExpectedFinalValue AS EndValue,
                           LeaseContract.LeaseFee AS LeaseFee,
                           NULL AS finance
                       FROM LeaseContract
                       JOIN Customer ON LeaseContract.CustomerID = Customer.CustomerID
                       JOIN Vehicle ON LeaseContract.Vin = Vehicle.Vin
                
                       UNION ALL
                
                       SELECT
                           SalesContract.ContractDate AS date,
                           Vehicle.Vin,
                           Vehicle.Year,
                           Vehicle.Make,
                           Vehicle.Model,
                           Vehicle.VehicleType,
                           Vehicle.Color,
                           Vehicle.Odometer,
                           Vehicle.Price,
                           CONCAT(Customer.FirstName, ' ', Customer.LastName) AS name,
                           Customer.Email,
                           SalesContract.SalesTaxes AS tax,
                           SalesContract.RecordingFee AS recording,
                           SalesContract.ProcessingFee AS processing,
                           NULL AS EndValue,
                           NULL AS LeaseFee,
                           SalesContract.isFinance AS finance
                       FROM SalesContract
                       JOIN Customer ON SalesContract.CustomerID = Customer.CustomerID
                       JOIN Vehicle ON SalesContract.Vin = Vehicle.Vin;
                """;
    }
    public static String selectSalesContract(){
        return """
                SELECT
                    SalesContract.ContractDate as date,
                    Vehicle.Vin, Vehicle.Year, Vehicle.Make, Vehicle.Model, Vehicle.VehicleType, Vehicle.Color, Vehicle.Odometer, Vehicle.Price,
                    CONCAT(Customer.FirstName, ' ', Customer.LastName) AS name,
                    Customer.Email as email,
                    SalesContract.SalesTaxes AS tax,
                    SalesContract.RecordingFee AS recording,
                    SalesContract.ProcessingFee AS processing,
                    SalesContract.isFinance as finance
                FROM SalesContract
                JOIN Customer ON SalesContract.CustomerID = Customer.CustomerID
                JOIN Vehicle ON SalesContract.Vin = Vehicle.Vin;
                """;
    }
    public static String selectLeaseContract(){
        return """
                SELECT
                    LeaseContract.ContractDate as date,
                    Vehicle.Vin, Vehicle.Year, Vehicle.Make, Vehicle.Model, Vehicle.VehicleType, Vehicle.Color, Vehicle.Odometer, Vehicle.Price,
                    CONCAT(Customer.FirstName, ' ', Customer.LastName) AS name,
                    Customer.Email,
                    LeaseContract.ExpectedFinalValue AS EndValue,
                    LeaseContract.LeaseFee AS LeaseFee
                FROM LeaseContract
                JOIN Customer ON LeaseContract.CustomerID = Customer.CustomerID
                JOIN Vehicle ON LeaseContract.Vin = Vehicle.Vin;
                """;
    }
    public static String queryInsertNewSaleContract(){

        return """
                    INSERT INTO SalesContract
                    (`ContractDate`,`year`,`make`,`model`,`vehicleType`,`color`,`odometer`,`price`)
                    VALUES
                    ( ?, -- <{vin: }>,
                      ?, -- <{year: }>,
                      ?, -- <{make: }>,
                      ?, -- <{model: }>,
                      ?, -- <{vehicleType: }>,
                      ?, -- <{color: }>,
                      ?, -- <{odometer: }>,
                      ? -- <{price: }>
                      );
                     """;

    }


    public static String insertNewLeaseContract(){

        return """
        INSERT INTO LeaseContract
        (`LeaseDate`, `CustomerID`, `Vin`, `DealershipID`, `LeaseContractExpectedFinalValue`, `LeaseContractLeaseFee`, `TotalPrice`, `MonthlyPayment`)
        VALUES
        (?, ?, ?, ?, ?, ?, ?, ?);
        """;
    }

    public static String insertNewCustomer(){
        return """
                INSERT INTO Customer (FirstName, LastName, Email)
                    VALUES (?, ?, ?)
                     """;
    }


}
