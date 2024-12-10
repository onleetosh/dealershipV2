package com.onleetosh.pluralsight.datamanager;

import com.onleetosh.pluralsight.contract.Contract;
import com.onleetosh.pluralsight.contract.Customer;
import com.onleetosh.pluralsight.contract.LeaseContract;
import com.onleetosh.pluralsight.contract.SalesContract;
import com.onleetosh.pluralsight.dealership.Vehicle;
import com.onleetosh.pluralsight.util.Queries;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContractManager {
    private final BasicDataSource contractDataSource;
    public ContractManager(BasicDataSource contractDataSource) {
        this.contractDataSource = contractDataSource;
    }


    /**
     *  updated method with new Customer class
     * @return
     */
    public List<LeaseContract> getLeaseContractsFromDatabase() {
        List<LeaseContract> contracts = new ArrayList<>();

        String query = Queries.selectLeaseContract();

        try (Connection connection = contractDataSource.getConnection();
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

                //create customer object

                Customer customer = new Customer(
                        results.getString("Name"),
                        results.getString("Email")

                );

                // Create LeaseContract object
                contracts.add(new LeaseContract(
                        results.getString("date"),
                        customer,
                        vehicle,
                        results.getDouble("EndValue"),
                        results.getDouble("LeaseFee")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching lease contracts: ", e);
        }

        return contracts;
    }

    public List<SalesContract> getSalesContractsFromDatabase() {
        List<SalesContract> contracts = new ArrayList<>();

        String query = Queries.selectSalesContract();

        try (Connection connection = contractDataSource.getConnection();
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

                //create customer object

                Customer customer = new Customer(
                        results.getString("Name"),
                        results.getString("Email")

                );


                // Create SalesContract object
                contracts.add(new SalesContract(
                        results.getString("date"),
                        customer,
                        vehicle,
                        results.getDouble("tax"),
                        results.getDouble("recording"),
                        results.getDouble("processing"),
                        results.getBoolean("finance")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching lease contracts: ", e);
        }

        return contracts;
    }

    public List<Contract> getContractsFromDatabase() {
        List<Contract> contracts = new ArrayList<>();
        String query = Queries.selectContracts();

        try (Connection connection = contractDataSource.getConnection();
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

                // Create Customer object
                Customer customer = new Customer(
                        results.getString("name"), // Full name from CONCAT
                        results.getString("Email")
                );

                // Check if it's a lease or sales contract based on NULL values
                if (results.getDouble("LeaseFee") != 0) {
                    // Lease contract
                    contracts.add(new LeaseContract(
                            results.getString("date"),
                            customer,
                            vehicle,
                            results.getDouble("EndValue"),
                            results.getDouble("LeaseFee")
                    ));
                } else {
                    // Sales contract
                    contracts.add(new SalesContract(
                            results.getString("date"),
                            customer,
                            vehicle,
                            results.getDouble("tax"),
                            results.getDouble("recording"),
                            results.getDouble("processing"),
                            results.getBoolean("finance")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching contracts: ", e);
        }

        return contracts;
    }


    public List<Contract> getAllContractsFromDatabase() {
        List<Contract> contracts = new ArrayList<>();
        String query = Queries.selectContracts();

        try (Connection connection = contractDataSource.getConnection();
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

                // Create Customer object
                Customer customer = new Customer(
                        results.getString("name"), // Full name from CONCAT
                        results.getString("Email")
                );

                // Determine contract type and create appropriate contract object
                String contractType = results.getString("ContractType");
                if (contractType.equalsIgnoreCase("Lease")) {
                    contracts.add(new LeaseContract(
                            results.getString("ContractDate"),
                            customer,
                            vehicle,
                            results.getDouble("ExpectedFinalValue"),
                            results.getDouble("LeaseFee")
                    ));
                } else if (contractType.equalsIgnoreCase("Sales")) {
                    contracts.add(new SalesContract(
                            results.getString("ContractDate"),
                            customer,
                            vehicle,
                            results.getDouble("SalesTaxes"),
                            results.getDouble("RecordingFee"),
                            results.getDouble("ProcessingFee"),
                            results.getBoolean("isFinance")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all contracts: ", e);
        }

        return contracts;
    }


    /**
     * Get Sales contract without Customer Class
     * @return
     */
    public List<SalesContract> getSalesContractsFromDatabase2() {
        List<SalesContract> contracts= new ArrayList<>();

        String query = Queries.selectSalesContract();

        try (Connection connection = contractDataSource.getConnection();
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

    /**
     *  Get all contracts
     * @return
     */

    /**
     * Add a Lease Contract
     */

    public void processInsertLeaseContract(LeaseContract lease, Customer customer, Vehicle vehicle){

        try(Connection connection = contractDataSource.getConnection();){

            //insert vehicle detail
            try(PreparedStatement query = connection.prepareStatement(
                    Queries.insertNewLeaseContract()))
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


            //insert customer detail
            try(PreparedStatement query = connection.prepareStatement(
                    Queries.insertNewCustomer()))
            {
                // Splitting the full name into first and last name
                String fullName = customer.getName(); // "Tashi Thompson"
                String[] nameParts = fullName.split(" ", 2); // Split on the first space
                String firstName = nameParts[0]; // "Tashi"
                // String lastName = nameParts.length > 1 ? nameParts[1] : ""; // "Thompson" or empty if no last name
                String lastName = nameParts[1] ;



                // Set the first and last name in the prepared statement
                query.getGeneratedKeys();
                query.setString(1, firstName);
                query.setString(2, lastName);
                query.setString(3, customer.getEmail());

                int rows = query.executeUpdate();


            }

            //insert lease details
            try(PreparedStatement query = connection.prepareStatement(
                    Queries.insertNewLeaseContract()))
            {

                //Contract ID should be autogenerated when adding to data base?
                query.getGeneratedKeys();
                query.setString(2, lease.getDate());
                query.getGeneratedKeys();
                //can customer be auto generated when insert to the data base
                query.setInt(4, lease.getVehicleSold().getVin());
                query.setDouble(5,lease.getExpectEndingValue());
                query.setDouble(6, lease.getLeaseFee());
                query.setDouble(7, lease.getTotalPrice());
                query.setDouble(8, lease.getMonthlyPayment());
                int rows = query.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void processInsertNewContract(Contract contract, Customer customer, Vehicle vehicle) {
        try (Connection connection = contractDataSource.getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            // Insert vehicle details
            String insertVehicleSQL = "INSERT INTO Vehicles (vin, year, make, model, vehicle_type, color, odometer, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement vehicleStmt = connection.prepareStatement(insertVehicleSQL)) {
                vehicleStmt.setInt(1, vehicle.getVin());
                vehicleStmt.setInt(2, vehicle.getYear());
                vehicleStmt.setString(3, vehicle.getMake());
                vehicleStmt.setString(4, vehicle.getModel());
                vehicleStmt.setString(5, vehicle.getVehicleType());
                vehicleStmt.setString(6, vehicle.getColor());
                vehicleStmt.setInt(7, vehicle.getOdometer());
                vehicleStmt.setDouble(8, vehicle.getPrice());

                vehicleStmt.executeUpdate();
            }

            // Insert customer details
            String insertCustomerSQL = "INSERT INTO Customers (first_name, last_name, email) VALUES (?, ?, ?)";
            try (PreparedStatement customerStmt = connection.prepareStatement(insertCustomerSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                String[] nameParts = customer.getName().split(" ", 2);
                String firstName = nameParts[0];
                String lastName = nameParts.length > 1 ? nameParts[1] : "";

                customerStmt.setString(1, firstName);
                customerStmt.setString(2, lastName);
                customerStmt.setString(3, customer.getEmail());

                customerStmt.executeUpdate();
                ResultSet generatedKeys = customerStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int customerId = generatedKeys.getInt(1);
                    System.out.println("Inserted customer ID: " + customerId);
                }
            }

            // Insert contract details
            try (PreparedStatement contractStmt = connection.prepareStatement(Queries.insertNewLeaseContract())) {
                contractStmt.setString(1, contract.getDate());
                contractStmt.setInt(2, vehicle.getVin());
                // Assuming customer ID is generated and retrieved earlier
                contractStmt.setInt(3, contract.getCustomerId());
                if (contract instanceof LeaseContract) {
                    LeaseContract lease = (LeaseContract) contract;
                    contractStmt.setDouble(4, lease.getExpectEndingValue());
                    contractStmt.setDouble(5, lease.getLeaseFee());
                    contractStmt.setDouble(6, lease.getTotalPrice());
                    contractStmt.setDouble(7, lease.getMonthlyPayment());
                }

                contractStmt.executeUpdate();
            }

            connection.commit(); // Commit transaction if successful
        } catch (SQLException e) {

            throw new RuntimeException("Database operation failed: " + e.getMessage(), e);
        }
    }

}
