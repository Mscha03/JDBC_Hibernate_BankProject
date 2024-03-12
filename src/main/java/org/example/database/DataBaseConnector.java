package org.example.database;


import java.sql.*;
import java.util.List;

public class DataBaseConnector {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:~/test";
    private static final String USER = "sa";
    private static final String PASS = "";

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;


    public void insert(String tableName, List<String> columns, List<String> information) {


        String query = "INSERT INTO " + tableName + "( " + String.join(", ", columns) + ") " +
                "SELECT * FROM (SELECT " + String.join(", ", information) + " ) AS tmp " +
                "WHERE NOT EXISTS ( SELECT * FROM " + tableName + " WHERE " +  tableName + "." + columns.get(0) + " = " +information.get(0) + ") LIMIT 1;";

        try {
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(URL, USER, PASS);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public  ResultSet find(String tableName, String primaryName, String primaryValue) throws SQLException {

        String query = "SELECT * FROM " + tableName + " WHERE " + primaryName + " = " + primaryValue + ";";

        try {

            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(URL, USER, PASS);
            statement = connection.createStatement();
            return statement.executeQuery(query);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public ResultSet getAll(String tableName){
        String query = "SELECT * FROM " + tableName + ";";

        try {

            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(URL, USER, PASS);
            statement = connection.createStatement();
            return statement.executeQuery(query);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String tableName, String primaryName, String primaryValue) {

        String query = "DELETE FROM " + tableName + " WHERE " + primaryName + " = " + primaryValue + ";";

        try {
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(URL, USER, PASS);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String tableName, String primaryName, String primaryValue, String editedVariable, String newValue) throws SQLException {

            String query = "UPDATE " +tableName+ " SET " + editedVariable + " = " + newValue + " WHERE " + primaryName + " = " + primaryValue + ";";

        try {
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(URL, USER, PASS);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet maximum(String tableName, String primaryName){
        String query = "SELECT MAX(" + primaryName + ") FROM " + tableName + ";";

        try {

            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(URL, USER, PASS);
            statement = connection.createStatement();
            return statement.executeQuery(query);


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
