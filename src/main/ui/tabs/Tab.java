package ui.tabs;

import ui.CarbonFootprintApp;

import javax.swing.*;
import java.awt.*;

public abstract class Tab extends JPanel {
    private CarbonFootprintApp app;

    public Tab(CarbonFootprintApp app) {
        this.app = app;
    }

    // EFFECTS: returns the app that controls this tab
    public CarbonFootprintApp getApp() {
        return app;
    }

    protected JPanel formatRow(JComponent component) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(component);

        return p;
    }

}
