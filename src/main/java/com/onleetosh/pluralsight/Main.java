package com.onleetosh.pluralsight;

import com.onleetosh.pluralsight.datamanager.*;
import com.onleetosh.pluralsight.util.UI;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

import static com.onleetosh.pluralsight.datamanager.DataManager.*;

public class Main {
    public static void main(String[] args) {

        try(BasicDataSource dataSource = processConfiguredDataSource(args)){
            DataManager dm = new DataManager(dataSource);
            UI ui = new UI(dm);
            ui.display(dataSource, args);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}