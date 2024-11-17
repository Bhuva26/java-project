import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Bus> buses = new ArrayList<>();
    private static ArrayList<Reservation> reservations = new ArrayList<>();
    private static int reservationIdCounter = 1;

    public static void main(String[] args) {
        initializeBuses();

        while (true) {
            System.out.println("\nBus Ticket Reservation System");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    customerMenu();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeBuses() {
        buses.add(new Bus(1, "CityA to CityB", 50));
        buses.add(new Bus(2, "CityC to CityD", 40));
    }

    private static void adminMenu() {
        System.out.println("\nAdmin Menu");
        System.out.println("1. Add Bus");
        System.out.println("2. View Buses");
        System.out.println("3. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                addBus();
                break;
            case 2:
                viewBuses();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addBus() {
        System.out.print("Enter Bus ID: ");
        int busId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Route: ");
        String route = scanner.nextLine();
        System.out.print("Enter Total Seats (or press Enter for default 40 seats): ");
        String seatsInput = scanner.nextLine();

        Bus newBus = new Bus();
        if (seatsInput.isEmpty()) {
            newBus.addBus(busId, route);
        } else {
            int totalSeats = Integer.parseInt(seatsInput);
            newBus.addBus(busId, route, totalSeats);
        }

        buses.add(newBus);
        System.out.println("Bus added successfully!");
    }

    private static void viewBuses() {
        System.out.println("\nList of Buses:");
        for (Bus bus : buses) {
            System.out.println(bus);
        }
    }

    private static void customerMenu() {
        System.out.println("\nCustomer Menu");
        System.out.println("1. Book Ticket");
        System.out.println("2. View Reservations");
        System.out.println("3. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                bookTicket();
                break;
            case 2:
                viewReservations();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void bookTicket() {
        System.out.print("Enter Bus ID: ");
        int busId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter Number of Seats (or press Enter for 1 seat): ");
        String seatsInput = scanner.nextLine();
        int seatsBooked = 1;
        if (!seatsInput.isEmpty()) {
            seatsBooked = Integer.parseInt(seatsInput);
        }
        Bus selectedBus = null;
        for (Bus bus : buses) {
            if (bus.getBusId() == busId) {
                selectedBus = bus;
                break;
            }
        }

        if (selectedBus != null && selectedBus.getAvailableSeats() >= seatsBooked) {
            Reservation newReservation = new Reservation(reservationIdCounter++, busId, customerName, seatsBooked);
            reservations.add(newReservation);
            selectedBus.bookSeats(seatsBooked); // Update available seats
            System.out.println("Ticket booked successfully!");
        } else {
            System.out.println("Booking failed: Bus not found or not enough available seats.");
        }
    }

    private static void viewReservations() {
        System.out.println("\nList of Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}

class Bus {
    private int busId;
    private String route;
    private int totalSeats;
    private int availableSeats;
    public Bus() {}

    public Bus(int busId, String route, int totalSeats) {
        this.busId = busId;
        this.route = route;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }
    public void addBus(int busId, String route) {
        this.busId = busId;
        this.route = route;
        this.totalSeats = 40;
        this.availableSeats = 40;
    }

    public void addBus(int busId, String route, int totalSeats) {
        this.busId = busId;
        this.route = route;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public int getBusId() {
        return busId;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookSeats(int seats) {
        if (availableSeats >= seats) {
            availableSeats -= seats;
        }
    }
    public String toString() {
        return "Bus ID: " + busId + ", Route: " + route + ", Total Seats: " + totalSeats + ", Available Seats: " + availableSeats;
    }
}
class Reservation {
    private int reservationId;
    private int busId;
    private String customerName;
    private int seatsBooked;
    public Reservation(int reservationId, int busId, String customerName, int seatsBooked) {
        this.reservationId = reservationId;
        this.busId = busId;
        this.customerName = customerName;
        this.seatsBooked = seatsBooked;
    }
    public String toString() {
        return "Reservation ID: " + reservationId + ", Bus ID: " + busId + ", Customer Name: " + customerName + ", Seats Booked: " + seatsBooked;
    }
}