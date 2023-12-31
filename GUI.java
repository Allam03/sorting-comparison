import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class GUI extends JFrame implements ActionListener {
    private JButton countingSortButton, bubbleSortButton, quickSortButton, randomizeArrayButton;
    private JLabel resultLabel;
    private Visualization viz;
    private int[] randomArray;
    private int[] array;
    private long time;
    private int[] stats;
    private JTextField arrayLengthField, delayField;
    private int length, delay;

    public GUI() {
        setTitle("Sorting Algorithms");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1680, 800);
        setLocationRelativeTo(null);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, createTopPanel(), createVisualizationPanel());
        splitPane.setResizeWeight(0.5);
        add(splitPane);
        setVisible(true);

        stats = new int[] { 0, 0 };
        length = 10;
        delay = 5;
        randomArray = ArrayGenerator.generateRandom(length);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();

        arrayLengthField = new JTextField(10);
        delayField = new JTextField(10);
        delayField.addActionListener(this);
        arrayLengthField.addActionListener(this);
        inputPanel.add(new JLabel("Array Length: "));
        inputPanel.add(arrayLengthField);
        inputPanel.add(new JLabel("Delay in ms: "));
        inputPanel.add(delayField);
        return inputPanel;
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        resultLabel = new JLabel();
        panel.add(resultLabel, BorderLayout.SOUTH);
        panel.add(createInputPanel(), BorderLayout.CENTER);
        panel.add(createButtonPanel(), BorderLayout.NORTH);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        countingSortButton = new JButton("Counting Sort");
        bubbleSortButton = new JButton("Bubble Sort");
        quickSortButton = new JButton("Quick Sort");
        randomizeArrayButton = new JButton("Randomize Array");

        buttonPanel.add(countingSortButton);
        buttonPanel.add(bubbleSortButton);
        buttonPanel.add(quickSortButton);
        buttonPanel.add(randomizeArrayButton);

        countingSortButton.addActionListener(this);
        bubbleSortButton.addActionListener(this);
        quickSortButton.addActionListener(this);
        randomizeArrayButton.addActionListener(this);

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
        if (e.getSource() == randomizeArrayButton) {
            randomArray = ArrayGenerator.generateRandom(length);
        } else if (e.getSource() == arrayLengthField) {
            try {
                int newLength = Integer.parseInt(arrayLengthField.getText());
                if (newLength > 0) {
                    length = newLength;
                    randomArray = ArrayGenerator.generateRandom(length);
                } else {
                    JOptionPane.showMessageDialog(this, "Length must be a positive integer.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for length.");
            }
        } else if (e.getSource() == delayField) {
            try {
                int newDelay = Integer.parseInt(delayField.getText());
                if (newDelay > 0) {
                    delay = newDelay;
                } else {
                    JOptionPane.showMessageDialog(this, "Delay must be a positive integer.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for delay.");
            }
        } else if (e.getSource() == countingSortButton) {
            array = randomArray.clone();
            time = System.nanoTime();
            Sorting.countingSort(array);
            time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time);
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    array = randomArray.clone();
                    stats[0] = 0;
                    stats[1] = 0;
                    SortingStats.countingSort(array, viz, delay);
                    return null;
                }

                @Override
                protected void done() {
                    resultLabel.setText(time + "ms, " +
                            stats[0] + " Swaps, " +
                            stats[1] + " Comparisons.");
                }
            };
            worker.execute();

        } else if (e.getSource() == bubbleSortButton) {

            array = randomArray.clone();
            time = System.nanoTime();
            Sorting.bubbleSort(array);
            time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time);
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    array = randomArray.clone();
                    stats[0] = 0;
                    stats[1] = 0;
                    SortingStats.bubbleSort(array, stats, viz, delay);
                    return null;
                }

                @Override
                protected void done() {
                    resultLabel.setText(time + "ms, " +
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
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    array = randomArray.clone();
                    stats[0] = 0;
                    stats[1] = 0;
                    SortingStats.quickSort(array, 0, array.length - 1, stats, viz, delay);
                    return null;
                }

                @Override
                protected void done() {
                    resultLabel.setText(time + "ms, " +
                            stats[0] + " Swaps, " +
                            stats[1] + " Comparisons.");
                }
            };
            worker.execute();
        }
    }
}