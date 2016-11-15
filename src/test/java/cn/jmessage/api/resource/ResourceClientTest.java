package cn.jmessage.api.resource;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.BaseTest;
import cn.jmessage.api.SlowTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

@Category(SlowTests.class)
public class ResourceClientTest extends BaseTest {

    private static Logger LOG = LoggerFactory.getLogger(ResourceClientTest.class);
    private ResourceClient resourceClient = null;

    @Before
    public void before() throws Exception {
        resourceClient = new ResourceClient(APP_KEY, MASTER_SECRET);
    }

    @Test
    public void testDownloadFile() {
        try {
            DownloadResult result = resourceClient.downloadFile("qiniu/image/B8C68B55D34B3BC9");
            String url = result.getUrl();
            LOG.info("Url: " + url);
            assertTrue(result.isResultOK());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testUploadFile() {
        try {
            UploadResult result = resourceClient.uploadFile("/users/caiyg/Desktop/discourse.png");
            if (null != result) {
                assertTrue(result.isResultOK());
                LOG.info("media_id: " + result.getMediaId());
            }
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
}
