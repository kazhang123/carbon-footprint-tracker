package persistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;

// source: TellerApp
// a writer that can write carbon footprint log data to a file
public class JsonSimpleWriter {
    private JSONObject obj;
    private FileWriter fileWriter;
    public static JSONArray logs = new JSONArray();

    // EFFECTS: constructs a writer that will write a json object to a json file
    public JsonSimpleWriter(File file) throws IOException {
        obj = new JSONObject();
        fileWriter = new FileWriter(file);
    }

    // MODIFIES: this
    // EFFECTS: writes jsonable to file
    public void write(Jsonable jsonable) throws IOException {
        jsonable.saveJson(fileWriter, obj);
    }

    // MODIFIES: this
    // EFFECTS: closes file writer
    public void close() throws IOException {
        fileWriter.close();
    }
}
