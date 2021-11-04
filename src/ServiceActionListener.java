package SimpleServicePoller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
            URL url = new URL(textFieldUrl.getText());
            URLConnection urlConnection = url.openConnection();
            httpCondition = DataOperations.getResponseDescription(urlConnection);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String creationTime = LocalDateTime.now().format(dateTimeFormatter);
            String lineText = "%s; %s; %s;%s\n".formatted(
                    textFieldUrl.getText(),
                    textFieldName.getText(),
                    creationTime,
                    httpCondition);

            addRowToJTable(defaultTableModel, textFieldUrl, textFieldName, creationTime, httpCondition);

            labelMessage.setText("");

            FileWriter fileWriter = new FileWriter(savePath, true);
            fileWriter.write(lineText);
            fileWriter.close();
        } catch (MalformedURLException malformedURLException) {
            String errorMessage = "The URL \"%s\" is not valid.".formatted(textFieldUrl.getText());
            labelMessage.setText(errorMessage);
        } catch (IOException ex) {
            String errorMessage = "Could not connect to \"%s\"".formatted(textFieldUrl.getText());
            labelMessage.setText(errorMessage);
        }

        cleanTextFields();
    }

    public void cleanTextFields() {
        textFieldUrl.setText("");
        textFieldName.setText("");
    }

    public static void addRowToJTable(DefaultTableModel defaultTableModel, JTextField textFieldUrl,
                                      JTextField textFieldName, String creationTime, String httpStatus) {
        Object[] newRow = {
                textFieldUrl.getText(),
                textFieldName.getText(),
                creationTime,
                httpStatus, httpStatus};
        defaultTableModel.addRow(newRow);
    }

}