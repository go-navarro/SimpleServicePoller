package SimpleServicePollerTests;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MockUrlConnection extends HttpURLConnection {
    protected URL url;
    protected int responseCode;
    protected boolean connected;

    protected MockUrlConnection(URL url, int responseCode, boolean connected) {
        super(url);
        this.url = url;
        this.responseCode = responseCode;
        this.connected = connected;
    }

    protected MockUrlConnection(URL url, boolean connected) {
        super(url);
        this.url = url;
        this.connected = connected;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {
        if(! connected){
            throw new IOException();
        }
    }

    public int getResponseCode() throws IOException{
        if(!connected) {
            throw new IOException();
        }
        return this.responseCode;
    }

}