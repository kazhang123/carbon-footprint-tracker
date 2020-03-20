package ui.tabs.overview;

import model.emission.CarbonFootprintLog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import ui.CarbonFootprintApp;
import ui.tabs.CalculateTab;
import ui.tabs.Tab;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class BarGraphTab extends Tab {
    private static final int WIDTH = CalculateTab.WIDTH;
    private static final int HEIGHT = 190;
    private static final String FOOTPRINT_KEY = "carbon footprint in tonnes of CO2e / year";
    private static final String TREE_KEY = "equivalence in number of trees";

    // REQUIRES; carbon footprint app that controls this tab
    // EFFECTS; creates tab with bar graph comparing user's carbon emission to their country average
    public BarGraphTab(CarbonFootprintApp app) {
        super(app);

        setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("HOW DO YOU COMPARE?");
        title.setFont(new Font("Dialog", Font.BOLD, 20));
        JPanel titleRow = formatRow(title);
        titleRow.setBackground(Color.WHITE);

        CategoryDataset footprintDataset = createFootprintDataset();
        JFreeChart footprintChart = createChart(footprintDataset, FOOTPRINT_KEY);

        CategoryDataset treeDataset = createTreesDataset();
        JFreeChart treeChart = createChart(treeDataset, TREE_KEY);

        ChartPanel footprintChartPanel = new ChartPanel(footprintChart);
        footprintChartPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        ChartPanel treeChartPanel = new ChartPanel(treeChart);
        treeChartPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.add(titleRow);
        this.add(footprintChartPanel);
        this.add(treeChartPanel);

        app.getMainTabs().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                CategoryPlot footprintPlot = footprintChart.getCategoryPlot();
                footprintPlot.setDataset(createFootprintDataset());

                CategoryPlot treePlot = treeChart.getCategoryPlot();
                treePlot.setDataset(createTreesDataset());
            }
        });

    }

    // EFFECTS: creates a dataset comparing user's carboon footprint to their country average
    private CategoryDataset createFootprintDataset() {
        CarbonFootprintLog log = getApp().getCarbonLog();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(log.getTotalEmission(), FOOTPRINT_KEY, "You");
        dataset.setValue(log.getAvgCountryFootprint(), FOOTPRINT_KEY, log.getCountry() + "'s average");

        return dataset;
    }

    // EFFECTS: creates a dataset comparing user's number of trees needed to
    //          offset their footprint to their country average
    private CategoryDataset createTreesDataset() {
        CarbonFootprintLog log = getApp().getCarbonLog();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(log.numTreesToOffset(log.getTotalEmission()), TREE_KEY, "You");
        dataset.setValue(log.numTreesToOffset(log.getAvgCountryFootprint()), TREE_KEY, log.getCountry() + "'s average");

        return dataset;
    }

    // EFFECTS: creates a horizontal bar graph based off given dataset
    private JFreeChart createChart(CategoryDataset dataset, String key) {
        JFreeChart chart = ChartFactory.createBarChart(null, key, null, dataset,
                PlotOrientation.HORIZONTAL, false, true, false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        plot.setBackgroundPaint(null);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setMaximumBarWidth(0.1);
        renderer.setSeriesPaint(0, new Color(47, 163, 111));

        plot.getDomainAxis().setLabelFont(new Font("Dialog", Font.PLAIN, 11));
        plot.setOutlinePaint(null);

        return chart;

    }
}
