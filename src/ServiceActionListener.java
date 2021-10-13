package SimpleServicePoller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServiceActionListener implements ActionListener {
    String savePath;

    JTextField textFieldUrl;
    JTextField textFieldName;
    JLabel labelMessage;
    String httpCondition;

    DefaultTableModel defaultTableModel;
    DataOperations dataOperations;

    public ServiceActionListener(JTextField textFieldUrl, JTextField textFieldName, JLabel labelMessage,
                                 DataOperations dataOperations) {
        this.textFieldUrl = textFieldUrl;
        this.textFieldName = textFieldName;
        this.labelMessage = labelMessage;

        this.dataOperations = dataOperations;
        this.savePath = dataOperations.savePath;
        this.defaultTableModel = dataOperations.defaultTableModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            httpCondition = DataOperations.getResponseCondition(textFieldUrl.getText());
            loadingBar();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String creationTime = LocalDateTime.now().format(dateTimeFormatter);
            String lineText = "%s; %s; %s;%s\n".formatted(
                    textFieldUrl.getText(),
                    textFieldName.getText(),
                    creationTime,
                    httpCondition);
            dataOperations.addRowToJTable(textFieldUrl, textFieldName, creationTime, httpCondition);
            labelMessage.setText("");

            FileWriter fileWriter = new FileWriter(savePath, true);
            fileWriter.write(lineText);
            fileWriter.close();
        } catch (IOException ex) {
            String errorMessage = "The URL \"%s\" is not valid.".formatted(textFieldUrl.getText());
            labelMessage.setText(errorMessage);
        }

        cleanTextFields();
    }

    private void loadingBar() {
        int timeOut = 350;
        Timer timer = new Timer(timeOut, new ActionListener() {
            int i = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                i += 1;
                labelMessage.setText("Request is being made" + ".".repeat(i));
                if(i > 3) {
                    labelMessage.setText("");
                    ((Timer)e.getSource()).stop();
                }
            }});
        timer.start();
    }

    public void cleanTextFields() {
        textFieldUrl.setText("");
        textFieldName.setText("");
    }
}
