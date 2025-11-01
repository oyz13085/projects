package db;

import constants.CommonConstants;

import java.sql.*;

import java.util.Scanner;

public class myJDBC {

    //register new user into database
    public static boolean register(String username, String password) {
        try {
            //first check if username exists in the db or not
            if (!checkUser(username)) {
                //connect to db
                Connection connection = DriverManager.getConnection(
                        CommonConstants.DB_URL,
                        CommonConstants.DB_USER,
                        CommonConstants.DB_PASSWORD);

                //create insert query
                PreparedStatement insertUser = connection.prepareStatement(
                        "INSERT INTO "  + CommonConstants.DB_USERS_TABLE_NAME + "(username,password)"+
                        "VALUES (?,?);"
                );

                //insert parameters
                insertUser.setString(1, username);
                insertUser.setString(2, password);

                //update db with new user
                insertUser.executeUpdate();
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;

    }

    //check if username already exist in db or not
    public static boolean checkUser(String username){
        try{
            Connection connection = DriverManager.getConnection(
                    CommonConstants.DB_URL,
                    CommonConstants.DB_USER,
                    CommonConstants.DB_PASSWORD);
            PreparedStatement checkUserExists = connection.prepareStatement(
                    "SELECT * FROM " + CommonConstants.DB_USERS_TABLE_NAME + " WHERE USERNAME = ?"
            );
            //1 means the first ? and username will replace the first ?
            checkUserExists.setString(1,username);

            ResultSet resultSet = checkUserExists.executeQuery();

            //check to see if the result set is empty
            if(!resultSet.isBeforeFirst()){
                return false;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return true;
    }

    //validate login credentials by checking if username/password exists in database
    public static boolean validateLogin(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(
                    CommonConstants.DB_URL,
                    CommonConstants.DB_USER,
                    CommonConstants.DB_PASSWORD);

            //create select query
            PreparedStatement validateUser = connection.prepareStatement(
                    "SELECT * FROM " + CommonConstants.DB_USERS_TABLE_NAME +
                            " WHERE USERNAME = ? AND PASSWORD = ?"
            );

            validateUser.setString(1, username);
            validateUser.setString(2, password);

            ResultSet resultSet = validateUser.executeQuery();

            if(!resultSet.isBeforeFirst()){
                return false;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    //get the amount of user
    public static double getAmount(String username) {
        try{
            Connection connection = DriverManager.getConnection(
                    CommonConstants.DB_URL,
                    CommonConstants.DB_USER,
                    CommonConstants.DB_PASSWORD);

            PreparedStatement findAmount = connection.prepareStatement(
                    "SELECT amount FROM "+CommonConstants.DB_USERS_TABLE_NAME+" WHERE USERNAME = ?"
            );

            findAmount.setString(1, username);
            ResultSet resultSet = findAmount.executeQuery();
            if(!resultSet.isBeforeFirst()){
                return 0;
            }
            resultSet.next();
            return resultSet.getDouble(1);


        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;

    }

    //deposit and update amount
    public static double depositAmount(String username) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount you want to deposit: ");
        double amount = scanner.nextDouble();
        try{
            Connection connection = DriverManager.getConnection(
                    CommonConstants.DB_URL,
                    CommonConstants.DB_USER,
                    CommonConstants.DB_PASSWORD);

            PreparedStatement deposit = connection.prepareStatement("UPDATE " + CommonConstants.DB_USERS_TABLE_NAME +
                    " SET amount = amount + ?" +
                    " WHERE username = ?");

            deposit.setDouble(1, amount);
            deposit.setString(2, username);
            deposit.executeUpdate();

            PreparedStatement getdeposit = connection.prepareStatement(
                    "SELECT amount FROM "+ CommonConstants.DB_USERS_TABLE_NAME + " WHERE username = ?");

            getdeposit.setString(1, username);

            ResultSet resultSet = getdeposit.executeQuery();
            if(!resultSet.isBeforeFirst()){
                return 0;
            }
            resultSet.next();
            return resultSet.getDouble(1);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static double withdrawAmount(String username) {
        try{
            Scanner scanner = new Scanner(System.in);
            double amount = 0;
            while(true){
                System.out.print("Enter the amount you want to withdraw: ");
                amount = scanner.nextDouble();
                if(amount < 0 || amount > getAmount(username)){
                    System.out.println("Invalid amount");
                }else{
                    break;
                }
            };

            Connection connection = DriverManager.getConnection(
                    CommonConstants.DB_URL,
                    CommonConstants.DB_USER,
                    CommonConstants.DB_PASSWORD
            );

            PreparedStatement withdraw = connection.prepareStatement("UPDATE " + CommonConstants.DB_USERS_TABLE_NAME +
                    " SET amount = amount - ?" +
                    " WHERE username = ?");

            withdraw.setDouble(1,amount);
            withdraw.setString(2,username);
            withdraw.executeUpdate();

            PreparedStatement showWithdraw = connection.prepareStatement("SELECT amount FROM " + CommonConstants.DB_USERS_TABLE_NAME +
                    " WHERE username = ?");

            showWithdraw.setString(1, username);
            ResultSet resultSet = showWithdraw.executeQuery();
            if(!resultSet.isBeforeFirst()){
                return 0;
            }
            resultSet.next();
            return resultSet.getDouble(1);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;

    }

}
