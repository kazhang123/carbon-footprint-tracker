package model.emission;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;

// represents energy used in a home as a source of carbon emission,
// with an energy type, monthly power consumed, and annual carbon emission
public class HomeEnergy implements CarbonEmission {

    // Emission factors in tonnes CO2e / kwh
    public static final double GAS_EF = 0.00018;
    public static final double OIL_EF = 0.00025;
    public static final double ELECTRIC_EF = 0.0005; // differs by country, temporary constant

    private double carbonEmission;
    private EnergyType energyType;
    private double monthlyKwh;

    // EFFECTS: constructs a new home energy emission source
    public HomeEnergy(EnergyType energyType) {
        carbonEmission = 0;
        this.energyType = energyType;
        monthlyKwh = 0;
    }

    // EFFECTS: returns the carbon emission in tonnes of CO2e
    @Override
    public double getCarbonEmission() {
        return carbonEmission;
    }

    // MODIFIES: this
    // EFFECTS: calculates and sets the annual emission quantity in tonnes of carbon dioxide equivalent based on
    //          given kwh used per month
    @Override
    public void calculateCarbonEmission(double monthlyKwh) {
        if (energyType.equals(EnergyType.ELECTRICITY)) {
            carbonEmission = monthlyKwh * 12 * ELECTRIC_EF;
        } else if (energyType.equals(EnergyType.GAS)) {
            carbonEmission = monthlyKwh * 12 * GAS_EF;
        } else {
            carbonEmission = monthlyKwh * 12 * OIL_EF;
        }
        setMonthlyKwh(monthlyKwh);
    }

    // EFFECTS: returns string representation of home energy's carbon emission
    @Override
    public String toString() {
        String energyString = "";
        if (energyType.equals(EnergyType.ELECTRICITY)) {
            energyString =  "Electricity: " + String.format("%.2f", getCarbonEmission());
        } else if (energyType.equals(EnergyType.OIL)) {
            energyString =  "Oil: " + String.format("%.2f", getCarbonEmission());
        } else {
            energyString =  "Gas: " + String.format("%.2f", getCarbonEmission());
        }
        return energyString;
    }

    // EFFECTS: returns the maximum monthly kwh a user can set
    @Override
    public double getMax() {
        return 5000;
    }

    // EFFECTS: returns the monthlyKwh
    @Override
    public double getValue() {
        return monthlyKwh;
    }

    // EFFECTS: returns the energy type of the home energy source
    public EnergyType getEnergyType() {
        return energyType;
    }

    // EFFECTS: returns the monthly energy consumption in kwh
    public double getMonthlyKwh() {
        return monthlyKwh;
    }

    // REQUIRES: monthlyKwh >= 0
    // MODIFIES: this
    // EFFECTS: sets the the monthly energy consumption in kwh and sets carbon footprint emission based on kwh amount
    private void setMonthlyKwh(double monthlyKwh) {
        this.monthlyKwh = monthlyKwh;
    }


    // EFFECTS: saves home energy object to JSON array
    @Override
    public void saveJson(FileWriter fileWriter, Object obj) {
        JSONObject energyObj = new JSONObject();

        if (energyType.equals(EnergyType.ELECTRICITY)) {
            energyObj.put("label", "Electricity");
        } else if (energyType.equals(EnergyType.GAS)) {
            energyObj.put("label", "Gas");
        } else {
            energyObj.put("label", "Oil");
        }

        energyObj.put("monthlyKwh", monthlyKwh);

        JSONArray emissions = (JSONArray) obj;
        emissions.add(energyObj);
    }


}
