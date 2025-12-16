package Service;

import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static final String USER_FILE = "users.txt";
    public static final String ADMIN_SHOW_FILE = "adminShows.txt";
    public static final String BOOKING_FILE = "bookings.txt";



    public static void main(String[] args) {
        while (true) {
            System.out.println("\n==== Ticket Booking System ====");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    SignUp su = new SignUp();
                    su.signUp();
                    break;
                case "2":
                    Login lg = new Login();
                    lg.login();
                    break;
                case "3":
                    System.out.println("Thank you for using the Ticket Booking System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
