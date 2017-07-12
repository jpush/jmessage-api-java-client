package cn.jmessage.api.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.JMessageClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.BaseTest;
import cn.jmessage.api.SlowTests;
import cn.jmessage.api.common.model.message.MessageBody;
import cn.jmessage.api.common.model.message.MessagePayload;


/**
 * MessageClientTest
 * Created by tangyikai on 15/9/8.
 */
@Category(SlowTests.class)
public class MessageClientTest extends BaseTest {

    private static Logger LOG = LoggerFactory.getLogger(MessageClientTest.class);
    private MessageClient messageClient = null;
    private static String cursor;

    @Before
    public void before() throws Exception {
        messageClient = new MessageClient(APP_KEY, MASTER_SECRET);
    }

    @Test
    public void testSendMessage() {

        MessageBody messageBody = MessageBody.newBuilder()
                .setText("Test api send Message")
                .build();

        JsonObject bodyObj = new JsonObject();
        bodyObj.addProperty("text", "Test api send Message");

        assertEquals(bodyObj, messageBody.toJSON());


        MessagePayload payload = MessagePayload.newBuilder()
                .setVersion(1)
                .setTargetType("single")
                .setTargetId(JUNIT_USER)
                .setFromType("admin")
                .setFromId(JUNIT_ADMIN)
                .setMessageType(MessageType.TEXT)
                .setMessageBody(messageBody)
                .build();

        JsonObject messObj = new JsonObject();
        messObj.addProperty("version", 1);
        messObj.addProperty("target_type", "single");
        messObj.addProperty("target_id", JUNIT_USER);
        messObj.addProperty("from_type", "admin");
        messObj.addProperty("from_id", JUNIT_ADMIN);
        messObj.addProperty("msg_type", "text");
        messObj.add("msg_body", messageBody.toJSON());

        assertEquals(messObj, payload.toJSON());

        try {
            SendMessageResult res = messageClient.sendMessage(payload);
            assertTrue(res.isResultOK());
            System.out.println(res.getMsg_id());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }

    }

    @Test
    public void testSendImageMessage() {
        MessageBody messageBody = new MessageBody.Builder()
                .setMediaId("qiniu/image/r/A92D550D57464CDF5ADC0D79FBD46210")
                .setMediaCrc32(4258069839L)
                .setWidth(43)
                .setHeight(44)
                .setFormat("png")
                .setFsize(2670)
                .build();

        JsonObject bodyObj = new JsonObject();
        bodyObj.addProperty("media_id", "qiniu/image/r/A92D550D57464CDF5ADC0D79FBD46210");
        bodyObj.addProperty("media_crc32", 4258069839L);
        bodyObj.addProperty("width", 43);
        bodyObj.addProperty("height", 44);
        bodyObj.addProperty("format", "png");
        bodyObj.addProperty("fsize", 2670);

        assertEquals(bodyObj, messageBody.toJSON());

        MessagePayload payload = MessagePayload.newBuilder()
                .setVersion(1)
                .setTargetType("single")
                .setTargetId(JUNIT_USER)
                .setFromType("admin")
                .setFromId("junit_admin")
                .setMessageType(MessageType.IMAGE)
                .setMessageBody(messageBody)
                .build();

        JsonObject messObj = new JsonObject();
        messObj.addProperty("version", 1);
        messObj.addProperty("target_type", "single");
        messObj.addProperty("target_id", JUNIT_USER);
        messObj.addProperty("from_type", "admin");
        messObj.addProperty("from_id", "junit_admin");
        messObj.addProperty("msg_type", "image");
        messObj.add("msg_body", messageBody.toJSON());

        assertEquals(messObj, payload.toJSON());

        try {
            SendMessageResult res = messageClient.sendMessage(payload);
            assertTrue(res.isResultOK());
            System.out.println(res.getMsg_id());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }

    }

