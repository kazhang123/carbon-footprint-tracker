package ui.tabs.overview;

import ui.CarbonFootprintApp;
import ui.tabs.Tab;

public class BarGraphTab extends Tab {

    // REQUIRES; carbon footprint app that controls this tab
    // EFFECTS; creates tab with bar graph comparing user's carbon emission to their country average
    public BarGraphTab(CarbonFootprintApp app) {
        super(app);
    }
}
