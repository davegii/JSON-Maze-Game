package student.adventure;

import java.util.Arrays;
import java.util.List;

public class Locations {
    private int placeNum;
    private String location;
    private String description;
    private String[] items;
    private String[] directionDescriptions;
    private List<PossibleDirections> possibleDirections;

    @Override
    public String toString() {
        String possibleDirectionsString = "[";
        for(int i = 0; i < possibleDirections.size(); i++){
            possibleDirectionsString+= possibleDirections.get(i).getDirection() + ",";
        }
        possibleDirectionsString = possibleDirectionsString.substring(0,possibleDirectionsString.length()-1);
        possibleDirectionsString += "]";
        return "You are at " + location +
                ", " + description +
                "\nItems visible: " + Arrays.toString(items) +
                "\nFrom here, you can see: " + Arrays.toString(directionDescriptions) +
                "\nYou can go: " + possibleDirectionsString;
    }

    public List<PossibleDirections> getPossibleDirections() {
        return possibleDirections;
    }

    public int getPlaceNum() {
        return placeNum;
    }

    public String getLocation() {
        return location;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }
}
