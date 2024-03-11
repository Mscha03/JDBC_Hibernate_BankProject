package org.example;

import org.example.database.DataBaseConnector;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Account {
    private  int accountNumber;
    private  String accountHolderName;
    private  float balance;

    private static final List<String> columns = Arrays.asList("ACCOUNTNUMBER", "ACCOUNTHOLDERNAME", "BALANCE");

    private static DataBaseConnector dataBaseConnector;

    public Account(int accountNumber, String accountHolderName, float balance) throws ClassNotFoundException {
        this.accountNumber = accountNumber;
        this.accountHolderName = "'" + accountHolderName + "'";
        this.balance = balance;

        createAccountInDatabase();
    }


    public void createAccountInDatabase() {

        dataBaseConnector = new DataBaseConnector();

        List<String> sql = new ArrayList<>();
        sql.add(Integer.toString(accountNumber));
        sql.add(accountHolderName);
        sql.add(Float.toString(balance));

        dataBaseConnector.insert("ACCOUNTS", columns, sql);

        System.out.println(sql);
    }

    @Nullable
    public static Account readAccountFromDatabase(int accountNumber) throws SQLException, ClassNotFoundException {
        dataBaseConnector = new DataBaseConnector();

        ResultSet resultSet = dataBaseConnector.read("ACCOUNTS", "accountNumber" , Integer.toString(accountNumber));

        if (resultSet.next()) {
            return new Account(
                    resultSet.getInt("accountNumber"),
                    resultSet.getString("accountHolderName"),
                    resultSet.getFloat("balance")
            );
        }
        return null;
    }

    public void getAccountHolderName() {
        System.out.println(accountHolderName);
    }
}
