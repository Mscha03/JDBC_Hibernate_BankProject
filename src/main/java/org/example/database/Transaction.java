package org.example.database;

import org.example.Account;

public class Transaction {
    private int TransactionNumber;
    private Account account;
    private float amount;

    public Transaction(int transactionNumber, Account account, float amount) {
        TransactionNumber = transactionNumber;
        this.account = account;
        this.amount = amount;
    }


}
