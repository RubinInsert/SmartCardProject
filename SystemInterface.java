import java.util.Scanner;

/* 
 * TODO
 * - Dont allow ID's to be less than zero (Journey, Smartcard)
 * - Error is thrown when a non-int is given for a "Start of Journey" and "End of Journey", and i suspect many other integer inputs
 * - 
 */
public class SystemInterface
{
    Scanner input;
    SmartCard smartCard1;
    SmartCard smartCard2;
    SmartCard smartCard3;
    ConsoleUtil consoleUtil; // Cant use static objects, have to make do with this :(
    SmartCardUtil smartCardUtil;
    private void run()
    {
        input = new Scanner(System.in);
        consoleUtil = new ConsoleUtil();
        smartCardUtil = new SmartCardUtil();
        consoleUtil.clearScreen();
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
        System.out.println("6. List Transport specific Journeys");
        System.out.println("7. List Journeys on Smart Card");
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
                deleteJourney();
                break;
            case 5: // List Smart Cards
                smartCardUtil.listAllSmartCards();
                displayMainScreen();
                break;
            case 6: // List Journeys on Smart Card
                listJourneysWithTransportMode();
                break;
            case 7: // List Transport specific Journeys
                listJourneysOnSmartCard();
                break;
            case 8: // Total Fare Costs
                calculateAndDisplayFareCosts();
                break;
            default:
            consoleUtil.showError("Page Number Invalid!");
                break;
        }
    }
    void createSmartCard() {
        if(smartCardUtil.getSmartCardCount() < 3 ) { // If there is an empty card slot available 
            char type = ' ';
            double balance = 0;
            consoleUtil.clearScreen();
            int cardID;
            int executionCount = 0;
            do {
                if(executionCount > 0) consoleUtil.showError("Please enter an ID Number greater than or equal to zero and is not currently being used by existing Smart Cards.");
                executionCount++;
                System.out.print("Enter a number for the Card ID (greater than or equal to zero): ");
                cardID = input.nextInt();
                for(int x = 0; x < smartCardUtil.getSmartCardCount(); x++) { // Loop over existing cards
                    if(cardID == smartCardUtil.getSmartCard(x).getSmartCardID()) { // Check if the new ID given by user matches any existing cards and throw error if a match is found.
                        cardID = -1; // trigger error in next while loop.
                    }
                }
            } while (cardID < 0);
            executionCount = 0;
          
            consoleUtil.clearScreen();
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
                    consoleUtil.showError("The number you inputted was out of the range of the menu!");
                    displayMainScreen();
                    return;
            }
            boolean check = false;
            while (!check) {
                consoleUtil.clearScreen();
                System.out.println("Enter the balance you wish to load onto the card: ");
                balance = input.nextDouble();
                if (balance < 5) {
                    consoleUtil.clearScreen();
                    consoleUtil.showError("The balance you entered is too low, please enter a balance greater than 5.");
                } else {
                    check = true;
                    System.out.println("SmartCard created under ID: " + cardID);
                    smartCardUtil.sortSmartCard(new SmartCard(cardID, type, balance));
                    consoleUtil.waitForKeyPress(); 
                    consoleUtil.clearScreen();
                    displayMainScreen();  }
                }}
               
        else { // There is no empty card slot
            consoleUtil.showError("You have reached max number of cards. You cannot create a new card until you delete one!");
            displayMainScreen();
            }
    }

    void deleteSmartCard() {
        consoleUtil.clearScreen();
        System.out.print("Enter the Smart Card ID you want to delete: ");
        int idToDelete = input.nextInt(); 
        if(idToDelete < 0) {
            consoleUtil.showError("ID cannot be negative");
            displayMainScreen();
        }
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
            consoleUtil.showError("Smart Card with ID " + idToDelete + " not found.");
        }
        consoleUtil.waitForKeyPress(); 
        consoleUtil.clearScreen();
        displayMainScreen();
    }

    void createJourney() {
        if(smartCardUtil.isAnySmartCardsCreated() == false) {
            consoleUtil.showError("A SmartCard must be created prior to creating a Journey!"); // If there are no smartcards active, dont allow user to create a journey
            displayMainScreen();
        }
        int executionCount = 0;
        String transportMode;
        int startOfJourney, endOfJourney;
        consoleUtil.clearScreen();
        input.nextLine(); // Consume newline character
        System.out.println("============================Creating a new Journey============================");

        // Prompt user for journey details
        System.out.print("Enter Journey ID: ");
    
    
        int journeyID = input.nextInt();

        //input.nextLine(); // Consume newline character

        do {
        if(executionCount > 0) consoleUtil.showError("Please enter only one of the provided responses."); // If this isnt the first time executing this code, send an error to user.
        System.out.print("Enter Transport Mode (train/bus/tram): ");
        transportMode = input.next().toUpperCase().trim(); // Make response uppercase for consistency, and trim any spaces before or after text. I.e. "  train" --> "TRAIN"
        executionCount++;
        } while (!transportMode.equals("TRAIN") // If response does not match TRAIN
                && !transportMode.equals("BUS") // or BUS
                && !transportMode.equals("TRAM")); // or TRAM, repeat question and ask for input until user inputs correctly
        executionCount = 0;

        do {
            if(executionCount > 0) consoleUtil.showError("Please enter only a number between 1 and 10"); // If this isnt the first time executing this code, send an error to user.
            System.out.print("Enter Start of Journey (1-10): ");
            startOfJourney = input.nextInt();
            executionCount++;
        } while (startOfJourney < 1 || startOfJourney > 10);
        executionCount = 0;
        do {
            if(executionCount > 0) consoleUtil.showError("Please enter only a number between 1 and 10"); // If this isnt the first time executing this code, send an error to user.
            System.out.print("Enter End of Journey (1-10, different from start): ");
            endOfJourney = input.nextInt();
            executionCount++;
        } while (endOfJourney < 1 || endOfJourney > 10); // If user input is not within defined ranges ask question again.
        executionCount = 0;

        int distanceOfJourney = endOfJourney - startOfJourney; // Calculate distance of journey
        distanceOfJourney = distanceOfJourney < 0 ? -1 * distanceOfJourney : distanceOfJourney; // ensure value is positive
        // Create the journey object
        Journey newJourney = new Journey(journeyID, transportMode, startOfJourney, endOfJourney, distanceOfJourney);

        // Add the journey to a smart card
        System.out.println("Which Smart Card would you like to add this journey to?");
        int chosenCardMenuNumber;
        int menuNumberTracker = 0; // Incase a card must be removed from the list, this number keeps track of the menu number so it doesnt skip a number.
        for(int x = 0; x < smartCardUtil.getSmartCardCount(); x++) {
            if(smartCardUtil.getSmartCard(x).hasReachedMaxJourneys()) continue; // Do not display cards which cannot have any journeys added.
            menuNumberTracker++;
            System.out.println(menuNumberTracker + ". ID: " + smartCardUtil.getSmartCard(x).getSmartCardID() + " || Card Type: " + smartCardUtil.getSmartCard(x).getType()); 
        }
        do {
            if(executionCount > 0) consoleUtil.showError("Please enter a valid menu number (Between 1-" + menuNumberTracker + ")");
            System.out.print("Enter a menu number: ");
            chosenCardMenuNumber = input.nextInt();
            consoleUtil.clearScreen();
            executionCount++;
        } while (chosenCardMenuNumber < 1 || chosenCardMenuNumber > smartCardUtil.getAvailableSmartCardCount());
        int y=0;
        for(int x = 0; x < smartCardUtil.getSmartCardCount(); x++) { // Loop through again to find the card which corresponds with the menu number provided by the user
            if(smartCardUtil.getSmartCard(x).hasReachedMaxJourneys()) continue; // Skip cards with no journeys available, just like the menu.
            y++;
            if(y == chosenCardMenuNumber) {
                smartCardUtil.getSmartCard(x).addJourney(newJourney); // Add the journey to the corresponding card.
                System.out.println("Journey created successfully in Smart Card ID: " + smartCardUtil.getSmartCard(x).getSmartCardID());
            }
        }
        
        consoleUtil.waitForKeyPress();
        displayMainScreen();
    }
    public class SmartCardUtil { // Access functions by prefixing with SmartCardHelperFunctions..... SmartCardUtil.getSmartCard(3);
        int getSmartCardCount() { // Gets total number of SmartCards which exist.
            int count = 0;
            if(smartCard1 != null) count++;
            if(smartCard2 != null) count++;
            if(smartCard3 != null) count++;
            return count;
        }
        int getAvailableSmartCardCount() { // Gets total number of SmartCards which dont have all their journeys full.
            int count = 0;
            if(smartCard1 != null && !smartCard1.hasReachedMaxJourneys()) count++;
            if(smartCard2 != null && !smartCard2.hasReachedMaxJourneys()) count++;
            if(smartCard3 != null && !smartCard3.hasReachedMaxJourneys()) count++;
            return count;
        }
        SmartCard getSmartCard(int index) { // Ugly function to get around the fact we cant use arrays. Allows you to index smartcards. I.e getSmartCard(2) returns the second available smart card.
            switch (index) {
                case 0: 
                if(smartCard1 != null) return smartCard1;
                if(smartCard2 != null) return smartCard2;
                if(smartCard3 != null) return smartCard3;
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
        // Checks if smartcard is created and if so prints it out. 
        void listAllSmartCards() { 
            consoleUtil.clearScreen();
        if (smartCard1 != null) {
            smartCard1.print();
        }
        if (smartCard2 != null) {
            smartCard2.print();
        }
        if (smartCard3 != null) {
            smartCard3.print();
        }
        consoleUtil.waitForKeyPress();
    }

        void sortSmartCard(SmartCard sc) { // Place the new smartcard in the earliest available slot
            if(smartCard1 == null) { 
                smartCard1 = sc;
            } else if(smartCard2 == null) {
                smartCard2 = sc;
            } else if(smartCard3 == null) {
                smartCard3 = sc;
            }
        }
        boolean isAnySmartCardsCreated() {
            if(smartCard1 == null && smartCard2 == null && smartCard3 == null) return false;
            return true;
        }
    }
    public class ConsoleUtil {
        public void waitForKeyPress() { // Wait for user to press Enter Key before proceeding with code execution.
            System.out.println("<Press Enter to Continue>");
            input.nextLine(); // Consume first line.
            input.nextLine();
            
        }
        public void clearScreen() { // Creates a lot of new lines to clear the window -> Not system specific unlike "cls" for windows.
        System.out.println(System.lineSeparator().repeat(100));
        }
        public void showError(String errorMessage) {
            consoleUtil.clearScreen();
            System.out.println("==============================Error==============================");
            System.out.println(errorMessage);
            System.out.println("=================================================================");
            consoleUtil.waitForKeyPress();
            consoleUtil.clearScreen();
        }
    
    }

    

    void calculateAndDisplayFareCosts() {
        double totalFare = 0;
        System.out.println("Total transport mode journeys cost/fare:");
        System.out.println("---------------------------------------------------------");

        // Calculate total fare for all journeys
        for (int i = 0; i < smartCardUtil.getSmartCardCount(); i++) {
            SmartCard currentCard = smartCardUtil.getSmartCard(i);
            if (currentCard != null) {
                for (int j = 0; j < currentCard.getJourneyCount(); j++) {
                    Journey journey = currentCard.getJourney(j);
                    if (journey != null) {
                        double fare = calculateFare(journey.getTransportMode(), currentCard.getType(), journey.getDistanceOfJourney());
                        totalFare += fare;
                        System.out.println("Total cost of " + journey.getTransportMode() + " journeys is $" + fare);
                    }
                }
            }
        }

        // Display total fare
        System.out.println("---------------------------------------------------------");
        System.out.println("Total Fare: $" + totalFare);

        // Breakdown by smart card
        System.out.println("Breakdown by smartcard:");
        System.out.println("---------------------------------------------------------");
        for (int i = 0; i < smartCardUtil.getSmartCardCount(); i++) {
            SmartCard currentCard = smartCardUtil.getSmartCard(i);
            if (currentCard != null) {
                double cardFare = 0;
                for (int j = 0; j < currentCard.getJourneyCount(); j++) {
                    Journey journey = currentCard.getJourney(j);
                    if (journey != null) {
                        cardFare += calculateFare(journey.getTransportMode(), currentCard.getType(), journey.getDistanceOfJourney());
                    }
                }
                System.out.println("SmartCard " + currentCard.getSmartCardID() + ":");
                System.out.println("Total fare: $" + cardFare);
            }
        }
    }
        double calculateFare(String transportMode, char cardType, int distanceOfJourney) {
        double baseFare = 1.5; // Base fare for all types of smart cards
        double farePerDistance;
        switch (Character.toUpperCase(cardType)) {
            case 'C':
                farePerDistance = 1.86;
                break;
            case 'A':
                farePerDistance = 2.24;
                break;
            case 'S':
                farePerDistance = 1.60;
                break;
            default:
                farePerDistance = 0;
        }
        double totalFare = baseFare + farePerDistance * distanceOfJourney;
        totalFare = Math.round(totalFare * 100.0) / 100.0;
        return totalFare;
        
    }

    void deleteJourney() {
        consoleUtil.clearScreen();
        System.out.print("Enter the Smart Card ID: ");
        int cardID = input.nextInt();
        System.out.print("Enter the Journey ID you want to delete: ");
        int journeyID = input.nextInt();

        SmartCard smartCard = findSmartCardByID(cardID);
        if (smartCard != null) {
            smartCard.deleteJourney(journeyID);
        } else {
            consoleUtil.showError("Smart Card with ID " + cardID + " not found.");
        }
        consoleUtil.waitForKeyPress(); 
        displayMainScreen();
    }

    SmartCard findSmartCardByID(int cardID) {
        if (smartCard1 != null && smartCard1.getSmartCardID() == cardID) {
            return smartCard1;
        } else if (smartCard2 != null && smartCard2.getSmartCardID() == cardID) {
            return smartCard2;
        } else if (smartCard3 != null && smartCard3.getSmartCardID() == cardID) {
            return smartCard3;
        }
        return null;
    }


    void listJourneysWithTransportMode() {
        consoleUtil.clearScreen();
        System.out.print("Enter the transport mode: ");
        String transportMode = input.next().trim().toUpperCase(); // Convert input to uppercase for consistency

        boolean foundJourney = false;

        // Iterate through all smartcards and their journeys
        for (int i = 0; i < smartCardUtil.getSmartCardCount(); i++) {
            SmartCard currentCard = smartCardUtil.getSmartCard(i);
            if (currentCard != null) {
                for (int j = 0; j < currentCard.getJourneyCount(); j++) {
                    Journey journey = currentCard.getJourney(j);
                    if (journey != null && journey.getTransportMode().equals(transportMode)) {
                        System.out.println("Journey " + journey.getJourneyID() + " has transport mode " + transportMode +
                                ", and it belongs to smartcard " + currentCard.getSmartCardID());
                        foundJourney = true;
                    }
                }
            }
        }

        // If no journeys match the specified transport mode, display a message
        if (!foundJourney) {
            System.out.println("No journeys with that transport mode.");
        }

        consoleUtil.waitForKeyPress();
        consoleUtil.clearScreen();
        displayMainScreen();
    }
    void listJourneysOnSmartCard() {
        int executionCount = 0;
        System.out.println("Which Smart Card would you like to list Journeys from? ");
        int chosenCardMenuNumber;
        for(int x = 0; x < smartCardUtil.getSmartCardCount(); x++) {
            System.out.println((x+1) + ". ID: " + smartCardUtil.getSmartCard(x).getSmartCardID() + " || Card Type: " + smartCardUtil.getSmartCard(x).getType()); 
        }
        do {
            if(executionCount > 0) consoleUtil.showError("Please enter a valid menu number (Between 1-" + smartCardUtil.getSmartCardCount() + ")");
            System.out.print("Enter a menu number: ");
            chosenCardMenuNumber = input.nextInt();
            consoleUtil.clearScreen();
            executionCount++;
        } while (chosenCardMenuNumber < 1 || chosenCardMenuNumber > smartCardUtil.getSmartCardCount());
        for(int x = 0; x < smartCardUtil.getSmartCardCount(); x++) { // Loop through again to find the card which corresponds with the menu number provided by the user
            if((x+1) == chosenCardMenuNumber) {
                SmartCard chosenSmartCard = smartCardUtil.getSmartCard(x);
                if(chosenSmartCard.getJourneyCount() == 0) {
                    consoleUtil.showError("The selected card has no Journeys!");
                    displayMainScreen();
                }
                for(int j = 0; j < chosenSmartCard.getJourneyCount(); j++) {
                    chosenSmartCard.getJourney(j).print();
                }
                consoleUtil.waitForKeyPress();
                displayMainScreen();
            }
        }
    }
    public static void main(String[] args)
    {
        SystemInterface systemUI = new SystemInterface();
        systemUI.run();
    }
    
}
