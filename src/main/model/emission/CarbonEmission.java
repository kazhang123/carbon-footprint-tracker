package model.emission;

import persistence.Jsonable;

// represents a carbon footprint source, with an annual carbon emission measured in tonnes of CO2 equivalent
public abstract class CarbonEmission implements Jsonable {

    protected double carbonEmission;

    public CarbonEmission() {
        carbonEmission = 0;
    }

    // EFFECTS: returns total carbon emission in tonnes of carbon dioxide equivalent (CO2e)
    public double getCarbonEmission() {
        return carbonEmission;
    }

    // MODIFIES: this
    // EFFECTS: sets and calculates carbon emission of given amount depending on emission type
    protected abstract void calculateCarbonEmission(double amount);

    // EFFECTS: returns a string representation of carbon emission source
    public abstract String toString();
}
