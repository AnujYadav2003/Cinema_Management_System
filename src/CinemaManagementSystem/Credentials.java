package CinemaManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Credentials {
    private Connection connection;
    private Scanner scanner;
    private String role;
    private User user;

    public Credentials(Connection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
        this.user=new User(connection,scanner);
    }

    public void menu() {
        while (true) {
            System.out.println("\nWELCOME TO THE CINEMA MANAGEMENT SYSTEM:-");
            System.out.println("Credentials  Menu:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit Credentials Menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> register();
                case 2 -> login();

                case 3-> {
                    System.out.println("Exiting Credentials Menu.");
                    return;
                }
                default -> {
                    System.out.println("Invalid choice.");
                    System.out.println();
                }
            }
        }
    }

    public void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email ---(@gmail.com) :");
        String email = scanner.nextLine();
        System.out.print("Enter phone number (+91-XXXXXXXXXX) :");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter your role (admin/user): ");
        String role = scanner.nextLine().toLowerCase();

        String query = "INSERT INTO Users (username, email, phone_number, role) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setString(4, role);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("User registered successfully as " + role + ".");
                if (role.equals("admin")) {
                    Admin admin = new Admin(connection, scanner);
                    admin.menu();
                } else {
                    user.menu();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: (---@gmail.com) ");
        String email = scanner.nextLine();

        String query = "SELECT * FROM Users WHERE username=? AND email=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.role = resultSet.getString("role");
                System.out.println("Login successful. Welcome, " + username + "!");
                if (this.role.equals("admin")) {
                    Admin admin = new Admin(connection, scanner);
                    admin.menu();
                } else {
                    User user = new User(connection, scanner);
                    user.menu();
                }
            } else {
                System.out.println("Invalid username or email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}