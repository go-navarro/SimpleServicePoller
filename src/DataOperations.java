package SimpleServicePoller;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

public class DataOperations {
    DefaultTableModel defaultTableModel;
    String savePath;

    public DataOperations(String savePath) throws IOException {
        this.defaultTableModel = initializeTable(savePath);
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
            savedDataArray[i] = split;
        }

        return new DefaultTableModel(savedDataArray, colNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public static String getResponseDescription(HttpURLConnection httpURLConnection) throws IOException {
        int httpStatus = httpURLConnection.getResponseCode();
        httpURLConnection.getResponseMessage();

        String httpCondition;
        if((httpStatus == 200)) {
            httpCondition = "OK";
        }else {
            httpCondition = "FAIL";
        }
        return httpCondition;
    }

}