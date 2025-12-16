package Service;

import Admin.AdminMenu;
import User.UserMenu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static Service.Main.USER_FILE;
import static Service.Main.sc;

public class Login {
    public void login() {

        String email;
        while (true) {
            System.out.print("Enter your email: ");
            email = sc.nextLine().trim().toLowerCase();
            if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                break;
            }
            System.out.println("Invalid email format.");
        }

        String password;
        while (true) {
            System.out.print("Enter your password: ");
            password = sc.nextLine().trim();
            if (password.matches("^(?=.*[A-Za-z])(?=.*\\d).{6,}$")) {
                break;
            }
            System.out.println("Password too short. Must be at least 6 characters.");
        }

        if (email.equals("admin@gmail.com") && password.equals("admin123")) {
            System.out.println("Admin login successful!");
            AdminMenu am = new AdminMenu();
            am.adminMenu();
        }
        else if (validateUser(email, password)) {
            System.out.println("Login successful! Welcome, " + email + "!");
            UserMenu um = new UserMenu();
            um.userMenu(email);
        }
        else {
            System.out.println("Invalid email or password!");
        }



    }

    public boolean validateUser(String email, String password) {
        try {
            File file = new File(USER_FILE);
            if (!file.exists()) return false;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[2].equalsIgnoreCase(email) && parts[4].equals(password)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }
        return false;
    }
}

