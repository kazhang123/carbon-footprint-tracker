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

/*
represents a tab where user can select between different ways to view
their data
 */
public class OverviewTab extends Tab {
    private static final String BAR_ICON = "data/icons/barIcon.png";
    private static final String LINE_ICON = "data/icons/lineIcon.png";
    private static final String PIE_ICON =  "data/icons/pieIcon.png";
    private JTabbedPane tabbedPane;
    private Image pieImage;
    private Image lineImage;
    private Image barImage;
    private ImageIcon pieIcon;
    private ImageIcon lineIcon;
    private ImageIcon barIcon;

    // REQUIRES: carbon footprint app that controls this tab
    // EFFECTS: creates overview tab that summarizes user data with charts
    public OverviewTab(CarbonFootprintApp app) {
        super(app);
        setBackground(Color.WHITE);
        tabbedPane = new JTabbedPane();
        JPanel pieTab = new PieChartTab(app);
        JPanel lineTab = new LineGraphTab(app);
        JPanel barTab = new BarGraphTab(app);

        makeImages();
        makeIcons();
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

    // EFFECTS: creates image icons from the images created from PNGs in file
    private void makeIcons() {
        pieIcon = new ImageIcon(pieImage);
        lineIcon = new ImageIcon(lineImage);
        barIcon = new ImageIcon(barImage);
    }

    // EFFECTS: creates images from PNGs in file
    private void makeImages() {
        try {
            pieImage = ImageIO.read(new File(PIE_ICON));
            lineImage = ImageIO.read(new File(LINE_ICON));
            barImage = ImageIO.read(new File(BAR_ICON));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
