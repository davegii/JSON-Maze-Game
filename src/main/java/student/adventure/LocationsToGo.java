package student.adventure;

import student.adventure.Locations;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LocationsToGo {
    private List<Locations> locations;
    private List<String> pickedUpItems = new ArrayList<>();
    private List<String> traversedRooms = new ArrayList<>();

    public List<Locations> getLocations() {
        return locations;
    }

    public List<String> getPickedUpItems() {
        return pickedUpItems;
    }

    public List<String> getTraversedRooms() {
        return traversedRooms;
    }

    /**
     * Runs the game, examining the location object with the given object, and asking for user input
     * @param locationNum is a value for the index of a locations object
     * @param shouldExit is a boolean that, if true, will not recursively call runGame, used for testing
     */
    public void runGame(int locationNum, boolean shouldExit){
        int currentLocNum = locationNum;
        traversedRooms.add(locations.get(currentLocNum).getLocation());
        if (!shouldExit) {
            examine(locations.get(currentLocNum));
            askForUserInput(locations.get(currentLocNum));
        }
    }

    /**
     * Uses scanner to scan for a user's input, then evaluates the input
     * @param location is the location object that that will be used to compare the user input with
     */
    private void askForUserInput(Locations location){
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        evaluateInput(location, input,false);
    }

    /**
     * Prints out the examination of the location
     * @param location is the location object that will have its data used
     */
    public void examine(Locations location){
        if(location != null){
            System.out.print(location.toString() + "\n> ");
        } else{
            System.out.println("Something went wrong with deserialization");
        }
    }

    /**
     * This helper method attempts to separate the words of the user input, calling the appropriate method
     * that matches the input, aka the key words (quit,exit, go, take, etc)
     * CLARIFICATION: (While the comparison used magic strings, it was harder to understand if I were to use a
     * different way of doing it, for example, putting the key words in a string array made it confusing for me to know
     * which one I was comparing, thus I kept the magic strings)
     * @param location is the location object that will have its data compared with the input
     * @param input is a string that is the user input
     * @param shouldExit is a boolean that, if true, will not recursively call runGame, used for testing
     */
    public void evaluateInput(Locations location, String input, boolean shouldExit){
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
            if(shouldExit){
                System.out.println("Examining");
            }else {
                runGame(location.getPlaceNum(),false);
            }
        } else if(firstWord.equals("go")){
            if(shouldExit) {
                System.out.println("Going");
            }else{
                go(location, secondWord, false);
            }
        } else if(firstWord.equals("take")){
            if(shouldExit) {
                System.out.println("Taking");
            }else {
                take(location, secondWord, false);
            }
        } else if(firstWord.equals("drop")){
            if(shouldExit) {
                System.out.println("Dropping");
            }else {
                drop(location, secondWord, false);
            }

        }  else{
            System.out.println("I don't understand \""+input+"\"");
            if(!shouldExit) {
                runGame(location.getPlaceNum(),false);
            }
        }
    }

    /**
     * Helper method that tests if the user can go in the desired direction, if possible, then tests
     * for if the corresponding room is the winning room or not
     * @param location is the location object that will have its data compared with the input
     * @param direction is a string that represents the direction the user wants to go
     * @param shouldExit is a boolean that, if true, will not recursively call runGame, used for testing
     */
    public void go(Locations location, String direction, boolean shouldExit){
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
        if(newCurrentLocationNum == locations.size()-1){
            System.out.println("YOU WON!");
            return;
        } else if(!newLocationExists){
            System.out.println("I can't go \""+direction+"\"");
        }
        if(shouldExit && !newLocationExists){
        } else if(shouldExit) {
            examine(locations.get(newCurrentLocationNum));
        } else if(newCurrentLocationNum == locations.size()-1){
            examine(locations.get(newCurrentLocationNum));
        } else{
            runGame(newCurrentLocationNum,false);
        }

    }

    /**
     * This helper method determines if the object exists to be taken, and if it does,
     * it adds it to an arraylist representing the users inventory and removes the item from the array representing
     * the items in the room
     * @param location is the location object that will have its data compared with the input
     * @param item is a string that is the desired item the user wants to take
     * @param shouldExit is a boolean that, if true, will not recursively call runGame, used for testing
     */
    public void take(Locations location, String item, boolean shouldExit){
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
        if(!shouldExit) {
            runGame(location.getPlaceNum(),false);
        }
    }

    /**
     * This helper method checks if the item exists to be dropped, if it does, then there is another test
     * for if there exists an item already in the room that matches the item that the user wants to drop,
     * if it does, then the method adds the item to the array of items in the room and removes it from the
     * arraylist representing the users inventory
     * @param location is the location object that will have its data compared with the input
     * @param item is a string representing the desired item that the user wants to drop
     * @param shouldExit is a boolean that, if true, will not recursively call runGame, used for testing
     */
    public void drop(Locations location, String item, boolean shouldExit){
        String lowCaseItem = item.toLowerCase();
        boolean itemExists = false;
        String itemToDrop = "";
        String[] tempItemArray = new String[location.getItems().length+1];
        for(int i = 0; i< pickedUpItems.size(); i++){
            itemToDrop = pickedUpItems.get(i);
            if(itemToDrop.equals(lowCaseItem)){
                for(int j=0; j<location.getItems().length;j++){
                    if(lowCaseItem.equals(location.getItems()[j].toLowerCase())){
                        System.out.println("The item \""+item+"\" is already in this room!");
                        if(!shouldExit) {
                            runGame(location.getPlaceNum(),false);
                        }
                        return;
                    }
                }
                itemExists = true;
                pickedUpItems.remove(i);
                break;
            }
        }

        if(itemExists){
            int i =0;
            if(location.getItems().length ==0) {
                for (i = 0; i < tempItemArray.length - 1; i++) {
                    tempItemArray[i] = location.getItems()[i];
                }
            }else{
                tempItemArray = new String[location.getItems().length];
            }
            tempItemArray[i] = item;
            location.setItems(tempItemArray);
        }else{
            System.out.println("You don't have \"" + item + "\"!");
        }
        if(!shouldExit) {
            runGame(location.getPlaceNum(),false);
        }
    }


}
