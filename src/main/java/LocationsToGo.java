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

    public void runGame(int locationNum){
        int currentLocNum = locationNum;
        examine(locations.get(currentLocNum));
        askForUserInput(locations.get(currentLocNum));
    }

    private void askForUserInput(Locations location){
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        evaluateInput(location, input);
    }

    public void examine(Locations location){
        if(location != null){
            System.out.println(location.toString() + "\n> ");
        }
    }

    public void evaluateInput(Locations location, String input){
        String inputToCompare = input.replaceAll("\\s+", " ");
        inputToCompare = inputToCompare.toLowerCase();
        //gets first word
        String arr[] = inputToCompare.split(" ", 2);
        String firstWord = "";
        String secondWord = "";
        if(arr.length==1){
            firstWord = arr[0];

        }else if(arr.length==2){
            firstWord = arr[0];
            secondWord = arr[1];
        }
        System.out.println(firstWord);
        System.out.println(secondWord);
        //compares
        if (firstWord.equals("examine")) {
            runGame(location.getPlaceNum());
        } else if(firstWord.equals("go")){
            go(location, secondWord);
        }

    }

    public void go(Locations location, String direction){
        String lowCaseDirection = direction.toLowerCase();
        boolean newLocationExists = false;
        int newCurrentLocationNum = 0;
        for(int i = 0; i< location.getPossibleDirections().size(); i++){
            PossibleDirections currentPossibleDirection = location.getPossibleDirections().get(i);
            if(lowCaseDirection.equals(currentPossibleDirection.getDirection().toLowerCase())){
                newCurrentLocationNum = currentPossibleDirection.getPlaceNum();
                newLocationExists = true;
                break;
            }
        }
        if(!newLocationExists){
            System.out.println("That location does not exist");
        }else{
            runGame(newCurrentLocationNum);
        }
    }


}
