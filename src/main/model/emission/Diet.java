package model.emission;

import model.emission.exception.NegativeAmountException;
import org.json.simple.JSONObject;
import persistence.JsonSimpleWriter;

import java.io.FileWriter;

// represents a diet as a source of carbon emissions,
// with a diet type, daily calorie intake, and annual carbon emission
public class Diet extends CarbonEmission {

    // Emission factors by diet type measured in tonnes Co2e / 2000 kCal
    public static final double HIGHMEATEATER_EF = 0.00719; // >= 100 g meat / day
    public static final double MEDMEATEATER_EF = 0.00563; // 50-99 g meat/day
    public static final double LOWMEATEATER_EF = 0.00467; // < 50 g meat/day
    public static final double PESCETARIAN_EF = 0.00391;
    public static final double VEGETARIAN_EF = 0.00381;
    public static final double VEGAN_EF = 0.00289;

    private DietType dietType;
    private int calsPerDay;

    // EFFECTS: constructs a new diet emission source
    public Diet(DietType dietType) {
        super();
        this.dietType = dietType;
        calsPerDay = 0;
    }

    // MODIFIES: this
    // EFFECTS: calculates and sets the annual emission quantity in
    //          tonnes of carbon dioxide equivalent based on calories
    //          consumed per day and type of diet
    @Override
    protected void calculateCarbonEmission(double calories) {
        if (dietType.equals(DietType.HIGH_MEAT)) {
            carbonEmission = calories * 365 * (HIGHMEATEATER_EF / 2000);
        } else if (dietType.equals(DietType.MEDIUM_MEAT)) {
            carbonEmission = calories * 365 * (MEDMEATEATER_EF / 2000);
        } else if (dietType.equals(DietType.LOW_MEAT)) {
            carbonEmission = calories * 365 * (LOWMEATEATER_EF / 2000);
        } else if (dietType.equals(DietType.PESCETARIAN)) {
            carbonEmission = calories * 365 * (PESCETARIAN_EF / 2000);
        } else if (dietType.equals(DietType.VEGETARIAN)) {
            carbonEmission = calories * 365 * (VEGETARIAN_EF / 2000);
        } else {
            carbonEmission = calories * 365 * (VEGAN_EF / 2000);
        }
    }

    // EFFECTS: returns a string representation of diet's carbon emission
    @Override
    public String toString() {
        return "Diet: " + String.format("%.2f", getCarbonEmission());
    }

    // EFFECTS: returns the diet type
    public DietType getDietType() {
        return dietType;
    }

    // EFFECTS: sets the diet type and changes carbon footprint based on new diet type
    public void setDietType(DietType type) {
        dietType = type;
        calculateCarbonEmission(calsPerDay);
    }

    // EFFECTS: returns the number of kCal consumed per day
    public int getCalPerDay() {
        return calsPerDay;
    }

    // MODIFIES: this
    // EFFECTS: sets the kCal consumed per day
    public void setCalPerDay(int cals) throws NegativeAmountException {
        if (cals < 0) {
            throw new NegativeAmountException();
        }
        calsPerDay = cals;
        calculateCarbonEmission(calsPerDay);
    }


    @Override
    public void saveJson(FileWriter fileWriter) {
        JSONObject dietObj = new JSONObject();
        dietObj.put("label", "Diet");
        dietObj.put("cals", calsPerDay);
        dietObj.put("dietType", dietType.name());
        JsonSimpleWriter.emissions.add(dietObj);

    }

}
