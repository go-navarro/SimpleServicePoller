package SimpleServicePoller;

import java.io.File;

public class ServicePollerMain {
    static String savePath = "serviceData.txt";

    public static void readSaveFile(String savePath) throws Exception {
        File file = new File(savePath);
        if(! file.exists()) {
            file.createNewFile();
        }
    }

    public static void main(String[] args) throws Exception {
        readSaveFile(savePath);
        ServicePollerFrame servicePollerFrame = new ServicePollerFrame(savePath);
    }

}