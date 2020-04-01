package model.emission;

import persistence.Jsonable;

// represents a source of carbon emission
public interface CarbonEmission extends Jsonable {
    // EFFECTS: returns total carbon emission in tonnes of carbon dioxide equivalent (CO2e)
    double getCarbonEmission();

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: sets and calculates carbon emission of given amount depending on emission type
    void calculateCarbonEmission(double amount);

    // EFFECTS: returns a string representation of carbon emission source
    String toString();

    // EFFECTS: returns the max quantity the user can select for the emission source
    double getMax();

    // EFFECTS: returns the value of the carbon emission source that affects its carbon emission
    double getValue();
}
