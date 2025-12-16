package Admin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Service.Main.ADMIN_SHOW_FILE;
import static Service.Main.sc;

public class AddShow {
    public void addShow() {
        try {
            String id, name, date, time, venue;

            // ===== Show ID =====
            while (true) {
                System.out.print("Enter Show ID: ");
                id = sc.nextLine().trim();
                if (!id.isEmpty()) break;
                System.out.println("Show ID cannot be empty!");
            }

            // ===== Show Name =====
            while (true) {
                System.out.print("Enter Show Name: ");
                name = sc.nextLine().trim();
                if (name.matches("^[A-Za-z0-9 ]{3,}$")) break;
                System.out.println("Invalid name! Use at least 3 letters/numbers.");
            }

            // ===== Date =====
            while (true) {
                System.out.print("Enter Date (YYYY-MM-DD): ");
                date = sc.nextLine().trim();
                if (date.matches("^\\d{4}-\\d{2}-\\d{2}$")) break;
                System.out.println("Invalid date format! Example: 2025-10-23");
            }

            // ===== Time =====
            while (true) {
                System.out.print("Enter Time (e.g., 7:30 PM): ");
                time = sc.nextLine().trim();
                if (time.matches("^(1[0-2]|0?[1-9]):[0-5][0-9]\\s?(AM|PM|am|pm)$")) break;
                System.out.println("Invalid time format! Example: 7:30 PM");
            }

            // ===== Venue =====
            while (true) {
                System.out.print("Enter Venue: ");
                venue = sc.nextLine().trim();
                if (venue.matches("^[A-Za-z0-9 ,.()-]{3,}$")) break;
                System.out.println("Invalid venue name! Example: City Hall");
            }

            // ===== Ticket Types =====
            List<String> ticketTypes = new ArrayList<>();
            int typeCount = 0;

            while (true) {
                System.out.print("How many ticket types do you want to add? ");
                String input = sc.nextLine().trim();
                if (input.matches("^\\d+$") && Integer.parseInt(input) > 0) {
                    typeCount = Integer.parseInt(input);
                    break;
                }
                System.out.println("Please enter a positive number.");
            }

            for (int i = 1; i <= typeCount; i++) {
                System.out.println("\n--- Ticket Type " + i + " ---");

                String typeName, price, seats;

                // Ticket Type Name
                while (true) {
                    System.out.print("Enter Ticket Type Name (e.g., Adult, Child, VIP): ");
                    typeName = sc.nextLine().trim();
                    if (typeName.matches("^[A-Za-z0-9 ]{3,}$")) break;
                    System.out.println("Invalid name! Use at least 3 letters.");
                }

                // Ticket Price
                while (true) {
                    System.out.print("Enter Ticket Price: ");
                    price = sc.nextLine().trim();
                    if (price.matches("^\\d+(\\.\\d{1,2})?$")) break;
                    System.out.println("Invalid price! Example: 1500 or 1500.50");
                }

                // Available Seats
                while (true) {
                    System.out.print("Enter Available Seats: ");
                    seats = sc.nextLine().trim();
                    if (seats.matches("^\\d+$") && Integer.parseInt(seats) > 0) break;
                    System.out.println("Invalid seats! Must be a positive number.");
                }

                ticketTypes.add(typeName + ":" + price + ":" + seats);
            }

            // ===== Save Show to File =====
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_SHOW_FILE, true))) {
                String ticketData = String.join(";", ticketTypes);
                writer.write(id + "," + name + "," + date + "," + time + "," + venue + "," + ticketData);
                writer.newLine();
            }

            System.out.println("\n Show added successfully with " + typeCount + " ticket type(s)!");

        } catch (IOException e) {
            System.out.println("Error adding show: " + e.getMessage());
        }
    }
}
