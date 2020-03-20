package ui.tabs.overview;

import ui.CarbonFootprintApp;
import ui.tabs.Tab;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OverviewTab extends Tab {
    private static final String BAR_ICON = "data/icons/barIcon.png";
    private static final String LINE_ICON = "data/icons/lineIcon.png";
    private static final String PIE_ICON =  "data/icons/pieIcon.png";
    private Image pieImage;
    private Image lineImage;
    private Image barImage;

    // REQUIRES: carbon footprint app that controls this tab
    // EFFECTS: creates overview tab that summarizes user data with charts
    public OverviewTab(CarbonFootprintApp app) {
        super(app);
        setBackground(Color.WHITE);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(SwingConstants.BOTTOM);
        JPanel pieTab = new PieChartTab(app);
        JPanel lineTab = new LineGraphTab(app);
        JPanel barTab = new BarGraphTab(app);

        try {
            pieImage = ImageIO.read(new File(PIE_ICON));
            lineImage = ImageIO.read(new File(LINE_ICON));
            barImage = ImageIO.read(new File(BAR_ICON));

        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon pieIcon = new ImageIcon(pieImage);
        ImageIcon lineIcon = new ImageIcon(lineImage);
        ImageIcon barIcon = new ImageIcon(barImage);
        tabbedPane.addTab("", pieIcon, pieTab);
        tabbedPane.addTab("", lineIcon, lineTab);
        tabbedPane.addTab("", barIcon, barTab);
        tabbedPane.setTabPlacement(SwingConstants.RIGHT);

        // source: https://stackoverflow.com/questions/5183687/java-remove-margin-padding-on-a-jtabbedpane/15877026#15877026
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            private final Insets borderInsets = new Insets(0,0,0,0);
            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {

            }
            @Override
            protected Insets getContentBorderInsets(int tabPlacement) {
                return borderInsets;
            }
        });

        this.add(tabbedPane);


    }


}
