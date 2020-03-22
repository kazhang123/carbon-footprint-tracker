package ui.tabs.overview;

import model.emission.CarbonFootprintLog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import ui.CarbonFootprintApp;
import ui.tabs.CalculateTab;
import ui.tabs.Tab;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.List;

/*
represents a tab within the Overview tab that displays a time series graph of user's data
 */
public class LineGraphTab extends Tab {
    private static final int WIDTH = CalculateTab.WIDTH;

    // REQUIRES: carbon footprint app that controls this tab
    // EFFECTS: creates a tab with line graph displaying user's carbon footprint history
    public LineGraphTab(CarbonFootprintApp app) {
        super(app);

        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("YOUR FOOTPRINT OVER TIME");
        JPanel titleRow = formatRow(title);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Dialog", Font.BOLD, 20));
        titleRow.setBackground(Color.WHITE);

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setPreferredSize(new Dimension(WIDTH, PieChartTab.CHART_HEIGHT));


        this.add(titleRow);
        this.add(chartPanel);

        app.getMainTabs().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                XYPlot plot = (XYPlot) chart.getPlot();
                plot.setDataset(createDataset());
            }
        });

    }

    // EFFECTS: returns a time series data set based off history of user's saved carbon footprints
    private XYDataset createDataset() {
        TimeSeries series = new TimeSeries("");
        CarbonFootprintLog currLog = getApp().getCarbonLog();
        currLog.updateDate();

        List<CarbonFootprintLog> logs = getApp().getAllLogs();
        logs.add(currLog);

        for (CarbonFootprintLog log : logs) {
            series.addOrUpdate(new Day(log.getDay(), log.getMonth(), log.getYear()), log.getTotalEmission());
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    // EFFECTS: creates a time series chart from given dataset
    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart("", "Date",
                "Carbon Emission (CO2e / year)", dataset);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(null);

        return chart;
    }
}
