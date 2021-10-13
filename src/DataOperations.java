package SimpleServicePoller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class DataOperations {
    DefaultTableModel defaultTableModel;
    JPanel jPanel;
    int rowWidth;
    int rowHeight;
    String savePath;

    public DataOperations(String savePath, JPanel jPanel, int rowWidth, int rowHeight) throws IOException {
        this.defaultTableModel = initializeTable(savePath);
        this.jPanel = jPanel;
        this.rowWidth = rowWidth;
        this.rowHeight = rowHeight;
        this.savePath = savePath;
    }

    public static DefaultTableModel initializeTable(String savePath) throws IOException {
        Object[] colNames = {"URL", "Name", "Creation Time", "Current Status"};

        FileReader fileReader = new FileReader(savePath);
        BufferedReader in = new BufferedReader(fileReader);

        List<String> savedDataList = in.lines().toList();
        Object[][] savedDataArray = new Object[savedDataList.size()][4];

        for(int i = 0; i < savedDataList.size(); i++) {
            Object[] split = savedDataList.get(i).split(";");
            split[3] = getResponseCondition(split[0].toString());

            savedDataArray[i] = split;
        }

        return new DefaultTableModel(savedDataArray, colNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public void viewSavedData() throws IOException {
        JTable jTable = new JTable(defaultTableModel);

        jTable.setPreferredScrollableViewportSize(new Dimension(rowWidth * 3, rowHeight * 3));
        jTable.setRowHeight(rowHeight);
        jTable.setFillsViewportHeight(true);
        JScrollPane jScrollPane = new JScrollPane(jTable);

        jScrollPane.setBounds(0, 0, 4 * rowWidth, 3 * rowHeight);
        jPanel.add(jScrollPane);
        jPanel.repaint();
    }

    public void addRowToJTable(JTextField textFieldUrl, JTextField textFieldName, String creationTime, String httpStatus) {
        Object[] newRow = {
                textFieldUrl.getText(),
                textFieldName.getText(),
                creationTime,
                httpStatus};
        defaultTableModel.addRow(newRow);
    }

    public static String getResponseCondition(String fieldUrl) throws IOException {
        int httpStatus = getResponseCode(fieldUrl);
        String httpCondition;
        if((httpStatus == 200)) {
            httpCondition = "OK";
        }else {
            httpCondition = "FAIL";
        }
        return httpCondition;
    }

    private static int getResponseCode(String urlPath) throws IOException {
        URL url = new URL(urlPath);
        URLConnection urlConnection = url.openConnection();

        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        return httpURLConnection.getResponseCode();
    }

}
