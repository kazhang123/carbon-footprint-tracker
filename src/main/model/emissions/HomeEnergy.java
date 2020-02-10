package model.emissions;

public class HomeEnergy extends CarbonFootprint {

    // Emission factors in tonnes CO2e / kwh
    public static final double GAS_EF = 0.00018;
    public static final double OIL_EF = 0.00025;
    public static final double ELECTRIC_EF = 0.0005; // differs by country, temporary constant

    private EnergyType energyType;
    private int monthlyKwh;

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

    // EFFECTS: returns the energy type of the home energy source
    public EnergyType getEnergyType() {
        return energyType;
    }

    // EFFECTS: returns the monthly energy consumption in kwh
    public int getMonthlyKwh() {
        return monthlyKwh;
    }

    // MODIFIES: this
    // REQUIRES: monthlyKwh >= 0
    // EFFECTS: sets the the monthly energy consumption in kwh and sets carbon footprint emission based on kwh amount
    public void setMonthlyKwh(int monthlyKwh) {
        this.monthlyKwh = monthlyKwh;
        calculateCarbonEmission(this.monthlyKwh);
    }




}
