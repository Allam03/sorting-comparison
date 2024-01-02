package com.mypackage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.*;

public class Visualization {
    private ChartPanel chartPanel;
    private JFreeChart chart;
    private DefaultCategoryDataset dataset;
    private String[] labels = { "Random", "Inversely Sorted", "Sorted" };
    CategoryPlot plot;
    CategoryAxis domainAxis;
    String xlab;

    public Visualization() {
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createBarChart(
                "Bar Chart",
                "Number",
                "Value",
                dataset);

        plot = (CategoryPlot) chart.getPlot();
        domainAxis = plot.getDomainAxis();

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1680, 600));
    }

    public void updatePlot(int[] data) {
        dataset.clear();
        if (data.length == 3) {
            xlab = "Method";
            domainAxis.setLabel(xlab);
            for (int i = 0; i < data.length; i++) {
                dataset.addValue(data[i], xlab, labels[i]);
            }
        } else if (data.length > 0) {
            xlab = "Number";
            for (int i = 0; i < data.length; i++) {
                dataset.addValue(data[i], xlab, String.valueOf(i));
            }
        }
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}
