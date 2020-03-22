package model.emission;

import persistence.Jsonable;

// represents a carbon footprint source, with an annual carbon emission measured in tonnes of CO2 equivalent
public abstract class CarbonEmission implements Jsonable {
    public static final double MAX_KWH = 5000;
    public static final double MAX_CALS = 5000;
    public static final double MAX_KM = 100;


    protected double carbonEmission;

    public CarbonEmission() {
        carbonEmission = 0;
    }

    // EFFECTS: returns total carbon emission in tonnes of carbon dioxide equivalent (CO2e)
    public double getCarbonEmission() {
        return carbonEmission;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: sets and calculates carbon emission of given amount depending on emission type
    public abstract void calculateCarbonEmission(double amount);

    // EFFECTS: returns a string representation of carbon emission source
    public abstract String toString();

    // EFFECTS: returns the max quantity the user can select for the emission source
    public abstract double getMax();
}
