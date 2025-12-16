package User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static Service.Main.BOOKING_FILE;

public class ViewMyBookings {
    public void viewMyBookings(String userEmail) {
        File file = new File(BOOKING_FILE);
        if (!file.exists() || file.length() == 0) {
            System.out.println("\n You have no bookings yet.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean hasBooking = false;

            System.out.println("\n=== My Bookings ===");
            System.out.printf("%-10s %-8s %-20s %-10s %-8s %-10s%n",
                    "Book ID", "Show ID", "Show Name", "Type", "Qty", "Total");
            System.out.println("---------------------------------------------------------------");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7 && parts[1].equalsIgnoreCase(userEmail)) {
                    String bookingId = parts[0];
                    String showId = parts[2];
                    String showName = parts[3];
                    String ticketType = parts[4];
                    String quantity = parts[5];
                    String total = parts[6];

                    System.out.printf("%-10s %-8s %-20s %-10s %-8s Rs.%-10s%n",
                            bookingId, showId, showName, ticketType, quantity, total);

                    hasBooking = true;
                }
            }

            if (!hasBooking) {
                System.out.println(" No bookings found for your account.");
            }

        } catch (IOException e) {
            System.out.println(" Error reading your bookings: " + e.getMessage());
        }
    }
}
