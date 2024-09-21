import java.util.*;

// Class to represent the User's Bank Account
class BankAccount {
    private double balance; 

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {   
        if (amount > 0 && amount <= balance && isValidDenomination(amount)) {
            balance -= amount;
            System.out.println("Withdrew: " + amount);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private boolean isValidDenomination(double amount) {
        // Check if the amount can be broken down into valid denominations of 100, 500, 2000
        return amount % 100 == 0 && 
               (amount == 100 || amount == 500 || amount == 2000 || 
                amount == 1500 || amount == 2500 || amount == 3000 || 
                amount == 3500 || amount == 4000 || amount == 4500 || 
                amount == 5000 || amount == 5500 || amount == 6000 || 
                amount == 6500 || amount == 7000 || amount == 7500 || 
                amount == 8000 || amount == 8500 || amount == 9000 || 
                amount == 9500 || amount == 10000);
    }
}

// Functional Interface for ATM Operations
@FunctionalInterface
interface Consumer<T> {
    void accept(T t);
}

// Class to represent the ATM Machine
class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    private void performOperation(String prompt, Consumer<Double> operation) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        double amount = scanner.nextDouble();
        operation.accept(amount);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n***ATM Method Options***");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> System.out.println("Current balance: " + account.getBalance());
                case 2 -> performOperation("Enter amount to withdraw (valid denominations: 100, 500, 2000): ", account::withdraw);
                case 3 -> performOperation("Enter amount to deposit: ", account::deposit);
                
                case 4 -> {
                    exit = true;
                    System.out.println("Thank you for using the ATM.");
                }
                default -> System.out.println("please choose the correct choice");
            }
        }
        scanner.close();
    }
}

// Main class to run the ATM system
public class task3 {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000); // Initial balance
        ATM atm = new ATM(account);
        atm.start();
    }
}
