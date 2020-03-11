package ui.tabs.overview;

import ui.CarbonFootprintApp;
import ui.tabs.Tab;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OverviewTab extends Tab {

    // REQUIRES: carbon footprint app that controls this tab
    // EFFECTS: creates overview tab that summarizes user data with charts
    public OverviewTab(CarbonFootprintApp app) {
        super(app);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(SwingConstants.BOTTOM);
        JPanel pieTab = new PieChartTab(app);
        JPanel lineTab = new LineGraphTab(app);
        JPanel barTab = new BarGraphTab(app);

        tabbedPane.addTab("PIE", pieTab);
        tabbedPane.addTab("LINE", lineTab);
        tabbedPane.addTab("BAR", barTab);

        this.add(tabbedPane);


    }


}
