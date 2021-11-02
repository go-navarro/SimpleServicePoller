package SimpleServicePollerTests;

import SimpleServicePoller.DataOperations;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;

public class DataOperationsTest {

    @Test
    public void testFailedRequest() throws Exception {
        URL url = new URL("https://someWebSite");
        MockUrlConnection mockUrlConnection = new MockUrlConnection(url, 400);
        String responseCondition = DataOperations.getResponseDescription(mockUrlConnection);

        Assert.assertEquals("FAIL", responseCondition);
    }

    @Test
    public void testSuccessfulRequest() throws Exception {
        URL url = new URL("https://someWebSite");
        MockUrlConnection mockUrlConnection = new MockUrlConnection(url, 200);
        String responseCondition = DataOperations.getResponseDescription(mockUrlConnection);

        Assert.assertEquals("OK", responseCondition);
    }

}