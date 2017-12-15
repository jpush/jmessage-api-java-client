package cn.jmessage.api.examples;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.chatroom.*;
import cn.jmessage.api.common.model.Members;
import cn.jmessage.api.common.model.chatroom.ChatRoomPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatRoomExample {

    private static Logger LOG = LoggerFactory.getLogger(ChatRoomExample.class);
    private static final String appkey = "242780bfdd7315dc1989fe2b";
    private static final String masterSecret = "2f5ced2bef64167950e63d13";
    private ChatRoomClient mClient = new ChatRoomClient(appkey, masterSecret);

    public static void main(String[] args) {
        
    }

    public void testCreateChatRoom() {
        try {
            ChatRoomPayload payload = ChatRoomPayload.newBuilder()
                    .setName("haha")
                    .setDesc("test")
                    .setOwnerUsername("junit_admin")
                    .setMembers(Members.newBuilder().addMember("junit_user1", "junit_user2").build())
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

    public void testGetUserChatRoomInfo() {
        try {
            ChatRoomListResult result = mClient.getUserChatRoomInfo("junit_admin");
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

    public void testUpdateChatRoomInfo() {
        try {
            ResponseWrapper result = mClient.updateChatRoomInfo(10000072, "junit_user1", "new Test", "666");
            LOG.info("Got result " + result.toString());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

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

    public void testUpdateUserSpeakStatus() {
        try {
            ResponseWrapper result = mClient.updateUserSpeakStatus(12500011, "junit_user1", 1);
            LOG.info("Got result " + result.toString());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public void testGetChatRoomMembers() {
        try {
            ChatRoomMemberList result = mClient.getChatRoomMembers(12500011, 0, 3);
            ChatRoomMemberList.ChatRoomMember[] members = result.getMembers();
            if (members.length > 0) {
                for (ChatRoomMemberList.ChatRoomMember member: members) {
                    LOG.info("Member: " + member.getUsername());
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

    public void testAddChatRoomMember() {
        try {
            ResponseWrapper result = mClient.addChatRoomMember(12500011, "junit_user");
            LOG.info("Got result: " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public void testDeleteChatRoomMember() {
        try {
            ResponseWrapper result = mClient.removeChatRoomMembers(12500011, "junit_user");
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
