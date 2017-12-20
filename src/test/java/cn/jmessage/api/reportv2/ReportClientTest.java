package cn.jmessage.api.reportv2;

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
public class ReportClientTest extends BaseTest {

    private static Logger LOG = LoggerFactory.getLogger(ReportClientTest.class);
    private ReportClient reportClient = null;

    @Before
    public void before() throws Exception {
        reportClient = new ReportClient(APP_KEY, MASTER_SECRET);
    }

    @Test
    public void testGetUserStat() {
        try {
            UserStatListResult result = reportClient.getUserStatistic("2016-11-08", 3);
            LOG.info("Got result: " + result);
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
    public void testGetMessageStat() {
        try {
            MessageStatListResult result = reportClient.getMessageStatistic("DAY", "2016-11-08", 3);
            LOG.info("Got result: " + result);
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
    public void testGetGroupStat() {
        try {
            GroupStatListResult result = reportClient.getGroupStatistic("2016-11-08", 3);
            LOG.info("Got result: " + result);
            assertTrue(result.isResultOK());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
}
