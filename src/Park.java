import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Abstract class for vehicles
abstract class Vehicle {
    private String plateNumber;
    private String ownerName;
    private int parkingLotNumber;

    public Vehicle(String plateNumber, String ownerName) {
        this.plateNumber = plateNumber;
        this.ownerName = ownerName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getParkingLotNumber() {
        return parkingLotNumber;
    }

    public void setParkingLotNumber(int parkingLotNumber) {
        this.parkingLotNumber = parkingLotNumber;
    }

    // Abstract method to get the vehicle type
    public abstract String getType();
}

// Car class which extends Vehicle
class Car extends Vehicle {
    public Car(String plateNumber, String ownerName) {
        super(plateNumber, ownerName);
    }

    @Override
    public String getType() {
        return "Car";
    }
}

// Motorcycle class which extends Vehicle
class Motorcycle extends Vehicle {
    public Motorcycle(String plateNumber, String ownerName) {
        super(plateNumber, ownerName);
    }

    @Override
    public String getType() {
        return "Motorcycle";
    }
}

// Interface for parking operations
interface Parking {
    boolean parkVehicle(Vehicle vehicle, int parkingLotNumber);
    boolean removeVehicle(String plateNumber);
    void displayParkedVehicles();
    void displayParkingSlots();
}
// ParkingLot class implementing Parking interface
class ParkingLot implements Parking {
    private Map<String, Vehicle> vehiclesByPlateNumber;
    private boolean[] parkingSlots;

    public ParkingLot(int capacity) {
        vehiclesByPlateNumber = new HashMap<>();
        parkingSlots = new boolean[capacity];
    }

    @Override
    public boolean parkVehicle(Vehicle vehicle, int parkingLotNumber) {
        if (parkingLotNumber > 0 && parkingLotNumber <= parkingSlots.length && !parkingSlots[parkingLotNumber - 1]) {
            vehicle.setParkingLotNumber(parkingLotNumber);
            vehiclesByPlateNumber.put(vehicle.getPlateNumber(), vehicle);
            parkingSlots[parkingLotNumber - 1] = true;
            return true;
        }
        return false; // Parking lot is full or invalid
    }

    @Override
    public boolean removeVehicle(String plateNumber) {
        for (Map.Entry<String, Vehicle> entry : vehiclesByPlateNumber.entrySet()) {
            Vehicle vehicle = entry.getValue();
            if (vehicle.getPlateNumber().equals(plateNumber)) {
                vehiclesByPlateNumber.remove(vehicle.getPlateNumber());
                parkingSlots[vehicle.getParkingLotNumber() - 1] = false;
                return true;
            }
        }
        return false; // Vehicle not found with the given plate number
    }

    @Override
    public void displayParkedVehicles() {
        System.out.println("\nParked Vehicles:");
        for (Map.Entry<String, Vehicle> entry : vehiclesByPlateNumber.entrySet()) {
            Vehicle vehicle = entry.getValue();
            System.out.println(vehicle.getType() + " - Plate Number: " + entry.getKey() +
                    ", Owner: " + vehicle.getOwnerName() +
                    ", Parking Lot Number: " + vehicle.getParkingLotNumber());
        }
    }

