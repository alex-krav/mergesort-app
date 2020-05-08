package com.alexoft.ui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;

public class App {
    public static void main(String[] args) {
        new App();
    }

    public App() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Merge sort application");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new BoxPane());
                frame.setResizable(false);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}

class BoxPane extends JPanel {
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
}

class InputPane extends JPanel {

    public InputPane() {
        setLayout(new GridBagLayout());
        TitledBorder titleBorder = new TitledBorder("Input");
        titleBorder.setTitleFont(new Font(null, 0, 12));
        setBorder(new CompoundBorder(titleBorder, new EmptyBorder(8, 0, 0, 0)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font(null, 0, 12));

        JComponent textTab = new TextTab();
        tabs.addTab("Text", null, textTab, null);

        JComponent fileTab = new FileTab();
        tabs.addTab("File", null, fileTab, null);

        JComponent generatorTab = new GeneratorTab();
        tabs.addTab("Generate", null, generatorTab, null);

        add(tabs, gbc);
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
}

class TextTab extends JPanel {
    private JTextArea inputText;
    private JLabel label;

    public TextTab() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label = new JLabel("space separated input array (first number - size):");
        label.setFont(new Font(null, 0, 12));
        add(label);
        inputText = new JTextArea();
        inputText.setWrapStyleWord(true);
        inputText.setLineWrap(true);
        inputText.setFont(new Font(null, 0, 14));
        JScrollPane scrollPane = new JScrollPane(inputText);
        add(scrollPane);
    }
}

class FileTab extends JPanel {
    private JButton select;
    private JLabel label, fileName;
    private JFileChooser fileChooser;

    public FileTab() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label = new JLabel("input file: ");
        label.setFont(new Font(null, 0, 12));
        add(label); add(new JLabel(" "));
        fileName = new JLabel(" ");
        fileName.setFont(new Font(null, 0, 12));
        add(fileName); add(new JLabel(" "));
        select = new JButton("Select");
        select.setFont(new Font(null, 0, 12));
        add(select);

        select.addActionListener(e -> selectFile());
    }

    public void selectFile() {
        JFileChooser chooser = new JFileChooser();
        // optionally set chooser options ...
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            // read  and/or display the file somehow. ....
            fileName.setText(trim(f.getName()));
        } else {
            // user changed their mind
        }
    }

    public String trim(String name) {
        int extId = name.lastIndexOf('.');
        String ext = (extId > -1) ? name.substring(extId) : "";
        if (name.length() > 30)
            return name.substring(0, 30) + "... " + ext;
        else
            return name;
    }
}

class GeneratorTab extends JPanel {
    private JTextField size;
    private JTextField min;
    private JTextField max;

    public GeneratorTab() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2,2,2,2);
        JPanel panel = new JPanel(new GridBagLayout());

        gbc.gridwidth = 2;
        JLabel label1 = new JLabel("generates array of random integers");
        label1.setFont(new Font(null, 0, 12));
        panel.add(label1, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;
        JLabel label2 = new JLabel("array size: ");
        label2.setFont(new Font(null, 0, 12));
        panel.add(label2, gbc);
        gbc.gridx++;
        size = new JTextField(10);
        size.setFont(new Font(null, 0, 14));
        panel.add(size, gbc);
        gbc.gridy++;
        gbc.gridx = 0;

        gbc.gridwidth = 2;
        JLabel label3 = new JLabel("default min: -2,147,483,648");
        label3.setFont(new Font(null, 0, 12));
        panel.add(label3, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;
        JLabel label4 = new JLabel("min value (inclusive): ");
        label4.setFont(new Font(null, 0, 12));
        panel.add(label4, gbc);
        gbc.gridx++;
        min = new JTextField(10);
        min.setFont(new Font(null, 0, 14));
        panel.add(min, gbc);
        gbc.gridy++;
        gbc.gridx = 0;

        gbc.gridwidth = 2;
        JLabel label5 = new JLabel("default max: 2,147,483,647");
        label5.setFont(new Font(null, 0, 12));
        panel.add(label5, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;
        JLabel label6 = new JLabel("max value (inclusive): ");
        label6.setFont(new Font(null, 0, 12));
        panel.add(label6, gbc);
        gbc.gridx++;
        max = new JTextField(10);
        max.setFont(new Font(null, 0, 14));
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
}

class ActionPane extends JPanel {
    private JButton sort, cancel, save;

    public ActionPane() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.25;
        gbc.insets = new Insets(4, 4, 4, 4);
        JPanel orderPane = new OrderPane();
        add(orderPane, gbc);

        gbc.gridy++;
        sort = new JButton("Sort");
        sort.setFont(new Font(null, 0, 12));
        add(sort, gbc);
        gbc.gridy++;
        cancel = new JButton("Cancel");
        cancel.setFont(new Font(null,0,12));
        add(cancel, gbc);
        cancel.setEnabled(false);
        gbc.gridy++;
        save = new JButton("Save");
        save.setFont(new Font(null, 0, 12));
        add(save, gbc);
        save.setEnabled(false);

        sort.addActionListener(event -> {
            new LogPane();
            sort.setEnabled(false);
        });
    }
}

class OrderPane extends JPanel {

    private JRadioButton asc;
    private JRadioButton desc;

    public OrderPane() {
        setLayout(new GridBagLayout());
        TitledBorder titleBorder = new TitledBorder("Order");
        titleBorder.setTitleFont(new Font(null, 0, 12));
        setBorder(new CompoundBorder(titleBorder, new EmptyBorder(8, 0, 0, 0)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 4, 0);
        gbc.anchor = GridBagConstraints.WEST;

        JPanel panel = new JPanel(new GridBagLayout());
        asc = new JRadioButton("ascending", true);
        asc.setFont(new Font(null, 0, 12));
        panel.add(asc, gbc);
        gbc.gridy++;
        desc = new JRadioButton("descending");
        desc.setFont(new Font(null, 0, 12));
        panel.add(desc, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        add(panel, gbc);
    }
}

class LogPane extends JPanel {
    public static void main(String[] args) {
        new LogPane();
    }

    public LogPane() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Output logs");
                final JTextArea log = new JTextArea(20,50);
                log.setWrapStyleWord(true);
                log.setLineWrap(true);
                log.setFont(new Font(null, 0, 14));
                final JScrollPane scroll = new JScrollPane(log);

                frame.add(scroll);
                frame.pack();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
    }
}