package cn.jmessage.api.chatroom;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.BaseTest;
import cn.jmessage.api.SlowTests;
import cn.jmessage.api.common.model.Members;
import cn.jmessage.api.common.model.chatroom.ChatRoomPayload;
import cn.jmessage.api.user.UserListResult;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Category(SlowTests.class)
public class ChatRoomClientTest extends BaseTest {

    private Logger LOG = LoggerFactory.getLogger(ChatRoomClientTest.class);

    private ChatRoomClient mClient = new ChatRoomClient(APP_KEY, MASTER_SECRET);

    @Test
    public void testCreateChatRoom() {
        try {
            ChatRoomPayload payload = ChatRoomPayload.newBuilder()
                    .setName("haha")
                    .setDesc("test")
                    .setOwnerUsername(JUNIT_ADMIN)
                    .setMembers(Members.newBuilder().addMember(JUNIT_USER1, JUNIT_USER2).build())
                    .build();
            CreateChatRoomResult result = mClient.createChatRoom(payload);
            LOG.info("Got result, room id:" + result.getChatroom_id());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testGetBatchChatRoomInfo() {
        try {
            ChatRoomListResult result = mClient.getBatchChatRoomInfo(12500011, 10000072);
            ChatRoomResult[] rooms = result.getRooms();
            if (rooms.length > 0) {
                for (ChatRoomResult room: rooms) {
                    LOG.info("Got result " + room.toString());
                }
            }
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testGetUserChatRoomInfo() {
        try {
            ChatRoomListResult result = mClient.getUserChatRoomInfo(JUNIT_ADMIN);
            ChatRoomResult[] rooms = result.getRooms();
            if (rooms.length > 0) {
                for (ChatRoomResult room: rooms) {
                    LOG.info("Got result " + room.toString());
                }
            }
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testGetAppChatRoomInfo() {
        try {
            ChatRoomListResult result = mClient.getAppChatRoomInfo(0, 2);
            LOG.info("Got result " + result.getList().toString());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateChatRoomInfo() {
        try {
            ResponseWrapper result = mClient.updateChatRoomInfo(10000072, JUNIT_USER1, "new Test", "666");
            LOG.info("Got result " + result.toString());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteChatRoom() {
        try {
            ResponseWrapper result = mClient.deleteChatRoom(12500013);
            LOG.info("Got result " + result.toString());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateUserSpeakStatus() {
        try {
            ResponseWrapper result = mClient.updateUserSpeakStatus(12500011, JUNIT_USER1, 1);
            LOG.info("Got result " + result.toString());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testGetChatRoomMembers() {
        try {
            ChatRoomMemberList result = mClient.getChatRoomMembers(12500011, 0, 3);
            ChatRoomMemberList.ChatRoomMember[] members = result.getMembers();
            if (members.length > 0) {
                for (ChatRoomMemberList.ChatRoomMember member: members) {
                    LOG.info("Member: " + member.username);
                }
            }
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testAddChatRoomMember() {
        try {
            ResponseWrapper result = mClient.addChatRoomMember(12500011, JUNIT_USER);
            LOG.info("Got result: " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteChatRoomMember() {
        try {
            ResponseWrapper result = mClient.removeChatRoomMembers(12500011, JUNIT_USER);
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
