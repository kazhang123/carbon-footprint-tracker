package persistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;

// source: TellerApp
// a writer that can write carbon footprint log data to a file
public class JsonSimpleWriter {
    public static JSONObject obj = new JSONObject();
    private FileWriter fileWriter;
    public static JSONArray logs = new JSONArray();
    public static JSONArray emissions = new JSONArray();

    // EFFECTS: constructs a writer that will write a json object to a json file
    public JsonSimpleWriter(File file) throws IOException {
        fileWriter = new FileWriter(file);
    }

    // MODIFIES: this
    // EFFECTS: writes jsonable to file
    public void write(Jsonable jsonable) throws IOException {
        jsonable.saveJson(fileWriter);
    }

    // MODIFIES: this
    // EFFECTS: closes file writer
    public void close() throws IOException {
        fileWriter.close();
    }
}
