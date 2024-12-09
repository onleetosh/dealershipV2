package com.onleetosh.pluralsight.datamanager;

import com.onleetosh.pluralsight.contract.Contract;
import com.onleetosh.pluralsight.contract.LeaseContract;
import com.onleetosh.pluralsight.contract.SalesContract;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;
import java.util.List;

import static com.onleetosh.pluralsight.datamanager.DataManager.processConfiguredDataSource;

public class ContractRequest {


    public static void getContracts(String[] args){
        try (BasicDataSource dataSource = processConfiguredDataSource(args)) {
            DataManager dm = new DataManager(dataSource);
            List<Contract> contracts = dm.getAllContractsFromDatabase();
            if (!contracts.isEmpty()) {
                for (Contract sales : contracts) {
                    System.out.println(sales);
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


}
