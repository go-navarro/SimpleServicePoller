package SimpleServicePoller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ServicePollerFrame extends JFrame {
    static final int rowHeight = 50;
    static final int rowWidth = 200;

    JTextField textField;
    JTextField textFieldName;
    JLabel labelMessage;
    static JPanel jPanel;

    public ServicePollerFrame(String savePath) throws IOException {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(rowWidth * 4 + 15, rowHeight * 8);
        setTitle("Service Poller");
        setVisible(true);
        setResizable(false);

        jPanel = new JPanel();
        jPanel.setLayout(null);
        add(jPanel);

        DataOperations dataOperations = new DataOperations(savePath);

        addJLabel(jPanel, "Clear Table?", 0, rowHeight * 4, rowWidth, rowHeight);
        addJLabel(jPanel, "URL", rowWidth, rowHeight * 4, rowWidth, rowHeight);
        addJLabel(jPanel, "Name", 2 * rowWidth, rowHeight * 4, rowWidth, rowHeight);
        addJLabel(jPanel, "Add service?", 3 * rowWidth, rowHeight * 4, rowWidth, rowHeight);

        addDeleteAllButton(jPanel, 0, rowHeight * 5, new Color(255, 17, 0),
                dataOperations);
        addTextField(jPanel, rowWidth, rowHeight * 5, rowWidth, rowHeight);

        textFieldName = new JTextField(40);
        textFieldName.setBounds(2 * rowWidth, rowHeight * 5, rowWidth, rowHeight);
        textFieldName.setBorder(BorderFactory.createLoweredBevelBorder());
        jPanel.add(textFieldName);

        labelMessage = getJLabel("", 0, rowHeight * 6, rowWidth * 4, rowHeight);
        labelMessage.setFont(new Font("Dialog", Font.PLAIN, 12));
        jPanel.add(labelMessage);

        ServiceActionListener serviceActionListener = new ServiceActionListener(textField, textFieldName,
                labelMessage, dataOperations);
        addAddButton(jPanel, 3 * rowWidth, 5 * rowHeight, new Color(76, 139, 245), serviceActionListener);

        viewSavedData(dataOperations.defaultTableModel);

        jPanel.repaint();

        periodicUpdateCheck(dataOperations);
    }

    private void addTextField(JPanel jPanel, int x, int y, int width, int height) {
        textField = new JTextField(40);
        textField.setBounds(x, y, width, height);
        textField.setBorder(BorderFactory.createLoweredBevelBorder());
        jPanel.add(textField);
    }

    private void addAddButton(JPanel jPanel, int x, int y, Color color, ActionListener actionListener) {
        JButton buttonClearData = new JButton();
        buttonClearData.setBounds(x, y, rowWidth, rowHeight);
        buttonClearData.setText("Add");
        buttonClearData.setBackground(color);
        buttonClearData.setForeground(Color.white);
        buttonClearData.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        buttonClearData.addActionListener(actionListener);
        jPanel.add(buttonClearData);
    }

    private void addDeleteAllButton(JPanel jPanel, int x, int y, Color color, DataOperations dataOperations) {
        ServiceDeletionListener serviceDeletionListener = new ServiceDeletionListener(dataOperations);

        JButton buttonClearData = new JButton();
        buttonClearData.setBounds(x, y, rowWidth, rowHeight);
        buttonClearData.setText("Clear");
        buttonClearData.setBackground(color);
        buttonClearData.setForeground(Color.white);
        buttonClearData.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        buttonClearData.addActionListener(serviceDeletionListener);

        jPanel.add(buttonClearData);
    }

    private JLabel getJLabel(String text, int x, int y, int width, int height) {
        JLabel labelClear = new JLabel(text);
        labelClear.setBounds(x, y, width, height);
        labelClear.setBorder(BorderFactory.createLoweredBevelBorder());
        labelClear.setHorizontalAlignment(SwingConstants.CENTER);
        return labelClear;
    }

    private void addJLabel(JPanel jPanel, String text, int x, int y, int width, int height) {
        JLabel jLabel = new JLabel(text);
        jLabel.setBounds(x, y, width, height);
        jLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel.add(jLabel);
    }

    private void periodicUpdateCheck(DataOperations dataOperations) {
        int timeOutMilliSeconds = 1000 * 10;
        Timer timer = new Timer(timeOutMilliSeconds, e -> {
                try {
                    viewSavedData(dataOperations.defaultTableModel);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        timer.start();
    }

    public void viewSavedData(DefaultTableModel defaultTableModel) throws IOException {
        DataOperations.updateCurrentStatusOfTable(defaultTableModel);

        JTable jTable = new JTable(defaultTableModel);

        jTable.setPreferredScrollableViewportSize(new Dimension(rowWidth * 3, rowHeight * 3));
        jTable.setRowHeight(rowHeight);
        jTable.setFillsViewportHeight(true);
        JScrollPane jScrollPane = new JScrollPane(jTable);

        jScrollPane.setBounds(0, 0, 4 * rowWidth, 3 * rowHeight);
        jPanel.add(jScrollPane);
        jPanel.repaint();
    }


}