package User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static Service.Main.*;

public class CancelBooking {
    public void cancelBooking(String userEmail) {
        System.out.print("Enter Booking ID to cancel: ");
        String bookingId = sc.nextLine().trim();

        File bookingFile = new File(BOOKING_FILE);
        if (!bookingFile.exists()) {
            System.out.println("No bookings found!");
            return;
        }

        File tempFile = new File("tempBookings.txt");
        boolean found = false;
        String canceledShowId = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(bookingFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(bookingId) && parts[1].equals(userEmail)) {
                    found = true;
                    canceledShowId = parts[2];
                    continue; // skip writing this booking (delete it)
                }
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error canceling booking.");
            return;
        }

        bookingFile.delete();
        tempFile.renameTo(bookingFile);

        if (found) {
            increaseSeatCount(canceledShowId);
            System.out.println("Booking canceled successfully!");
        } else {
            System.out.println("Booking ID not found or not yours.");
        }
    }

    // ===== INCREASE SEAT COUNT AFTER CANCEL =====
    static void increaseSeatCount(String showId) {
        File file = new File(ADMIN_SHOW_FILE);
        if (!file.exists()) return;

        List<String> updatedShows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(showId)) {
                    int seats = Integer.parseInt(parts[6]);
                    parts[6] = String.valueOf(seats + 1);
                    updatedShows.add(String.join(",", parts));
                } else {
                    updatedShows.add(line);
                }
            }
        } catch (IOException e) {
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_SHOW_FILE))) {
            for (String s : updatedShows) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating seat count.");
        }
    }
}
