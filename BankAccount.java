package mobilepaymentapplication;


public class BankAccount {
    private int userId;
    private String accountHolderName;
    private String accountNumber;
    private String routingNumber;

    public BankAccount(int userId, String accountHolderName, String accountNumber, String routingNumber) {
        this.userId = userId;
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
    }

    public int getUserId() {
        return userId;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }
}