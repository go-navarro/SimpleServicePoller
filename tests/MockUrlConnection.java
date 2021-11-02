package SimpleServicePollerTests;

import java.net.HttpURLConnection;
import java.net.URL;

public class MockUrlConnection extends HttpURLConnection {
    protected URL url;
    protected int responseCode;

    protected MockUrlConnection(URL url, int responseCode) {
        super(url);
        this.url = url;
        this.responseCode = responseCode;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() {

    }

    public int getResponseCode() {
        return this.responseCode;
    }

}