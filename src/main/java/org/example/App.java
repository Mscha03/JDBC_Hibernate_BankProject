package org.example;


import org.example.exception.InsufficientFundsException;
import org.example.exception.InvalidTransactionException;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InsufficientFundsException, InvalidTransactionException {

        Account.showOneAccount(9);
        Account account = Account.getOneAccount(9);
        account.deposit(500);
        Account.showOneAccount(9);

        Account.showOneAccount(3);
        Account account1 = Account.getOneAccount(3);
        account1.withdraw(700);
        Account.showOneAccount(3);
    }
}
