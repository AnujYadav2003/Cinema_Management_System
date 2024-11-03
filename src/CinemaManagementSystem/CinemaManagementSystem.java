//package CinemaManagementSystem;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.InputMismatchException;
//import java.util.Scanner;
//
//public class CinemaManagementSystem {
//    private static Connection connect() {
//        Connection connection = null;
//        try {
//            String url = "jdbc:mysql://localhost:3306/cinema"; // Replace with your database URL
//            String user = "root"; // Replace with your database username
//            String password = "Anuj@1234"; // Replace with your database password
//            connection = DriverManager.getConnection(url, user, password);
//            System.out.println("Connected to the database successfully.");
//        } catch (SQLException e) {
//            System.out.println("Database connection failed.");
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        Connection connection = connect();
//
//        if (connection == null) {
//            System.out.println("Exiting the application.");
//            return; // Exit if connection failed
//        }
//
//        User user = new User(connection, scanner);
//        CinemaHall cinemaHall = new CinemaHall(connection, scanner);
//        Movie movie = new Movie(connection, scanner);
//        Booking booking = new Booking(connection, scanner);
//        Payment payment = new Payment(connection, scanner);
//        Admin admin = new Admin(connection, scanner);
//        Credentials credentials=new Credentials(connection);
//
//        while (true) {
//            System.out.println("WELCOME TO THE CINEMA MANAGEMENT SYSTEM:-");
//            System.out.println("\nMain Menu:");
//            System.out.println("1. Credentials  Menu");
//            System.out.println("2. User  Menu");
//            System.out.println("3. Cinema Hall Menu");
//            System.out.println("4. Movie Menu");
//            System.out.println("5. Booking Menu");
//            System.out.println("6. Payment Menu");
//            System.out.println("7. Admin Menu");
//            System.out.println("8. Exit");
//            System.out.print("Select an option:");
//
//            int choice = 0;
//            try {
//                choice = scanner.nextInt();
//                scanner.nextLine();  // Consume newline
//            } catch (InputMismatchException e) {
//                System.out.println("Invalid input. Please enter a number.");
//                scanner.nextLine(); // Clear the invalid input
//                continue; // Skip to the next iteration of the loop
//            }
//
//            switch (choice) {
//                case 1:
//                    credentials.menu();
//                    break;
//                case 2:
//                    user.menu();
//                    break;
//                case 3:
//                    cinemaHall.menu();
//                    break;
//                case 4:
//                    movie.menu();
//                    break;
//                case 5:
//                    booking.menu();
//                    break;
//                case 6:
//                    payment.menu();
//                    break;
//                case 7:
//                    admin.menu();
//                    break;
//                case 8:
//                    System.out.println("Exiting...");
//                    try {
//                        connection.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    scanner.close();
//                    System.exit(0);
//                default:
//                    System.out.println("Invalid choice.");
//            }
//        }
//    }
//}

package CinemaManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CinemaManagementSystem {
    private static Connection connect() {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost:3306/cinema";
            String user = "root";
            String password = "Anuj@1234";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database successfully.");
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = connect();

        if (connection == null) {
            System.out.println("Exiting the application.");
            return;
        }

        Credentials credentials = new Credentials(connection);

        while (true) {
            System.out.println("WELCOME TO THE CINEMA MANAGEMENT SYSTEM:-");
            System.out.println("\nMain Menu:");
            System.out.println("1. Credentials Menu");
            System.out.println("2. Exit");
            System.out.print("Select an option: ");

            int choice = 0;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    credentials.menu();
                    break;
                case 2:
                    System.out.println("THANK YOU FOR USING CINEMA MANAGEMENT SYSTEM");
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}