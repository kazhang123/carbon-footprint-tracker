package ui.tabs;

import model.CountryList;
import model.emission.*;
import ui.CarbonFootprintApp;
import ui.tabs.overview.BarGraphTab;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

// source: SmartHome
public class CalculateTab extends Tab {
    private static final int ROW_HEIGHT = 55;
    private static final int PADDING = 90;
    public static final int WIDTH = CarbonFootprintApp.WIDTH - 2 * PADDING;
    private static final String OVERVIEW_BUTTON = "Get results";

    private JLabel country;
    private JComboBox countriesBox;
    private GridLayout rowLayout;


    // REQUIRES: CarbonFootprintApp that controls this tab
    // EFFECTS: creates Calculate tab that calculates the carbon emission for each emission source based on user input
    public CalculateTab(CarbonFootprintApp app) {
        super(app);
        rowLayout = new GridLayout(1, 2);
        placeGreeting();
        placeCountrySelectionBox();
        ArrayList<CarbonEmission> emissionList = (ArrayList<CarbonEmission>) app.getCarbonLog().getEmissionSources();
        this.add(makeDietEditor(emissionList.get(0)));

        for (int i = 1; i < 4; i++) {
            CarbonEmission energy = emissionList.get(i);
            this.add(makeEnergyEditor(energy));
        }

        for (int i = 4; i < 6; i++) {
            CarbonEmission transport = emissionList.get(i);
            this.add(makeTransEditor(transport));
        }

        JPanel buttonRow = makeResultsButton();
        buttonRow.add(app.makeSaveButton());
        this.add(buttonRow);

    }

    // MODIFIES: this
    // EFFECTS: adds a greeting to the top of the panel
    public void placeGreeting() {
        JLabel greeting = new JLabel("Calculate the impact of your lifestyle", JLabel.CENTER);
        greeting.setPreferredSize(new Dimension(WIDTH, ROW_HEIGHT));
        this.add(greeting);
    }

    // MODIFIES: this
    // EFFECTS: adds a combo box for all country options to select from
    private void placeCountrySelectionBox() {
        Set<String> countryNames = CountryList.getCountries().keySet();

        String[] countryNamesArray = new String[countryNames.size()];

        countryNamesArray = countryNames.toArray(countryNamesArray);

        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, countryNamesArray);
        Collections.sort(list);