    @Override
    public void displayParkingSlots() {
        System.out.println("\nParking Slots:");
        for (int i = 0; i < parkingSlots.length; i++) {
            int slotNumber = i + 1;
            if (parkingSlots[i]) {
                System.out.println(slotNumber + ". ╭────────────────╮\n" +
                        "   │    Occupied    │\n" +
                        "   ╰────────────────╯");
            } else {
                System.out.println(slotNumber + ". ╭────────────────╮\n" +
                        "   │   Available    │\n" +
                        "   ╰────────────────╯");
            }
        }
    }

}

// Main class for user interaction
public class Park {
    public static void main(String[] args) {


        System.out.println("██████╗  █████╗ ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗     ███████╗██╗   ██╗███████╗████████╗███████╗███╗   ███╗");
        System.out.println("██╔══██╗██╔══██╗██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝     ██╔════╝╚██╗ ██╔╝██╔════╝╚══██╔══╝██╔════╝████╗ ████║");
        System.out.println("██████╔╝███████║██████╔╝█████╔╝ ██║██╔██╗ ██║██║  ███╗    ███████╗ ╚████╔╝ ███████╗   ██║   █████╗  ██╔████╔██║");
        System.out.println("██╔═══╝ ██╔══██║██╔══██╗██╔═██╗ ██║██║╚██╗██║██║   ██║    ╚════██║  ╚██╔╝  ╚════██║   ██║   ██╔══╝  ██║╚██╔╝██║");
        System.out.println("██║     ██║  ██║██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝    ███████║   ██║   ███████║   ██║   ███████╗██║ ╚═╝ ██║");
        System.out.println("╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝     ╚══════╝   ╚═╝   ╚══════╝   ╚═╝   ╚══════╝╚═╝     ╚═╝");
        System.out.println("                                                                                                              ");


        System.out.println("+--------------------------------------+");
        System.out.println("|                                      |");
        System.out.println("|      NCF PARKING SYSTEM              |");
        System.out.println("|                                      |");
        System.out.println("|  1. Park a Vehicle                   |");
        System.out.println("|  2. Remove a Vehicle                 |");
        System.out.println("|  3. Display Information of Owners    |");
        System.out.println("|  4. Parking Slot  Monitoring         |");
        System.out.println("|  5. Exit                             |");
        System.out.println("|                                      |");
        System.out.println("+--------------------------------------+");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of parking slots:");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        // Create a parking lot with user-defined number of spaces
        ParkingLot parkingLot = new ParkingLot(capacity);

        // Main loop for user interaction
        while (true) {
            System.out.println("\nEnter your choice (1-5):");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("+---------------------------------------+");
                    System.out.println("|                                       |");
                    System.out.println("|              Park a Vehicle           |");
                    System.out.println("|                                       |");
                    System.out.println("+---------------------------------------+");

                    System.out.println("Enter vehicle type (car/motorcycle):");
                    String type = scanner.nextLine();
                    System.out.println("Enter plate number:");
                    String plateNumber = scanner.nextLine();
                    System.out.println("Enter owner's name:");
                    String ownerName = scanner.nextLine();
                    System.out.println("Enter parking lot number:");
                    int parkingLotNumber = scanner.nextInt();
                    scanner.nextLine(); // consume the newline character

                    Vehicle vehicle;
                    if (type.equalsIgnoreCase("car")) {
                        vehicle = new Car(plateNumber, ownerName);
                    } else if (type.equalsIgnoreCase("motorcycle")) {
                        vehicle = new Motorcycle(plateNumber, ownerName);
                    } else {
                        System.out.println("Invalid vehicle type!");
                        continue;
                    }

                    if (parkingLot.parkVehicle(vehicle, parkingLotNumber)) {
                        System.out.println(vehicle.getType() + " parked successfully!");
                    } else {
                        System.out.println("Parking lot is full or invalid!");
                    }
                    break;
                case 2:
                    System.out.println("+---------------------------------------+");
                    System.out.println("|                                       |");
                    System.out.println("|             Remove a Vehicle          |");
                    System.out.println("|                                       |");
                    System.out.println("+---------------------------------------+");

                    System.out.println("Enter plate number:");
                    plateNumber = scanner.nextLine();

                    if (parkingLot.removeVehicle(plateNumber)) {
                        System.out.println("Vehicle removed from parking lot.");
                    } else {
                        System.out.println("Vehicle not found in parking lot.");
                    }
                    break;
                case 3:
                    parkingLot.displayParkedVehicles();
                    break;
                case 4:
                    parkingLot.displayParkingSlots();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
