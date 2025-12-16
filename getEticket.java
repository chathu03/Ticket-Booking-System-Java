package User;

import java.io.*;
import java.util.Scanner;
import static Service.Main.BOOKING_FILE;

public class getEticket {

    public void geteTicket(String userEmail) {
        File file = new File(BOOKING_FILE);
        if (!file.exists() || file.length() == 0) {
            System.out.println("\n You have no bookings yet.");
            return;
        }

        boolean hasBooking = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            System.out.println("\n=== My Bookings ===");
            System.out.printf("%-10s %-8s %-20s %-10s %-8s %-10s%n",
                    "Book ID", "Show ID", "Show Name", "Type", "Qty", "Total");
            System.out.println("---------------------------------------------------------------");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7 && parts[1].equalsIgnoreCase(userEmail)) {
                    System.out.printf("%-10s %-8s %-20s %-10s %-8s Rs.%-10s%n",
                            parts[0], parts[2], parts[3], parts[4], parts[5], parts[6]);
                    hasBooking = true;
                }
            }

        } catch (IOException e) {
            System.out.println(" Error reading your bookings: " + e.getMessage());
        }

        if (!hasBooking) {
            System.out.println(" No bookings found for your account.");
            return;
        }

        // Let user select a booking to generate E-Ticket
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter the Book ID to generate your E-Ticket: ");
        String selectedBookId = sc.nextLine().trim();

        generateETicket(userEmail, selectedBookId);
    }

    private void generateETicket(String userEmail, String bookId) {
        File file = new File(BOOKING_FILE);
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7 && parts[0].equalsIgnoreCase(bookId) && parts[1].equalsIgnoreCase(userEmail)) {
                    found = true;

                    String showId = parts[2];
                    String showName = parts[3];
                    String type = parts[4];
                    String qty = parts[5];
                    String total = parts[6];

                    // Create folder if not exists
                    File folder = new File("etickets");
                    if (!folder.exists()) {
                        folder.mkdir();
                    }

                    // File path
                    File ticketFile = new File(folder, "E_Ticket_" + bookId + ".txt");

                    // Check if file already exists
                    if (ticketFile.exists()) {
                        System.out.println("\nE-Ticket already generated for this booking: " + ticketFile.getAbsolutePath());
                        return; // stop further execution
                    }

                    // Write new e-ticket file
                    String ticketContent = "===================================\n"
                            + "          E-TICKET\n"
                            + "===================================\n"
                            + "Booking ID : " + bookId + "\n"
                            + "Show ID    : " + showId + "\n"
                            + "Show Name  : " + showName + "\n"
                            + "Ticket Type: " + type + "\n"
                            + "Quantity   : " + qty + "\n"
                            + "Total Paid : Rs." + total + "\n"
                            + "Booked By  : " + userEmail + "\n"
                            + "===================================\n"
                            + "Thank you for booking with us!\n";

                    try (FileWriter writer = new FileWriter(ticketFile)) {
                        writer.write(ticketContent);
                    }

                    System.out.println("\nE-Ticket generated successfully: " + ticketFile.getAbsolutePath());
                    break;
                }
            }

            if (!found) {
                System.out.println("Invalid Book ID or not found in your bookings.");
            }

        } catch (IOException e) {
            System.out.println(" Error generating E-Ticket: " + e.getMessage());
        }
    }
}
