package cn.jmessage.api.examples;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.sensitiveword.SensitiveWordClient;
import cn.jmessage.api.sensitiveword.SensitiveWordListResult;
import cn.jmessage.api.sensitiveword.SensitiveWordStatusResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SensitiveWordExample {

    protected static final Logger LOG = LoggerFactory.getLogger(SensitiveWordExample.class);

    private static final String appkey = "242780bfdd7315dc1989fe2b";
    private static final String masterSecret = "2f5ced2bef64167950e63d13";
    private static SensitiveWordClient client = new SensitiveWordClient(appkey, masterSecret);

    public static void main(String[] args) {
        testGetSensitiveWordStatus();
    }

    public static void testAddSensitiveWord() {
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

    public static void testUpdateSensitiveWord() {
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

    public static void testDeleteSensitiveWord() {
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

    public static void testGetSensitiveWordList() {
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

    public static void testUpdateSensitiveWordStatus() {
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

    public static void testGetSensitiveWordStatus() {
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
