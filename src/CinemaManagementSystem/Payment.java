package CinemaManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Payment {
    private Connection connection;
    private Scanner scanner;

    public Payment(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void menu() {
        System.out.println("\nPayment Menu:");
        System.out.println("1. Make Payment");
        System.out.println("2. View Payments");
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> makePayment();
            case 2 -> viewPayments();
            default -> System.out.println("Invalid choice.");
        }
    }

    public void makePayment() {
        System.out.print("Enter Booking ID: ");
        int bookingId = scanner.nextInt();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();

        if (!doesBookingExist(bookingId)) {
            System.out.println("Booking ID does not exist.");
            return;
        }

        String query = "INSERT INTO payments (booking_id, amount) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookingId);
            preparedStatement.setDouble(2, amount);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Payment made successfully.");
            } else {
                System.out.println("Failed to make payment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean doesBookingExist(int bookingId) {
        String query = "SELECT COUNT(*) FROM bookings WHERE booking_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookingId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void viewPayments() {
        String query = "SELECT p.payment_id, p.booking_id, p.amount, u.username " +
                "FROM payments p " +
                "JOIN bookings b ON p.booking_id = b.booking_id " +
                "JOIN Users u ON b.user_id = u.user_id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("\nPayments List:");
            System.out.println("+------------+------------+---------+-------------------+");
            System.out.printf("| Payment ID | Booking ID | Amount  | Username          |\n");
            System.out.println("+------------+------------+---------+-------------------+");

            while (resultSet.next()) {
                int paymentId = resultSet.getInt("payment_id");
                int bookingId = resultSet.getInt("booking_id");
                double amount = resultSet.getDouble("amount");
                String username = resultSet.getString("username");

                System.out.printf("| %-9d | %-9d | $%-7.2f | %-18s |\n",
                        paymentId, bookingId, amount, username);
                System.out.println("+------------+------------+---------+-------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}