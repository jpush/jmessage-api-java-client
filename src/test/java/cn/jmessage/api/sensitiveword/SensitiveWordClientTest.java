package cn.jmessage.api.sensitiveword;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.BaseTest;
import cn.jmessage.api.SlowTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Category(SlowTests.class)
public class SensitiveWordClientTest extends BaseTest {
    private static Logger LOG = LoggerFactory.getLogger(SensitiveWordClient.class);
    private SensitiveWordClient client = null;

    @Before
    public void before() throws Exception {
        client = new SensitiveWordClient(APP_KEY, MASTER_SECRET);
    }

    @Test
    public void testAddSensitiveWord() {
        try {
            ResponseWrapper result = client.addSensitiveWords("fuck", "FUCK");
            LOG.info("response code: " + result.responseCode + " content " + result.responseContent);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateSensitiveWord() {
        try {
            ResponseWrapper result = client.updateSensitiveWord("f**k", "fuck");
            LOG.info("response code: " + result.responseCode + " content " + result.responseContent);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteSensitiveWord() {
        try {
            ResponseWrapper result = client.deleteSensitiveWord("f**k");
            LOG.info("response code: " + result.responseCode + " content " + result.responseContent);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testGetSensitiveWordList() {
        try {
            SensitiveWordListResult result = client.getSensitiveWordList(0, 10);
            LOG.info(result.toString());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateSensitiveWordStatus() {
        try {
            ResponseWrapper result = client.updateSensitiveWordStatus(0);
            LOG.info("response code: " + result.responseCode + " content " + result.responseContent);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testGetSensitiveWordStatus() {
        try {
            SensitiveWordStatusResult result = client.getSensitiveWordStatus();
            LOG.info(result.toString());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
}
