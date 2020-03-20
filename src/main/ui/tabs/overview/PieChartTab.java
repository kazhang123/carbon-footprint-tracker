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
import javax.swing.border.EmptyBorder;
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
    private CarbonFootprintLog log = getApp().getCarbonLog();

    // REQUIRES: carbon footprint app that controls this tab
    // EFFECTS: creates tab with pie chart displaying the percentage of impact each carbon emission source has
    public PieChartTab(CarbonFootprintApp app) {
        super(app);
        setBackground(Color.WHITE);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        PieDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));

        this.add(chartPanel, BorderLayout.CENTER);

        double emission = getApp().getCarbonLog().getTotalEmission();
        JLabel totalEmission = new JLabel(String.valueOf(emission));

        JLabel carbonEmission = new JLabel("<html> YOUR FOOTPRINT IS <br/>"
                + String.format("%.2f", log.getTotalEmission())
                + " TONNES OF CO2e <br/> PER YEAR  <br/> </html>");

        JLabel offsetTrees = new JLabel("<html>You would need <br/>" +  log.numTreesToOffset(log.getTotalEmission())
                + " <br/>trees to offset your footprint </html>");

        carbonEmission.setBorder(new EmptyBorder(200, 20, 10, 15));
        offsetTrees.setBorder(new EmptyBorder(0, 20, 0, 15));

        carbonEmission.setFont(new Font("Dialog", Font.BOLD, 18));
        offsetTrees.setFont(new Font("Dialog", Font.PLAIN, 18));
        JPanel labelColumn = makeLabelColumn(carbonEmission, offsetTrees);

        this.add(labelColumn);

        app.getMainTabs().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                totalEmission.setText(String.valueOf(getApp().getCarbonLog().getTotalEmission()));

                RingPlot plot = (RingPlot) chart.getPlot();
                plot.setDataset(createDataset());
                plot.setCenterText(makeCenterText(log.getTotalEmission()));
                carbonEmission.setText("<html> YOUR FOOTPRINT IS <br/>"
                        + String.format("%.2f", log.getTotalEmission())
                        + " TONNES OF CO2e <br/> PER YEAR  <br/> </html>");
                offsetTrees.setText("<html>YOU WOULD NEED <br/>" +  log.numTreesToOffset(log.getTotalEmission())
                        + " TREES <br/>" + "TO OFFSET <br/> YOUR FOOTPRINT</html>");

            }
        });
    }

    // EFFECTS: returns data set based on contribution of each emission source to total carbon emission
    private PieDataset createDataset() {
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
        initializeChart(plot);

        plot.setCenterTextFont(new Font("Dialog", Font.BOLD, 25));
        double totalEmission = getApp().getCarbonLog().getTotalEmission();
        plot.setCenterText(makeCenterText(log.getTotalEmission()));
        setSectionColours(plot);
        plot.setBackgroundPaint(null);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}"));

        return chart;
    }

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
        plot.setSectionPaint("Electricity", new Color(255, 190, 117 ));
        plot.setSectionPaint("Gas", new Color(187, 236, 248));
        plot.setSectionPaint("Oil", new Color(189, 241, 178));
        plot.setSectionPaint("Public Transportation", new Color(255, 254, 166));
        plot.setSectionPaint("Vehicle", new Color(164, 196, 243 ));
    }

    // EFFECTS: makes text to be placed in center of ring chart
    private String makeCenterText(double total) {
        return String.format("%.2f", total) + " TONNES";
    }

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
