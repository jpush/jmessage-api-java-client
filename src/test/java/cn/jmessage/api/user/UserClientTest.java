package cn.jmessage.api.user;


import cn.jmessage.api.BaseTest;
import cn.jmessage.api.SlowTests;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.common.model.RegisterPayload;
import cn.jmessage.api.common.model.UserPayload;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.ResponseWrapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(SlowTests.class)
public class UserClientTest extends BaseTest {


    private static Logger LOG = LoggerFactory.getLogger(UserClientTest.class);
    private UserClient userClient = null;

    @Before
    public void before() throws Exception {
        userClient = new UserClient(APP_KEY, MASTER_SECRET);
    }

    /**
     * Method: registerUsers(RegisterPayload payload)
     */
    @Test
    public void testRegisterUsers() {
        try {
            List<RegisterInfo> users = new ArrayList<RegisterInfo>();
            RegisterInfo user = RegisterInfo.newBuilder()
                    .setUsername("junit_test_user")
                    .setPassword("junit_test_pass")
                    .build();

            JsonObject obj = new JsonObject();
            obj.addProperty("username", "junit_test_user");
            obj.addProperty("password", "junit_test_pass");

            Assert.assertEquals("", obj, user.toJSON());

            users.add(user);
            RegisterInfo[] regUsers = new RegisterInfo[users.size()];

            RegisterPayload payload = RegisterPayload.newBuilder()
                    .addUsers(users.toArray(regUsers)).build();

            JsonArray arr = new JsonArray();
            arr.add(obj);

            Assert.assertEquals("", arr, payload.toJSON());

            ResponseWrapper res = userClient.registerUsers(payload);
            assertEquals(201, res.responseCode);

            ResponseWrapper res1 = userClient.deleteUser("junit_test_user");
            assertEquals(204, res1.responseCode);

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
    
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterUsers_UsernameNull() {
		List<RegisterInfo> users = new ArrayList<RegisterInfo>();
		RegisterInfo user = RegisterInfo.newBuilder().setPassword("junit_test_pass").build();

		JsonObject obj = new JsonObject();
		obj.addProperty("password", "junit_test_pass");

		Assert.assertEquals("", obj, user.toJSON());

		users.add(user);
		RegisterInfo[] regUsers = new RegisterInfo[users.size()];
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterUsers_UsernameBlank() {
		List<RegisterInfo> users = new ArrayList<RegisterInfo>();
		RegisterInfo user = RegisterInfo.newBuilder()
				.setUsername(" ")
				.setPassword("junit_test_pass").build();

		JsonObject obj = new JsonObject();
		obj.addProperty("username", " ");
		obj.addProperty("password", "junit_test_pass");
		Assert.assertEquals("", obj, user.toJSON());
		users.add(user);
		RegisterInfo[] regUsers = new RegisterInfo[users.size()];
	}
    
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterUsers_PasswordNull() {
		List<RegisterInfo> users = new ArrayList<RegisterInfo>();
		RegisterInfo user = RegisterInfo.newBuilder().setUsername("junit_test_user").build();

		JsonObject obj = new JsonObject();
		obj.addProperty("username", "junit_test_user");

		Assert.assertEquals("", obj, user.toJSON());

		users.add(user);
		RegisterInfo[] regUsers = new RegisterInfo[users.size()];
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterUesrs_PasswordBlank() {
		List<RegisterInfo> users = new ArrayList<RegisterInfo>();
		RegisterInfo user = RegisterInfo.newBuilder()
				.setUsername("junit_test_user")
				.setPassword(" ")
				.build();

		JsonObject obj = new JsonObject();
		obj.addProperty("username", "junit_test_user");
		obj.addProperty("password", " ");
		Assert.assertEquals("", obj, user.toJSON());
		users.add(user);
		RegisterInfo[] regUsers = new RegisterInfo[users.size()];
	}
	
	/**
	 * username too short, should not less than 4 bytes.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterUsers_UsernameInvalid() {
		List<RegisterInfo> users = new ArrayList<RegisterInfo>();
		RegisterInfo user = RegisterInfo.newBuilder()
				.setUsername("123")
				.setPassword("junit_test_pass")
				.build();

		JsonObject obj = new JsonObject();
		obj.addProperty("username", "123");
		obj.addProperty("password", "junit_test_pass");
		Assert.assertEquals("", obj, user.toJSON());

		users.add(user);
		RegisterInfo[] regUsers = new RegisterInfo[users.size()];
	}
	
	/**
	 * username too long, should less than 128 bytes.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterUsers_UsernameInvalid1() {
		List<RegisterInfo> users = new ArrayList<RegisterInfo>();
		RegisterInfo user = RegisterInfo.newBuilder()
				.setUsername(MORE_THAN_128)
				.setPassword("junit_test_pass")
				.build();

		JsonObject obj = new JsonObject();
		obj.addProperty("username", MORE_THAN_128);
		obj.addProperty("password", "junit_test_pass");
		Assert.assertEquals("", obj, user.toJSON());

		users.add(user);
		RegisterInfo[] regUsers = new RegisterInfo[users.size()];
	}
	
	/**
	 * username format error. Must start with alphabet or number.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterUsers_UsernameInvalid2() {
		List<RegisterInfo> users = new ArrayList<RegisterInfo>();
		RegisterInfo user = RegisterInfo.newBuilder()
				.setUsername("@test")
				.setPassword("junit_test_pass")
				.build();

		JsonObject obj = new JsonObject();
		obj.addProperty("username", "@test");
		obj.addProperty("password", "junit_test_pass");
		Assert.assertEquals("", obj, user.toJSON());

		users.add(user);
		RegisterInfo[] regUsers = new RegisterInfo[users.size()];
	}
	
	/**
	 * username contain line feed character
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterUsers_UsernameInvalid3() {
		List<RegisterInfo> users = new ArrayList<RegisterInfo>();
		RegisterInfo user = RegisterInfo.newBuilder()
				.setUsername("junit \n test")
				.setPassword("junit_test_pass").build();

		JsonObject obj = new JsonObject();
		obj.addProperty("username", " ");
		obj.addProperty("password", "junit_test_pass");
		Assert.assertEquals("", obj, user.toJSON());
		users.add(user);
		RegisterInfo[] regUsers = new RegisterInfo[users.size()];
	}
	
	/**
	 * password too short, should not less than 4 bytes.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterUsers_PasswordInvalid() {
		List<RegisterInfo> users = new ArrayList<RegisterInfo>();
		RegisterInfo user = RegisterInfo.newBuilder()
				.setUsername("junit_test_user")
				.setPassword("123")
				.build();

		JsonObject obj = new JsonObject();
		obj.addProperty("username", "junit_test_user");
		obj.addProperty("password", "123");
		Assert.assertEquals("", obj, user.toJSON());

		users.add(user);
		RegisterInfo[] regUsers = new RegisterInfo[users.size()];
	}
	
	/**
	 * password too long, should less than 128 bytes.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterUsers_PasswordInvalid1() {
		List<RegisterInfo> users = new ArrayList<RegisterInfo>();
		RegisterInfo user = RegisterInfo.newBuilder()
				.setUsername("junit_test_user")
				.setPassword(MORE_THAN_128)
				.build();

		JsonObject obj = new JsonObject();
		obj.addProperty("username", "junit_test_user");
		obj.addProperty("password", MORE_THAN_128);
		Assert.assertEquals("", obj, user.toJSON());

		users.add(user);
		RegisterInfo[] regUsers = new RegisterInfo[users.size()];
	}

    @Test
    public void testRegisterUsers_Exist() {
        try {
            List<RegisterInfo> users = new ArrayList<RegisterInfo>();
            RegisterInfo user = RegisterInfo.newBuilder()
                    .setUsername(JUNIT_USER1)
                    .setPassword(JUNIT_USER1)
                    .build();

            users.add(user);
            RegisterInfo[] regUsers = new RegisterInfo[users.size()];

            RegisterPayload payload = RegisterPayload.newBuilder()
                    .addUsers(users.toArray(regUsers)).build();

            ResponseWrapper res = userClient.registerUsers(payload);
            assertEquals(201, res.responseCode);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            assertTrue(false);
        } catch (APIRequestException e) {
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
            assertEquals(899001, e.getErrorCode());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterUserNull() {
        try {
            userClient.registerUsers(null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: registerAdmins(RegisterPayload payload)
     */
    @Test
    public void testRegisterAdmins() {
        try {
            RegisterInfo user = RegisterInfo.newBuilder()
                    .setUsername("junit_test_admin")
                    .setPassword("junit_test_pass")
                    .build();

            JsonObject obj = new JsonObject();
            obj.addProperty("username", "junit_test_admin");
            obj.addProperty("password", "junit_test_pass");
            Assert.assertEquals("", obj, user.toJSON());
            ResponseWrapper res = userClient.registerAdmins(user);
            assertEquals(201, res.responseCode);
            ResponseWrapper res1 = userClient.deleteUser("junit_test_admin");
            assertEquals(204, res1.responseCode);

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

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterAdminNull() {
        try {
            userClient.registerAdmins(null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }


    /**
     * Method: getUserInfo(String username)
     */
    @Test
    public void testGetUserInfo() {
        try {
            UserInfoResult res = userClient.getUserInfo(JUNIT_USER);
            assertEquals(JUNIT_USER, res.getUsername());
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

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserInfoNull() {
        try {
            userClient.getUserInfo(null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserInfoEmpty() {
        try {
            userClient.getUserInfo("");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserInfoBlank() {
        try {
            userClient.getUserInfo("  ");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: updatePassword(String username, String password)
     */
    @Test
    public void testUpdatePassword() {
        try {
            ResponseWrapper res = userClient.updatePassword(JUNIT_USER, "junit_new_password");
            assertEquals(204, res.responseCode);
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

    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePassword_UsernameNull() {
        try {
            userClient.updatePassword(null, "password");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePassword_UsernameBlank() {
        try {
            userClient.updatePassword(" ", "password");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePassword_PasswordNull() {
        try {
            userClient.updatePassword("test_user", null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePassword_PasswordBlank() {
        try {
            userClient.updatePassword("test_user", " ");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePassword_PasswordLengthNotEnough() {
        try {
            userClient.updatePassword("test_user", "123");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePassword_PasswordOverLength() {
        try {
            userClient.updatePassword("test_user", MORE_THAN_128);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: updateUserInfo(String username, UserPayload payload)
     */
    @Test
    public void testUpdateUserInfo() {
        try {

            UserPayload payload = UserPayload.newBuilder()
                    .setNickname("hell My name")
                    .setRegion("shenzhen")
                    .setBirthday("2015-04-01")
                    .build();

            JsonObject obj = new JsonObject();
            obj.addProperty("nickname", "hell My name");
            obj.addProperty("region", "shenzhen");
            obj.addProperty("birthday", "2015-04-01" );

            Assert.assertEquals("", obj, payload.toJSON());

            ResponseWrapper res = userClient.updateUserInfo(JUNIT_USER, payload);
            assertEquals(204, res.responseCode);

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

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserInfo_UsernameNull() {
        try {
            userClient.updateUserInfo(null, UserPayload.newBuilder().build());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserInfo_UsernameBlank() {
        try {
            userClient.updateUserInfo(" ", UserPayload.newBuilder().build());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserInfo_PayloadNull() {
        try {
            userClient.updateUserInfo("test_user", null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: getUserList(int start, int count)
     */
    @Test
    public void testGetUserList() {
        try {
            UserListResult res = userClient.getUserList(0, 5);
            try {
                assertEquals(Integer.valueOf(5), res.getCount());
            } catch (Exception e) {
                LOG.error("parse response content error.", e);
                assertTrue(false);
            }
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

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetUserList_StartNegative() {
        try {
            userClient.getUserList(-1, 3);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserList_CountNegative() {
        try {
            userClient.getUserList(0, -1);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserList_CountMoreThan500() {
        try {
            userClient.getUserList(0, 501);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetAdminListByAppkey() {
    	try {
			UserListResult res = userClient.getAdminListByAppkey(0, 1);
			try {
                assertEquals(Integer.valueOf(1), res.getCount());
            } catch (Exception e) {
                LOG.error("parse response content error.", e);
                assertTrue(false);
            }
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
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAdminListByAppkey_StartNegative() {
    	try {
            userClient.getAdminListByAppkey(-1, 3);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAdminListByAppkey_CountNegative() {
        try {
            userClient.getAdminListByAppkey(0, -1);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAdminListByAppkey_CountMoreThan500() {
        try {
            userClient.getAdminListByAppkey(0, 501);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: getGroupList(String username)
     */
    @Test
    public void testGetGroupList() {
        try {
            UserGroupsResult res = userClient.getGroupList(JUNIT_USER);
            assertTrue(res.isResultOK());
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

    @Test(expected = IllegalArgumentException.class)
    public void testGetGroupList_UsernameNull() {
        try {
            userClient.getGroupList(null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetGroupList_UsernameBlank() {
        try {
            userClient.getGroupList(" ");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetGroupList_UsernameInvalid() {
        try {
            userClient.getGroupList("junit \n test");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: deleteUser(String username)
     */
    @Test
    public void testDeleteUser() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUser_UsernameNull() {
        try {
            userClient.deleteUser(null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUser_UsernameBlank() {
        try {
            userClient.deleteUser(" ");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUser_UsernameInvalid() {
        try {
            userClient.deleteUser("junit \n test");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

}
