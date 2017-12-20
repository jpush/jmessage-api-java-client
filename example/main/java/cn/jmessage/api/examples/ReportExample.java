package cn.jmessage.api.examples;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.reportv2.GroupStatListResult;
import cn.jmessage.api.reportv2.MessageStatListResult;
import cn.jmessage.api.reportv2.ReportClient;
import cn.jmessage.api.reportv2.UserStatListResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportExample {

    private static Logger LOG = LoggerFactory.getLogger(ReportExample.class);
    private static final String appkey = "242780bfdd7315dc1989fe2b";
    private static final String masterSecret = "2f5ced2bef64167950e63d13";
    private ReportClient mClient = new ReportClient(appkey, masterSecret);

    public static void main(String[] args) {

    }

    public void testGetUserStat() {
        try {
            UserStatListResult result = mClient.getUserStatistic("2016-11-08", 3);
            LOG.info("Got result: " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public void testGetMessageStat() {
        try {
            MessageStatListResult result = mClient.getMessageStatistic("DAY", "2016-11-08", 3);
            LOG.info("Got result: " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public void testGetGroupStat() {
        try {
            GroupStatListResult result = mClient.getGroupStatistic("2016-11-08", 3);
            LOG.info("Got result: " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

}
