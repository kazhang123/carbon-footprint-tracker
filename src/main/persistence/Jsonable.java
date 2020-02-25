package persistence;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

// source: TellerApp
// represents data that can be saved to file
public interface Jsonable {
    // MODIFIES: printWriter
    // EFFECTS: writes the jsonable to printWriter
    void saveJson(FileWriter fileWriter, Object obj) throws IOException;

}
