
import java.util.*;
import java.io.*;

class InsufficientFundsException extends Exception {
    InsufficientFundsException(String message) {
        super(message);
    }
}

public class ATMWithdrawal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FileReader reader = null;
        int balance = 5000;

        try {
            reader = new FileReader("Task 1/card-reader.txt");
            reader.close();
            reader = null;

            System.out.print("Enter account number: ");
            int account = sc.nextInt();

            System.out.print("Enter withdrawal amount: ");
            int amount = sc.nextInt();

            if (amount < 0) {
                throw new IllegalArgumentException(
                    "Amount cannot be negative"
                );
            }

            if (amount == 0) {
                throw new IllegalArgumentException(
                    "Amount must be greater than zero"
                );
            }

            if (amount > balance) {
                throw new InsufficientFundsException(
                    "Insufficient account balance"
                );
            }

            balance = balance - amount;

            System.out.println("Account Number: " + account);
            System.out.println("Withdrawal successful");
            System.out.println("Remaining Balance: " + balance);

        } catch (FileNotFoundException e) {
            System.out.println("Error: Card reader file is missing");

        } catch (InputMismatchException | IllegalStateException e) {
            System.out.println("Error: Enter valid numeric input");

        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("File closing error");
                }
            }

            sc.close();
            System.out.println("ATM transaction completed");
        }
    }
}