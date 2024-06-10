/**
 * This class used List based on BikeRegistry. It uses the Class Menu for menu options.
 * @author Tegnoor Gill
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import ca.cmpt213.bike.BikeRegistry;

public class Main {
    public static void main(String[] args) {
        boolean isNotExist = true;
        Scanner scanner = new Scanner(System.in);

        List<BikeRegistry> bikeRegistryList = new ArrayList<>();

        while(isNotExist){
            Menu.displayMainMenu();
            try{
                int option = scanner.nextInt();

                switch (option){
                    case 1:
                        Menu.displayAllBikes(bikeRegistryList);
                        break;
                    case 2:
                        Menu.addNewBike(bikeRegistryList);
                        break;
                    case 3:
                        Menu.deleteBike(bikeRegistryList);
                        break;
                    case 4:
                        Menu.alterBike(bikeRegistryList);
                        break;
                    case 5:
                        Menu.objectToString(bikeRegistryList);
                        break;
                    case 6:
                        isNotExist = false;
                        System.out.println("BYE!");
                        break;
                    default:
                        System.out.println("option out of range, pick a number between 1-6");
                        break;
                }
            }
            catch(Exception e){
                System.out.println("option out of range, pick a number between 1-6");
                scanner.nextLine();
            }
            System.out.println();

        }
        scanner.close();
    }
}
