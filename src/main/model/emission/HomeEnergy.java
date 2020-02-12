package model.emission;

import model.emission.exception.NegativeAmountException;

// represents energy used in a home as a source of carbon emission,
// with an energy type, monthly power consumed, and annual carbon emission
public class HomeEnergy extends CarbonFootprint {

    // Emission factors in tonnes CO2e / kwh
    public static final double GAS_EF = 0.00018;
    public static final double OIL_EF = 0.00025;
    public static final double ELECTRIC_EF = 0.0005; // differs by country, temporary constant

    private EnergyType energyType;
    private double monthlyKwh;

    // EFFECTS: constructs a new home energy emission source
    public HomeEnergy(EnergyType energyType) {
        super();
        this.energyType = energyType;
        monthlyKwh = 0;
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
        } else if (energyType.equals(EnergyType.OIL)) {
            carbonEmission = monthlyKwh * 12 * OIL_EF;
        }
    }

    // EFFECTS: returns string representation of home energy's carbon emission
    @Override
    public String toString() {
        String energyString = "";
        if (energyType.equals(EnergyType.ELECTRICITY)) {
            energyString =  "Electricity: " + String.format("%.2f", getCarbonFootprint());
        } else if (energyType.equals(EnergyType.OIL)) {
            energyString =  "Oil: " + String.format("%.2f", getCarbonFootprint());
        } else if (energyType.equals(EnergyType.GAS)) {
            energyString =  "Gas: " + String.format("%.2f", getCarbonFootprint());
        }
        return energyString;
    }

    // EFFECTS: returns the energy type of the home energy source
    public EnergyType getEnergyType() {
        return energyType;
    }

    // EFFECTS: returns the monthly energy consumption in kwh
    public double getMonthlyKwh() {
        return monthlyKwh;
    }

    // MODIFIES: this
    // EFFECTS: sets the the monthly energy consumption in kwh and sets carbon footprint emission based on kwh amount
    public void setMonthlyKwh(double monthlyKwh) throws NegativeAmountException {
        if (monthlyKwh < 0) {
            throw new NegativeAmountException();
        }
        this.monthlyKwh = monthlyKwh;
        calculateCarbonEmission(this.monthlyKwh);
    }




}