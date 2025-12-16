package User;

import static Service.Main.sc;

public class UserMenu {


    public void userMenu(String userEmail) {
        while (true) {
            System.out.println("\n==== User Menu ====");
            System.out.println("1. View Shows");
            System.out.println("2. Book Ticket");
            System.out.println("3. View My Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Get e-ticket");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    ViewShows vs = new ViewShows();
                    vs.viewShows();
                    break;
                case "2":
                    BookTicket bt = new BookTicket();
                    bt.bookTicket(userEmail);
                    break;
                case "3":
                    ViewMyBookings vmb = new ViewMyBookings();
                    vmb.viewMyBookings(userEmail);
                    break;
                case "4":
                    CancelBooking cb = new CancelBooking();
                    cb.cancelBooking(userEmail);
                    break;
                    case "5":
                    getEticket ge = new getEticket();
                    ge.geteTicket(userEmail);
                    break;
                case "6":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
