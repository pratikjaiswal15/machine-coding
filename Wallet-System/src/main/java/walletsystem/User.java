package walletsystem;

public class User {
    private String name;
    private Wallet wallet;

    public User(String name) {
        this.name = name;
        this.wallet = new Wallet();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
