import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GUI extends JFrame implements ActionListener {
    private JButton countingSortButton, bubbleSortButton, quickSortButton, randomizeArrayButton;
    private JLabel resultLabel;
    private JCheckBox visualizeCheckBox;
    private Visualization viz;
    private int[] randomArray;
    private int[] array;
    private long time;
    private int[] stats;
    private JTextField arrayLengthField, delayField;
    private int length, delay;
    private Map<Object, Runnable> actionMap;

    public GUI() {
        setTitle("Sorting Algorithms");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1680, 800);
        setLocationRelativeTo(null);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, createTopPanel(), createVisualizationPanel());
        splitPane.setResizeWeight(0.5);
        add(splitPane);
        setVisible(true);

        stats = new int[] { 0, 0 }; // swaps, comparisons
        length = 10;
        delay = 5;
        randomArray = ArrayGenerator.generateRandom(length);

        actionMap = new HashMap<>();
        actionMap.put(arrayLengthField, () -> updateArrayLength());
        actionMap.put(delayField, () -> updateDelay());
        actionMap.put(countingSortButton, () -> performCountingSort());
        actionMap.put(bubbleSortButton, () -> performBubbleSort());
        actionMap.put(quickSortButton, () -> performQuickSort());
        actionMap.put(randomizeArrayButton, () -> randomizeArray());
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();

        arrayLengthField = new JTextField("10", 5);
        arrayLengthField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                updateArrayLength();
            }
        });

        delayField = new JTextField("5", 5);
        delayField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                updateDelay();
            }
        });
        inputPanel.add(new JLabel("Array Length: "));
        inputPanel.add(arrayLengthField);
        inputPanel.add(new JLabel("Delay in ms: "));
        inputPanel.add(delayField);
        return inputPanel;
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultLabel = new JLabel();
        labelPanel.add(resultLabel);
        panel.add(labelPanel, BorderLayout.SOUTH);
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
        visualizeCheckBox = new JCheckBox("Visualize");

        buttonPanel.add(countingSortButton);
        buttonPanel.add(bubbleSortButton);
        buttonPanel.add(quickSortButton);
        buttonPanel.add(randomizeArrayButton);
        buttonPanel.add(visualizeCheckBox);

        countingSortButton.addActionListener(this);
        bubbleSortButton.addActionListener(this);
        quickSortButton.addActionListener(this);
        randomizeArrayButton.addActionListener(this);
        visualizeCheckBox.setSelected(true);

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
        Runnable action = actionMap.get(e.getSource());
        if (action != null) {
            action.run();
        }
    }

    public void disableInput() {
        countingSortButton.setEnabled(false);
        bubbleSortButton.setEnabled(false);
        quickSortButton.setEnabled(false);
        randomizeArrayButton.setEnabled(false);
        arrayLengthField.setEnabled(false);
        delayField.setEnabled(false);
        visualizeCheckBox.setEnabled(false);
    }

    public void enableInput() {
        countingSortButton.setEnabled(true);
        bubbleSortButton.setEnabled(true);
        quickSortButton.setEnabled(true);
        randomizeArrayButton.setEnabled(true);
        arrayLengthField.setEnabled(true);
        delayField.setEnabled(true);
        visualizeCheckBox.setEnabled(true);
    }

    public void randomizeArray() {
        randomArray = ArrayGenerator.generateRandom(length);
    }

    public void updateArrayLength() {
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
    }

    public void updateDelay() {
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
    }

    public void performCountingSort() {
        disableInput();
        array = randomArray.clone();
        stats[0] = 0;
        stats[1] = 0;
        time = System.nanoTime();
        Sorting.countingSort(array);
        time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time);
        resultLabel.setText(time + "ms, " +
                stats[0] + " Swaps, " +
                stats[1] + " Comparisons.");
        if (!visualizeCheckBox.isSelected()){
            enableInput();
            return;
        }
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                array = randomArray.clone();
                SortingStats.countingSort(array, viz, delay);
                return null;
            }

            @Override
            protected void done() {
                enableInput();
            }
        };
        worker.execute();

    }

    private void performBubbleSort() {
        disableInput();
        array = randomArray.clone();
        stats[0] = 0;
        stats[1] = 0;
        time = System.nanoTime();
        Sorting.bubbleSort(array, stats);
        time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time);
        resultLabel.setText(time + "ms, " +
                stats[0] + " Swaps, " +
                stats[1] + " Comparisons.");
        if (!visualizeCheckBox.isSelected()){
            enableInput();
            return;
        }
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                array = randomArray.clone();
                SortingStats.bubbleSort(array, viz, delay);
                return null;
            }

            @Override
            protected void done() {
                enableInput();

            }
        };
        worker.execute();
    }

    private void performQuickSort() {
        disableInput();
        array = randomArray.clone();
        stats[0] = 0;
        stats[1] = 0;
        time = System.nanoTime();
        Sorting.quickSort(array, 0, array.length - 1, stats);
        time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time);
        resultLabel.setText(time + "ms, " +
                stats[0] + " Swaps, " +
                stats[1] + " Comparisons.");
        if (!visualizeCheckBox.isSelected()){
            enableInput();
            return;
        }
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                array = randomArray.clone();
                SortingStats.quickSort(array, 0, array.length - 1, viz, delay);
                return null;
            }

            @Override
            protected void done() {
                enableInput();

            }
        };
        worker.execute();
    }
}