    @Test
    public void testSendCustomMessage() {
        MessageBody messageBody = new MessageBody.Builder()
                .setText("Test api send Message")
                .addExtra("test", "jpush")
                .build();

        JsonObject bodyObj = new JsonObject();
        bodyObj.addProperty("text", "Test api send Message");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("test", "jpush");
        bodyObj.add("extras", jsonObject);

        assertEquals(bodyObj, messageBody.toJSON());


        MessagePayload payload = MessagePayload.newBuilder()
                .setVersion(1)
                .setTargetType("single")
                .setTargetId(JUNIT_USER)
                .setFromType("admin")
                .setFromId("junit_admin")
                .setMessageType(MessageType.CUSTOM)
                .setMessageBody(messageBody)
                .build();

        JsonObject messObj = new JsonObject();
        messObj.addProperty("version", 1);
        messObj.addProperty("target_type", "single");
        messObj.addProperty("target_id", JUNIT_USER);
        messObj.addProperty("from_type", "admin");
        messObj.addProperty("from_id", "junit_admin");
        messObj.addProperty("msg_type", "custom");
        messObj.add("msg_body", messageBody.toJSON());

        assertEquals(messObj, payload.toJSON());

        try {
            SendMessageResult res = messageClient.sendMessage(payload);
            assertTrue(res.isResultOK());
            System.out.println(res.getMsg_id());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSendMessage_MessageBodyNull() {
    	MessagePayload payload = MessagePayload.newBuilder()
                .setVersion(1)
                .setTargetType("single")
                .setTargetId(JUNIT_USER)
                .setFromType("admin")
                .setFromId("junit_admin")
                .setMessageType(MessageType.TEXT)
                .build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSendMessage_VersionNull() {
    	MessageBody messageBody = MessageBody.newBuilder()
                .setText("Test api send Message")
                .build();

        JsonObject bodyObj = new JsonObject();
        bodyObj.addProperty("text", "Test api send Message");

        assertEquals(bodyObj, messageBody.toJSON());
        
        MessagePayload payload = MessagePayload.newBuilder()
                .setTargetType("single")
                .setTargetId(JUNIT_USER)
                .setFromType("admin")
                .setFromId("junit_admin")
                .setMessageType(MessageType.TEXT)
                .build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSendMessage_TargetTypeNull() {
    	MessageBody messageBody = MessageBody.newBuilder()
                .setText("Test api send Message")
                .build();

        JsonObject bodyObj = new JsonObject();
        bodyObj.addProperty("text", "Test api send Message");

        assertEquals(bodyObj, messageBody.toJSON());
        
        MessagePayload payload = MessagePayload.newBuilder()
        		.setVersion(1)
                .setTargetId(JUNIT_USER)
                .setFromType("admin")
                .setFromId("junit_admin")
                .setMessageType(MessageType.TEXT)
                .build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSendMessage_TargetIdNull() {
    	MessageBody messageBody = MessageBody.newBuilder()
                .setText("Test api send Message")
                .build();

        JsonObject bodyObj = new JsonObject();
        bodyObj.addProperty("text", "Test api send Message");

        assertEquals(bodyObj, messageBody.toJSON());

        MessagePayload payload = MessagePayload.newBuilder()
                .setVersion(1)
                .setTargetType("single")
                .setFromType("admin")
                .setFromId("junit_admin")
                .setMessageType(MessageType.TEXT)
                .setMessageBody(messageBody)
                .build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSendMessage_TargetIdInvalid(){
    	MessageBody messageBody = MessageBody.newBuilder()
                .setText("Test api send Message")
                .build();

        JsonObject bodyObj = new JsonObject();
        bodyObj.addProperty("text", "Test api send Message");

        assertEquals(bodyObj, messageBody.toJSON());

        MessagePayload payload = MessagePayload.newBuilder()
                .setVersion(1)
                .setTargetType("single")
                .setTargetId("junit \n test id")
                .setFromType("admin")
                .setFromId("junit_admin")
                .setMessageType(MessageType.TEXT)
                .setMessageBody(messageBody)
                .build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSendMessage_FromTypeNull() {
    	MessageBody messageBody = MessageBody.newBuilder()
                .setText("Test api send Message")
                .build();

        JsonObject bodyObj = new JsonObject();
        bodyObj.addProperty("text", "Test api send Message");

        assertEquals(bodyObj, messageBody.toJSON());

        MessagePayload payload = MessagePayload.newBuilder()
                .setVersion(1)
                .setTargetType("single")
                .setTargetId(JUNIT_USER)
                .setFromId("junit_admin")
                .setMessageType(MessageType.TEXT)
                .setMessageBody(messageBody)
                .build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSendMessage_FromIdNull() {
    	MessageBody messageBody = MessageBody.newBuilder()
                .setText("Test api send Message")
                .build();

        JsonObject bodyObj = new JsonObject();
        bodyObj.addProperty("text", "Test api send Message");

        assertEquals(bodyObj, messageBody.toJSON());

        MessagePayload payload = MessagePayload.newBuilder()
                .setVersion(1)
                .setTargetType("single")
                .setTargetId(JUNIT_USER)
                .setFromType("admin")
                .setMessageType(MessageType.TEXT)
                .setMessageBody(messageBody)
                .build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSendMessage_FromIdInvalid(){
    	MessageBody messageBody = MessageBody.newBuilder()
                .setText("Test api send Message")
                .build();

        JsonObject bodyObj = new JsonObject();
        bodyObj.addProperty("text", "Test api send Message");

        assertEquals(bodyObj, messageBody.toJSON());

        MessagePayload payload = MessagePayload.newBuilder()
                .setVersion(1)
                .setTargetType("single")
                .setTargetId(JUNIT_USER)
                .setFromType("admin")
                .setFromId("junit \n admin")
                .setMessageType(MessageType.TEXT)
                .setMessageBody(messageBody)
                .build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSendMessage_MessageTypeNull() {
    	MessageBody messageBody = MessageBody.newBuilder()
                .setText("Test api send Message")
                .build();

        JsonObject bodyObj = new JsonObject();
        bodyObj.addProperty("text", "Test api send Message");

        assertEquals(bodyObj, messageBody.toJSON());

        MessagePayload payload = MessagePayload.newBuilder()
                .setVersion(1)
                .setTargetType("single")
                .setTargetId(JUNIT_USER)
                .setFromType("admin")
                .setFromId("junit_admin")
                .setMessageBody(messageBody)
                .build();
    }

    @Test
    public void testGetMessageList() {
        try {
            MessageListResult result = messageClient.getMessageList(10, "2016-09-08 10:10:10", "2016-09-15 10:10:10");
            assertTrue(result.isResultOK());
            String cursor = result.getCursor();
            LOG.info("Cursor: " + cursor);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testGetMessageListByCursor() {
        try {
            MessageListResult result = messageClient.getMessageListByCursor("1B9FFFC066E6D2305BA6A746D4113667");
            assertTrue(result.isResultOK());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMessageList_invalidCount() {
        try {
            MessageListResult result = messageClient.getMessageList(1001, null, null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testGetUserMessageList() {
        try {
            MessageListResult result = messageClient.getUserMessages(JUNIT_USER, 5, "2016-11-08 10:10:10", "2016-11-15 10:10:10");
            cursor = result.getCursor();
            LOG.info("Cursor: " + cursor);
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
    public void testGetUserMessagesByCursor() {
        try {
            MessageListResult result = messageClient.getUserMessagesByCursor(JUNIT_USER, "14352B2CE97B02D9");
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
    public void testRetractMessage() {
        JMessageClient client = new JMessageClient(APP_KEY, MASTER_SECRET);
        try {
            ResponseWrapper result = client.retractMessage(JUNIT_ADMIN, 364561741);
            LOG.info(result.responseContent);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
}
