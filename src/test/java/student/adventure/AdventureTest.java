package student.adventure;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import com.google.gson.Gson;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class AdventureTest {
    private Gson gson;
    private String myJson;
    private Reader reader;
    private LocationsToGo map;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() throws IOException {
        gson = new Gson();
        myJson = "src/main/java/student/adventure/adventure.json";
        reader = Files.newBufferedReader(Paths.get(myJson));
        map = gson.fromJson(reader, LocationsToGo.class);
        System.setOut(new PrintStream(outputStreamCaptor));
        ByteArrayInputStream inputStream;

    }

    @Test
    public void testEmptyFile() throws IOException{
        myJson = "src/main/java/student/adventure/emptyJson.json";
        reader = Files.newBufferedReader(Paths.get(myJson));
        map = gson.fromJson(reader, LocationsToGo.class);
        map.runGame(0,true);
        assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testEvaluateInputLocation0Quit(){
        Locations loc0ToTest = map.getLocations().get(0);
        map.evaluateInput(loc0ToTest, "qUiT",true);
        assertEquals("Goodbye!", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testEvaluateInputLocation0Exit(){
        Locations loc0ToTest = map.getLocations().get(0);
        map.evaluateInput(loc0ToTest, "eXit",true);
        assertEquals("Goodbye!", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testEvaluateExamine(){
        Locations loc1ToTest = map.getLocations().get(1);
        map.evaluateInput(loc1ToTest, "ExaMiNE",true);
        assertEquals("Examining", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testEvaluateGo(){
        Locations loc3ToTest = map.getLocations().get(3);
        map.evaluateInput(loc3ToTest, "go        north",true);
        assertEquals("Going", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testEvaluateTake(){
        Locations loc3ToTest = map.getLocations().get(3);
        map.evaluateInput(loc3ToTest, "taKE     something",true);
        assertEquals("Taking", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testEvaluateDrop(){
        Locations loc2ToTest = map.getLocations().get(2);
        map.evaluateInput(loc2ToTest, "DROP        north",true);
        assertEquals("Dropping", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testEvaluateUnknownCommand(){
        Locations loc0ToTest = map.getLocations().get(0);
        map.evaluateInput(loc0ToTest, "YEEEHAW",true);
        assertEquals("I don't understand \"YEEEHAW\"", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testExamineLocation0(){
        Locations loc0ToTest = map.getLocations().get(0);
        map.examine(loc0ToTest);
        assertEquals(loc0ToTest.toString() + "\n>", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void testExamineNull() {
        map.examine(null);
        assertEquals("Something went wrong with deserialization", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testGoFromLoc2ToLoc1() {
        Locations loc2ToTest = map.getLocations().get(2);
        Locations loc1ToTest = map.getLocations().get(1);
        map.go(loc2ToTest,"EaSt",true);
        assertEquals(loc1ToTest.toString() + "\n>", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void testGoFromLoc3ToNotPossibleLoc() {
        Locations loc3ToTest = map.getLocations().get(3);
        map.go(loc3ToTest,"north",true);
        assertEquals("I can't go \""+"north"+"\"",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void testGoToWinningLocation() {
        Locations loc1ToTest = map.getLocations().get(1);
        Locations loc4ToTest = map.getLocations().get(4);
        map.go(loc1ToTest,"north",true);
        assertEquals("YOU WON!",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void testTakeItemRoomHas(){
        Locations loc0ToTest = map.getLocations().get(0);
        map.take(loc0ToTest, "five buCKs",true);
        String[] emptyArr = {""};
        assertEquals(emptyArr, loc0ToTest.getItems());
    }

    @Test
    public void testTakeItemRoomDoesNotHave(){
        Locations loc0ToTest = map.getLocations().get(0);
        map.take(loc0ToTest, "five buc",true);
        assertEquals("There is no item \"" + "five buc" + "\" in the room",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void testTakeItemRoomHasAndDrop(){
        Locations loc0ToTest = map.getLocations().get(0);
        map.take(loc0ToTest, "five buCKs",true);
        map.drop(loc0ToTest,"five bucks", true);
        String[] arrWithFiveBucks = {"five bucks"};
        assertEquals(arrWithFiveBucks, loc0ToTest.getItems());
    }

    @Test
    public void testTakeItemFromLoc0DropDuplicateItemAtLoc1(){
        Locations loc0ToTest = map.getLocations().get(0);
        Locations loc1ToTest = map.getLocations().get(1);
        map.take(loc0ToTest,"five bucks", true);
        map.drop(loc1ToTest,"five bucks", true);
        assertEquals("The item \""+"five bucks"+"\" is already in this room!",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void testDropItemUserDoesNotHave(){
        Locations loc0ToTest = map.getLocations().get(0);
        map.drop(loc0ToTest, "2",true);
        assertEquals("You don't have \"2\"!", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testTraversedRooms(){
        Locations loc0ToTest = map.getLocations().get(0);
        Locations loc2ToTest = map.getLocations().get(2);
        Locations loc1ToTest = map.getLocations().get(1);
        List<String> testList = new ArrayList<>();
        testList.add(loc0ToTest.getLocation());
        testList.add(loc2ToTest.getLocation());
        testList.add(loc1ToTest.getLocation());
        map.runGame(loc0ToTest.getPlaceNum(),true);
        map.runGame(loc2ToTest.getPlaceNum(),true);
        map.runGame(loc1ToTest.getPlaceNum(),true);
        assertEquals(testList, map.getTraversedRooms());
    }
}