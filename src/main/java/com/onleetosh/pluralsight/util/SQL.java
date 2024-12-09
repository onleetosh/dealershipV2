package com.onleetosh.pluralsight.util;

public class SQL {


    public static String queryAllForContract() {

        return """
                SELECT
                    LeaseContract.ContractDate,
                    Vehicle.Vin, Vehicle.Year, Vehicle.Make, Vehicle.Model, Vehicle.VehicleType, Vehicle.Color, Vehicle.Odometer, Vehicle.Price,
                    CONCAT(Customer.FirstName, ' ', Customer.LastName) AS name,
                    Customer.Email,
                    LeaseContract.ExpectedFinalValue AS EndValue,
                    LeaseContract.LeaseFee AS LeaseFee,
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
                    SalesContract.ContractDate,
                    Vehicle.Vin, Vehicle.Year, Vehicle.Make, Vehicle.Model, Vehicle.VehicleType, Vehicle.Color, Vehicle.Odometer, Vehicle.Price,
                    CONCAT(Customer.FirstName, ' ', Customer.LastName) AS name,
                    Customer.Email,
                    NULL AS EndValue,
                    NULL AS LeaseFee,
                    SalesContract.SalesTaxes AS SalesTaxes,
                    SalesContract.RecordingFee AS RecordingFee,
                    SalesContract.ProcessingFee AS ProcessingFee, 
                    SalesContract.TotalPrice,
                    SalesContract.MonthlyPayment,
                    'Sales' AS ContractType,
                    SalesContract.isFinance
                FROM SalesContract
                JOIN Vehicle ON SalesContract.Vin = Vehicle.Vin
                JOIN Customer ON SalesContract.CustomerID = Customer.CustomerID;
                """;
    }
    public static String queryForContract(){

        return """
                SELECT
                    LeaseContract.ContractDate,
                    Vehicle.Vin, Vehicle.Year, Vehicle.Make, Vehicle.Model, Vehicle.VehicleType, Vehicle.Color, Vehicle.Odometer, Vehicle.Price,
                    CONCAT(Customer.FirstName, ' ', Customer.LastName) AS name,
                    Customer.Email,
                    LeaseContract.ExpectedFinalValue AS EndValue,
                    LeaseContractLeaseFee AS LeaseFee,
                    NULL AS SalesTaxes,
                    NULL AS RecordingFee,
                    NULL AS ProcessingFee,
                    LeaseContract.TotalPrice,
                    LeaseContract.MonthlyPayment,
                    'Lease' AS ContractType,
                    NULL AS isFinance
                FROM LeaseContract
                
                UNION ALL
                
                SELECT\s
                    SalesContract.ContractDate,
                    Vehicle.Vin, Vehicle.Year, Vehicle.Make, Vehicle.Model, Vehicle.VehicleType, Vehicle.Color, Vehicle.Odometer, Vehicle.Price,
                    CONCAT(Customer.FirstName, ' ', Customer.LastName) AS name,
                    NULL AS EndValue,
                    NULL AS Lease,
                    SalesContract.SalesTaxes as SalesTax
                    SalesContract.RecordingFee as RecordingFee,
                    SalesContract.ProcessingFee as ProcessingFee, 
                    SalesContract.TotalPrice,
                    SalesContract.MonthlyPayment,
                    'Sales' AS ContractType,
                    SalesContract.isFinance
                FROM SalesContract;
                """;
    }

    public static String querySalesContract(){
        return """
                SELECT
                    SalesContract.ContractDate as date,
                    Vehicle.Vin, Vehicle.Year, Vehicle.Make, Vehicle.Model, Vehicle.VehicleType, Vehicle.Color, Vehicle.Odometer, Vehicle.Price,
                    CONCAT(Customer.FirstName, ' ', Customer.LastName) AS name,
                    Customer.Email,
                    SalesContract.SalesTaxes AS tax,
                    SalesContract.RecordingFee AS recording,
                    SalesContract.ProcessingFee AS processing,
                    SalesContract.isFinance as finance
                FROM SalesContract
                JOIN Customer ON SalesContract.CustomerID = Customer.CustomerID
                JOIN Vehicle ON SalesContract.Vin = Vehicle.Vin;
                """;
    }
    public static String queryLeaseContract(){
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

    /**
     * this method returns a query statement used to get vehicles by color in a specific dealership
     */
    public static String queryVehicleByColor(){
        return """
                SELECT Vehicle.vin, year, make, model, vehicleType, color, odometer, price
                FROM vehicle
                JOIN inventory ON vehicle.vin = inventory.vin
                WHERE inventory.dealershipID = ? 
                AND vehicle.color = ?;
                """;
    }

    public static String queryVehicleByYear(){
        return """
                SELECT Vehicle.vin, year, make, model, vehicleType, color, odometer, price
                FROM vehicle
                JOIN inventory ON vehicle.vin = inventory.vin
                WHERE inventory.dealershipID = ? 
                AND vehicle.year >= ? AND vehicle.year <= ?;
                """;    }


    public static String queryVehicleByType(){
        return """
                SELECT Vehicle.vin, year, make, model, vehicleType, color, odometer, price
                FROM vehicle
                JOIN inventory ON vehicle.vin = inventory.vin
                WHERE inventory.dealershipID = ? 
                AND vehicle.vehicleType = ?;
                """;
    }


    //TODO FIX query
    public static String queryVehicleByMakeModel(){
        return "SELECT * FROM Vehicle WHERE Make = ? OR Model = ?";
    }


    /**
     * this method returns a query statement used to get vehicles by price range in a specific dealership
     */
    public static String queryVehicleByPriceRange(){
        return """
                SELECT Vehicle.vin, year, make, model, vehicleType, color, odometer, price
                FROM vehicle
                JOIN inventory ON vehicle.vin = inventory.vin
                WHERE inventory.dealershipID = ? 
                AND vehicle.price >= ? AND vehicle.price <= ?;
                """;
    }



    public static String queryVehicleByMileage(){
        return """
                SELECT Vehicle.vin, year, make, model, vehicleType, color, odometer, price
                FROM vehicle
                JOIN inventory ON vehicle.vin = inventory.vin
                WHERE inventory.dealershipID = ? 
                AND vehicle.odometer >= ? and vehicle.odometer <= ?;
                """;
    }

    public static String queryAllVehicles(){
        return "SELECT * FROM Vehicle";
    }


    public static String queryInsertVehicle(){
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

    public static String queryInsertToDealershipInventory(){
        return """
                     INSERT INTO `inventory` (`dealershipID`, `vin`)
                     VALUES (?, ?);
                     """;
    }

    public static String queryRemoveVehicle(){
        return """
                DELETE Inventory, Vehicle
                FROM Inventory
                JOIN Vehicle ON Inventory.Vin = Vehicle.Vin
                WHERE Inventory.Vin = ?;
                """;
    }
}
