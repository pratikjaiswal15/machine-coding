package walletsystem;

import java.time.LocalDateTime;

public class Transaction {
    private double amount;
    private LocalDateTime timestamp;
    private TransactionType transactionType;

    public Transaction(double amount, TransactionType transactionType) {
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
