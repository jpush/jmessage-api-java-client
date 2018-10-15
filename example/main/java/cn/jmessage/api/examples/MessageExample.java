package cn.jmessage.api.examples;

import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.common.model.message.MessagePayload;
import cn.jmessage.api.message.MessageListResult;
import cn.jmessage.api.message.MessageResult;
import cn.jmessage.api.message.MessageType;
import cn.jmessage.api.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.message.MessageBody;
import cn.jmessage.api.message.SendMessageResult;

public class MessageExample {

    protected static final Logger LOG = LoggerFactory.getLogger(MessageExample.class);

    private static final String appkey = "7b4b94cca0d185d611e53cca";
    private static final String masterSecret = "860803cf613ed54aa3b941a8";

    public static void main(String[] args) {
        testGetMessageList();
    }

    /**
     * Send single text message by admin, this method will invoke sendMessage() in JMessageClient eventually, whose 
     * parameters are as list:
     */
    public static void testSendSingleTextByAdmin() {
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
    
    /**
     * Send group text message by admin
     */
    public static void testSendGroupTextByAdmin() {
    	JMessageClient client = new JMessageClient(appkey, masterSecret);
    	
    	try {
    		MessageBody body = MessageBody.text("Hello World!");
    		SendMessageResult result = client.sendGroupTextByAdmin("targetUserName", "fromUserName", body);
    		LOG.info(String.valueOf(result.getMsg_id()));
    	} catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testSendImageMessage() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        MessageBody messageBody = new MessageBody.Builder()
                .setMediaId("qiniu/image/r/A92D550D57464CDF5ADC0D79FBD46210")
                .setMediaCrc32(4258069839L)
                .setWidth(43)
                .setHeight(44)
                .setFormat("png")
                .setFsize(2670)
                .build();

        MessagePayload payload = MessagePayload.newBuilder()
                .setVersion(1)
                .setTargetType("single")
                .setTargetId("test_user1")
                .setFromType("admin")
                .setFromId("junit_admin")
                .setMessageType(MessageType.IMAGE)
                .setMessageBody(messageBody)
                .build();

        try {
            SendMessageResult res = client.sendMessage(payload);
            System.out.println(res.getMsg_id());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }

    }

    /**
     * Get message list without cursor(first time), will return cursor, the later request will
     * use cursor to get messages.
     */
    public static void testGetMessageList() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            MessageListResult result = client.getMessageList(10, "2018-06-08 10:10:10", "2018-06-15 10:10:10");
            String cursor = result.getCursor();
            if (null != cursor && StringUtils.isNotEmpty(cursor)) {
                MessageResult[] messages = result.getMessages();
                MessageListResult secondResult = client.getMessageListByCursor(cursor);
                MessageResult[] secondMessages = secondResult.getMessages();
            }
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testGetUserMessageList() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            MessageListResult result = client.getUserMessages("username", 10, "2016-09-08 10:10:10", "2016-09-15 10:10:10");
            String cursor = result.getCursor();
            MessageListResult secondResult = client.getUserMessagesByCursor("username", cursor);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testRetractMessage() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            ResponseWrapper result = client.retractMessage("username", 12345);
            LOG.info(result.toString());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
}
