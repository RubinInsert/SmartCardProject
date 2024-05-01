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
    private Journey journey1;
    private Journey journey2; // Only available for Adults and Seniors
    private Journey journey3; // Only available for Seniors
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
    public boolean hasReachedMaxJourneys() { // Determines whether a card has reached the capacity of Journeys it's specific type can hold.
        switch (type) {
            case 'C': // Children can have a maximum of 1 Journey
                if(journey1 != null) return true;
                break;
            case 'A': // Adults can have a maxiumum of 2 Journeys
                if(journey1 != null && journey2 != null) return true;
            break;
            case 'S': // Seniors can have a maximum of 3 Journeys
                if(journey1 != null && journey2 != null && journey3 != null) return true;
            break;
            default:
                return false;
        }
        return false;
    }
    public int getJourneyCount() { // returns number of journeys on the card
        int count = 0;
        if(journey1 != null) count++;
        if(journey2 != null) count++;
        if(journey3 != null) count++;
        return count;
    }
    public void addJourney(Journey j) {
        reorganiseJourneys();
        if(journey1 == null) journey1 = j;
        else if(journey2 == null) journey2 = j;
        else if(journey3 == null) journey3 = j;
    }
    private void reorganiseJourneys() { // Removes gaps in the 3 Journey variables ( if user deletes one etc. ).
        if(journey1 == null) {
            journey1 = journey2;
            journey2 = journey3;
        }
        if(journey2 == null) {
            journey2 = journey3;
        }
    }
    Journey getJourney(int index) {
        reorganiseJourneys();
        switch (index) {
            case 0: 
                return journey1;
            case 1:
                return journey2;
            case 2:
                return journey3;
            default:
            return null;
        }
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
