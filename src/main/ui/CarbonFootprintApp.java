package ui;

import model.emission.CarbonFootprintLog;
import model.CountryList;
import model.emission.*;
import model.emission.exception.NegativeAmountException;
import org.json.simple.parser.ParseException;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.TakeActionTab;
import ui.tabs.CalculateTab;
import ui.tabs.overview.OverviewTab;

import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.Scanner;

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
    private boolean runProgram;
    private Scanner input;

    private JTabbedPane topBar;

    // EFFECTS: runs a new carbon footprint app
    public CarbonFootprintApp() {
        super("Carbon Footprint Tracker");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        carbonLog = loadCurrentLog();

        topBar = new JTabbedPane();
        topBar.setTabPlacement(JTabbedPane.TOP);

        loadTabs();

        add(topBar);

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds Calculate, Overview, Take Action tabs to this ui
    private void loadTabs() {
        JPanel calculateTab = new CalculateTab(this);
        JPanel overviewTab = new OverviewTab(this);
        JPanel takeActionTab = new TakeActionTab(this);

        topBar.add("Calculate", calculateTab);
        topBar.add("Overview", overviewTab);
        topBar.add("Take Action", takeActionTab);
    }

    // EFFECTS: runs CarbonFootprint App
    private void runCarbonFootprint() {
        String command = null;
        runProgram = true;
        input = new Scanner(System.in);

        loadCurrentLog();

        while (runProgram) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                runProgram = false;
            } else {
                processCommand(command);
            }
        }
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
        } catch (IOException e) {
            return initializeCarbonFootprint();
        } catch (ParseException e) {
            return initializeCarbonFootprint();
        }
    }

    // EFFECTS: displays past carbon footprint logs saved to JSON_FILE
    private void displayPast() {
        try {
            List<CarbonFootprintLog> allLogs = JsonReader.readJson(new File(JSON_FILE));
            System.out.println("Your carbon footprint over time in tonnes of CO2e per year:");
            for (CarbonFootprintLog l : allLogs) {
                System.out.println(String.format("%.2f", l.getTotalEmission()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("No records to display \n");
        }


    }

    // EFFECTS: saves state of carbon footprint log to JSON_FILE
    private void saveLog() {
        try {
            JsonWriter writer = new JsonWriter(new File(JSON_FILE));
            writer.write(carbonLog);
            writer.close();
            System.out.println("Current footprint saved to file \n");
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

    // EFFECTS: displays start menu for program
    private void displayMenu() {
        System.out.println("What would you like to do?");
        System.out.println("c - calculate new carbon footprint");
        System.out.println("e - edit footprint");
        System.out.println("p - see your statistics");
        System.out.println("s - save your current footprint to file");
        System.out.println("h - see your emissions over time");
        System.out.println("t - receive tips on reducing your emissions!");
        System.out.println("q - quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("c")) {
            calculateNewFootprint();
        } else if (command.equals("e")) {
            editFooprint();
        } else if (command.equals("p")) {
            printStatistics();
        } else if (command.equals("s")) {
            saveLog();
        } else if (command.equals("h")) {
            displayPast();
        } else if (command.equals("t")) {
            printTips();
        } else {
            System.out.println("Invalid selection");
        }
    }

    // MODIFIES: this
    // EFFECTS: calculates carbon footprint through entire process
    private void calculateNewFootprint() {
        initializeCarbonFootprint();
        selectCountry();
        selectDiet();
        selectHomeEnergyE();
        selectHomeEnergyO();
        selectHomeEnergyGas();
        selectTranportation();
        selectVehicle();

        printStatistics();
    }


    // EFFECTS: prints user's carbon footprint statistics to screen
    private void printStatistics() {
        System.out.println("YOUR CARBON FOOTPRINT: \n");
        System.out.println(diet.toString() + " tonnes of CO2, " + carbonLog.percentageEmission(diet)
                + "% of your total emission");
        System.out.println(electricity.toString() + " tonnes of CO2, " + carbonLog.percentageEmission(electricity)
                + "% of your total emission");
        System.out.println(oil.toString() + " tonnes of CO2, " + carbonLog.percentageEmission(oil)
                + "% of your total emission");
        System.out.println(gas.toString() + " tonnes of CO2, " + carbonLog.percentageEmission(gas)
                + "% of your total emission");
        System.out.println(transportation.toString() + " tonnes of CO2, "
                + carbonLog.percentageEmission(transportation) + "% of your total emission");
        System.out.println(car.toString() + " tonnes of CO2, " + carbonLog.percentageEmission(car)
                + "% of your total emission \n");
        System.out.println("Your annual emission: " + String.format("%.2f", carbonLog.getTotalEmission())
                + " tonnes of CO2");
        System.out.println("You would need " + carbonLog.numTreesToOffset()
                + " trees to offset your footprint. \n");
        System.out.println("How do you compare?");
        printCountryAverage();
        System.out.println("The world average: " + CarbonFootprintLog.WORLD_AVG + " tonnes of CO2 a year \n");
    }

    // EFFECTS: prints caverage carbon footprint of user country if found.If not found, prints that it was not found
    private void printCountryAverage() {
        if (CountryList.getCountries().get(carbonLog.getCountry()) != null) {
            System.out.println("Your country's average: " + carbonLog.getAvgCountryFootprint()
                    + " tonnes of CO2 a year \n");
        } else {
            System.out.println("Sorry, don't have data for your selected country... \n");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to edit aspects of their emission sources
    private void editFooprint() {
        String selection = "";

        while (!(selection.equals("c") || selection.equals("d") || selection.equals("e")
                || selection.equals("o") || selection.equals("g") || selection.equals("t")
                || selection.equals("v"))) {

            displayEditMenu();
            selection = input.next();
        }

        if (selection.equals("c")) {
            selectCountry();
        } else if (selection.equals("d")) {
            selectDiet();
        } else if (selection.equals("e")) {
            selectHomeEnergyE();
        } else if (selection.equals("o")) {
            selectHomeEnergyO();
        } else if (selection.equals("g")) {
            selectHomeEnergyGas();
        } else if (selection.equals("t")) {
            selectTranportation();
        } else if (selection.equals("v")) {
            selectVehicle();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select their diet fields
    private void selectDiet() {
        String selection = "";

        while (!(selection.equals("h") || selection.equals("m") || selection.equals("l") || selection.equals("p")
                || selection.equals("p") || selection.equals("t") || selection.equals("v") || selection.equals("s"))) {

            displayDietMenu();

            selection = input.next();
            selection = selection.toLowerCase();
        }

        processDietSelection(selection);

        System.out.println("Enter daily calorie intake:");
        int cals = input.nextInt();

        try {
            diet.calculateCarbonEmission(cals);
        } catch (NegativeAmountException e) {
            System.out.println("Cannot calculate negative calorie intake... \n");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter their monthly electricity use
    private void selectHomeEnergyE() {
        System.out.println("Enter amount of electricity used for energy in your home, in kwh per month");
        double amount = input.nextDouble();
        try {
            electricity.calculateCarbonEmission(amount);
        } catch (NegativeAmountException e) {
            System.out.println("Cannot calculate negative energy use... \n");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter their monthly oil use
    private void selectHomeEnergyO() {
        System.out.println("Enter amount of oil used for energy in your home, in kwh per month");
        double amount = input.nextDouble();
        try {
            oil.calculateCarbonEmission(amount);
        } catch (NegativeAmountException e) {
            System.out.println("Cannot calculate negative energy use... \n");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter their monthly gas use
    private void selectHomeEnergyGas() {
        System.out.println("Enter amount of gas used for energy in your home, in kwh per month");
        double amount = input.nextDouble();
        try {
            gas.calculateCarbonEmission(amount);
        } catch (NegativeAmountException e) {
            System.out.println("Cannot calculate negative energy use... \n");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter their public transportation distance/day
    private void selectTranportation() {
        System.out.println("Enter distance spent on bus per day, in km");
        double distance = input.nextDouble();
        try {
            transportation.calculateCarbonEmission(distance);
        } catch (NegativeAmountException e) {
            System.out.println("Cannot calculte negative distance... \n");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter their vehicle distance/day
    private void selectVehicle() {
        System.out.println("Enter distance spent in car per day, in km");
        double distance = input.nextDouble();
        try {
            car.calculateCarbonEmission(distance);
        } catch (NegativeAmountException e) {
            System.out.println("Cannot calculate negative distance... \n");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter their public transportation distance/day
    private void selectCountry() {
        System.out.println("Enter your country");
        String country = input.next();
        country = country.toUpperCase();
        carbonLog.setCountry(country);
    }

    // EFFECTS: prints sustainability OffsetTips.txt based on user's emission sources to screen
    private void printTips() {
        System.out.println("REDUCE YOUR IMPACT:\n");
        if (diet.getDietType().equals(DietType.HIGH_MEAT) || diet.getDietType().equals(DietType.MEDIUM_MEAT)) {
            System.out.println("Consider eating meatless meals once a week! \n"
                    + " Eating one meatless meal a week can offset your footprint by up to 0.4 tonnes a year \n");
        }
        if (car.getCarbonEmission() > 0) {
            System.out.println("Take public transportation! \n For each day of the week you take the bus,"
                    + " you can save up to 0.7 tonnes of CO2 and $2000 every year on fuel. \n");
            System.out.println("Carpool to work to save 0.9 tonnes a year. \n");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("data/OffsetTips.txt"));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: displays menu to select what emission source to edit
    private void displayEditMenu() {
        System.out.println("What would you like to change?");
        System.out.println("c - country");
        System.out.println("d - diet");
        System.out.println("e - electricity use");
        System.out.println("o - oil use");
        System.out.println("g - gas use");
        System.out.println("t - public transportation use");
        System.out.println("v - vehicle use ");
    }

    // EFFECTS: displays menu to select diet type
    private void displayDietMenu() {
        System.out.println("What is your diet like?");
        System.out.println("h - Heavy meat eater (over 100 g a day)");
        System.out.println("m - Medium meat eater (50 - 99 g a day)");
        System.out.println("l - Light meat eater (less than 50 g a day)");
        System.out.println("p - Pescatarian");
        System.out.println("t - Vegetarian");
        System.out.println("v - Vegan ");
    }

    // MODIFIES: this
    // EFFECTS: processes user command on the diet menu
    private void processDietSelection(String selection) {
        if (selection.equals("h")) {
            diet.setDietType(DietType.HIGH_MEAT);
        } else if (selection.equals("m")) {
            diet.setDietType(DietType.MEDIUM_MEAT);
        } else if (selection.equals("l")) {
            diet.setDietType(DietType.LOW_MEAT);
        } else if (selection.equals("p")) {
            diet.setDietType(DietType.PESCETARIAN);
        } else if (selection.equals("t")) {
            diet.setDietType(DietType.VEGETARIAN);
        } else if (selection.equals("v")) {
            diet.setDietType(DietType.VEGAN);
        }
    }

    // EFFECTS: returns the carbon footprint log controlled by this ui
    public CarbonFootprintLog getCarbonLog() {
        return carbonLog;
    }

    // EFFECTS: returns the tabbed pane of this ui
    public JTabbedPane getTopBar() {
        return topBar;
    }
}
