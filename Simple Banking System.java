import java.util.*;

abstract class BankAcc {
    private int accNo;
    private String name;
    private double balance;
    private int pin;  // secure pin

    BankAcc(int accNo, String name, double balance, int pin) {
        this.accNo = accNo;
        this.name = name;
        this.balance = balance;
        this.pin = pin;
    }

    // Getters
    public int getAccNo() { return accNo; }
    public String getName() { return name; }
    public double getBalance() { return balance; }

    // Setters
    protected void setBalance(double balance) { this.balance = balance; }
    protected void setName(String name) { this.name = name; }

    // PIN handling
    public boolean verifyPin(int enteredPin) {
        return this.pin == enteredPin;
    }

    public void changePin(int oldPin, int newPin) throws Exception {
        if (this.pin != oldPin) {
            throw new Exception("Old PIN is incorrect!");
        }
        this.pin = newPin;
        System.out.println("PIN changed successfully.");
    }

    public void resetPin(int newPin) { // for admin
        this.pin = newPin;
    }

    // Transactions
    public void deposit(double amt) {
        balance += amt;
        System.out.println("Deposited: " + amt);
    }

    public abstract void withdraw(double amt) throws Exception;

    // Display info
    public void showBalance() {
        System.out.println("Account No: " + accNo + ", Balance: " + balance);
    }

    public void showDetails() {
        System.out.println("AccNo: " + accNo + ", Name: " + name + ", Balance: " + balance);
    }
}

class SavingsAcc extends BankAcc {
    private double minBal = 1000;

    SavingsAcc(int accNo, String name, double balance, int pin) {
        super(accNo, name, balance, pin);
    }

    @Override
    public void withdraw(double amt) throws Exception {
        if (getBalance() - amt < minBal) {
            throw new Exception("Can't withdraw. Min balance: " + minBal);
        }
        setBalance(getBalance() - amt);
        System.out.println("Withdrawn: " + amt);
    }
}

class CurrentAcc extends BankAcc {
    private double overdraftLimit = 2000;

    CurrentAcc(int accNo, String name, double balance, int pin) {
        super(accNo, name, balance, pin);
    }

    @Override
    public void withdraw(double amt) throws Exception {
        if (getBalance() - amt < -overdraftLimit) {
            throw new Exception("Can't withdraw. Overdraft limit exceeded.");
        }
        setBalance(getBalance() - amt);
        System.out.println("Withdrawn: " + amt);
    }
}

public class BankingSystem {
    static Scanner sc = new Scanner(System.in);
    static HashMap<Integer, BankAcc> accounts = new HashMap<>();

    // Admin credentials
    static String adminUser = "Loga";
    static String adminPass = "admin@1234";

    public static void main(String[] args) {
        // Sample accounts
        accounts.put(101, new SavingsAcc(101, "Arjun", 5000, 1111));
        accounts.put(102, new CurrentAcc(102, "Meera", 8000, 2222));

        while (true) {
            System.out.println("\n--- BANK MENU ---");
            System.out.println("1. Customer Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> customerMenu();
                case 2 -> adminMenu();
                case 3 -> {
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void customerMenu() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        BankAcc acc = accounts.get(accNo);

        if (acc == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();
        if (!acc.verifyPin(pin)) {
            System.out.println("Invalid PIN!");
            return;
        }

        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Change PIN");
            System.out.println("5. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter amount: ");
                        double d = sc.nextDouble();
                        acc.deposit(d);
                    }
                    case 2 -> {
                        System.out.print("Enter amount: ");
                        double w = sc.nextDouble();
                        acc.withdraw(w);
                    }
                    case 3 -> acc.showBalance();
                    case 4 -> {
                        System.out.print("Enter old PIN: ");
                        int oldPin = sc.nextInt();
                        System.out.print("Enter new PIN: ");
                        int newPin = sc.nextInt();
                        acc.changePin(oldPin, newPin);
                    }
                    case 5 -> {
                        System.out.println("Logging out...");
                        return;
                    }
                    default -> System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static void adminMenu() {
        System.out.print("Enter Admin Username: ");
        String user = sc.next();
        System.out.print("Enter Admin Password: ");
        String pass = sc.next();

        if (!(user.equals(adminUser) && pass.equals(adminPass))) {
            System.out.println("Invalid Admin Credentials!");
            return;
        }

        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Show All Accounts");
            System.out.println("2. Reset User PIN");
            System.out.println("3. Create New Account");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.println("All Account Details:");
                    for (BankAcc acc : accounts.values()) {
                        acc.showDetails();
                    }
                }
                case 2 -> {
                    System.out.print("Enter Account No: ");
                    int accNo = sc.nextInt();
                    BankAcc acc = accounts.get(accNo);
                    if (acc == null) {
                        System.out.println("Account not found!");
                        break;
                    }
                    System.out.print("Enter New PIN: ");
                    int newPin = sc.nextInt();
                    acc.resetPin(newPin);
                    System.out.println("PIN reset by Admin successfully.");
                }
                case 3 -> {
                    System.out.print("Enter Account No: ");
                    int newAccNo = sc.nextInt();
                    if (accounts.containsKey(newAccNo)) {
                        System.out.println("Account with this number already exists!");
                        break;
                    }
                    System.out.print("Enter Customer Name: ");
                    String newName = sc.next();
                    System.out.print("Enter Initial Balance: ");
                    double initBal = sc.nextDouble();
                    System.out.print("Set 4-digit PIN: ");
                    int newPin = sc.nextInt();

                    System.out.print("Choose Account Type (1 = Savings, 2 = Current): ");
                    int type = sc.nextInt();

                    BankAcc newAcc;
                    if (type == 1) {
                        newAcc = new SavingsAcc(newAccNo, newName, initBal, newPin);
                    } else if (type == 2) {
                        newAcc = new CurrentAcc(newAccNo, newName, initBal, newPin);
                    } else {
                        System.out.println("Invalid account type!");
                        break;
                    }

                    accounts.put(newAccNo, newAcc);
                    System.out.println("New account created successfully for " + newName);
                }
                case 4 -> {
                    System.out.println("Logging out Admin...");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }
}
