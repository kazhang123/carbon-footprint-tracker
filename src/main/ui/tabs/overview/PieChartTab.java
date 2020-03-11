package ui.tabs.overview;

import ui.CarbonFootprintApp;
import ui.tabs.Tab;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class PieChartTab extends Tab {

    // REQUIRES: carbon footprint app that controls this tab
    // EFFECTS: creates tab with pie chart displaying the percentage of impact each carbon emission source has
    public PieChartTab(CarbonFootprintApp app) {
        super(app);

        double emission = getApp().getCarbonLog().getTotalEmission();
        JLabel totalEmission = new JLabel(String.valueOf(emission));
        JPanel row = formatRow(totalEmission);

        this.add(row);

        app.getMainTabs().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                totalEmission.setText(String.valueOf(getApp().getCarbonLog().getTotalEmission()));
            }
        });
    }
}