        countriesBox = new JComboBox(list.toArray());
        countriesBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) countriesBox.getSelectedItem();
                getApp().getCarbonLog().setCountry(selected);
            }
        });

        countriesBox.setSelectedItem("CANADA");

        country = new JLabel("Select your country:", SwingConstants.LEFT);

        JPanel row = new JPanel(rowLayout);
        row.add(country);
        row.add(countriesBox);
        row.setPreferredSize(new Dimension(WIDTH, ROW_HEIGHT));



        this.add(row);
    }

    // MODIFIES: this
    // EFFECTS: creates row to select monthly kwh use
    private JPanel makeEnergyEditor(CarbonEmission c) {
        HomeEnergy energy = (HomeEnergy) c;
        String name = energy.getEnergyType().toString();
        JLabel desc = new JLabel("What is your monthly " + name + " use in kwh?", SwingConstants.LEFT);

        JPanel row = new JPanel(rowLayout);
        row.setPreferredSize(new Dimension(WIDTH, ROW_HEIGHT));
        JPanel slider = getSlider(c);

        row.add(desc);
        row.add(slider);

        return row;

    }

    // REQUIRES: c != null
    // EFFECTS: returns a slider to control c's field that effects their carbon emission
    private JPanel getSlider(CarbonEmission c) {
        JPanel sliderPanel = new JPanel();
        JLabel status = new JLabel("0");
        JSlider slider = new JSlider(0, (int) c.getMax(), 0);
        slider.setMajorTickSpacing((int) Math.round(c.getMax() / 4));
//        slider.setMinorTickSpacing(100);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new SliderListener(c, status));

        sliderPanel.add(slider);
        sliderPanel.add(status);

        return sliderPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates row to select diet type and daily calorie intake
    private JPanel makeDietEditor(CarbonEmission c) {
        Diet diet = (Diet) c;
        JLabel name = new JLabel("What is your diet like?", SwingConstants.LEFT);
        JPanel row = new JPanel(rowLayout);
        String[] dietTypes = {"Heavy meat eater (over 100 g a day)", "Medium meat eater (50 - 99 g a day)",
                "Light meat eater (less than 50 g a day)", "Pescatarian", "Vegetarian", "Vegan"};
        JComboBox dietBox = new JComboBox(dietTypes);

        row.add(name);
        row.add(dietBox);
        row.setPreferredSize(new Dimension(WIDTH, ROW_HEIGHT));

        JPanel calRow = new JPanel(rowLayout);
        calRow.add(new JLabel("Average daily calorie consumption:", SwingConstants.LEFT));
        calRow.add(getSlider(c));
        calRow.setPreferredSize(new Dimension(WIDTH, ROW_HEIGHT));

        JPanel dietRow = new JPanel(new GridLayout(2, 1));

        dietRow.add(row);
        dietRow.add(calRow);

        dietBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selection = e.getActionCommand();
                processDietSelection(diet, selection);
            }
        });

        return dietRow;
    }

    // MODIFIES: this
    // EFFECTS: creates row to select daily transportation travel distance
    private JPanel makeTransEditor(CarbonEmission c) {
        String type = c.getClass().getSimpleName().toLowerCase();
        JLabel name = new JLabel("Average daily travel via " + type + " in km:", SwingConstants.LEFT);
        JPanel row = new JPanel(rowLayout);
        JPanel slider = getSlider(c);

        row.add(name);
        row.add(slider);
        row.setPreferredSize(new Dimension(WIDTH, ROW_HEIGHT));

        return row;
    }

    // EFFECTS: constructs a results button that switches to the overview tab on the console and returns
    //          row with button included
    private JPanel makeResultsButton() {
        JPanel buttonRow = new JPanel();
        JButton resultsButton = new JButton(OVERVIEW_BUTTON);
        buttonRow.add(resultsButton);
        buttonRow.setPreferredSize(new Dimension(WIDTH, ROW_HEIGHT));

        resultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(OVERVIEW_BUTTON)) {
                    getApp().getMainTabs().setSelectedIndex(CarbonFootprintApp.OVERVIEW_TAB_INDEX);
                }
            }
        });
        return buttonRow;
    }

    private class SliderListener implements ChangeListener {
        private CarbonEmission emission;
        private JLabel status;

        // REQUIRES: c != null
        public SliderListener(CarbonEmission c, JLabel status) {
            emission = c;
            this.status = status;
        }

        // MODIFIES: this, c
        // EFFECTS: sets carbon emission of source to value of slider
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider slider = (JSlider) e.getSource();
            status.setText(String.valueOf(slider.getValue()));
            if (!slider.getValueIsAdjusting()) {
                emission.calculateCarbonEmission(slider.getValue());
            }

        }
    }

    // MODIFIES: this
    // EFFECTS: selects diet type based on user input
    private void processDietSelection(Diet diet, String selection) {
        switch (selection) {
            case "Heavy meat eater (over 100 g a day)":
                diet.setDietType(DietType.HIGH_MEAT);
                break;
            case "Medium meat eater (50 - 99 g a day)":
                diet.setDietType(DietType.MEDIUM_MEAT);
                break;
            case "Light meat eater (less than 50 g a day)":
                diet.setDietType(DietType.LOW_MEAT);
                break;
            case "Pescatarian":
                diet.setDietType(DietType.PESCETARIAN);
                break;
            case "Vegetarian":
                diet.setDietType(DietType.VEGETARIAN);
                break;
            case "Vegan":
                diet.setDietType(DietType.VEGAN);
                break;
        }
    }



}
