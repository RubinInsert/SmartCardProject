// Sam Bosworth - Student Number: c3477699
// Alex Rubin - Student Number: c3486124
public class Journey {
    private int journeyID;
    private String transportMode;
    private int startOfJourney;
    private int endOfJourney;
    private int distanceOfJourney;

    public Journey(int journeyID, String transportMode, int startOfJourney, int endOfJourney, int distanceOfJourney) {
        this.journeyID = journeyID;
        this.transportMode = transportMode;
        this.startOfJourney = startOfJourney;
        this.endOfJourney = endOfJourney;
        this.distanceOfJourney = distanceOfJourney;
    }

    // Getters and Setters
    public int getJourneyID() {
        return journeyID;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    public int getStartOfJourney() {
        return startOfJourney;
    }

    public void setStartOfJourney(int startOfJourney) {
        this.startOfJourney = startOfJourney;
    }

    public int getEndOfJourney() {
        return endOfJourney;
    }

    public void setEndOfJourney(int endOfJourney) {
        this.endOfJourney = endOfJourney;
    }

    public int getDistanceOfJourney() {
        return distanceOfJourney;
    }

    public void setDistanceOfJourney(int distanceOfJourney) {
        this.distanceOfJourney = distanceOfJourney;
    }
    void printTruncated() {
        System.out.println("    Journey " + this.getJourneyID() + " has transport mode " + this.getTransportMode());
    }
    void print() {
        System.out.println("Journey " + this.getJourneyID() + " has transport mode " + this.getTransportMode() + " starting from " + this.getStartOfJourney() + " and ending at " + this.getEndOfJourney() + " with journey distance of " + this.getDistanceOfJourney() + " station(s) / stop(s)");
    }
}
