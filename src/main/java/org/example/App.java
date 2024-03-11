package org.example;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

//        Account account = new Account(12, "Ashkan", 10000);
        Account account2 = Account.readAccountFromDatabase(2);
        account2.getAccountHolderName();


    }
}
