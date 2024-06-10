/**
 * This class uses methods from the package BikeRegistry
 * These methods are invoked from the main class of this program.
 * Contains adding/removing/altering methods
 * menu method is printing the options only
 * objectToString invokes the toString method in BikeRegistry to print.
 * @author Tegnoor Gill
 * @version 1.0
 * @param List<BikeRegistry>
 */
import java.util.Locale;
import java.util.Scanner;
import java.util.List;
import ca.cmpt213.bike.BikeRegistry;

public class Menu {
    public static void displayMainMenu(){
        System.out.println("*************");
        System.out.println("* Main Menu *");
        System.out.println("*************");
        System.out.println("1. List Bikes");
        System.out.println("2. Add a new Bike");
        System.out.println("3. Remove a Bike");
        System.out.println("4. Change Bike attribute");
        System.out.println("5. DEBUG: Dump objects (toString)");
        System.out.println("6. Exit");

        System.out.print(">");
    }

    /**
     * Tries to add. user input. if all fields filled correctly
     * the program then uses the current max size + 1 for the id for the bike
     * */
    public static void addNewBike(List <BikeRegistry> ar){
        Scanner scanner = new Scanner(System.in);
        try{
            System.out.print("Enter Bike owner name:        ");
            String owner = scanner.nextLine();
            System.out.print("Enter Bike type:              ");
            String type = scanner.nextLine();
            System.out.print("Enter Bike's serial number:   ");
            String serialNumber = scanner.nextLine();
            System.out.print("Enter Bike's brake type:      ");
            String brakeType = scanner.nextLine();
            if(brakeType.toLowerCase().equals("rim")){
                brakeType = "Rim";
            }else if(brakeType.toLowerCase().equals("disc")){
                brakeType = "Disc";
            }else if(brakeType.toLowerCase().equals("drum")){
                brakeType = "Drum";
            }else {
                System.out.println("Invalid brake type");
                return;
            }
            System.out.print("Enter Bike's wheel size:      ");
            double wheelSize = scanner.nextDouble();
            int id = ar.size()+1;
            BikeRegistry newBike = new BikeRegistry(id, owner, type, serialNumber, brakeType, wheelSize);
            ar.add(newBike);
        }
        catch (Exception e){
            System.out.println("Error while adding. Try again.");
        }

    }

    /**
     * formats the print and displays all bikes
     * */
    public static void displayAllBikes(List <BikeRegistry> ar){
        if(ar.isEmpty()) {
            System.out.println("No bikes found. Add bikes first.");
        }else {
            String format = "%-3s %-16s %-10s %-11s %-7s %-9s%n";
            System.out.printf(format, "ID", "Owner,", "Type,", "Serial,", "Brake,", "Wheel Size");
            for (BikeRegistry b : ar) {
                System.out.printf(format, b.getID(), b.getOwner(), b.getType(), b.getSerialNumber(), b.getBrakeType(), b.getWheelSize());
            }
        }
    }

    /**
     * modular coded used in alter and removing bikes. checks if input is valid
     * */
    private static int validInput(List <BikeRegistry> ar){
        Scanner scanner = new Scanner(System.in);
        while(true){
            displayAllBikes(ar);
            if(ar.isEmpty()){
                break;
            }
            System.out.println("Enter ID (0 to cancel)");
            System.out.print(">");
            int id = scanner.nextInt();
            try{



                if(ar.isEmpty()){
                    System.out.println("No bikes found. Add bikes first.");
                    break;
                }
                else if(id < 0 || id > ar.size()){
                    System.out.println("Invalid input. Try again.");
                }else{
                    return id;
                }
            }catch (Exception e){
                System.out.println("Invalid input. Try again.");
            }
        }
        return 0;
    }

    /**
     * deletes the bike and then changes all the id's of each bike, so they are in numarical order without
     * skipping numbers.
     * */
    public static void deleteBike(List <BikeRegistry> ar){
            while(true){
                int id = validInput(ar);
                if(id == 0){
                    break;
                }else{
                    try {
                        ar.remove(ar.get((id - 1)));
                        System.out.printf("Bike %d deleted.\n", id);
                        int i = 1;
                        for(BikeRegistry b: ar){
                            b.setID(i);
                            i++;
                        }
                        break;
                    }catch (Exception e){
                        System.out.println("Error while deleting. Try again.");
                    }
                }
            }
    }

    /**
     * allows you to change the bikes attribute
     * */
    public static void alterBike(List <BikeRegistry> ar){
        boolean isAltering = true;
        Scanner scanner = new Scanner(System.in);
        while(isAltering){
            int id = validInput(ar);
            if(id == 0){
                break;
            }else{
                System.out.println("Which Attribute? (Owner, Type, Serial, Brake, Wheel Size)");
                System.out.print(">");
                String attribute = scanner.nextLine();
                attribute = attribute.toLowerCase(Locale.ROOT);
                try{

                    switch (attribute){
                        case "owner":
                            System.out.print("New Value\n>");
                            String newOwner = scanner.nextLine();
                            ar.get((id - 1)).setOwner(newOwner);
                            isAltering = false;
                            System.out.println("Saved!");
                            break;
                        case "type":
                            System.out.print("New Value\n>");
                            String newType = scanner.nextLine();
                            ar.get((id - 1)).setType(newType);
                            isAltering = false;
                            System.out.println("Saved!");
                            break;
                        case "serial":
                            System.out.print("New Value\n>");
                            String newSerial = scanner.nextLine();
                            ar.get((id - 1)).setSerialNumber(newSerial);
                            isAltering = false;
                            System.out.println("Saved!");
                            break;
                        case "brake":
                            System.out.print("New Value\n>");
                            String newBrake = scanner.nextLine();
                            if(newBrake.toLowerCase().equals("rim")){
                                newBrake = "Rim";
                            }else if(newBrake.toLowerCase().equals("disc")){
                                newBrake = "Disc";
                            }else if(newBrake.toLowerCase().equals("drum")){
                                newBrake = "Drum";
                            }else {
                                System.out.println("Invalid brake type");
                                return;
                            }
                            ar.get((id - 1)).setBrakeType(newBrake);
                            isAltering = false;
                            System.out.println("Saved!");
                            break;
                        case "wheel size":
                            System.out.print("New Value\n>");
                            double newWheelSize = scanner.nextDouble();
                            ar.get((id - 1)).setWheelSize(newWheelSize);
                            isAltering = false;
                            System.out.println("Saved!");
                            break;
                        default:
                            System.out.println("Invalid input. Try again.");
                            break;
                    }

                }catch (Exception e){
                    System.out.println("Error while altering. Try again.");
                }
            }
        }
    }

    /**
     * to string function for printing/debugging
     * */
    public static void objectToString(List <BikeRegistry> ar){
        for(BikeRegistry b : ar){
            System.out.println(b.toString());
        }
    }
}
