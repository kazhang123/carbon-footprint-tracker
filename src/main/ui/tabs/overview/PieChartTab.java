package ui.tabs.overview;

import com.sun.tools.internal.xjc.reader.Ring;
import model.emission.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CenterTextMode;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import ui.CarbonFootprintApp;
import ui.tabs.Tab;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.text.AttributedString;
import java.util.ArrayList;

public class PieChartTab extends Tab {
    protected static final int CHART_WIDTH = 600;
    protected static final int CHART_HEIGHT = 600;

    private ArrayList<CarbonEmission> emissions;
    private String[] sourceLabels = {"Diet", "Electricity", "Gas", "Oil", "Public Transportation", "Vehicle"};

    // REQUIRES: carbon footprint app that controls this tab
    // EFFECTS: creates tab with pie chart displaying the percentage of impact each carbon emission source has
    public PieChartTab(CarbonFootprintApp app) {
        super(app);

        double emission = getApp().getCarbonLog().getTotalEmission();
        JLabel totalEmission = new JLabel(String.valueOf(emission));
//        JPanel row = formatRow(totalEmission);



        PieDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));

        JLabel offsetTrees = new JLabel("<html>YOU WOULD NEED <br/>" +  getApp().getCarbonLog().numTreesToOffset()
                + " <br/>TREES TO OFFSET YOUR FOOTPRINT</html>");
        offsetTrees.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(chartPanel);
        this.add(offsetTrees);
//        this.add(row);

        app.getMainTabs().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                totalEmission.setText(String.valueOf(getApp().getCarbonLog().getTotalEmission()));

                RingPlot plot = (RingPlot) chart.getPlot();
                plot.setDataset(createDataset());
                plot.setCenterText(makeCenterText(getApp().getCarbonLog().getTotalEmission()));

            }
        });
    }

    // EFFECTS: returns data set based on contribution of each emission source to total carbon emission
    private PieDataset createDataset() {
        CarbonFootprintLog log = getApp().getCarbonLog();
        ArrayList<CarbonEmission> emissions = (ArrayList<CarbonEmission>) log.getEmissionSources();

        DefaultPieDataset result = new DefaultPieDataset();

        int i = 0;
        for (String s : sourceLabels) {
            result.setValue(s, log.percentageEmission(emissions.get(i)));
            i++;
        }

        return result;
    }

    // EFFECTS: returns pie chart of emission sources
    private JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createRingChart(null, dataset, false, false, false);
        RingPlot plot = (RingPlot) chart.getPlot();
        plot.setSeparatorsVisible(false);
        plot.setShadowPaint(null);
        plot.setLabelShadowPaint(null);
        plot.setLabelBackgroundPaint(null);
        plot.setLabelOutlinePaint(null);
        plot.setOutlinePaint(null);
        plot.setLabelLinkPaint(Color.LIGHT_GRAY);
        plot.setSectionOutlinesVisible(false);
        plot.setSectionDepth(0.3);
        plot.setCenterTextMode(CenterTextMode.FIXED);
        double totalEmission = getApp().getCarbonLog().getTotalEmission();
        plot.setCenterText(makeCenterText(totalEmission));
        setSectionColours(plot);
        plot.setBackgroundPaint(null);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}"));

        return chart;
    }

    // MODIFIES: this
    // EFFECTS: sets the colour for each section of the chart
    private void setSectionColours(RingPlot plot) {
        plot.setSectionPaint("Diet", new Color(250, 176, 215));
        plot.setSectionPaint("Electricity", new Color(255, 190, 117 ));
        plot.setSectionPaint("Gas", new Color(187, 236, 248));
        plot.setSectionPaint("Oil", new Color(189, 241, 178));
        plot.setSectionPaint("Public Transportation", new Color(255, 254, 166));
        plot.setSectionPaint("Vehicle", new Color(164, 196, 243 ));
    }

    // EFFECTS: makes text to be placed in center of ring chart
    private String makeCenterText(double total) {
        return "Your footprint is " + String.format("%.2f", total) + " tonnes of CO2e";
    }


}
