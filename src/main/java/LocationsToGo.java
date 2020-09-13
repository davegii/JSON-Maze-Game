import com.google.gson.annotations.Expose;
import com.sun.deploy.util.StringUtils;

import javax.xml.stream.Location;
import java.util.List;
import java.util.Scanner;

public class LocationsToGo {
    private List<Locations> locations;

    public List<Locations> getLocations() {
        return locations;
    }

    public void runGame(){
        int currentLocNum = 0;
        examine(locations.get(currentLocNum));
    }

    public void examine(Locations location){
        if(location != null){
            System.out.println(location.toString() + "\n> ");
        }
    }




}
