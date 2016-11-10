package cn.jmessage.api.examples;

import java.util.ArrayList;
import java.util.List;

import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.common.model.FriendNote;
import cn.jmessage.api.common.model.NoDisturbPayload;
import cn.jmessage.api.user.UserStateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.user.UserInfoResult;
import cn.jmessage.api.user.UserListResult;

public class UserExample {

    protected static final Logger LOG = LoggerFactory.getLogger(UserExample.class);

    private static final String appkey = "242780bfdd7315dc1989fe2b";
    private static final String masterSecret = "2f5ced2bef64167950e63d13";
    private static final long test_gid = 10004809;
    
    public static void main(String[] args) {
//        testRegisterUsers();
    }



    public static void testRegisterUsers() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {

            List<RegisterInfo> users = new ArrayList<RegisterInfo>();

            RegisterInfo user = RegisterInfo.newBuilder()
                    .setUsername("test_user")
                    .setPassword("test_pass")
                    .build();

            RegisterInfo user1 = RegisterInfo.newBuilder()
                    .setUsername("test_user1")
                    .setPassword("test_pass1")
                    .build();

            users.add(user);
            users.add(user1);

            RegisterInfo[] regUsers = new RegisterInfo[users.size()];

            String res = client.registerUsers(users.toArray(regUsers));
            LOG.info(res);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testGetUserInfo() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            UserInfoResult res = client.getUserInfo("test_user");
            LOG.info(res.getUsername());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testGetUserState() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            UserStateResult result = client.getUserState("test_user");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testUpdatePassword() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            client.updateUserPassword("test_user", "test_new_pass");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testUpdateUserInfo() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            client.updateUserInfo("test_user", "test_nick", "2000-01-12", "help me!", 1, "shenzhen", "nanshan");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testGetUsers() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            UserListResult res = client.getUserList(0, 30);
            LOG.info(res.getOriginalContent());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testDeleteUser() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            client.deleteUser("test_user_119");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
    
    /**
     * Get admins by appkey
     */
    public void testGetAdminListByAppkey() {
    	JMessageClient client = new JMessageClient(appkey, masterSecret);
    	try {
			UserListResult res = client.getAdminListByAppkey(0, 1);
			LOG.info(res.getOriginalContent());
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
		}
    }

    public void testGetBlackList() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            UserListResult result = client.getBlackList("username");
            UserInfoResult[] users = result.getUsers();
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public void testAddBlackList() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            ResponseWrapper response = client.addBlackList("username", "user1", "user2");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public void testSetNoDisturb() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            NoDisturbPayload payload = new NoDisturbPayload.Builder()
                    .setAddSingleUsers("test_user1", "test_user2")
                    .setAddGroupIds(test_gid)
                    .build();
            ResponseWrapper response = client.setNoDisturb("test_user", payload);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public void testAddFriends() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            ResponseWrapper response = client.addFriends("test_user", "test_user1", "test_user2");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public void testDeleteFriends() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            ResponseWrapper response = client.deleteFriends("test_user", "test_user1", "test_user2");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public void testUpdateFriendsNote() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            List<FriendNote> friendNotes = new ArrayList<FriendNote>();
            FriendNote friendNote1 = new FriendNote.Builder()
                    .setNoteName("note name 1")
                    .setOthers("test")
                    .setUsername("test_user1")
                    .builder();
            FriendNote friendNote2 = new FriendNote.Builder()
                    .setNoteName("note name 2")
                    .setOthers("test")
                    .setUsername("test_user2")
                    .builder();
            friendNotes.add(friendNote1);
            friendNotes.add(friendNote2);
            FriendNote[] array = new FriendNote[friendNotes.size()];
            ResponseWrapper result = client.updateFriendsNote("test_user", friendNotes.toArray(array));
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

}

