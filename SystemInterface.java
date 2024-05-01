import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/* DONE:
 * - Main Screen
 * - Create Smart Card Screen
 * - Create Journey Screen
 * - Delete Smart Card Screen
 * - List Smart Cards Screen
 * TODO:
 * - Delete a Journey
 * - List the Journeys on a Smart Card
 * - List of Journeys with a particular transport mode and which smartcard they belong to
 * - Summary of total cost/fare for all transportation modes journeys made by all Smartcards registered in the system.
 */
public class SystemInterface
{
    static Scanner input;
    static int currentIDNumber = 0; // Tracks the current Smart Card number so no smart card has the same ID
    static SmartCard smartCard1;
    static SmartCard smartCard2;
    static SmartCard smartCard3;
    private void run()
    {
        ConsoleUtil.clearScreen();
        displayMainScreen();
        //This method should control the flow of the program
    }
    int displayMainScreen() { // Returns the number the user returns when selecting a menu item. I.e. "Create a new Smart Card" -> returns 1.
        System.out.println("===============- Smart Card Project - Main Menu -===============");
        System.out.println("1. Create a new Smart Card");
        System.out.println("2. Create a new Journey");
        System.out.println("3. Delete a Smart Card");
        System.out.println("4. Delete a Journey");
        System.out.println("5. List Smart Cards");
        System.out.println("6. List Journeys on Smart Card");
        System.out.println("7. List Transport specific Journeys");
        System.out.println("8. Total Fare Costs");
        System.out.println("=================================================================");
        System.out.print("Input Menu Number: ");
        int in = input.nextInt();
        selectPage(in);
        return in;
    }
    void selectPage(int pageNumber) {
        switch (pageNumber) {
            case 0:
                displayMainScreen();
                break;
            case 1: // Create a new Smart Card
                createSmartCard();
                break;
            case 2: // Create a new Journey
                createJourney();
                break;
            case 3: // Delete a Smart Card
                deleteSmartCard();
                break;
            case 4: // Delete a Journey
                break;
            case 5: // List Smart Cards
                SmartCardUtil.listAllSmartCards();
                displayMainScreen();
                break;
            case 6: // List Journeys on Smart Card
                break;
            case 7: // List Transport specific Journeys
                break;
            case 8: // Total Fare Costs
                break;
            default:
            ConsoleUtil.showError("Page Number Invalid!");
                break;
        }
    }
    void createSmartCard() {
        if(smartCard1 == null || smartCard2 == null || smartCard3 == null) { // If there is an empty card slot available 
            char type = ' ';
            double balance;
            ConsoleUtil.clearScreen();
            System.out.println("What Type of Card are you creating?");
            System.out.println("1. Child");
            System.out.println("2. Senior");
            System.out.println("3. Adult");
            System.out.print("Enter menu number: ");
            int in = input.nextInt();
            switch (in) {
                case 1: // User chose "Child"
                    type = 'C';
                    break;
                case 2: // User chose "Senior"
                    type = 'S';
                    break;
                case 3: // User chose "Adult"
                    type = 'A';
                    break;
                default:
                    ConsoleUtil.showError("The number you inputted was out of the range of the menu!");
                    displayMainScreen();
                    return;
            }
            System.out.println("Enter the balance you wish to load onto the card: "); // TODO: Add input checks e.g. if user inputs negative number, or some bs.
            balance = input.nextDouble();
            SmartCard newTempCard = new SmartCard(currentIDNumber, type, balance);
            System.out.println("SmartCard created under ID: " + currentIDNumber);
            currentIDNumber += 1; // add one to the global variable to make sure the next card has a new number for it's ID.
            SmartCardUtil.sortSmartCards(newTempCard);
            displayMainScreen();
            
        } else { // There is no empty card slot
            ConsoleUtil.showError("You have reached max number of cards. You cannot create a new card until you delete one!");
            displayMainScreen();
        }
    }



    void deleteSmartCard() {
        ConsoleUtil.clearScreen();
        System.out.print("Enter the Smart Card ID you want to delete: ");
        int idToDelete = input.nextInt();
        if (smartCard1 != null && smartCard1.getSmartCardID() == idToDelete) {
            smartCard1 = null;
            System.out.println("Smart Card with ID " + idToDelete + " has been deleted.");
        } else if (smartCard2 != null && smartCard2.getSmartCardID() == idToDelete) {
            smartCard2 = null;
            System.out.println("Smart Card with ID " + idToDelete + " has been deleted.");
        } else if (smartCard3 != null && smartCard3.getSmartCardID() == idToDelete) {
            smartCard3 = null;
            System.out.println("Smart Card with ID " + idToDelete + " has been deleted.");
        } else {
            ConsoleUtil.showError("Smart Card with ID " + idToDelete + " not found.");
        }
        ConsoleUtil.waitForKeyPress();
        ConsoleUtil.clearScreen();
        displayMainScreen();
    }

