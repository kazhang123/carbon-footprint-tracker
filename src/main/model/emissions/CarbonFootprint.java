package model.emissions;

public abstract class CarbonFootprint {

    protected double carbonEmission;

    public CarbonFootprint() {
        carbonEmission = 0;
    }

    // EFFECTS: returns total carbon footprint in tonnes of carbon dioxide equivalent (CO2e)
    public double getCarbonFootprint() {
        return carbonEmission;
    }

    // MODIFIES: this
    // EFFECTS: sets and calculates carbon footprint of given amount depending on emission type
    public abstract void calculateCarbonEmission(double amount);
}
