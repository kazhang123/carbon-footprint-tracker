package persistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;

// source: TellerApp
// a writer that can write carbon footprint log data to a file
public class JsonWriter {
    private FileWriter fileWriter;
    private JSONArray jsonLogs;

    // EFFECTS: constructs a writer that will write a savedLogs.json object to a savedLogs.json file
    public JsonWriter(File file) throws IOException {
        try {
            jsonLogs = JsonReader.getJsonLogs(file);
        } catch (ParseException e) {
            jsonLogs = new JSONArray();
        }
        fileWriter = new FileWriter(file);
    }

    // MODIFIES: this
    // EFFECTS: writes jsonable to file
    public void write(Jsonable jsonable) throws IOException {
        jsonable.saveJson(fileWriter, jsonLogs);
    }

    // MODIFIES: this
    // EFFECTS: closes file writer
    public void close() throws IOException {
        fileWriter.close();
    }
}
