package model.emission;

import persistence.Saveable;

// represents a carbon footprint source, with an annual carbon emission measured in tonnes of CO2 equivalent
public abstract class CarbonEmission implements Saveable {
    protected static int nextId;
    protected int id;

    protected double carbonEmission;

    public CarbonEmission() {
        id = CarbonFootprintLog.nextLogId;
        carbonEmission = 0;
    }

    // EFFECTS: constructs a source of carbon emission
    // NOTE: to be used when constructing a source from file
    public CarbonEmission(int nextId, int id, double carbonEmission) {
        this.nextId = nextId;
        this.id = id;
        this.carbonEmission = carbonEmission;
    }

    public int getId() {
        return id;
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
