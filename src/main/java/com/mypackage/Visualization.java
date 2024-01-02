package com.mypackage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.*;

import javax.swing.SwingUtilities;

public class Visualization {
    private ChartPanel chartPanel;
    private JFreeChart chart;
    private DefaultCategoryDataset dataset;
    private String[] labels = { "Random", "Inversely Sorted", "Sorted" };
    private CategoryPlot plot;
    private CategoryAxis domainAxis;
    private String xlab;
    private BarRenderer renderer;

    public Visualization() {
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createBarChart(
                "Bar Chart",
                "Number",
                "Value",
                dataset);

        chart.getLegend().setVisible(false);
        plot = (CategoryPlot) chart.getPlot();

        domainAxis = plot.getDomainAxis();
        domainAxis.setTickMarksVisible(false);
        domainAxis.setTickLabelsVisible(false);

        renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setSeriesPaint(0, new Color(10,25,200));

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1680, 600));
    }

    public void updatePlot(int[] data) {
        SwingUtilities.invokeLater(() -> {
            dataset.clear();
            if (data.length == 3) {
                setComparisonChart();
                for (int i = 0; i < data.length; i++) {
                    dataset.addValue(data[i], xlab, labels[i]);
                }
            } else if (data.length > 0) {
                setSortingChart();
                for (int i = 0; i < data.length; i++) {
                    dataset.addValue(data[i], "", String.valueOf(i));
                }
            }
        });
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    private void setComparisonChart() {
        domainAxis.setTickMarksVisible(true);
        domainAxis.setTickLabelsVisible(true);
        xlab = "Method";
        domainAxis.setLabel(xlab);
    }

    private void setSortingChart() {
        domainAxis.setTickMarksVisible(false);
        domainAxis.setTickLabelsVisible(false);
        domainAxis.setLabel("Number");
    }
}
