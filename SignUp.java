package Service;

import java.io.*;
import java.util.Scanner;

import static Service.Main.USER_FILE;

public class SignUp {

    public void signUp() {
        try {
             Scanner sc = new Scanner(System.in);

            String fname;
            while (true) {
                System.out.print("Enter your first name: ");
                fname = sc.nextLine();
                if (!fname.trim().isEmpty() && fname.matches("[a-zA-Z]+")) {
                    break;
                }
                System.out.println("Invalid first name. Only letters allowed.");
            }

            String lname;
            while (true) {
                System.out.print("Enter your last name: ");
                lname = sc.nextLine();
                if (!lname.trim().isEmpty() && lname.matches("[a-zA-Z]+")) {
                    break;
                }
                System.out.println("Invalid last name. Only letters allowed.");
            }

            String email;
            while (true) {
                System.out.print("Enter your email: ");
                email = sc.nextLine();
                if (email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    break;
                }
                System.out.println("Invalid email format.Example: user@example.com");
            }

            String mobile;
            while (true) {
                System.out.print("Enter your mobile: ");
                mobile = sc.nextLine();
                if (mobile.matches("\\d{10}")) {
                    break;
                }
                System.out.println("Invalid mobile. Must be 10 digits.");
            }

            String password;
            while (true) {
                System.out.print("Enter your password: ");
                password = sc.nextLine();
                if (password.matches("^(?=.*[A-Za-z])(?=.*\\d).{6,}$")) {
                    break;
                }
                System.out.println("Password too short. Must be at least 6 characters.");
            }

            while (true) {
                System.out.print("Verify your password: ");
                String confirmPassword = sc.nextLine();
                if (password.equals(confirmPassword)) {
                    break;
                }
                System.out.println("Passwords do not match. Try again.");
            }

            // Check if email already exists
            if (userExists(email)) {
                System.out.println("This email is already registered. Try another.");
                return;
            }

            // Save user data to file
            BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true));
            writer.write(fname + "," + lname + "," + email + "," + mobile + "," + password);
            writer.newLine();
            writer.close();

            System.out.println("Sign up successful! You can now log in.");

        } catch (IOException e) {
            System.out.println("Error while signing up: " + e.getMessage());
        }
    }

    static boolean userExists(String email) throws IOException {
        File file = new File(USER_FILE);
        if (!file.exists()) return false;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3 && parts[2].equalsIgnoreCase(email)) {
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;
    }





}
