package org.example;

import org.example.database.DataBaseConnector;
import org.example.exception.InsufficientFundsException;
import org.example.exception.InvalidTransactionException;
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


    private void createAccountInDatabase() {

        dataBaseConnector = new DataBaseConnector();

        List<String> sql = new ArrayList<>();
        sql.add(Integer.toString(accountNumber));
        sql.add(accountHolderName);
        sql.add(Float.toString(balance));

        dataBaseConnector.insert("ACCOUNTS", columns, sql);
    }

    @Nullable
    public static Account getOneAccount(int accountNumber) throws SQLException, ClassNotFoundException {
        dataBaseConnector = new DataBaseConnector();

        try {
            ResultSet resultSet = dataBaseConnector.find("ACCOUNTS", "accountNumber", Integer.toString(accountNumber));

            if (resultSet.next()) {
                return new Account(
                        resultSet.getInt("accountNumber"),
                        resultSet.getString("accountHolderName"),
                        resultSet.getFloat("balance")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static List<Account> getAllAccounts(){
        dataBaseConnector = new DataBaseConnector();

        List<Account > listOfAccounts = new ArrayList<>();

        try {
            ResultSet resultSet = dataBaseConnector.getAll("ACCOUNTS");

            while (resultSet.next()) {
                listOfAccounts.add(new Account(
                        resultSet.getInt("accountNumber"),
                        resultSet.getString("accountHolderName"),
                        resultSet.getFloat("balance")
                ));
            }

            return listOfAccounts;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void deleteAccountFromDatabase(int accountNumber){
        dataBaseConnector = new DataBaseConnector();

        dataBaseConnector.delete("ACCOUNTS", "accountNumber" , Integer.toString(accountNumber));
    }

    public static void showOneAccount(int accountNumber) throws SQLException, ClassNotFoundException {
        dataBaseConnector = new DataBaseConnector();

        Account account = getOneAccount(accountNumber);

        if (account != null) {
            System.out.println("[ number: " + account.accountNumber +
                    "\t  owner: " + account.accountHolderName +
                    "\t balance: " + account.balance + " ]");
        }
    }

    public static void showAllAccounts() {
        List<Account> accountList = getAllAccounts();
        accountList.stream()
                .map(account -> "number: " + account.accountNumber +
                        "\t\t owner: " + account.accountHolderName +
                        "\t\t balance: " + account.balance)
                .forEach(System.out::println);
    }

    public void deposit(float amount) throws SQLException {
        if (amount < 0)
            throw new IllegalArgumentException("amount of deposit can not be negative");
        else {
            dataBaseConnector = new DataBaseConnector();

            dataBaseConnector.update("ACCOUNTS", "ACCOUNTNUMBER", Integer.toString(accountNumber),
                    "BALANCE", Float.toString(balance + amount));

            Transaction transaction = new Transaction(this,amount);
        }
    }

    public void withdraw(float amount) throws InsufficientFundsException, InvalidTransactionException, SQLException {
        if (amount > balance)
            throw new InsufficientFundsException("insufficient balance ");
        else if (amount < 0)
            throw new InvalidTransactionException("not valid amount ");
        else {
            dataBaseConnector = new DataBaseConnector();

            dataBaseConnector.update("ACCOUNTS", "ACCOUNTNUMBER", Integer.toString(accountNumber),
                    "BALANCE", Float.toString(balance - amount));

            Transaction transaction = new Transaction(this,amount);
        }
    }

    public int getAccountNumber() {
        return accountNumber;
    }
}
