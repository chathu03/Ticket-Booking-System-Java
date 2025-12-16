# 🎟️ Console-Based Ticket Booking System (Java)

This is a simple command-line application built in Java for managing show tickets, featuring separate modules for Admins and regular Users. It uses file I/O to persist data, simulating a basic database.

## ✨ Features

### User Features:
* **Sign Up & Login:** Secure user registration and login with input validation (Email, Password, Mobile, Names).
* **View Shows:** See a list of all available shows, including date, time, venue, and ticket type details (name, price, and available seats).
* **Book Ticket:** Select a show and ticket type, specify quantity, and automatically update the available seat count.
* **View My Bookings:** See a list of all tickets booked by the user.
* **Cancel Booking:** Cancel an existing booking by ID, which restores the seat count for the show.
* **Get E-Ticket:** Generate a text file (`.txt`) e-ticket for a specific booking ID.

### Admin Features:
* **Admin Login:** Dedicated credentials to access administrative functions (`admin@gmail.com` / `admin123`).
* **Add Show:** Create a new show entry with details like ID, Name, Date, Time, Venue, and multiple ticket types (e.g., VIP, General).
* **View All Shows:** Display comprehensive information for all shows and their ticket sections.
* **Delete Show:** Remove a show from the system using its ID.

## 🛠️ Project Structure

The project is organized into three main packages:

| Package | Purpose | Key Files |
| :--- | :--- | :--- |
| `Service` | Core functionality, main entry point, and login/signup. | `Main.java`, `Login.java`, `SignUp.java` |
| `Admin` | Administrative tasks related to show management. | `AdminMenu.java`, `AddShow.java`, `DeleteShows.java`, `ViewAllShows.java` |
| `User` | Functions for ticket booking and managing personal bookings. | `UserMenu.java`, `BookTicket.java`, `ViewShows.java`, `CancelBooking.java`, `getEticket.java` |

## 📁 Data Persistence

The system uses plain text files to store data:

* `users.txt`: Stores user registration data (`fname,lname,email,mobile,password`).
* `adminShows.txt`: Stores show details and ticket inventory (`id,name,date,time,venue,ticketType1:price:seats;ticketType2:price:seats`).
* `bookings.txt`: Stores booking records (`bookingId,userEmail,showId,showName,ticketType,qty,total`).

## ▶️ How to Run

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git](https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git)
    cd YOUR_REPOSITORY_NAME
    ```
2.  **Compile:** Compile all Java files (ensure you have the correct package structure).
    ```bash
    javac Service/*.java Admin/*.java User/*.java
    ```
3.  **Run:** Execute the `Main` class from the `Service` package.
    ```bash
    java Service.Main
    ```
