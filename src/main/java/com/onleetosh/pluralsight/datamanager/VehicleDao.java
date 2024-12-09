package com.onleetosh.pluralsight.datamanager;

import com.onleetosh.pluralsight.dealership.Vehicle;
import com.onleetosh.pluralsight.util.SQL;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {

    /**
     * get vehicle by color
     */
    public List<Vehicle> getVehicleFromDatabaseByColor(int dealershipId, String value, DataSource dataSource) {
        ArrayList<Vehicle> vehicle = new ArrayList<>();

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
}
