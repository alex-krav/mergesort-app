package com.alexoft.ui;

import com.alexoft.service.Log;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class View {
    private static final Font labelFont = new Font(null, Font.PLAIN, 12);
    private static final Font textFont = new Font(null, Font.PLAIN, 14);

    private BoxPane boxPane;
    private LogPane logPane = new LogPane(false);
    private JFrame frame;

    public View() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        frame = new JFrame("Merge sort application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(boxPane = new BoxPane());
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JButton getSelectButton() {
        return boxPane.getInputPane().getFileTab().getSelectButton();
    }
    public JRadioButton getAscRadioButton() {
        return boxPane.getActionPane().getOrderPane().getAscRadioButton();
    }
    public JRadioButton getDescRadioButton() {
        return boxPane.getActionPane().getOrderPane().getDescRadioButton();
    }
    public JButton getSortButton() {
        return boxPane.getActionPane().getSortButton();
    }
    public JButton getCancelButton() {
        return boxPane.getActionPane().getCancelButton();
    }
    public JButton getSaveButton() {
        return boxPane.getActionPane().getSaveButton();
    }

    public Enum<Model.Tab> getActiveTab() {
        return boxPane.getInputPane().getSelectedTab();
    }

    public FileTab getFileTab() {
        return boxPane.getInputPane().getFileTab();
    }

    public void setFileName(String name) {
        getFileTab().getFileName().setText(name);
    }

    public void enableTabs(boolean enabled) {
        boxPane.getInputPane().enableTabs(enabled);
    }

    public void enableOrder(boolean enabled) {
        boxPane.getActionPane().getOrderPane().enableRadioButtons(enabled);
    }

    public void openLogPane() {
        logPane.setVisible(true);
        logPane.getTextArea().setText("");
    }

    public String getInputText() {
        return boxPane.getInputPane().getTextTab().getInputText().getText();
    }

    public JFrame getFrame() {
        return frame;
    }

    public String getArraySize() {
        return boxPane.getInputPane().getGeneratorTab().getSizeField().getText();
    }

    public String getMinValue() {
        return boxPane.getInputPane().getGeneratorTab().getMinField().getText();
    }

    public String getMaxValue() {
        return boxPane.getInputPane().getGeneratorTab().getMaxField().getText();
    }

    public LogPane getLogPane() {
        return logPane;
    }

    private static class BoxPane extends JPanel {
        private InputPane inputPane;
        private ActionPane actionPane;

        public BoxPane() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.75;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.BOTH;
            add((inputPane = new InputPane()), gbc);

            gbc.gridx++;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 0.25;
            gbc.weighty = 1;
            add((actionPane = new ActionPane()), gbc);
        }

        public InputPane getInputPane() {
            return inputPane;
        }

        public ActionPane getActionPane() {
            return actionPane;
        }
    }

    private static class InputPane extends JPanel {
        TextTab textTab;
        FileTab fileTab;
        GeneratorTab generatorTab;
        JTabbedPane tabs;

        public InputPane() {
            setLayout(new GridBagLayout());
            TitledBorder titleBorder = new TitledBorder("Input");
            titleBorder.setTitleFont(labelFont);
            setBorder(new CompoundBorder(titleBorder, new EmptyBorder(8, 0, 0, 0)));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;

            tabs = new JTabbedPane();
            tabs.setFont(labelFont);

            textTab = new TextTab();
            tabs.addTab("Text", null, textTab, null);

            fileTab = new FileTab();
            tabs.addTab("File", null, fileTab, null);

            generatorTab = new GeneratorTab();
            tabs.addTab("Generate", null, generatorTab, null);

            add(tabs, gbc);
            tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        }

        public TextTab getTextTab() {
            return textTab;
        }

        public FileTab getFileTab() {
            return fileTab;
        }

        public GeneratorTab getGeneratorTab() {
            return generatorTab;
        }

        public Enum<Model.Tab> getSelectedTab() {
            if(tabs.getSelectedComponent().equals(textTab))
                return Model.Tab.TEXT;
            else if (tabs.getSelectedComponent().equals(fileTab))
                return Model.Tab.FILE;
            else return Model.Tab.RANDOM;
        }

        public void enableTabs(boolean enabled) {
            tabs.setEnabledAt(0, enabled);
            tabs.setEnabledAt(1, enabled);
            tabs.setEnabledAt(2, enabled);

            textTab.getInputText().setEditable(enabled);
            fileTab.getSelectButton().setEnabled(enabled);
            generatorTab.getSizeField().setEditable(enabled);
            generatorTab.getMinField().setEditable(enabled);
            generatorTab.getMaxField().setEditable(enabled);
        }
    }

    private static class TextTab extends JPanel {
        private JTextArea inputText;

        public TextTab() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            JLabel label = new JLabel("space separated input array (first number - size):");
            label.setFont(labelFont);
            add(label);
            inputText = new JTextArea();
            inputText.setWrapStyleWord(true);
            inputText.setLineWrap(true);
            inputText.setFont(textFont);
            JScrollPane scrollPane = new JScrollPane(inputText);
            add(scrollPane);
        }

        public JTextArea getInputText() {
            return inputText;
        }
    }

    private static class FileTab extends JPanel {
        private JButton select;
        private JLabel fileName;

        public FileTab() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            JLabel label = new JLabel("input file: ");
            label.setFont(labelFont);
            add(label);
            add(new JLabel(" "));
            fileName = new JLabel(" ");
            fileName.setFont(labelFont);
            add(fileName);
            add(new JLabel(" "));
            select = new JButton("Select");
            select.setFont(labelFont);
            add(select);
        }

        public JButton getSelectButton() {
            return select;
        }

        public JLabel getFileName() {
            return fileName;
        }
    }

    private static class GeneratorTab extends JPanel {
        private JTextField size;
        private JTextField min;
        private JTextField max;

        public GeneratorTab() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(2, 2, 2, 2);
            JPanel panel = new JPanel(new GridBagLayout());

            gbc.gridwidth = 2;
            JLabel label1 = new JLabel("generates array of random integers");
            label1.setFont(labelFont);
            panel.add(label1, gbc);
            gbc.gridwidth = 1;
            gbc.gridy++;
            JLabel label2 = new JLabel("array size: ");
            label2.setFont(labelFont);
            panel.add(label2, gbc);
            gbc.gridx++;
            size = new JTextField(10);
            size.setFont(textFont);
            panel.add(size, gbc);
            gbc.gridy++;
            gbc.gridx = 0;

            gbc.gridwidth = 2;
            JLabel label3 = new JLabel("default min: -2,147,483,648");
            label3.setFont(labelFont);
            panel.add(label3, gbc);
            gbc.gridwidth = 1;
            gbc.gridy++;
            JLabel label4 = new JLabel("min value (inclusive): ");
            label4.setFont(labelFont);
            panel.add(label4, gbc);
            gbc.gridx++;
            min = new JTextField(10);
            min.setFont(textFont);
            panel.add(min, gbc);
            gbc.gridy++;
            gbc.gridx = 0;

            gbc.gridwidth = 2;
            JLabel label5 = new JLabel("default max: 2,147,483,647");
            label5.setFont(labelFont);
            panel.add(label5, gbc);
            gbc.gridwidth = 1;
            gbc.gridy++;
            JLabel label6 = new JLabel("max value (inclusive): ");
            label6.setFont(labelFont);
            panel.add(label6, gbc);
            gbc.gridx++;
            max = new JTextField(10);
            max.setFont(textFont);
            panel.add(max, gbc);
            gbc.gridy++;

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(4, 4, 4, 4);
            add(panel, gbc);
        }

        public JTextField getSizeField() {
            return size;
        }

        public JTextField getMinField() {
            return min;
        }

        public JTextField getMaxField() {
            return max;
        }
    }

    private static class ActionPane extends JPanel {
        private JButton sort, cancel, save;
        private OrderPane orderPane;

        public ActionPane() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.weighty = 0.25;
            gbc.insets = new Insets(4, 4, 4, 4);
            add((orderPane = new OrderPane()), gbc);

            gbc.gridy++;
            sort = new JButton("Sort");
            sort.setFont(labelFont);
            add(sort, gbc);
            gbc.gridy++;
            cancel = new JButton("Cancel");
            cancel.setFont(labelFont);
            add(cancel, gbc);
            cancel.setEnabled(false);
            gbc.gridy++;
            save = new JButton("Save");
            save.setFont(labelFont);
            add(save, gbc);
            save.setEnabled(false);
        }

        public JButton getSortButton() {
            return sort;
        }

        public JButton getCancelButton() {
            return cancel;
        }

        public JButton getSaveButton() {
            return save;
        }

        public OrderPane getOrderPane() {
            return orderPane;
        }
    }

    private static class OrderPane extends JPanel {

        private JRadioButton asc;
        private JRadioButton desc;

        public OrderPane() {
            setLayout(new GridBagLayout());
            TitledBorder titleBorder = new TitledBorder("Order");
            titleBorder.setTitleFont(labelFont);
            setBorder(new CompoundBorder(titleBorder, new EmptyBorder(8, 0, 0, 0)));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 4, 0);
            gbc.anchor = GridBagConstraints.WEST;

            JPanel panel = new JPanel(new GridBagLayout());
            asc = new JRadioButton("ascending", true);
            asc.setFont(labelFont);
            panel.add(asc, gbc);
            gbc.gridy++;
            desc = new JRadioButton("descending");
            desc.setFont(labelFont);
            panel.add(desc, gbc);
            ButtonGroup group = new ButtonGroup();
            group.add(asc);
            group.add(desc);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            add(panel, gbc);
        }

        public JRadioButton getAscRadioButton() {
            return asc;
        }

        public JRadioButton getDescRadioButton() {
            return desc;
        }

        public void enableRadioButtons(boolean enabled) {
            asc.setEnabled(enabled);
            desc.setEnabled(enabled);
        }
    }

    public static class LogPane extends JPanel implements Log {
        private final JTextArea textArea;
        private JFrame frame;

        public LogPane(boolean visible) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }

            frame = new JFrame("Output logs");
            textArea = new JTextArea(20, 50);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            textArea.setFont(textFont);
            final JScrollPane scroll = new JScrollPane(textArea);

            frame.add(scroll);
            frame.pack();
            frame.setVisible(visible);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }

        public JTextArea getTextArea() {
            return textArea;
        }

        public void setVisible(boolean visible) {
            frame.setVisible(visible);
        }

        @Override
        public void write(String message) {
            textArea.append(message+"\n");
        }
    }
}