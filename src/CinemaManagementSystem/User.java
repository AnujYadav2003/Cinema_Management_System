package CinemaManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner scanner;
    private Booking booking;
    private Payment payment;
    private Movie movie;
    private CinemaHall cinemaHall;
    private String role;

    public User(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
        this.booking = new Booking(connection, scanner);
        this.payment = new Payment(connection, scanner);
        this.movie = new Movie(connection, scanner);
        this.cinemaHall = new CinemaHall(connection, scanner);
    }

    public void menu() {
        while (true) {
            System.out.println("\nWELCOME TO THE CINEMA MANAGEMENT SYSTEM:-");
            System.out.println("User  Menu:------------------");
            System.out.println("1. Book Ticket");
            System.out.println("2. View My Bookings");
            System.out.println("3. View All Movies");
            System.out.println("4. View All Cinema Halls");
            System.out.println("5. Make Payment");
            System.out.println("6. Exit User Menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> booking.bookTicket();
                case 2 -> booking.viewBookings();
                case 3 -> movie.viewMovies();
                case 4 -> cinemaHall.viewCinemaHalls();
                case 5 -> payment.makePayment();
                case 6 -> {
                    System.out.println("THANK YOU FOR USING OUR CINEMA MANAGEMENT SYSTEM");
                    return;
                }
                default -> {System.out.println("Invalid choice.");
                System.out.println();}
            }
        }
    }

//    public void register() {
//        System.out.print("Enter username: ");
//        String username = scanner.nextLine();
//        System.out.print("Enter email: (---@gmail.com) ");
//        String email = scanner.nextLine();
//        System.out.print("Enter phone number (+91-XXXXXXXXXX) : ");
//        String phoneNumber = scanner.nextLine();
//        System.out.print("Enter your role (admin/user): ");
//        String role = scanner.nextLine().toLowerCase();
//
//        String query = "INSERT INTO Users (username, email, phone_number, role) VALUES (?, ?, ?, ?)";
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, email);
//            preparedStatement.setString(3, phoneNumber);
//            preparedStatement.setString(4, role);
//
//            int result = preparedStatement.executeUpdate();
//            if (result > 0) {
//                System.out.println("User  registered successfully as " + role + ".");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void login() {
//        System.out.print("Enter username: ");
//        String username = scanner.nextLine();
//        System.out.print("Enter email (---@gmail.com) : ");
//        String email = scanner.nextLine();
//
//        String query = "SELECT * FROM Users WHERE username=? AND email=?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, email);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                role = resultSet.getString("role"); // Store the role of the logged-in user
//                System.out.println("Login successful. Welcome, " + username + "!");
//                if (role.equals("admin")) {
//                    Admin admin = new Admin(connection, scanner);
//                    admin.menu(); // Navigate to admin menu
//                } else {
//                    menu(); // Navigate to user functionalities
//                }
//            } else {
//                System.out.println("Invalid username or email.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void viewAllUsers() {

        String query = "SELECT * FROM Users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("+---------+----------------+-----------------------+----------------+----------+");
            System.out.printf("| %-7s | %-14s | %-21s | %-15s | %-8s |\n", "User ID", "Username", "Email", "Phone", "Role");
            System.out.println("+---------+----------------+-----------------------+----------------+----------+");

            // Print each user record
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone_number");
                String role = resultSet.getString("role");

                // Format each line with proper alignment
                System.out.printf("| %-7d | %-14s | %-21s | %-15s | %-8s |\n", userId, username, email, phone, role);
                System.out.println("+---------+----------------+-----------------------+----------------+----------+");
            }



        } catch (SQLException e) {
            System.err.println("An error occurred while retrieving user data: " + e.getMessage());
        }
    }

}