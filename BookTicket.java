package User;

import java.io.*;
import java.util.*;
import static Service.Main.*;

public class BookTicket {

    public void bookTicket(String userEmail) {
        File file = new File(ADMIN_SHOW_FILE);

        // Check if show file exists
        if (!file.exists() || file.length() == 0) {
            System.out.println(" No shows available to book.");
            return;
        }

        // Display available shows before asking for show ID
        System.out.println("\n=== Available Shows ===");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean anyShow = false;

            System.out.println("\n=== Available Shows ===");
            System.out.printf("%-10s %-30s%n", "Show ID", "Show Name");
            System.out.println("--------------------------------------------------");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6);
                if (parts.length < 2) continue; // Make sure at least ID and Name exist

                String id = parts[0].trim();
                String name = parts[1].trim();

                System.out.printf("%-10s %-30s%n", id, name);
                anyShow = true;
            }

            if (!anyShow) {
                System.out.println(" No shows found in the system.");
                return;
            }
        } catch (IOException e) {
            System.out.println(" Error reading show file: " + e.getMessage());
            return;
        }

        // Now ask user to enter show ID
        System.out.print("\nEnter Show ID to book: ");
        String showId = sc.nextLine().trim();

        List<String> updatedShows = new ArrayList<>();
        boolean showFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6);
                if (parts.length < 6) continue;

                if (parts[0].equalsIgnoreCase(showId)) {
                    showFound = true;
                    String showName = parts[1];
                    String ticketSection = parts[5];

                    // Display ticket options
                    String[] ticketTypes = ticketSection.split(";");
                    System.out.println("\n=== Available Ticket Types ===");
                    for (int i = 0; i < ticketTypes.length; i++) {
                        String[] t = ticketTypes[i].split(":");
                        System.out.printf("%d. %s - Rs.%s (%s seats left)%n", i + 1, t[0], t[1], t[2]);
                    }

                    System.out.print("\nEnter ticket type name to book: ");
                    String chosenType = sc.nextLine().trim();

                    // Find chosen type
                    boolean typeFound = false;
                    for (int i = 0; i < ticketTypes.length; i++) {
                        String[] t = ticketTypes[i].split(":");
                        if (t[0].equalsIgnoreCase(chosenType)) {
                            typeFound = true;

                            int availableSeats = Integer.parseInt(t[2]);
                            if (availableSeats <= 0) {
                                System.out.println(" No seats left for " + t[0]);
                                updatedShows.add(line);
                                break;
                            }

                            int qty;
                            while (true) {
                                System.out.print("Enter number of tickets: ");
                                String input = sc.nextLine().trim();
                                if (input.matches("^\\d+$")) {
                                    qty = Integer.parseInt(input);
                                    if (qty > 0 && qty <= availableSeats) break;
                                }
                                System.out.println("️ Invalid quantity or not enough seats.");
                            }

                            double price = Double.parseDouble(t[1]);
                            double total = qty * price;
                            int remaining = availableSeats - qty;
                            t[2] = String.valueOf(remaining);
                            ticketTypes[i] = String.join(":", t);

                            // Update the ticket section
                            String newTicketSection = String.join(";", ticketTypes);
                            line = String.join(",", parts[0], parts[1], parts[2], parts[3], parts[4], newTicketSection);

                            // Save booking
                            saveBooking(userEmail, showId, showName, t[0], qty, total);

                            System.out.println(" " + qty + " " + t[0] + " ticket(s) booked successfully!");
                            System.out.println(" Total: Rs." + total);
                            break;
                        }
                    }

                    if (!typeFound) {
                        System.out.println(" Ticket type not found!");
                    }

                    updatedShows.add(line);
                } else {
                    updatedShows.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println(" Error reading show file: " + e.getMessage());
            return;
        }

        if (!showFound) {
            System.out.println(" Show ID not found!");
            return;
        }

        // Update show file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_SHOW_FILE))) {
            for (String s : updatedShows) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(" Error updating show file: " + e.getMessage());
        }
    }

    private void saveBooking(String userEmail, String showId, String showName, String ticketType, int qty, double total) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKING_FILE, true))) {
            String bookingId = UUID.randomUUID().toString().substring(0, 8);
            writer.write(bookingId + "," + userEmail + "," + showId + "," + showName + "," +
                    ticketType + "," + qty + "," + total);
            writer.newLine();
        } catch (IOException e) {
            System.out.println(" Error saving booking: " + e.getMessage());
        }
    }
}
