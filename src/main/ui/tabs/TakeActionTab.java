package ui.tabs;

import sun.invoke.empty.Empty;
import ui.CarbonFootprintApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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

        makeLabels();
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

    // MODIFIES: this
    // EFFECTS: creates JLabels with each icon and associated tip
    private void makeLabels() {
        JLabel thermoTip = new JLabel(thermoIcon);
        thermoTip.setText("<html>Adjust your thermostat."
                + "For every degree you turn down your thermostat, you save 0.06 tonnes of CO2 every year."
                + "Reducing your heating by 1 degree celsius can reduce your emissions by 8% and save $55 every year.</html>");
        thermoTip.setHorizontalTextPosition(SwingConstants.CENTER);
        thermoTip.setVerticalTextPosition(SwingConstants.BOTTOM);
        thermoTip.setBorder(new EmptyBorder(10, 10,0, 0));

        JLabel clothesTip = new JLabel(clothesIcon);
        clothesTip.setText("<html>Hang your clothes instead of using the dryer."
                + "Line drying your laundry will save about 0.15 tonnes of CO2 and $70 annually.</html>");
        clothesTip.setHorizontalTextPosition(SwingConstants.CENTER);
        clothesTip.setVerticalTextPosition(SwingConstants.BOTTOM);
        clothesTip.setBorder(new EmptyBorder(10, 10,0, 0));


        JLabel lightTip = new JLabel(lightIcon);
        lightTip.setText("<html>Change your lightbulbs to low-energy bulbs."
                + "Changing 1 lightbulb to a low-energy 18 W bulb will save you $20 "
                + "and the planet 0.052 tonnes of CO2 annually.</html>");
        lightTip.setHorizontalTextPosition(SwingConstants.CENTER);
        lightTip.setVerticalTextPosition(SwingConstants.BOTTOM);
        lightTip.setBorder(new EmptyBorder(10, 10,0, 0));

        JLabel meatTip = new JLabel( meatIcon);
        meatTip.setText("<html>Consider eating meatless meals once a week! "
                + " Eating one meatless meal a week can offset your footprint by up to 0.4 tonnes a year.</html>");
        meatTip.setHorizontalTextPosition(SwingConstants.CENTER);
        meatTip.setVerticalTextPosition(SwingConstants.BOTTOM);
        meatTip.setBorder(new EmptyBorder(10, 10,0, 0));

        JLabel carTip = new JLabel(carIcon);
        carTip.setText("<html>Take public transportation! For each day of the week you take the bus,"
                + " you can save up to 0.7 tonnes of CO2 and $2000 every year on fuel.</html>"
                + "Carpool to work to save 0.9 tonnes a year.");
        carTip.setHorizontalTextPosition(SwingConstants.CENTER);
        carTip.setVerticalTextPosition(SwingConstants.BOTTOM);
        carTip.setBorder(new EmptyBorder(10, 10,0, 0));

        JLabel outletTip = new JLabel(outletIcon);
        outletTip.setText("<html>Turn off electrical equipment that aren't in use!</html>");
        outletTip.setHorizontalTextPosition(SwingConstants.CENTER);
        outletTip.setVerticalTextPosition(SwingConstants.BOTTOM);
        outletTip.setBorder(new EmptyBorder(10, 10,0, 0));

        this.add(thermoTip);
        this.add(clothesTip);
        this.add(lightTip);
        this.add(meatTip);
        this.add(carTip);
        this.add(outletTip);
    }
}
