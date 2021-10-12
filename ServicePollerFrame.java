package SimpleServicePoller;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ServicePollerFrame extends JFrame {
    static String savePath = "src/SimpleServicePoller/serviceData.txt";
    static int rowHeight = 50;
    static int rowWidth = 200;

    static JTextField textFieldUrl;
    static JTextField textFieldName;
    static JPanel jPanel;

    public ServicePollerFrame() throws IOException {
        jPanel = new JPanel();
        jPanel.setLayout(null);
        add(jPanel);

        JLabel labelClear = new JLabel("Clear Table?");
        labelClear.setBounds(0, rowHeight * 4, rowWidth, rowHeight);
        labelClear.setBorder(BorderFactory.createLoweredBevelBorder());
        labelClear.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel.add(labelClear);

        JLabel labelUrl = new JLabel("URL");
        labelUrl.setBounds(rowWidth, rowHeight * 4, rowWidth, rowHeight);
        labelUrl.setBorder(BorderFactory.createLoweredBevelBorder());
        labelUrl.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel.add(labelUrl);

        JLabel labelName = new JLabel("Name");
        labelName.setBounds(2 * rowWidth, rowHeight * 4, rowWidth, rowHeight);
        jPanel.add(labelName);
        labelName.setBorder(BorderFactory.createLoweredBevelBorder());
        labelName.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelAddService = new JLabel("Add service?");
        labelAddService.setBounds((3 * rowWidth), rowHeight * 4, rowWidth, rowHeight);
        jPanel.add(labelAddService);
        labelAddService.setBorder(BorderFactory.createLoweredBevelBorder());
        labelAddService.setHorizontalAlignment(SwingConstants.CENTER);

        JButton buttonClearData = new JButton();
        buttonClearData.setBounds(0, rowHeight * 5, rowWidth, rowHeight);
        buttonClearData.setText("Clear");
        buttonClearData.setBackground(new Color(255, 17, 0));
        buttonClearData.setForeground(Color.white);
        buttonClearData.setBorder(BorderFactory.createEmptyBorder(0, 0,0, 0));
        jPanel.add(buttonClearData);


        textFieldUrl = new JTextField(40);
        textFieldUrl.setBounds(rowWidth, rowHeight * 5, rowWidth, rowHeight);
        textFieldUrl.setBorder(BorderFactory.createLoweredBevelBorder());
        jPanel.add(textFieldUrl);

        textFieldName = new JTextField(40);
        textFieldName.setBounds(2 * rowWidth, rowHeight * 5, rowWidth, rowHeight);
        textFieldName.setBorder(BorderFactory.createLoweredBevelBorder());
        jPanel.add(textFieldName);

        JButton buttonSubmit = new JButton();
        buttonSubmit.setBounds((3 * rowWidth), rowHeight * 5, rowWidth, rowHeight);
        buttonSubmit.setText("Add");
        buttonSubmit.setBackground(new Color(76, 139, 245));
        buttonSubmit.setForeground(Color.white);
        buttonSubmit.setBorder(BorderFactory.createEmptyBorder(0, 0,0, 0));
        jPanel.add(buttonSubmit);

        JLabel labelMessage = new JLabel("");
        labelMessage.setFont(new Font("Dialog", Font.PLAIN, 12));
        labelMessage.setBounds(0, rowHeight * 6, rowWidth * 4, rowHeight);
        labelMessage.setBorder(BorderFactory.createLoweredBevelBorder());
        labelMessage.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel.add(labelMessage);

        DataOperations dataOperations = new DataOperations(savePath, jPanel, rowWidth, rowHeight);
        ServiceActionListener serviceActionListener = new ServiceActionListener(textFieldUrl, textFieldName, labelMessage, dataOperations);
        dataOperations.viewSavedData();
        ServiceDeletionListener serviceDeletionListener = new ServiceDeletionListener(dataOperations);
        jPanel.repaint();

        buttonSubmit.addActionListener(serviceActionListener);

        periodicUpdateCheck(labelMessage, dataOperations);

        buttonClearData.addActionListener(serviceDeletionListener);
    }

    private void periodicUpdateCheck(JLabel labelMessage, DataOperations dataOperations) {
        String mainMessage = "Checked the status of the pages";
        int timeOutMilliSeconds = 1000 * 5;
        Timer timer = new Timer(timeOutMilliSeconds, e -> {
                try {
                    dataOperations.viewSavedData();
                    labelMessage.setText(mainMessage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        timer.start();
    }

    public static void main(String[] args) throws IOException {
        ServicePollerFrame kryFrame1 = new ServicePollerFrame();
        kryFrame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        kryFrame1.setSize(rowWidth * 4 + 15, rowHeight * 8);
        kryFrame1.setVisible(true);
        kryFrame1.setTitle("Service Poller");

        System.out.println();
    }

}