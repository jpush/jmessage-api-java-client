package cn.jmessage.api.message;

import cn.jmessage.api.BaseTest;
import cn.jmessage.api.common.model.MessageBody;
import cn.jmessage.api.common.model.MessagePayload;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.ResponseWrapper;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * MessageClientTest
 * Created by tangyikai on 15/9/8.
 */
public class MessageClientTest extends BaseTest {

    private static Logger LOG = LoggerFactory.getLogger(MessageClientTest.class);
    private MessageClient messageClient = null;

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
                .setFromId("junit_admin")
                .setMessageType("text")
                .setMessageBody(messageBody)
                .build();

        JsonObject messObj = new JsonObject();
        messObj.addProperty("version", 1);
        messObj.addProperty("target_type", "single");
        messObj.addProperty("target_id", JUNIT_USER);
        messObj.addProperty("from_type", "admin");
        messObj.addProperty("from_id", "junit_admin");
        messObj.addProperty("msg_type", "text");
        messObj.add("msg_body", messageBody.toJSON());

        assertEquals(messObj, payload.toJSON());

        try {
            ResponseWrapper res = messageClient.sendMessage(payload);
            assertEquals(201, res.responseCode);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            assertTrue(false);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
            assertTrue(false);
        }

    }
}
