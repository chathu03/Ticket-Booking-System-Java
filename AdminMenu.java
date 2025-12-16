package Admin;


import static Service.Main.sc;

public class AdminMenu {

    public  void adminMenu() {
        while (true) {
            System.out.println("\n==== Admin Menu ====");
            System.out.println("1. Add Show");
            System.out.println("2. View All Shows");
            System.out.println("3. Delete Show");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    AddShow as = new AddShow();
                    as.addShow();
                    break;
                case "2":
                    ViewAllShows vas = new ViewAllShows();
                    vas.viewAllShows();
                    break;
                case "3":
                    DeleteShows ds = new DeleteShows();
                    ds.deleteShow();
                    break;
                case "4":
                    System.out.println("Logging out...");
                    return; // exit admin menu
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
