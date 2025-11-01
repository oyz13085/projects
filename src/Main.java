import db.myJDBC;

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static myJDBC myJDBC = new myJDBC();
    static String user = "";

    public static void main(String[] args) {
        startup();
        System.out.println(" ");
        functions();


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

    static void startup(){
        boolean exitLoop = true;
        boolean login = false;
        while(exitLoop && !login){
            System.out.println("Welcome to Banking System!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1:
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();

                    System.out.print("Enter password: ");
                    String password = sc.nextLine();

                    if(myJDBC.validateLogin(username, password)){
                        System.out.println("Login successful! Welcome " + username);
                        login = true;
                        user = username;
                    }
                    else{
                        System.out.println("Invalid username or password!");
                    }

                    break;

                case 2:
                    System.out.print("Enter username: ");
                    username = sc.nextLine();

                    System.out.print("Enter password: ");
                    password = sc.nextLine();

                    if(myJDBC.register(username, password)){
                        System.out.println("Register successful!");
                    }else{
                        System.out.println("You have register before!");
                    }
                    break;

                case 3:
                    exitLoop = false;
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void functions(){
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
                case 1 -> System.out.printf("RM%.2f\n", myJDBC.getAmount(user));
                case 2 -> System.out.printf("You now have RM%.2f\n", myJDBC.depositAmount(user));
                case 3 -> System.out.printf("You now have RM%.2f\n", myJDBC.withdrawAmount(user));
                case 4 -> isRunning = false;
                default -> System.out.println("Invalid choice");
            }
        }

        System.out.println("Thank you for using Banking System");



        sc.close();
    }
}