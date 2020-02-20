package ui;

import model.CarbonFootprintLog;
import model.CountryList;
import model.emission.*;
import model.emission.exception.NegativeAmountException;

import java.util.Scanner;

// carbon footprint tracker application
// Source code: TellerApp

public class CarbonFootprintApp {
    private CarbonFootprintLog carbonLog;
    private Diet diet;
    private HomeEnergy electricity;
    private HomeEnergy gas;
    private HomeEnergy oil;
    private Transportation transportation;
    private Vehicle car;
    private boolean runProgram;
    private Scanner input;

    // EFFECTS: runs a new carbon footprint app
    public CarbonFootprintApp() {
        runCarbonFootprint();
    }

    // EFFECTS: runs CarbonFootprint App
    private void runCarbonFootprint() {
        String command = null;
        runProgram = true;
        input = new Scanner(System.in);

        initializeCarbonFootprint();

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
    // EFFECTS: initializes carbon footprint with default values
    private void initializeCarbonFootprint() {
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
    }

    // EFFECTS: displays start menu for program
    private void displayMenu() {
        System.out.println("What would you like to do?");
        System.out.println("c - calculate new carbon footprint");
        System.out.println("e - edit footprint");
        System.out.println("s - see your statistics");
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
        } else if (command.equals("s")) {
            printStatistics();
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
        System.out.println(oil.toString() + " tonnes of CO2, "  + carbonLog.percentageEmission(oil)
                + "% of your total emission");
        System.out.println(gas.toString() + " tonnes of CO2, "  + carbonLog.percentageEmission(gas)
                + "% of your total emission");
        System.out.println(transportation.toString() + " tonnes of CO2, "
                + carbonLog.percentageEmission(transportation) + "% of your total emission");
        System.out.println(car.toString() + " tonnes of CO2, "  + carbonLog.percentageEmission(car)
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
            diet.setCalPerDay(cals);
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
            electricity.setMonthlyKwh(amount);
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
            oil.setMonthlyKwh(amount);
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
            gas.setMonthlyKwh(amount);
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
            transportation.setDistancePerDay(distance);
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
            car.setDistancePerDay(distance);
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

    // EFFECTS: prints sustainability tips based on user's emission sources to screen
    private void printTips() {
        System.out.println("REDUCE YOUR IMPACT:\n");
        if (diet.getDietType().equals(DietType.HIGH_MEAT) || diet.getDietType().equals(DietType.MEDIUM_MEAT)) {
            System.out.println("Consider eating meatless meals once a week! \n"
                    + " Eating one meatless meal a week can offset your footprint by up to 0.4 tonnes a year \n");
        }
        if (car.getCarbonFootprint() > 0) {
            System.out.println("Take public transportation! \n For each day of the week you take the bus,"
                    + " you can save up to 0.7 tonnes of CO2 and $2000 every year on fuel. \n");
            System.out.println("Carpool to work to save 0.9 tonnes a year. \n");
        }
        System.out.println("Adjust your thermostat. \n For every degree you turn down your thermostat,"
                + " you save 0.06 tonnes of CO2 every year. \n Reducing your heating by 1 degree celsius"
                + " can reduce your emissions by 8% and save $55 every year. \n"
                + " Help the planet by bundling up and wearing more layers! \n");
        System.out.println("Hang your clothes instead of using the dryer. \n "
                + " Line drying your laundry will save about 0.15 tonnes of CO2 and $70 annually. \n");
        System.out.println("Change your lightbulbs to low-energy bulbs. \n"
                + " Changing 1 lightbulb to a low-energy 18 W bulb will save"
                + " you $20 and the planet 0.052 tonnes of CO2 annually. \n");
        System.out.println("Turn off electrical equipment that aren't in use! \n");
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
}
