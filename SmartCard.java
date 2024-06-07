// Sam Bosworth - Student Number: c3477699
// Alex Rubin - Student Number: c3486124


/*
 * This class represents a SmartCard used for managing travel on the public transport network.
 * It stores the following details about a smartcard:
 *   - cardID: The ID of the smartcard.
 *   - type: The type of the card, which can be "C" for Child, "A" for Adult, or "S" for Senior.
 *   - balance: The balance available on the smartcard.
 * 
 * The class provides methods to:
 *   - Retrieve the card ID, type, and balance.
 *   - Add balance to the smartcard.
 *   - Deduct balance for a journey.
 *   - Check if the balance is sufficient for a journey.
 *   - Check if a given card type is valid.
 */
public class SmartCard { 
    private int cardID;           // the id of the smartcard
    private char type;            // the type of the smartcard (it can be "C", "A" or "S")
    private double balance;       // the balance available on the smartcard
    private Journey[] journeys;
    //private int maxJourneys;
    /**
     * Constructs a SmartCard object with the given card ID, type, and balance.
     * @param cardID The ID of the smartcard.
     * @param type The type of the smartcard ("C", "A", or "S").
     * @param balance The initial balance of the smartcard.
     */
    public SmartCard(int cardID, char type, double balance) {
        this.cardID = cardID;
        this.type = type;
        this.balance = balance;
        this.journeys = new Journey[getMaxJourneys()];
    }

    /**
     * Retrieves the ID of the smartcard.
     * @return The ID of the smartcard.
     */
    public int getSmartCardID() {
        return cardID; 
    }

    /**
     * Retrieves the type of the smartcard.
     * @return The type of the smartcard.
     */
    public char getType() {
        return type;
    }
    public String getTypeFormatted() { // Return a prettified version of the Smart Card Type.
        switch(type) {
            case 'C':
                return "CHILD";
            case 'A':
                return "ADULT";
            case 'S':
                return "SENIOR";
            default:
                return null;    
        }
    }
    /**
     * Retrieves the balance available on the smartcard.
     * @return The balance available on the smartcard.
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * Adds the specified amount to the balance of the smartcard.
     * @param amount The amount to add to the balance.
     */
    public void addBalance(double amount) {
        balance += amount;
    }

    /**
     * Deducts the specified amount from the balance of the smartcard.
     * @param amount The amount to deduct from the balance.
     */
    public void deductBalance(double amount) {
        balance -= amount;
    }

    /**
     * Checks if the balance is sufficient for a journey of the specified amount.
     * @param amount The amount required for the journey.
     * @return true if the balance is sufficient, otherwise false.
     */
    public boolean canDeduct(double amount) {
        return balance >= amount;
    }
    public int getMaxJourneys() {
        switch (type) {
            case 'C': // Children can have a maximum of 1 Journey
                return 1;
            case 'A': // Adults can have a maxiumum of 2 Journeys
                return 2;
            case 'S': // Seniors can have a maximum of 3 Journeys
                return 3;
            default:
                return 0;
        }
    }
    public boolean isDuplicateJourney(Journey j) {
        for(int x = 0; x < journeys.length; x++) {
            if(journeys[x] == null) continue;
            if(j.getStartOfJourney() == journeys[x].getStartOfJourney() && j.getEndOfJourney() == journeys[x].getEndOfJourney() && j.getTransportMode() == journeys[x].getTransportMode())  return true;
        }
        return false;
    }
    public boolean hasReachedMaxJourneys() { // Broken dont use yet.
        switch (type) {
            case 'C': // Children can have a maximum of 1 Journey
                if(journeys.length >= 1) return true;
            case 'A': // Adults can have a maxiumum of 2 Journeys
                if(journeys.length >= 2) return true;
            break;
            case 'S': // Seniors can have a maximum of 3 Journeys
                if(journeys.length >= 3) return true;
            break;
            default:
                return false;
        }
        return false;
    }
    public Journey[] getJourneys() {
        return journeys;
    }
    public Journey getJourneyByID(int id) { // returns null if no journey found with ID.
        for(int x = 0; x < journeys.length; x++ ) {
            if(journeys[x] == null) continue;
            if(journeys[x].getJourneyID() == id) return journeys[x];
        }
        return null;
    }
    public void deleteJourneyByID(int id) {
        for(int x = 0; x < journeys.length; x ++) {
            if(journeys[x] == null) continue;
            if(journeys[x].getJourneyID() == id) journeys[x] = null;
        }
    }
    public static int FillFirstEmpty(SmartCard sc) { // returns index the smartcard was placed at. Returns -1 if no slot was found.
        for(int x = 0; x < SystemInterface.smartCards.length; x++) {
            
            if(SystemInterface.smartCards[x] == null) {
                SystemInterface.smartCards[x] = sc;
                return x;
            }
        }
        return -1;
    }
    public int FillFirstEmptyJourney(Journey j) { // returns index the smartcard was placed at. Returns -1 if no slot was found.
        for(int x = 0; x < journeys.length; x++) {
            
            if(journeys[x] == null) {
                journeys[x] = j;
                j.setParentCard(this);
                return x;
            }
        }
        return -1;
    }
    public static boolean isAnySlotAvailable() {
        for(int x = 0; x < SystemInterface.smartCards.length; x++) {
            if(SystemInterface.smartCards[x] == null) return true; // a slot will NULL found.
        }
        return false; // no slots available
    }
    public static SmartCard getFromID(int id) { // Returns null if not found.
        for(int x = 0; x < SystemInterface.smartCards.length; x++) {
            if(SystemInterface.smartCards[x] == null) continue;
            if(SystemInterface.smartCards[x].cardID == id) return SystemInterface.smartCards[x];
        }
        return null;
    }
    public void Delete() {
        for(int x = 0; x < SystemInterface.smartCards.length; x++) {
            if(SystemInterface.smartCards[x] == this) SystemInterface.smartCards[x] = null;
        }
    }
    public static int getTotalCards() { // returns number of cards currently existing
        int y = 0;
        for(int x = 0; x < SystemInterface.smartCards.length; x++) {
            if(SystemInterface.smartCards[x] != null) {
                y++;
            }
        }
        return y;
    }
    public int getTotalJourneys() {
        int y = 0;
        for(int x = 0; x < journeys.length; x++) {
            if(journeys[x] != null) {
                y++;
            }
        }
        return y;
    }
    /**
     * Checks if the given card type is valid ("C", "A", or "S").
     * @param type The card type to check.
     * @return true if the type is valid, otherwise false.
     */
    public static boolean isValidType(char type) {
        return type == 'C' || type == 'A' || type == 'S';


    }
}
