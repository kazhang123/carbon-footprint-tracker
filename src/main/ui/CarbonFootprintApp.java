package ui;

import model.emission.CarbonFootprintLog;
import model.CountryList;
import model.emission.*;
import org.json.simple.parser.ParseException;
import persistence.JsonReader;
import persistence.JsonWriter;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import ui.tabs.TakeActionTab;
import ui.tabs.CalculateTab;
import ui.tabs.overview.OverviewTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// carbon footprint tracker application
// Source code: TellerApp
public class CarbonFootprintApp extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static final int CALCULATE_TAB_INDEX = 0;
    public static final int OVERVIEW_TAB_INDEX = 1;
    public static final int TAKE_ACTION_TAB_INDEX = 2;

    private static final String JSON_FILE = "data/savedLogs.json";
    private CarbonFootprintLog carbonLog;
    private Diet diet;
    private HomeEnergy electricity;
    private HomeEnergy gas;
    private HomeEnergy oil;
    private Transportation transportation;
    private Vehicle car;

    private JTabbedPane mainTabs;

    // EFFECTS: runs a new carbon footprint app
    public CarbonFootprintApp() {
        super("Carbon Footprint Tracker");
        setSize(WIDTH, HEIGHT);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        carbonLog = loadCurrentLog();

        mainTabs = new JTabbedPane();
        mainTabs.setTabPlacement(JTabbedPane.TOP);

        loadTabs();

        add(mainTabs);
        setVisible(true);
        playMusic();

    }

    // EFFECTS: plays the background music
    private void playMusic() {
        InputStream sound;
        try {
            sound = new FileInputStream(new File("data/Butterfly.wav"));
            AudioStream audioStream = new AudioStream(sound);
            AudioPlayer.player.start(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds Calculate, Overview, Take Action tabs to this ui
    private void loadTabs() {
        JPanel calculateTab = new CalculateTab(this);
        JPanel overviewTab = new OverviewTab(this);
        JPanel takeActionTab = new TakeActionTab(this);

        mainTabs.addTab("Calculate", calculateTab);
        mainTabs.addTab("Overview", overviewTab);
        mainTabs.addTab("Take Action", takeActionTab);

    }

    // MODIFIES: this
    // EFFECTS: loads most recent log from JSON_FILE if it exists,
    // otherwise initialize log with default values
    private CarbonFootprintLog loadCurrentLog() {
        try {
            List<CarbonFootprintLog> allLogs = JsonReader.readJson(new File(JSON_FILE));
            carbonLog = allLogs.get(allLogs.size() - 1);
            List<CarbonEmission> emissions = carbonLog.getEmissionSources();
            diet = (Diet) emissions.get(0);
            electricity = (HomeEnergy) emissions.get(1);
            gas = (HomeEnergy) emissions.get(2);
            oil = (HomeEnergy) emissions.get(3);
            transportation = (Transportation) emissions.get(4);
            car = (Vehicle) emissions.get(5);

            return carbonLog;
        } catch (Exception e) {
            return initializeCarbonFootprint();
        }
    }

    // EFFECTS: saves state of carbon footprint log to JSON_FILE
    private void saveLog() {
        try {
            carbonLog.updateDate();
            JsonWriter writer = new JsonWriter(new File(JSON_FILE));
            writer.write(carbonLog);
            writer.close();
        } catch (IOException e) {
            System.out.println("Unable to save to file \n");
        }

    }

    // MODIFIES: this
    // EFFECTS: initializes carbon footprint with default values
    private CarbonFootprintLog initializeCarbonFootprint() {
        carbonLog = new CarbonFootprintLog("Canada");
        diet = new Diet(DietType.MEDIUM_MEAT);
        electricity = new HomeEnergy(EnergyType.ELECTRICITY);
        gas = new HomeEnergy(EnergyType.GAS);
        oil = new HomeEnergy(EnergyType.OIL);
        transportation = new Transportation();
        car = new Vehicle();

        carbonLog.addCarbonSource(diet);
        carbonLog.addCarbonSource(electricity);
        carbonLog.addCarbonSource(gas);
        carbonLog.addCarbonSource(oil);
        carbonLog.addCarbonSource(transportation);
        carbonLog.addCarbonSource(car);

        return carbonLog;
    }


    // EFFECTS: returns the carbon footprint log controlled by this ui
    public CarbonFootprintLog getCarbonLog() {
        return carbonLog;
    }

    public List<CarbonFootprintLog> getAllLogs() {
        List<CarbonFootprintLog> allLogs = new ArrayList<>();
        try {
            allLogs = JsonReader.readJson(new File(JSON_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return allLogs;
    }

    // EFFECTS: returns the tabbed pane of this ui
    public JTabbedPane getMainTabs() {
        return mainTabs;
    }

    // EFFECTS: constructs a button to save current state of ui
    public JButton makeSaveButton() {
        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Save")) {
                    saveLog();
                }
            }
        });
        return save;
    }

}
