package cn.jmessage.api.examples;

import java.util.ArrayList;
import java.util.List;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.ApacheHttpClient;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.common.JMessageConfig;
import cn.jmessage.api.common.model.friend.FriendNote;
import cn.jmessage.api.common.model.NoDisturbPayload;
import cn.jmessage.api.user.UserStateListResult;
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

    private static final String appkey = "7b4b94cca0d185d611e53cca";
    private static final String masterSecret = "860803cf613ed54aa3b941a8";
    private static final long test_gid = 10004809;
    
    public static void main(String[] args) {
        testRegisterUsers();
//        testGetUserInfo();
//        testUpdateUserInfo();
    }



    public static void testRegisterUsers() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        String authCode = ServiceHelper.getBasicAuthorization(appkey, masterSecret);
//        ApacheHttpClient httpClient = new ApacheHttpClient(authCode, null, ClientConfig.getInstance());
        // 用 ApacheHttpClient 代替默认的 NativeHttpClient
//        client.setHttpClient(httpClient);
        try {

            List<RegisterInfo> users = new ArrayList<RegisterInfo>();

            RegisterInfo user = RegisterInfo.newBuilder()
                    .setUsername("test_user1")
                    .setPassword("test_pass1")
                    .build();

            RegisterInfo user1 = RegisterInfo.newBuilder()
                    .setUsername("test_user2")
                    .setPassword("test_pass2")
                    .addExtra("test", "ttt")
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

    public static void testGetUsersState() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            UserStateListResult[] results = client.getUsersState("user1", "user2", "user3");
            for (UserStateListResult result : results) {
                LOG.info(result.toString());
            }
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
            client.updateUserInfo("test_user", "test_nick", "2000-01-12", "help me!", 1, "shenzhen", "nanshan", "media id");
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
    public static void testGetAdminListByAppkey() {
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

    public static void testGetBlackList() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            UserInfoResult[] result = client.getBlackList("username");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testRemoveBlacklist() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            ResponseWrapper response = client.removeBlacklist("test_user", "test_user1", "test_user2");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testAddBlackList() {
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

    public static void testSetNoDisturb() {
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

    public static void testAddFriends() {
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

    public static void testDeleteFriends() {
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

    public static void testUpdateFriendsNote() {
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

    public static void testGetFriends() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            UserInfoResult[] userInfoArray = client.getFriendsInfo("test_user");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testForbidUser() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            ResponseWrapper result = client.forbidUser("user1", true);
            LOG.info("response code: " + result.responseCode);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

}

