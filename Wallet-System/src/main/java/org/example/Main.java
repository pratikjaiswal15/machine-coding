package org.example;

import walletsystem.User;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        User user1 = new User("Pratik");
        User user2 = new User("Shrutika");

        user1.getWallet().loadMoney(1000);
        user2.getWallet().loadMoney(100);

        user1.getWallet().senMoney(200, user2.getWallet());

        System.out.println(user1.getWallet().getBalance());
        System.out.println(user2.getWallet().getBalance());
    }
}