import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class GUI extends JFrame implements ActionListener {
    private JButton countingSortButton, bubbleSortButton, quickSortButton;
    private JLabel resultLabel;
    private Visualization viz;
    private int[] randomArray;
    private int[] array;
    private long time;
    private int[] stats;

    public GUI() {
        setTitle("Sorting Algorithms");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1680, 800);
        setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        countingSortButton = new JButton("Counting Sort");
        bubbleSortButton = new JButton("Bubble Sort");
        quickSortButton = new JButton("Quick Sort");
        resultLabel = new JLabel();

        buttonPanel.add(countingSortButton);
        buttonPanel.add(bubbleSortButton);
        buttonPanel.add(quickSortButton);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, createTopPanel(), createVisualizationPanel());
        splitPane.setResizeWeight(0.5);

        add(splitPane);
        setVisible(true);

        int length = 10;
        randomArray = ArrayGenerator.generateRandom(length);

        countingSortButton.addActionListener(this);
        bubbleSortButton.addActionListener(this);
        quickSortButton.addActionListener(this);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(resultLabel, BorderLayout.SOUTH);
        panel.add(createButtonPanel(), BorderLayout.NORTH);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(countingSortButton);
        buttonPanel.add(bubbleSortButton);
        buttonPanel.add(quickSortButton);
        return buttonPanel;
    }

    private JPanel createVisualizationPanel() {
        JPanel visualizationPanel = new JPanel(new BorderLayout());
        viz = new Visualization();
        visualizationPanel.add(new JScrollPane(viz), BorderLayout.CENTER);
        return visualizationPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == countingSortButton) {
            array = randomArray.clone();
            time = System.nanoTime();
            Sorting.countingSort(array);
            time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time);
            resultLabel.setText("Counting Sort took " + time + "ms to run");
        } else if (e.getSource() == bubbleSortButton) {

            array = randomArray.clone();
            time = System.nanoTime();
            Sorting.bubbleSort(array);
            time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time);
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    array = randomArray.clone();
                    stats = SortingStats.bubbleSort(array, viz);
                    return null;
                }
                @Override
                protected void done() {
                    resultLabel.setText("Bubble Sort took " + time + "ms to run, " +
                    stats[0] + " Swaps, " +
                    stats[1] + " Comparisons.");
                }
            };
            worker.execute();
        } else if (e.getSource() == quickSortButton) {
            array = randomArray.clone();
            time = System.nanoTime();
            Sorting.quickSort(array, 0, array.length - 1);
            time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time);
            resultLabel.setText("Quick Sort took " + time + "ms to run");
        }
    }
}