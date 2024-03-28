/* Add comment block here
 */
public class SmartCard
{
    private int cardID;           // the id of the smartcard
    private char type;            // the type of the smartcard (it can be "C", "A" or "S")
    private double balance;       // the balance available on the smartcard (should have a minimum balance of $5)
    private journey1;             // journey object
    private journey2;             // journey object - can only be used if the type of the smartcard is "A" or "S"
    private journey3;             // journey object - can only be used if the type of the smartcard is "S"

    // add comments
    public void setSmartCardID(int cardID)
    {
        this.cardID = cardID;
    }
    // add comments
    public int getSmartCardID()
    {
        return cardID; 
    }
    // complete all other methods from here.
}
