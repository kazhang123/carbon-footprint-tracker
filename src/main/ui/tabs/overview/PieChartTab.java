package ui.tabs.overview;

import model.emission.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CenterTextMode;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import ui.CarbonFootprintApp;
import ui.tabs.Tab;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

/*
represents a tab within the Overview tab that displays user
data as a pie chart
 */
public class PieChartTab extends Tab {
    protected static final int CHART_WIDTH = 600;
    protected static final int CHART_HEIGHT = 600;

    private ArrayList<CarbonEmission> emissions;
    private JLabel carbonEmission;
    private JLabel offsetTrees;

    // REQUIRES: carbon footprint app that controls this tab
    // EFFECTS: creates tab with pie chart displaying the percentage of impact each carbon emission source has
    public PieChartTab(CarbonFootprintApp app) {
        super(app);
        CarbonFootprintLog log = getApp().getCarbonLog();
        setBackground(Color.WHITE);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        PieDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));

        this.add(chartPanel, BorderLayout.CENTER);

        carbonEmission = new JLabel();
        offsetTrees = new JLabel();

        initializeLabels();
        JPanel labelColumn = makeLabelColumn(carbonEmission, offsetTrees);

        this.add(labelColumn);

        app.getMainTabs().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                RingPlot plot = (RingPlot) chart.getPlot();
                plot.setDataset(createDataset());
                plot.setCenterText(makeCenterText(getApp().getCarbonLog().getTotalEmission()));
                initializeLabels();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates JLabels to summarize user data
    private void initializeLabels() {
        CarbonFootprintLog log = getApp().getCarbonLog();
        carbonEmission.setText("<html> YOUR FOOTPRINT IS <br/>"
                + String.format("%.2f", log.getTotalEmission())
                + " TONNES OF CO2e <br/> PER YEAR  <br/> </html>");

        offsetTrees.setText("<html>You would need <br/>" + log.numTreesToOffset(log.getTotalEmission())
                + " trees to <br/> offset your footprint </html>");

        carbonEmission.setBorder(new EmptyBorder(200, 20, 10, 15));
        offsetTrees.setBorder(new EmptyBorder(0, 20, 0, 15));

        carbonEmission.setFont(new Font("Dialog", Font.BOLD, 18));
        offsetTrees.setFont(new Font("Dialog", Font.PLAIN, 18));
    }

    // EFFECTS: returns data set based on contribution of each emission source to total carbon emission
    private PieDataset createDataset() {
        CarbonFootprintLog log = getApp().getCarbonLog();
        ArrayList<CarbonEmission> emissions = (ArrayList<CarbonEmission>) log.getEmissionSources();

        DefaultPieDataset result = new DefaultPieDataset();

        for (CarbonEmission c : emissions) {
            if (c instanceof HomeEnergy) {
                String type = ((HomeEnergy) c).getEnergyType().toString();
                String cap = type.substring(0,1) + type.substring(1).toLowerCase();
                result.setValue(cap, log.percentageEmission(c));
            } else {
                result.setValue(c.getClass().getSimpleName(), log.percentageEmission(c));
            }
        }

        return result;
    }

    // EFFECTS: returns pie chart of emission sources
    private JFreeChart createChart(PieDataset dataset) {
        CarbonFootprintLog log = getApp().getCarbonLog();
        JFreeChart chart = ChartFactory.createRingChart(null, dataset, false, false, false);
        RingPlot plot = (RingPlot) chart.getPlot();
        initializeChart(plot);

        plot.setCenterTextFont(new Font("Dialog", Font.BOLD, 25));
        plot.setCenterText(makeCenterText(getApp().getCarbonLog().getTotalEmission()));
        setSectionColours(plot);
        plot.setBackgroundPaint(null);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}"));

        return chart;
    }

    // MODIFIES: this
    // EFFECTS: initializes chart graphics
    private void initializeChart(RingPlot plot) {
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
    }

    // MODIFIES: this
    // EFFECTS: sets the colour for each section of the chart
    private void setSectionColours(RingPlot plot) {
        plot.setSectionPaint("Diet", new Color(250, 176, 215));
        plot.setSectionPaint("Electricity", new Color(255, 190, 117));
        plot.setSectionPaint("Gas", new Color(187, 236, 248));
        plot.setSectionPaint("Oil", new Color(189, 241, 178));
        plot.setSectionPaint("Transportation", new Color(255, 254, 166));
        plot.setSectionPaint("Vehicle", new Color(164, 196, 243));
    }

    // EFFECTS: returns text to be placed in center of ring chart
    private String makeCenterText(double total) {
        return String.format("%.2f", total) + " TONNES";
    }

    // EFFECTS: returns a JPanel with JLabels stacked on top of each other describing user's data
    private JPanel makeLabelColumn(JLabel carbonEmission, JLabel offsetTrees) {
        JPanel labelColumn = new JPanel();
        labelColumn.setLayout(new BoxLayout(labelColumn, BoxLayout.Y_AXIS));
        labelColumn.add(carbonEmission);
        labelColumn.add(offsetTrees);
        labelColumn.add(Box.createGlue());
        labelColumn.setBackground(Color.WHITE);

        return labelColumn;
    }


}
