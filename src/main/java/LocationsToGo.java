import com.google.gson.annotations.Expose;
import com.sun.deploy.util.StringUtils;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LocationsToGo {
    private List<Locations> locations;
    private List<String> pickedUpItems = new ArrayList<>();

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
        //compares
        if(firstWord.equals("quit")||firstWord.equals("exit")){
            System.out.println("Goodbye!");
        } else if (firstWord.equals("examine")) {
            runGame(location.getPlaceNum());
        } else if(firstWord.equals("go")){
            go(location, secondWord);
        } else if(firstWord.equals("take")){
            take(location, secondWord);
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
        if(newCurrentLocationNum == 4){
            examine(locations.get(newCurrentLocationNum));
            System.out.println("YOU WON!");
        } else if(!newLocationExists){
            System.out.println("I can't go \""+direction+"\"");
            runGame(location.getPlaceNum());
        }else{
            runGame(newCurrentLocationNum);
        }
    }

    public void take(Locations location, String item){
        String lowCaseItem = item.toLowerCase();
        boolean itemExists = false;
        String locationItem = "";
        String[] tempItemArray = new String[location.getItems().length];
        int indexWhereItemEqualsArray = 0;
        for(int i =0; i< location.getItems().length; i++){
            locationItem = location.getItems()[i];
            if(lowCaseItem.equals(locationItem.toLowerCase())){
                itemExists = true;
                pickedUpItems.add(lowCaseItem);
                indexWhereItemEqualsArray = i;
                break;
            }
        }

        if (!itemExists) {
            System.out.println("There is no item \"" + item + "\" in the room");
        }else{
            for(int i = 0; i < tempItemArray.length;i++){
                if(i!=indexWhereItemEqualsArray) {
                    tempItemArray[i] = location.getItems()[i];
                }else{
                    tempItemArray[i]="";
                }
            }
            location.setItems(tempItemArray);
        }
        runGame(location.getPlaceNum());
    }


}
