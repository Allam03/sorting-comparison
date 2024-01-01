package com.mypackage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;

public class Visualization {
    private ChartPanel chartPanel;
    private JFreeChart chart;
    private DefaultCategoryDataset dataset;
    private String[] labels = { "Random", "Sorted", "Inversely Sorted" };

    public Visualization() {
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createBarChart(
                "Bar Chart",
                "Number",
                "Value",
                dataset);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
    }

    public void updatePlot(int[] data) {
        dataset.clear();
        if (data.length == 3) {
            for (int i = 0; i < data.length; i++) {
                dataset.addValue(data[i], "Method", labels[i]);
            }
        } else {
            for (int i = 0; i < data.length; i++) {
                dataset.addValue(data[i], "Number", String.valueOf(i));
            }
        }
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}
