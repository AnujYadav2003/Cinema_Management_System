 package CinemaManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class CinemaHall {
    private Connection connection;
    private Scanner scanner;

    public CinemaHall(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void menu() {
        System.out.println("\nCinema Hall Menu:");
        System.out.println("1. View Cinema Halls");
        System.out.println("2. Add Cinema Hall");
        System.out.println("3. Remove Cinema Hall");
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1 -> viewCinemaHalls();
            case 2 -> addCinemaHall();
            case 3 -> removeCinemaHall();
            default -> System.out.println("Invalid choice.");
        }
    }

    public void viewCinemaHalls() {
        String query = "SELECT * FROM CinemaHalls";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Print table header
            System.out.println("+--------------+-------------------+-------------+");
            System.out.printf("| %-12s | %-17s | %-11s |\n", "Hall ID", "Hall Name", "Capacity");
            System.out.println("+--------------+-------------------+-------------+");

            // Print each cinema hall record
            while (resultSet.next()) {
                int hallId = resultSet.getInt("hall_id");
                String hallName = resultSet.getString("hall_name");
                int capacity = resultSet.getInt("capacity");

                // Format each line with proper alignment
                System.out.printf("| %-12d | %-17s | %-11d |\n", hallId, hallName, capacity);
            }


            System.out.println("+--------------+-------------------+-------------+");

        } catch (SQLException e) {
            System.err.println("An error occurred while retrieving cinema hall data: " + e.getMessage());
        }
    }


    public void addCinemaHall() {
        System.out.print("Enter hall name: ");
        String hallName = scanner.nextLine();
        System.out.print("Enter capacity: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        String query = "INSERT INTO CinemaHalls (hall_name, capacity) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, hallName);
            preparedStatement.setInt(2, capacity);
            preparedStatement.executeUpdate();
            System.out.println("Cinema Hall added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCinemaHall() {
        System.out.print("Enter Hall ID to remove: ");
        int hallId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        String query = "DELETE FROM CinemaHalls WHERE hall_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, hallId);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Cinema Hall removed successfully.");
            } else {
                System.out.println("No Cinema Hall found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}