package com.onleetosh.pluralsight.datamanager;


import com.onleetosh.pluralsight.contract.Contract;
import com.onleetosh.pluralsight.contract.LeaseContract;
import com.onleetosh.pluralsight.contract.SalesContract;
import com.onleetosh.pluralsight.util.SQL;
import com.onleetosh.pluralsight.dealership.*;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.*;

public class DataManager {


    private final BasicDataSource dataSource;

    public DataManager(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Process arguments need to connect to database
     */
    public static BasicDataSource processConfiguredDataSource(String[] args){
        if (args.length != 3) {
            System.out.println(
                    "Application needs three arguments to run: " +
                            " <username> <password> <url>");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];
        String sqlServerUrl = args[2];

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(sqlServerUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;

    }


    /**
     * get dealership
     */
    public Dealership getDealershipFromDatabase(int dealershipId){
        try(Connection connection = dataSource.getConnection();){
            try(PreparedStatement query = connection.prepareStatement(
                    """
                    select
                    DealershipID, DealershipName, address, phone
                    from dealership
                    where dealershipID = ?
                    """);)
            {
                query.setInt(1, dealershipId);
                try(ResultSet results = query.executeQuery())
                {
                    Dealership result = null;
                    while(results.next()){

                        result = new Dealership(
                                this,
                                results.getInt(1),
                                results.getString(2),
                                results.getString(3),
                                results.getString(4)
                        );

                    }
                    return result;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertVehicleToInventory(int dealershipId, Vehicle vehicle){

        try(Connection connection = dataSource.getConnection();){
            try(PreparedStatement query = connection.prepareStatement(
                    SQL.queryInsertVehicle());)
            {
                query.setInt(1, vehicle.getVin());
                query.setInt(2, vehicle.getYear());
                query.setString(3, vehicle.getMake());
                query.setString(4, vehicle.getModel());
                query.setString(5, vehicle.getVehicleType());
                query.setString(6, vehicle.getColor());
                query.setInt(7, vehicle.getOdometer());
                query.setDouble(8, vehicle.getPrice());

                int rows = query.executeUpdate();

            }

            try(PreparedStatement query = connection.prepareStatement(
                    SQL.queryInsertToDealershipInventory());)
            {
                query.setInt(1,dealershipId);
                query.setInt(2, vehicle.getVin());
                int rows = query.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove a vehicle from database
     */
    public void removeVehicleFromInventory(int dealershipId, Vehicle vehicle){

        try(Connection connection = dataSource.getConnection();){
            try(PreparedStatement query = connection.prepareStatement(
                    SQL.queryRemoveVehicle());)
            {

                query.setInt(1, vehicle.getVin());
                int rows = query.executeUpdate();

            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get vehicle by color
     */
    public List<Vehicle> getVehicleFromDatabaseByColor(int dealershipId, String value) {
        ArrayList<Vehicle> vehicle = new ArrayList<>();

        //do the stuff with the datasource here...

        try(Connection connection = dataSource.getConnection();){
            try(PreparedStatement ps = connection.prepareStatement(
                    SQL.queryVehicleByColor());)
            {
                ps.setInt(1, dealershipId);
                ps.setString(2, value);
                try(ResultSet results = ps.executeQuery())
                {
                    //loop and return all results
                    while(results.next()){
                        vehicle.add( new Vehicle(
                                results.getInt(1),
                                results.getInt(2),
                                results.getString(3),
                                results.getString(4),
                                results.getString(5),
                                results.getString(6),
                                results.getInt(7),
                                results.getDouble(8)

                        ));
                    }
                }
            }
            return vehicle;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * get vehicle by type
     */
    public  List<Vehicle> getVehicleFromDatabaseByVehicleType(int dealershipId, String value) {
        ArrayList<Vehicle> vehicle = new ArrayList<>();


        //do the stuff with the datasource here...

        try(Connection connection = dataSource.getConnection();){
            try(PreparedStatement ps = connection.prepareStatement(
                    SQL.queryVehicleByType());)
            {
                ps.setInt(1, dealershipId);
                ps.setString(2, value);
                try(ResultSet results = ps.executeQuery())
                {
                    //loop and return all results
                    while(results.next()){
                        vehicle.add( new Vehicle(
                                results.getInt(1),
                                results.getInt(2),
                                results.getString(3),
                                results.getString(4),
                                results.getString(5),
                                results.getString(6),
                                results.getInt(7),
                                results.getDouble(8)

                        ));
                    }
                }
            }
            return vehicle;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * get vehicle by price range
     */
    public   List<Vehicle> getVehicleFromDatabaseByPriceRange(int dealershipId, double min, double max) {
        ArrayList<Vehicle> vehicle = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();){
            try(PreparedStatement ps = connection.prepareStatement(
                    SQL.queryVehicleByPriceRange());)
            {
                ps.setInt(1, dealershipId);
                ps.setDouble(2, min);
                ps.setDouble(3, max);
                try(ResultSet results = ps.executeQuery())
                {
                    //loop and return all results
                    while(results.next()){
                        vehicle.add( new Vehicle(
                                results.getInt(1),
                                results.getInt(2),
                                results.getString(3),
                                results.getString(4),
                                results.getString(5),
                                results.getString(6),
                                results.getInt(7),
                                results.getDouble(8)

                        ));
                    }
                }
            }
            return vehicle;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get vehicle by year
     */
    public   List<Vehicle> getVehicleFromDatabaseByYear(int dealershipId, int min, int max) {
        ArrayList<Vehicle> vehicle = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();){
            try(PreparedStatement ps = connection.prepareStatement(
                    SQL.queryVehicleByYear());)
            {
                ps.setInt(1, dealershipId);
                ps.setInt(2, min);
                ps.setInt(3, max);

                try(ResultSet results = ps.executeQuery())
                {
                    //loop and return all results
                    while(results.next()){
                        vehicle.add( new Vehicle(
                                results.getInt(1),
                                results.getInt(2),
                                results.getString(3),
                                results.getString(4),
                                results.getString(5),
                                results.getString(6),
                                results.getInt(7),
                                results.getDouble(8)

                        ));
                    }
                }
            }
            return vehicle;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * get vehicle by mileage
     */
    public   List<Vehicle> getVehicleFromDatabaseByMileage(int dealershipId, int min, int max) {
        ArrayList<Vehicle> vehicle = new ArrayList<>();


        try(Connection connection = dataSource.getConnection();){
            try(PreparedStatement ps = connection.prepareStatement(
                    SQL.queryVehicleByMileage());)
            {
                ps.setInt(1, dealershipId);
                ps.setInt(2, min);
                ps.setInt(3, max);

                try(ResultSet results = ps.executeQuery())
                {
                    //loop and return all results
                    while(results.next()){
                        vehicle.add( new Vehicle(
                                results.getInt(1),
                                results.getInt(2),
                                results.getString(3),
                                results.getString(4),
                                results.getString(5),
                                results.getString(6),
                                results.getInt(7),
                                results.getDouble(8)

                        ));
                    }
                }
            }
            return vehicle;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get vehicle by make/model
     */
    public  List<Vehicle> getVehicleFromDatabaseByMakeModel(int dealershipId, String value) {
        ArrayList<Vehicle> vehicle = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();){
            try(PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM Vehicle WHERE Color = ?");)
            {
                ps.setInt(1, dealershipId);
                ps.setString(2, value);
                try(ResultSet results = ps.executeQuery())
                {
                    //loop and return all results
                    while(results.next()){
                        vehicle.add( new Vehicle(
                                results.getInt(1),
                                results.getInt(2),
                                results.getString(3),
                                results.getString(4),
                                results.getString(5),
                                results.getString(6),
                                results.getInt(7),
                                results.getDouble(8)

                        ));
                    }
                }
            }
            return vehicle;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * method returns all vehicle listed in the database
     * @return
     */
    public  List<Vehicle> getAllVehicleFromDatabase() {
        ArrayList<Vehicle> vehicle = new ArrayList<>();
        //do the stuff with the datasource here...
        try(Connection connection = dataSource.getConnection();){
            try(PreparedStatement ps = connection.prepareStatement(
                    SQL.queryAllVehicles());)
            {
                try(ResultSet results = ps.executeQuery())
                {
                    //loop and return all results
                    while(results.next()){
                        vehicle.add( new Vehicle(
                                results.getInt(1),
                                results.getInt(2),
                                results.getString(3),
                                results.getString(4),
                                results.getString(5),
                                results.getString(6),
                                results.getInt(7),
                                results.getDouble(8)

                        ));
                    }
                }
            }
            return vehicle;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to return vehicles matching a dealership ID
     * @param dealershipId
     * @return
     */
    public List<Vehicle> getVehiclesFromDealership(int dealershipId){
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();){
            try(PreparedStatement query = connection.prepareStatement(
                    """
                            SELECT
                            vehicle.vin, year, make, model, vehicleType, color, odometer, price
                            FROM vehicle
                            join inventory on vehicle.vin = inventory.vin
                            WHERE inventory.dealershipID = ?
                            """);)
            {
                query.setInt(1, dealershipId);
                try(ResultSet results = query.executeQuery())
                {
                    while(results.next()){
                        vehicles.add( new Vehicle(
                                results.getInt("vin"),
                                results.getInt("year"),
                                results.getString("make"),
                                results.getString("model"),
                                results.getString("vehicleType"),
                                results.getString("color"),
                                results.getInt("odometer"),
                                results.getDouble("price")
                        ));

                    }
                }
            }

            return vehicles;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Get Lease contract
     * @return
     */
    public List<LeaseContract> getLeaseContractsFromDatabase() {
        List<LeaseContract> contracts = new ArrayList<>();

        String query = SQL.queryLeaseContract();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet results = ps.executeQuery()) {

            while (results.next()) {
                // Create Vehicle object
                Vehicle vehicle = new Vehicle(
                        results.getInt("Vin"),
                        results.getInt("Year"),
                        results.getString("Make"),
                        results.getString("Model"),
                        results.getString("VehicleType"),
                        results.getString("Color"),
                        results.getInt("Odometer"),
                        results.getDouble("Price")
                );

                // Create LeaseContract object
                contracts.add(new LeaseContract(
                        results.getString("date"),
                        results.getString("name"),
                        results.getString("email"),
                        vehicle
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching lease contracts: ", e);
        }

        return contracts;
    }


    public List<SalesContract> getSalesContractsFromDatabase() {
        List<SalesContract> contracts= new ArrayList<>();

        String query = SQL.querySalesContract();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet results = ps.executeQuery()) {

            while (results.next()) {
                // Create Vehicle object
                Vehicle vehicle = new Vehicle(
                        results.getInt("Vin"),
                        results.getInt("Year"),
                        results.getString("Make"),
                        results.getString("Model"),
                        results.getString("VehicleType"),
                        results.getString("Color"),
                        results.getInt("Odometer"),
                        results.getDouble("Price")
                );

                // Create SalesContract object
                contracts.add(new SalesContract(
                        results.getString("date"),
                        results.getString("name"),
                        results.getString("email"),
                        vehicle,
                        results.getDouble("tax"),
                        results.getDouble("recording"),
                        results.getDouble("processing"),
                        results.getBoolean("finance")

                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching sales contracts: ", e);
        }

        return contracts;
    }


    public List<Contract> getAllContractsFromDatabase() {
        List<Contract> contracts = new ArrayList<>();

        String query = SQL.queryAllForContract();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet results = ps.executeQuery()) {

            while (results.next()) {
                // Create Vehicle object
                Vehicle vehicle = new Vehicle(
                        results.getInt("Vin"), // Changed to String
                        results.getInt("Year"),
                        results.getString("Make"),
                        results.getString("Model"),
                        results.getString("VehicleType"),
                        results.getString("Color"),
                        results.getInt("Odometer"),
                        results.getDouble("Price")
                );

                String contractDate = results.getString("ContractDate");
                String name = results.getString("name");
                String email = results.getString("Email");
                double totalPrice = results.getDouble("TotalPrice");
                double monthlyPayment = results.getDouble("MonthlyPayment");

                String contractType = results.getString("ContractType");

                if (contractType.equalsIgnoreCase("lease")) {
                    // Create LeaseContract
                    contracts.add(new LeaseContract(
                            contractDate,
                            name,
                            email,
                            vehicle,
                            results.getDouble("EndValue"),
                            results.getDouble("LeaseFee"),
                            totalPrice,
                            monthlyPayment
                    ));
                } else if (contractType.equalsIgnoreCase("sales")) {
                    // Create SalesContract
                    contracts.add(new SalesContract(
                            contractDate,
                            name,
                            email,
                            vehicle,
                            results.getDouble("SalesTaxes"),
                            results.getDouble("RecordingFee"),
                            results.getDouble("ProcessingFee"),
                            totalPrice,
                            monthlyPayment,
                            results.getBoolean("isFinance")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception while fetching contracts: " + e.getMessage());
            throw new RuntimeException("Error fetching contracts: ", e);
        }

        return contracts;
    }




}
