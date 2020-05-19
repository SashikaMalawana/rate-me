package hello.com.eventratingapp;

public class RoundCompetitor {

    private String name;
    private String hometown;
    private String weightedAverageRating;

    public RoundCompetitor(String name, String hometown, String weightedAverageRating) {
        this.name = name;
        this.hometown = hometown;
        this.weightedAverageRating = weightedAverageRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getWeightedAverageRating() {
        return weightedAverageRating;
    }

    public void setWeightedAverageRating(String weightedAverageRating) {
        this.weightedAverageRating = weightedAverageRating;
    }
}