    void createJourney() {
        if(SmartCardUtil.isAnySmartCardsCreated() == false) {
            ConsoleUtil.showError("A SmartCard must be created prior to creating a Journey!"); // If there are no smartcards active, dont allow user to create a journey
            displayMainScreen();
        }
        int executionCount = 0;
        String transportMode;
        int startOfJourney, endOfJourney;
        ConsoleUtil.clearScreen();
        input.nextLine(); // Consume newline character
        System.out.println("============================Creating a new Journey============================");

        // Prompt user for journey details
        System.out.print("Enter Journey ID: ");
        int journeyID = input.nextInt();

        //input.nextLine(); // Consume newline character



        do {
        if(executionCount > 0) ConsoleUtil.showError("Please enter only one of the provided responses."); // If this isnt the first time executing this code, send an error to user.
        System.out.print("Enter Transport Mode (train/bus/tram): ");
        transportMode = input.next().toUpperCase().trim(); // Make response uppercase for consistency, and trim any spaces before or after text. I.e. "  train" --> "TRAIN"
        executionCount++;
        } while (!transportMode.equals("TRAIN") // If response does not match TRAIN
                && !transportMode.equals("BUS") // or BUS
                && !transportMode.equals("TRAM")); // or TRAM, repeat question and ask for input until user inputs correctly
        executionCount = 0;

        do {
            if(executionCount > 0) ConsoleUtil.showError("Please enter only a number between 1 and 10"); // If this isnt the first time executing this code, send an error to user.
            System.out.print("Enter Start of Journey (1-10): ");
            startOfJourney = input.nextInt();
            executionCount++;
        } while (startOfJourney < 1 || startOfJourney > 10);
        executionCount = 0;
        do {
            if(executionCount > 0) ConsoleUtil.showError("Please enter only a number between 1 and 10"); // If this isnt the first time executing this code, send an error to user.
            System.out.print("Enter End of Journey (1-10, different from start): ");
            endOfJourney = input.nextInt();
            executionCount++;
        } while (endOfJourney < 1 || endOfJourney > 10); // If user input is not within defined ranges ask question again.
        executionCount = 0;
        /* Oi sam ya dog, the end of the journey needs to be
         * a further stop than the start journey right? Like
         * I should check if the endOfJourney is larger than 
         * startOfJourney and throw and error if it isnt?
         */


        System.out.print("Enter Distance of Journey: "); // What the flip do we do here gang
        int distanceOfJourney = input.nextInt();

        // Create the journey object
        Journey newJourney = new Journey(journeyID, transportMode, startOfJourney, endOfJourney, distanceOfJourney);

        // Add the journey to a smart card (not implemented yet)
        System.out.println("Which Smart Card would you like to add this journey to?");
        int chosenCardMenuNumber;
        int menuNumberTracker = 0; // Incase a card must be removed from the list, this number keeps track of the menu number so it doesnt skip a number.
        for(int x = 0; x < SmartCardUtil.getSmartCardCount(); x++) {
            if(SmartCardUtil.getSmartCard(x).hasReachedMaxJourneys()) continue; // Do not display cards which cannot have any journeys added.
            menuNumberTracker++;
            System.out.println(menuNumberTracker + ". ID: " + SmartCardUtil.getSmartCard(x).getSmartCardID() + " || Card Type: " + SmartCardUtil.getSmartCard(x).getType()); 
        }
        do {
            if(executionCount > 0) ConsoleUtil.showError("Please enter a valid menu number (Between 1-" + menuNumberTracker + ")");
            System.out.print("Enter Menu Number: ");
            chosenCardMenuNumber = input.nextInt();
            ConsoleUtil.clearScreen();
            executionCount++;
        } while (chosenCardMenuNumber < 1 || chosenCardMenuNumber > SmartCardUtil.getAvailableSmartCardCount());
        int y=0;
        for(int x = 0; x < SmartCardUtil.getSmartCardCount(); x++) { // Loop through again to find the card which corresponds with the menu number provided by the user
            if(SmartCardUtil.getSmartCard(x).hasReachedMaxJourneys()) continue; // Skip cards with no journeys available, just like the menu.
            y++;
            if(y == chosenCardMenuNumber) {
                SmartCardUtil.getSmartCard(x).addJourney(newJourney); // Add the journey to the corresponding card.
                System.out.println("Journey created successfully in Smart Card ID: " + SmartCardUtil.getSmartCard(x).getSmartCardID());
            }
        }
        
        ConsoleUtil.waitForKeyPress();
        displayMainScreen();
    }
    public static class SmartCardUtil { // Access functions by prefixing with SmartCardHelperFunctions..... SmartCardUtil.getSmartCard(3);
        static int getSmartCardCount() { // Gets total number of SmartCards which exist.
            int count = 0;
            if(smartCard1 != null) count++;
            if(smartCard2 != null) count++;
            if(smartCard3 != null) count++;
            return count;
        }
        static int getAvailableSmartCardCount() { // Gets total number of SmartCards which dont have all their journeys full.
            int count = 0;
            if(smartCard1 != null && !smartCard1.hasReachedMaxJourneys()) count++;
            if(smartCard2 != null && !smartCard2.hasReachedMaxJourneys()) count++;
            if(smartCard3 != null && !smartCard3.hasReachedMaxJourneys()) count++;
            return count;
        }
        static SmartCard getSmartCard(int index) { // Ugly function to get around the fact we cant use arrays. Allows you to index smartcards. I.e getSmartCard(2) returns the second available smart card.
            switch (index) {
                case 0: 
                if(smartCard1 != null) return smartCard1;
                if(smartCard2 != null) return smartCard1;
                if(smartCard3 != null) return smartCard1;
                break;
                case 1:
                if (smartCard1 != null) {
                    if(smartCard2 != null) return smartCard2;
                    else if(smartCard3 != null) return smartCard3;
                    return null;
                }
                break;
                case 2:
                if(smartCard3 != null) return smartCard3;
                break;
                default:
                return null;
            }
            return null;
        }
        static void printSmartCard(SmartCard sc) {
            if(sc == null) return;
            System.out.println("Smartcard " + sc.getSmartCardID() + " has type " + sc.getType() + " and " + sc.getJourneyCount() + " journey(s)");
            for(int x=0; x < sc.getJourneyCount(); x++) {
                if(sc.getJourney(x) != null) JourneyUtil.printJourneyTruncated(sc.getJourney(x));
                
            }

    
        }
        static void listAllSmartCards() {
            ConsoleUtil.clearScreen();
            printSmartCard(smartCard1);
            printSmartCard(smartCard2);
            printSmartCard(smartCard3);
            ConsoleUtil.waitForKeyPress();
        }
        static void sortSmartCards(SmartCard sc) { // Place the new smartcard in the earliest available slot
            if(smartCard1 == null) { 
                smartCard1 = sc;
            } else if(smartCard2 == null) {
                smartCard2 = sc;
            } else if(smartCard3 == null) {
                smartCard3 = sc;
            }
        }
        static boolean isAnySmartCardsCreated() {
            if(smartCard1 == null && smartCard2 == null && smartCard3 == null) return false;
            return true;
        }
    }
    public static class JourneyUtil {
        static void printJourneyTruncated(Journey j) {
            if(j == null) return;
            System.out.println("    Journey " + j.getJourneyID() + " has transport mode " + j.getTransportMode());
        }
        static void printJourney(Journey j) {
            if(j == null) return;
            System.out.println("Journey " + j.getJourneyID() + " has transport mode " + j.getTransportMode() + " starting from " + j.getStartOfJourney() + " and ending at " + j.getEndOfJourney() + " with journey distance of " + j.getDistanceOfJourney() + " station(s) / stop(s)");
        }
    }
    public static class ConsoleUtil {
        public static void waitForKeyPress() { // Wait for user to press Enter Key before proceeding with code execution.
            System.out.println("<Press Enter to Continue>");
            try{
                System.in.read();
            }catch(Exception e){}
            
        }
        public static void clearScreen() { // Creates a lot of new lines to clear the window -> Not system specific unlike "cls" for windows.
        System.out.println(System.lineSeparator().repeat(100));
        }
        public static void showError(String errorMessage) {
            ConsoleUtil.clearScreen();
            System.out.println("==============================Error==============================");
            System.out.println(errorMessage);
            System.out.println("=================================================================");
            ConsoleUtil.waitForKeyPress();
            ConsoleUtil.clearScreen();
        }
    
    }

    public static void main(String[] args)
    {
        SystemInterface systemUI = new SystemInterface();
        input = new Scanner(System.in);
        systemUI.run();
    }
    
}
