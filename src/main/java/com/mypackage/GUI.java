package com.mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.HashMap;
import java.util.Map;

public class GUI extends JFrame implements ActionListener {
    private JButton sortButton, randomizeArrayButton, abortButton, compareButton;
    private JLabel resultLabel;
    private JCheckBox visualizeCheckBox;
    private JComboBox<String> methodMenu, statMenu;
    private JTextField arrayLengthField, delayField;
    private Visualization viz;
    private SwingWorker<Void, Void> currentWorker;
    private Map<Object, Runnable> actionMap;
    private int[] randomArray, array, comparisonStats, swaps_comparisons;
    private long time;
    private int length, delay;
    String method, stat, parameter;

    public GUI() {
        setTitle("Sorting Algorithms");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1680, 800);
        setLocationRelativeTo(null);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, createTopPanel(), createVisualizationPanel());
        splitPane.setResizeWeight(0.5);
        add(splitPane);
        setVisible(true);

        swaps_comparisons = new int[] { 0, 0 };
        comparisonStats = new int[] { 0, 0, 0 };
        length = 10;
        delay = 5;
        randomArray = ArrayGenerator.generateRandom(length);

        actionMap = new HashMap<>();
        actionMap.put(arrayLengthField, () -> updateArrayLength());
        actionMap.put(delayField, () -> updateDelay());
        actionMap.put(sortButton, () -> performSort());
        actionMap.put(randomizeArrayButton, () -> randomizeArray());
        actionMap.put(abortButton, () -> performAbort());
        actionMap.put(compareButton, () -> performCompare());
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
        sortButton = new JButton("Sort");
        randomizeArrayButton = new JButton("Randomize Array");
        visualizeCheckBox = new JCheckBox("Visualize");
        abortButton = new JButton("Abort");
        compareButton = new JButton("Compare");
        String[] methodOptions = { "Counting", "Bubble", "Quick" };
        methodMenu = new JComboBox<>(methodOptions);
        String[] statOptions = { "Runtime", "Swaps", "Comparisons" };
        statMenu = new JComboBox<>(statOptions);

        buttonPanel.add(sortButton);
        buttonPanel.add(methodMenu);
        buttonPanel.add(abortButton);
        buttonPanel.add(randomizeArrayButton);
        buttonPanel.add(compareButton);
        buttonPanel.add(statMenu);
        buttonPanel.add(visualizeCheckBox);

        sortButton.addActionListener(this);
        randomizeArrayButton.addActionListener(this);
        visualizeCheckBox.setSelected(true);
        abortButton.addActionListener(this);
        compareButton.addActionListener(this);

        return buttonPanel;
    }

    private JPanel createVisualizationPanel() {
        JPanel visualizationPanel = new JPanel(new BorderLayout());
        viz = new Visualization();
        visualizationPanel.add(viz.getChartPanel(), BorderLayout.CENTER);
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
        sortButton.setEnabled(false);
        randomizeArrayButton.setEnabled(false);
        arrayLengthField.setEnabled(false);
        delayField.setEnabled(false);
        visualizeCheckBox.setEnabled(false);
        compareButton.setEnabled(false);
        methodMenu.setEnabled(false);
        statMenu.setEnabled(false);
    }

    public void enableInput() {
        sortButton.setEnabled(true);
        randomizeArrayButton.setEnabled(true);
        arrayLengthField.setEnabled(true);
        delayField.setEnabled(true);
        visualizeCheckBox.setEnabled(true);
        compareButton.setEnabled(true);
        methodMenu.setEnabled(true);
        statMenu.setEnabled(true);
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

    public void performSort() {
        method = (String) methodMenu.getSelectedItem();
        if (method.equals("Counting")) {
            performCountingSort();
            return;
        }
        if (method.equals("Bubble")) {
            performBubbleSort();
            return;
        }
        if (method.equals("Quick")) {
            performQuickSort();
            return;
        }
    }

    public void performCountingSort() {
        disableInput();
        array = randomArray.clone();
        swaps_comparisons[0] = 0;
        swaps_comparisons[1] = 0;
        time = System.currentTimeMillis();
        Sorting.countingSort(array);
        time = System.currentTimeMillis() - time;
        resultLabel.setText(time + "ms, " +
                swaps_comparisons[0] + " Swaps, " +
                swaps_comparisons[1] + " Comparisons.");
        if (!visualizeCheckBox.isSelected()) {
            enableInput();
            return;
        }
        currentWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                array = randomArray.clone();
                try {
                    SortingViz.countingSort(array, viz, delay);
                } catch (InterruptedException e) {
                }
                return null;
            }

            @Override
            protected void done() {
                enableInput();
            }
        };
        currentWorker.execute();

    }

    private void performBubbleSort() {
        disableInput();
        array = randomArray.clone();
        swaps_comparisons[0] = 0;
        swaps_comparisons[1] = 0;
        time = System.currentTimeMillis();
        Sorting.bubbleSort(array, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        resultLabel.setText(time + "ms, " +
                swaps_comparisons[0] + " Swaps, " +
                swaps_comparisons[1] + " Comparisons.");
        if (!visualizeCheckBox.isSelected()) {
            enableInput();
            return;
        }
        currentWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                array = randomArray.clone();
                try {
                    SortingViz.bubbleSort(array, viz, delay);
                } catch (InterruptedException e) {
                }
                return null;
            }

            @Override
            protected void done() {
                enableInput();
            }
        };
        currentWorker.execute();
    }

    private void performQuickSort() {
        disableInput();
        array = randomArray.clone();
        swaps_comparisons[0] = 0;
        swaps_comparisons[1] = 0;
        time = System.currentTimeMillis();
        Sorting.quickSort(array, 0, array.length - 1, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        resultLabel.setText(time + "ms, " +
                swaps_comparisons[0] + " Swaps, " +
                swaps_comparisons[1] + " Comparisons.");
        if (!visualizeCheckBox.isSelected()) {
            enableInput();
            return;
        }
        currentWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                array = randomArray.clone();
                try {
                    SortingViz.quickSort(array, 0, array.length - 1, viz, delay);
                } catch (InterruptedException e) {
                }
                return null;
            }

            @Override
            protected void done() {
                enableInput();
            }
        };
        currentWorker.execute();
    }

    private void performAbort() {
        if (currentWorker != null && !currentWorker.isDone()) {
            currentWorker.cancel(true);
            currentWorker = null;
            enableInput();
        }
    }

    private void performCompare() {
        stat = (String) statMenu.getSelectedItem();
        method = (String) methodMenu.getSelectedItem();
        ComparisonStats compare = new ComparisonStats(randomArray, swaps_comparisons);
        switch (stat) {
            case "Runtime":
                parameter = "ms";
                compare.compareRuntime(method);
                break;

            case "Swaps":
                parameter = "swaps";
                compare.compareSwaps(method);
                break;

            case "Comparisons":
                parameter = "comparisons";
                compare.compareComparisons(method);
                break;

            default:
                break;
        }
        comparisonStats = compare.getComparisonStats();
        printResult();
        if (visualizeCheckBox.isSelected())
            viz.updatePlot(comparisonStats);
    }

    private void printResult() {
        resultLabel.setText("At length " + length + ", " + method + " sort: " +
                "Random " + comparisonStats[0] + " " + parameter + ", " +
                "Inversely sorted " + comparisonStats[1] + " " + parameter + ", " +
                "Sorted " + comparisonStats[2] + " " + parameter);
    }
}