package ui.tabs;

import ui.CarbonFootprintApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/*
represents the tab where tips for offsetting your footprint are displayed
 */
public class TakeActionTab extends Tab {
    private static final String CAR = "data/tips/car.png";
    private static final String CLOTHES = "data/tips/clothes.png";
    private static final String LIGHT = "data/tips/lightbulb.png";
    private static final String MEAT = "data/tips/meat.png";
    private static final String OUTLET = "data/tips/outlet.png";
    private static final String THERMOSTAT = "data/tips/thermostat.png";

    private ImageIcon carIcon;
    private ImageIcon clothesIcon;
    private ImageIcon lightIcon;
    private ImageIcon meatIcon;
    private ImageIcon outletIcon;
    private ImageIcon thermoIcon;

    // EFFECTS: constructs the tab that displays tips user should take to reduce footprint
    public TakeActionTab(CarbonFootprintApp app) {
        super(app);
        this.setLayout(new GridLayout(2, 3));
        makeIcons();

        JLabel thermoTip = makeLabel("<html>Adjust your thermostat. For every degree you turn down your thermostat, "
                + "you save 0.06 tonnes of CO2 every year. Reducing your heating by 1 degree celsius can reduce your "
                + "emissions by 8% and save $55 every year.</html>", thermoIcon);
        JLabel clothesTip = makeLabel("<html>Hang your clothes instead of using the dryer. Line drying your laundry "
                + "will save about 0.15 tonnes of CO2 and $70 annually.</html>", clothesIcon);
        JLabel lightTip = makeLabel("<html>Change your lightbulbs to low-energy bulbs. Changing 1 lightbulb to a "
                + "low-energy 18 W bulb will save you $20 and the planet 0.052 tonnes of CO2 annually.</html>",
                lightIcon);
        JLabel meatTip = makeLabel("<html>Consider eating meatless meals once a week! Eating one meatless meal a week "
                + "can offset your footprint by up to 0.4 tonnes a year.</html>", meatIcon);
        JLabel carTip = makeLabel("<html>Take public transportation! For each day of the week you take the bus, you "
                + "can save up to 0.7 tonnes of CO2 and $2000 every year on fuel.</html>"
                + "Carpool to work to save 0.9 tonnes a year.", carIcon);
        JLabel outletTip = makeLabel("<html>Turn off electrical equipment that isn't in use!</html>", outletIcon);

        this.add(thermoTip);
        this.add(clothesTip);
        this.add(lightTip);
        this.add(meatTip);
        this.add(carTip);
        this.add(outletTip);
    }

    // MODIFIES: this
    // EFFECTS: creates icons to be displayed on panel
    private void makeIcons() {
        carIcon = new ImageIcon(CAR);
        clothesIcon = new ImageIcon(CLOTHES);
        lightIcon = new ImageIcon(LIGHT);
        meatIcon = new ImageIcon(MEAT);
        outletIcon = new ImageIcon(OUTLET);
        thermoIcon = new ImageIcon(THERMOSTAT);
    }

    // EFFECTS: makes JLabels with icons and associated tip
    private JLabel makeLabel(String message, ImageIcon icon) {
        JLabel label = new JLabel(icon);
        label.setText(message);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setVerticalTextPosition(SwingConstants.BOTTOM);
        label.setBorder(new EmptyBorder(10, 10, 0, 0));

        return label;
    }
}
