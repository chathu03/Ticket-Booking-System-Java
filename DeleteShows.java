package Admin;

import java.io.*;

import static Service.Main.ADMIN_SHOW_FILE;
import static Service.Main.sc;

public class DeleteShows {
    public void deleteShow() {
        System.out.print("Enter Show ID to delete: ");
        String idToDelete = sc.nextLine().trim();

        File file = new File(ADMIN_SHOW_FILE);
        if (!file.exists()) {
            System.out.println("No shows found!");
            return;
        }

        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (!parts[0].equals(idToDelete)) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    found = true;
                }
            }

            reader.close();
            writer.close();

            file.delete();
            tempFile.renameTo(file);

            if (found)
                System.out.println("Show deleted successfully!");
            else
                System.out.println("Show ID not found!");

        } catch (IOException e) {
            System.out.println("Error deleting show: " + e.getMessage());
        }
    }
}
