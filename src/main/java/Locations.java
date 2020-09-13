import java.util.Arrays;
import java.util.List;

public class Locations {
    private int placeNum;
    private String location;
    private String description;
    private String[] items;
    private String[] directionDescriptions;
    private List<PossibleDirections> possibleDirections;

    public List<PossibleDirections> getPossibleDirections() {
        return possibleDirections;
    }



    public String getLocation() {
        return location;
    }



    @Override
    public String toString() {
        return "You are at " + location +
                ", " + description +
                "\nItems visible: " + Arrays.toString(items) +
                "\nFrom here, you can see: " + Arrays.toString(directionDescriptions) +
                "\nYou can go: " + Arrays.toString(new List[]{possibleDirections});
    }
}
