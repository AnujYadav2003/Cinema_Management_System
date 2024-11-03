//
//package CinemaManagementSystem;
//
//import java.sql.*;
//import java.util.Scanner;
//
//public class Admin {
//    private Connection connection;
//    private Scanner scanner;
//    private User user;
//    private Movie movie;
//    private CinemaHall cinemaHall;
//    private Booking booking;
//    private Payment payment;
//    public Admin(Connection connection, Scanner scanner) {
//        this.connection = connection;
//        this.scanner = scanner;
//        this.user = new User(connection, scanner);
//        this.movie = new Movie(connection, scanner);
//        this.cinemaHall = new CinemaHall(connection, scanner);
//        this.booking=new Booking(connection,scanner);
//        this.payment=new Payment(connection,scanner);
//    }
//
//    public void menu() {
////        System.out.println("\nWELCOME TO THE CINEMA MANAGEMENT SYSTEM:-");
//        System.out.println("WELCOME TO ADMIN MENU:-");
//        System.out.println("1. View All Users");
//        System.out.println("2. View All Movies");
//        System.out.println("3. Add Movies");
//        System.out.println("4. Remove Movies");
//        System.out.println("5. View All Cinema Halls");
//        System.out.println("6. Add Cinema Halls");
//        System.out.println("7. Remove Cinema Halls");
//        System.out.println("8. View All Bookings");
//        System.out.println("9. View All Payments");
//        System.out.println("10. Exit"); // Add option to exit
//        System.out.print("Select an option: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1 -> user.viewAllUsers();
//            case 2 -> movie.viewMovies();
//            case 3 -> movie.addMovie();
//            case 4 -> movie.removeMovie();
//            case 5 -> cinemaHall.viewCinemaHalls();
//            case 6 -> cinemaHall.addCinemaHall();
//            case 7 -> cinemaHall.removeCinemaHall();
//            case 8 -> booking.viewBookings();
//            case 9 -> payment.viewPayments();
//
//            case 10 -> {
//                System.out.println("THANK YOU FOR USING OUR CINEMA MANAGEMENT SYSTEM");
//                return;
//            }
//            default -> {System.out.println("Invalid choice.");
//                System.out.println();}
//        }
//    }
//
//
//}

package CinemaManagementSystem;

import java.sql.Connection;
import java.util.Scanner;

public class Admin {
    private Connection connection;
    private Scanner scanner;
    private User user;
    private Movie movie;
    private CinemaHall cinemaHall;
    private Booking booking;
    private Payment payment;

    public Admin(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
        this.user = new User(connection, scanner);
        this.movie = new Movie(connection, scanner);
        this.cinemaHall = new CinemaHall(connection, scanner);
        this.booking = new Booking(connection, scanner);
        this.payment = new Payment(connection, scanner);
    }

    public void menu() {
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("WELCOME TO ADMIN MENU:-");
            System.out.println("1. View All Users");
            System.out.println("2. View All Movies");
            System.out.println("3. Add Movies");
            System.out.println("4. Remove Movies");
            System.out.println("5. View All Cinema Halls");
            System.out.println("6. Add Cinema Halls");
            System.out.println("7. Remove Cinema Halls");
            System.out.println("8. View All Bookings");
            System.out.println("9. View All Payments");
            System.out.println("10. Exit"); // Add option to exit
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> user.viewAllUsers();
                case 2 -> movie.viewMovies();
                case 3 -> movie.addMovie();
                case 4 -> movie.removeMovie();
                case 5 -> cinemaHall.viewCinemaHalls();
                case 6 -> cinemaHall.addCinemaHall();
                case 7 -> cinemaHall.removeCinemaHall();
                case 8 -> booking.viewBookings();
                case 9 -> payment.viewPayments();
                case 10 -> {
                    System.out.println("THANK YOU FOR USING OUR CINEMA MANAGEMENT SYSTEM");
                    keepRunning = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}