package com.mypackage;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Visualization extends JPanel {
    private int[] data;
    private int step;

    public Visualization() {
        data = new int[] {};
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBarPlot(g);
    }

    private void drawBarPlot(Graphics g) {
        if (data == null || data.length == 0)
            return;

        int width = getWidth();
        int height = getHeight();
        int barWidth = (width / data.length);
        int maxData = Arrays.stream(data).max().orElse(0);
        if (maxData == 0)
            maxData = 1;

        g.drawLine(30, 10, 30, height - 10);
        if (maxData <= 10) {
            step = 1;
        } else {
            int magnitude = 10;
            while (maxData >= magnitude * 10) {
                magnitude *= 10;
            }
            step = magnitude;
        }
        int maxTicks = (maxData + step - 1) / step * step;

        for (int i = 0; i <= maxTicks; i += step) {
            int labelY = height - ((i * (height - 20)) / maxTicks);
            g.drawString(String.valueOf(i), 5, labelY);
            g.drawLine(30, labelY, 35, labelY);
        }

        for (int i = 0; i < data.length; i++) {
            int barHeight = (int) (((double) data[i] / maxData) * (height - 50));
            int x = (i * barWidth) + 30;
            int y = height - barHeight - 10;

            g.setColor(Color.BLUE);
            g.fillRect(x, y, barWidth, barHeight);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, barWidth, barHeight);
        }
    }

    public void updateData(int[] newData) {
        data = newData;
        repaint();
    }
}