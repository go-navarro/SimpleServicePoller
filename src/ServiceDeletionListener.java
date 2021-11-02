package SimpleServicePoller;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;

public class ServiceDeletionListener implements ActionListener {
    String savePath;
    DefaultTableModel defaultTableModel;

    public ServiceDeletionListener(DataOperations dataOperations) {
        this.defaultTableModel = dataOperations.defaultTableModel;
        this.savePath = dataOperations.savePath;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        defaultTableModel.setRowCount(0);

        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(savePath);
            printWriter.print("");
            printWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}