public class Journey
{
    private int journeyID;                 // the id of the journey
    private String transportMode;          // the public transport mode of the journey (can be only “train”, “bus” or “tram”) 
    private int startOfJourney;            // the starting point of the journey. It can be only a number between [1..10] 
    private int endOfJourney;              // the ending point of the journey. It can be only a number between [1..10] (should be different from the starting point of the journey)
    private int distanceOfJourney;         // the distance of the journey (i.e. the difference in number of stations/stops travelled between startOfJourney and endOfJourney)
    public Journey() {

    }
    // add comments
    public void setTransportMode(String transportMode)
    {
        this.transportMode = transportMode;
    }
    // add comments
    public String getTransportMode()
    {
        return transportMode;
    }
    // complete all other methods from here.
}
