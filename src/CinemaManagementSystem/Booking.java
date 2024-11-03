package CinemaManagementSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Booking {
    private Connection connection;
    private Scanner scanner;

    public Booking(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void menu() {
        System.out.println("\nBooking Menu:");
        System.out.println("1. Book Ticket");
        System.out.println("2. View Bookings");
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1 -> bookTicket();
            case 2 -> viewBookings();
            default -> System.out.println("Invalid choice.");
        }
    }

    public void bookTicket() {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter Movie ID: ");
        int movieId = scanner.nextInt();
        System.out.print("Enter Hall ID: ");
        int hallId = scanner.nextInt();
        System.out.print("Enter Number of Tickets: ");
        int numberOfTickets = scanner.nextInt();

        if (!doesUserExist(userId)) {
            System.out.println("User  ID does not exist.");
            return;
        }

        if (!doesMovieExist(movieId)) {
            System.out.println("Movie ID does not exist.");
            return;
        }

        if (!doesHallExist(hallId)) {
            System.out.println("Hall ID does not exist.");
            return;
        }

        String query = "INSERT INTO bookings (user_id, movie_id, hall_id, number_of_tickets) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, movieId);
            preparedStatement.setInt(3, hallId);
            preparedStatement.setInt(4, numberOfTickets);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Booking made successfully.");
            } else {
                System.out.println("Failed to make booking.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean doesUserExist(int userId) {
        String query = "SELECT COUNT(*) FROM Users WHERE user_id = ?";
        return doesEntityExist(query, userId);
    }

    private boolean doesMovieExist(int movieId) {
        String query = "SELECT COUNT(*) FROM Movies WHERE movie_id = ?";
        return doesEntityExist(query, movieId);
    }

    private boolean doesHallExist(int hallId) {
        String query = "SELECT COUNT(*) FROM CinemaHalls WHERE hall_id = ?";
        return doesEntityExist(query, hallId);
    }

    private boolean doesEntityExist(String query, int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void viewBookings() {
        String query = "SELECT b.booking_id, b.user_id, b.movie_id, b.hall_id, b.number_of_tickets, u.username, m.movie_name, h.hall_name " +
                "FROM bookings b " +
                "JOIN Users u ON b.user_id = u.user_id " +
                "JOIN Movies m ON b.movie_id = m.movie_id " +
                "JOIN CinemaHalls h ON b.hall_id = h.hall_id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {


            System.out.println("\nBookings List:");
            System.out.println("+------------+----------+----------+----------+-------------------+--------------------+-------------------------+----------------------+");
            System.out.printf("| %-10s | %-8s | %-7s | %-8s | %-17s | %-19s| %-24.5s | %-19s |\n",
                    "Booking ID", "User ID", "Movie ID", "Hall ID", "Number of Tickets", "Username", "Movie Name", "Hall Name");
            System.out.println("+------------+----------+----------+----------+-------------------+--------------------+-------------------------+----------------------+");


            while (resultSet.next()) {
                int bookingId = resultSet.getInt("booking_id");
                int userId = resultSet.getInt("user_id");
                int movieId = resultSet.getInt("movie_id");
                int hallId = resultSet.getInt("hall_id");
                int numberOfTickets = resultSet.getInt("number_of_tickets");
                String username = resultSet.getString("username");
                String movieName = resultSet.getString("movie_name");
                String hallName = resultSet.getString("hall_name");


                System.out.printf("| %-10d| %-8d | %-7d | %-8d | %-17s | %-19s | %-24.5s | %-19s |\n",
                        bookingId, userId, movieId, hallId, numberOfTickets, username, movieName, hallName);

                System.out.println("+------------+----------+----------+----------+-------------------+--------------------+-------------------------+----------------------+");

            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

}