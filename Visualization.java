import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Visualization extends JPanel {
    private int[] data;

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

        for (int i = 0; i < data.length; i++) {
            int barHeight = (int) (((double) data[i] / maxData) * (height - 100));
            int x = i * barWidth + 25;
            int y = height - barHeight - 25;

            g.setColor(Color.BLUE);
            g.fillRect(x, y, barWidth, barHeight);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, barWidth, barHeight);

            g.drawString(String.valueOf(data[i]), x, y - 10);
        }
    }

    public void updateData(int[] newData) {
        data = newData;
        repaint();
    }
}
