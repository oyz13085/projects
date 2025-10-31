import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {


        double balance = 174.56;
        boolean isRunning = true;
        int choice;

        while (isRunning) {
            System.out.println("Welcome to Banking System");
            System.out.println("1. Show Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Please enter your choice (1-4): ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> showBalance(balance);
                case 2 -> balance += deposit();
                case 3 -> balance -= withdraw(balance);
                case 4 -> isRunning = false;
                default -> System.out.println("Invalid choice");
            }
        }

        System.out.println("Thank you for using Banking System");



        sc.close();
    }

    static void showBalance(double balance) {
        System.out.printf("Your balance is: RM%.2f\n" , balance);
        System.out.println();
    }

    static double deposit() {
        double amount;

        System.out.println("Please enter the amount you want to deposit: ");
        System.out.print("RM");
        amount = sc.nextDouble();

        if (amount < 0) {
            System.out.println("Invalid amount\n");
            return 0;
        }else{
            return amount;
        }
    }

    static double withdraw(double balance) {
        double amount;
        System.out.println("Please enter the amount you want to withdraw: ");
        System.out.print("RM");
        amount = sc.nextDouble();
        if (amount < 0) {
            System.out.println("Invalid amount\n");
            return 0;
        }else if(amount > balance){
            System.out.println("Invalid amount\n");
            return 0;
        } else{
            System.out.println(" ");
            return amount;
        }
    }
}