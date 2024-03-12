package org.example;


import org.example.database.DataBaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Transaction {
    private int transactionNumber;
    private Account account;
    private float amount;

    private static final List<String> columns = Arrays.asList("TRANSACTIONNUMBER", "ACCOUNT", "AMOUNT");

    private static DataBaseConnector dataBaseConnector;

    public Transaction(Account account, float amount) throws SQLException {

        transactionNumber = lastTransactionNumber() + 1;
        this.account = account;
        this.amount = amount;
        createTransactionInDatabase();
    }


    private void createTransactionInDatabase() {

        dataBaseConnector = new DataBaseConnector();

        List<String> sql = new ArrayList<>();
        sql.add(Integer.toString(transactionNumber));
        sql.add(Integer.toString(account.getAccountNumber()));
        sql.add(Float.toString(amount));

        dataBaseConnector.insert("TRANSACTIONS", columns, sql);
    }

    private int lastTransactionNumber() throws SQLException {
        dataBaseConnector = new DataBaseConnector();

        ResultSet resultSet = dataBaseConnector.maximum("TRANSACTIONS", "TRANSACTIONNUMBER");
        if (resultSet.next()){
            return resultSet.getInt("MAX(TRANSACTIONNUMBER)");
        }
        return 0;
    }

}
