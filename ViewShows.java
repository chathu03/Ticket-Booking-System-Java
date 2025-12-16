package User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static Service.Main.ADMIN_SHOW_FILE;

public class ViewShows {
    public void viewShows() {
        File file = new File(ADMIN_SHOW_FILE);
        if (!file.exists() || file.length() == 0) {
            System.out.println("\n No shows available.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("\n=== Available Shows ===");
            System.out.printf("%-10s %-20s %-12s %-10s %-20s%n",
                    "ID", "Name", "Date", "Time", "Venue");
            System.out.println("--------------------------------------------------------------------------------");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6);
                if (parts.length < 6) continue;

                String id = parts[0];
                String name = parts[1];
                String date = parts[2];
                String time = parts[3];
                String venue = parts[4];
                String ticketData = parts[5];

                // Basic show info
                System.out.printf("%-10s %-20s %-12s %-10s %-20s%n",
                        id, name, date, time, venue);

                // Ticket details
                String[] ticketTypes = ticketData.split(";");
                System.out.println("Ticket Types:");
                for (String ticket : ticketTypes) {
                    String[] t = ticket.split(":");
                    if (t.length == 3) {
                        String type = t[0];
                        String price = t[1];
                        String seats = t[2];
                        System.out.printf("      - %-10s : Rs.%-7s (%s seats)%n", type, price, seats);
                    }
                }

                System.out.println("--------------------------------------------------------------------------------");
            }

        } catch (IOException e) {
            System.out.println("Error reading shows: " + e.getMessage());
        }
    }
}
