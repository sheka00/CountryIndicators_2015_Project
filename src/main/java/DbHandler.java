package com.company;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DbHandler {
    private static final String CON_STR = "jdbc:sqlite:CountryIndicators.db";
    private static DbHandler instance = null;
    private Connection connection;

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public void createTableCountryIndicators() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS CountryIndicators (\n"
                + "	countryName NVARCHAR(30) PRIMARY KEY,\n"
                + "	region NVARCHAR(30) NOT NULL,\n"
                + "	happinessRank INT NOT NULL,\n"
                + "	happinessScore FLOAT NOT NULL,\n"
                + "	standardError FLOAT NOT NULL,\n"
                + "	economy FLOAT NOT NULL,\n"
                + "	family FLOAT NOT NULL,\n"
                + "	health FLOAT NOT NULL,\n"
                + "	freedom FLOAT NOT NULL,\n"
                + "	trust FLOAT NOT NULL,\n"
                + "	generosity FLOAT NOT NULL,\n"
                + "	dystopiaResidual FLOAT NOT NULL\n"
                + ");";

        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addCountryIndicators(CountryIndicators countrIndi) {

        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO CountryIndicators(`countryName`, `region`, `happinessRank`," +
                        " `happinessScore`, `standardError`, `economy`," +
                        " `family`, `health`, `freedom`," +
                        " `trust`, `generosity`, `dystopiaResidual`) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setObject(1, countrIndi.countryName);
            statement.setObject(2, countrIndi.region);
            statement.setObject(3, countrIndi.happinessRank);
            statement.setObject(4, countrIndi.happinessScore);
            statement.setObject(5, countrIndi.standardError);
            statement.setObject(6, countrIndi.economy);
            statement.setObject(7, countrIndi.family);
            statement.setObject(8, countrIndi.health);
            statement.setObject(9, countrIndi.freedom);
            statement.setObject(10, countrIndi.trust);
            statement.setObject(11, countrIndi.generosity);
            statement.setObject(12, countrIndi.dystopiaResidual);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fillDatabase(List<CountryIndicators> countryIndicatorsList) {
        try {
            DbHandler dbHandler = DbHandler.getInstance();
            countryIndicatorsList.stream().forEach(count -> dbHandler.addCountryIndicators(count));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CountryIndicators> getAllCountryIndicators() {
        try (Statement statement = this.connection.createStatement()) {
            List<CountryIndicators> products = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CountryIndicators");
            while (resultSet.next()) {
                products.add(new CountryIndicators(
                        resultSet.getString("countryName"),
                        resultSet.getString("region"),
                        resultSet.getInt("happinessRank"),
                        resultSet.getFloat("happinessScore"),
                        resultSet.getFloat("standardError"),
                        resultSet.getFloat("economy"),
                        resultSet.getFloat("family"),
                        resultSet.getFloat("health"),
                        resultSet.getFloat("freedom"),
                        resultSet.getFloat("trust"),
                        resultSet.getFloat("generosity"),
                        resultSet.getFloat("dystopiaResidual")
                ));
            }

            return products;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
