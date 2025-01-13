package walletsystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Wallet {

    private double balance;
    private List<Transaction> transactionsHistory;

    public Wallet() {
        this.balance = 0;
        transactionsHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void loadMoney(double amount) {
        if(amount < 0) {
            throw new RuntimeException("Amount can't be smaller than 9");
        }
        balance += amount;
        transactionsHistory.add(new Transaction(amount, TransactionType.RECEIVE));
        System.out.println("Money successfully loaded into wallet" + amount);
    }

    public void senMoney(double amount, Wallet receiver) {
        if(amount < 0) {
            throw new RuntimeException("Amount can't be smaller than 0");
        }

        System.out.println("amount" + amount + "balance" + balance);
        if(balance < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        balance -= amount;
        receiver.receiveMoney(amount);
        transactionsHistory.add(new Transaction(amount, TransactionType.SEND ));
        System.out.println("Money sent successfully. Current balance" + balance);
    }

    private void receiveMoney(double amount) {
        balance += amount;
        transactionsHistory.add(new Transaction(amount, TransactionType.RECEIVE ));
    }

    public List<Transaction> getTransactionHistorySorted(String sortBy) {
        if ("amount".equalsIgnoreCase(sortBy)) {
            return transactionsHistory.stream()
                    .sorted(Comparator.comparingDouble(Transaction::getAmount))
                    .collect(Collectors.toList());
        } else if ("time".equalsIgnoreCase(sortBy)) {
            return transactionsHistory.stream()
                    .sorted(Comparator.comparing(Transaction::getTimestamp))
                    .collect(Collectors.toList());
        } else {
            return transactionsHistory;
        }
    }


    public List<Transaction> filterTransactionHistoryByType(TransactionType type) {
        return transactionsHistory.stream()
                .filter((transaction -> transaction.getTransactionType() == type))
                .collect(Collectors.toList());
    }

    public void printTransactionHistory(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

}
