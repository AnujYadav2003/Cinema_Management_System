package CinemaManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Movie {
    private Connection connection;
    private Scanner scanner;

    public Movie(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void menu() {
        System.out.println("\nMovie Menu:");
        System.out.println("1. View Movies");
        System.out.println("2. Add Movie");
        System.out.println("3. Remove Movie");
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1 -> viewMovies();
            case 2 -> addMovie();
            case 3 -> removeMovie();
            default -> System.out.println("Invalid choice.");
        }
    }

    public void viewMovies() {
        String query = "SELECT * FROM Movies";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Print table header
            System.out.println("+---------+----------------------------+------------------+--------------+");
            System.out.println("| Movie ID| Movie Name                 | Genre            | Duration     |");
            System.out.println("+---------+----------------------------+------------------+--------------+");

            // Print each movie record
            while (resultSet.next()) {
                int movieId = resultSet.getInt("movie_id");
                String movieName = resultSet.getString("movie_name");
                String genre = resultSet.getString("genre");
                int duration = resultSet.getInt("duration");
                System.out.printf("| %-7d | %-26s | %-16s | %-12d |\n", movieId, movieName, genre, duration);

            }

            // Print table footer
            System.out.println("+---------+----------------------------+------------------+--------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMovie() {
        System.out.print("Enter movie name: ");
        String movieName = scanner.nextLine ();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter duration (in minutes): ");
        int duration = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        String query = "INSERT INTO Movies (movie_name, genre, duration) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, movieName);
            preparedStatement.setString(2, genre);
            preparedStatement.setInt(3, duration);
            preparedStatement.executeUpdate();
            System.out.println("Movie added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMovie() {
        System.out.print("Enter Movie ID to remove: ");
        int movieId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        String query = "DELETE FROM Movies WHERE movie_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, movieId);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Movie removed successfully.");
            } else {
                System.out.println("No Movie found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}