package cn.jmessage.api.examples;

import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.MessageBody;
import cn.jmessage.api.message.SendMessageResult;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageExample {

    protected static final Logger LOG = LoggerFactory.getLogger(MessageExample.class);

    private static final String appkey = "242780bfdd7315dc1989fe2b";
    private static final String masterSecret = "2f5ced2bef64167950e63d13";

    public static void testGetUserInfo() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            MessageBody body = MessageBody.text("Help me!");
            SendMessageResult result = client.sendSingleTextByAdmin("targetUserName", "fromUserName", body);
            LOG.info(String.valueOf(result.getMsg_id()));
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
}
