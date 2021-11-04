package SimpleServicePoller;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Vector;

public class DataOperations {
    DefaultTableModel defaultTableModel;
    String savePath;

    public DataOperations(String savePath) throws IOException {
        this.defaultTableModel = initializeTable(savePath);
        this.savePath = savePath;
    }

    public static DefaultTableModel initializeTable(String savePath) throws IOException {
        Object[] colNames = {"URL", "Name", "Saving time", "Original Status"};

        FileReader fileReader = new FileReader(savePath);
        BufferedReader in = new BufferedReader(fileReader);

        List<String> savedDataList = in.lines().toList();

        Object[][] savedDataArray = new Object[savedDataList.size()][4];

        for(int i = 0; i < savedDataList.size(); i++) {
            Object[] split = savedDataList.get(i).split(";");
            savedDataArray[i] = split;
        }

        DefaultTableModel defaultTableModel = new DefaultTableModel(savedDataArray, colNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        appendCurrentStatus(defaultTableModel);
        return defaultTableModel;
    }

    public static String getResponseDescription(URLConnection urlConnection) {
        try{
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            int httpStatus = httpURLConnection.getResponseCode();
            if(httpStatus == 200) {
                return "OK";
            }
        }
        catch(IOException e) {
            return "NO CONNECTION";
        }
        return "FAIL";
    }

    public static void appendCurrentStatus(DefaultTableModel defaultTableModel) throws IOException {
        Vector<String> currentStatus = new Vector<>();

        for(int i = 0; i < defaultTableModel.getRowCount(); i++){
            String urlString = defaultTableModel.getValueAt(i, 0).toString();
            String responseDescription = DataOperations.getResponseDescription(new URL(urlString).openConnection());
            currentStatus.add(responseDescription);
        }
        defaultTableModel.addColumn("Current Status", currentStatus);
    }

    public static void updateCurrentStatusOfTable(DefaultTableModel defaultTableModel) throws IOException {
        for(int i = 0; i < defaultTableModel.getRowCount(); i++){
            String urlString = defaultTableModel.getValueAt(i, 0).toString();
            String responseDescription = DataOperations.getResponseDescription(new URL(urlString).openConnection());
            defaultTableModel.setValueAt(responseDescription, i, 4);
        }
    }

}