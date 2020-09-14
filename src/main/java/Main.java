

import com.google.gson.Gson;
import student.adventure.LocationsToGo;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        // TODO: Run an Adventure game on the console
        Gson gson = new Gson();
        String myJson = "src/main/java/student/adventure/adventure.json";
        Reader reader = Files.newBufferedReader(Paths.get(myJson));
        LocationsToGo map = gson.fromJson(reader, LocationsToGo.class);
        map.runGame(0,false);

    }
}