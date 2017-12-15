package cn.jmessage.api.group;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.BaseTest;
import cn.jmessage.api.SlowTests;
import cn.jmessage.api.common.model.group.GroupPayload;
import cn.jmessage.api.common.model.Members;

@Category(SlowTests.class)
public class GroupClientTest extends BaseTest {

    private static Logger LOG = LoggerFactory.getLogger(GroupClientTest.class);

    private GroupClient groupClient = null;

    @Before
    public void before() throws Exception {
        groupClient = new GroupClient(APP_KEY, MASTER_SECRET);
    }


    /**
     * Method: getGroupInfo(long gid)
     */
    @Test
    public void testGetGroupInfo() {
        try {
            GroupInfoResult res = groupClient.getGroupInfo(JUNIT_GID1);
            assertEquals(Long.valueOf(JUNIT_GID1), res.getGid());

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetGroupInfo_GidNegative() {
        try {
            groupClient.getGroupInfo(-1);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testGetGroupMembers() {
        try {
            MemberListResult res = groupClient.getGroupMembers(JUNIT_GID1);
            assertTrue(res.isResultOK());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetGroupMembers_GidNegative() {
        try {
            groupClient.getGroupMembers(-1);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: getGroupListByAppkey(int start, int count)
     */
    @Test
    public void testGetGroupListByAppkey() {
        try {
            GroupListResult res = groupClient.getGroupListByAppkey(0, 2);
            assertEquals(Integer.valueOf(2), res.getCount());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetGroupListByAppkey_StartNegative() {
        try {
            groupClient.getGroupListByAppkey(-1, 3);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetGroupListByAppkey_CountNegative() {
        try {
            groupClient.getGroupListByAppkey(0, -1);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetGroupListByAppkey_CountMoreThan500() {
        try {
            groupClient.getGroupListByAppkey(0, 501);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: createGroup(GroupPayload payload)
     */
    @Test
    public void testCreateGroup() {
        try {
            GroupPayload payload = GroupPayload.newBuilder()
                    .setOwner(JUNIT_USER)
                    .setName("junit_test_group")
                    .setDesc("for junit test")
                    .build();

            JsonObject obj = new JsonObject();
            obj.addProperty("owner_username", JUNIT_USER);
            obj.addProperty("name", "junit_test_group");
            obj.addProperty("desc", "for junit test");
            obj.addProperty("flag", 0);

            assertEquals(obj, payload.toJSON());

            CreateGroupResult res = groupClient.createGroup(payload);

            assertEquals("junit_test_group", res.getName());

            try {
                ResponseWrapper res1 = groupClient.deleteGroup(res.getGid());
                assertEquals(204, res1.responseCode);
            } catch (Exception e) {
                LOG.error("parse response content error.", e);
                assertTrue(false);
            }

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateGroupNull() {
        try {
            groupClient.createGroup(null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateGroup_GroupOwnerNull() {
    	GroupPayload payload = GroupPayload.newBuilder()
                .setName("junit_test_group")
                .setDesc("for junit test")
                .build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateGroup_GroupNameNull() {
    	GroupPayload payload = GroupPayload.newBuilder()
                .setOwner(JUNIT_USER)
                .setDesc("for junit test")
                .build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateGroup_GroupNameOverLength() {
    	GroupPayload payload = GroupPayload.newBuilder()
                .setOwner(JUNIT_USER)
                .setName(MORE_THAN_64)
                .setDesc("for junit test")
                .build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateGroup_GroupNameInvalid() {
    	GroupPayload payload = GroupPayload.newBuilder()
                .setOwner(JUNIT_USER)
                .setName("junit \n test")
                .setDesc("for junit test")
                .build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateGroup_GroupOwnerNameInvalid() {
    	GroupPayload payload = GroupPayload.newBuilder()
    			.setOwner("junit_user \n test")
    			.setName("junit test group")
    			.setDesc("for junit test")
    			.build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateGroup_GroupDescOverLength() {
    	GroupPayload payload = GroupPayload.newBuilder()
                .setOwner(JUNIT_USER)
                .setName("junit_test_group")
                .setDesc(MORE_THAN_250)
                .build();
    }
    
    /**
     * Method: addOrRemoveMembers(long gid, Members add, Members remove)
     */
    @Test
    public void testAddOrRemoveMembers() {
        try {
            Members members = Members.newBuilder()
                    .addMember(JUNIT_USER1).build();

            JsonArray array = new JsonArray();

            JsonPrimitive mem = new JsonPrimitive(JUNIT_USER1);
            array.add(mem);
            assertEquals(array, members.toJSON());

            ResponseWrapper res = groupClient.addOrRemoveMembers(JUNIT_GID1, members, null);
            assertEquals(204, res.responseCode);

            ResponseWrapper res1 = groupClient.addOrRemoveMembers(JUNIT_GID1, null, members);
            assertEquals(204, res1.responseCode);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddOrRemoveMembers_GidNegative() {
        try {
            groupClient.addOrRemoveMembers(-1, Members.newBuilder().build(), Members.newBuilder().build());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddOrRemoveMembers_AddRemoveBothNull() {
        try {
            groupClient.addOrRemoveMembers(10010, null, null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: deleteGroup(long gid)
     */
    @Test
    public void testDeleteGroup() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteGroup_GidNegative() {
        try {
            groupClient.deleteGroup(-1);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: updateGroupInfo(long gid, String groupName, String groupDesc)
     */
    @Test
    public void testUpdateGroupInfo() {
        try {
            ResponseWrapper res = groupClient.updateGroupInfo(JUNIT_GID1, "junit_group_new", null, "media id");
            assertEquals(204, res.responseCode);

            ResponseWrapper res1 = groupClient.updateGroupInfo(JUNIT_GID1, null, "junit group desc", "media id");
            assertEquals(204, res1.responseCode);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateGroupInfo_GidNegative() {
        try {
            groupClient.updateGroupInfo(-1, "test_group", "group desc", "media id");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateGroupInfo_NameDescBothNull() {
        try {
            groupClient.updateGroupInfo(10010, null, null, null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateGroupInfo_NameOverLength() {
        try {
            groupClient.updateGroupInfo(10010, MORE_THAN_64, "test desc", "media id");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateGroupInfo_DescOverLength() {
        try {
            groupClient.updateGroupInfo(10010, "test_name", MORE_THAN_250, "media id");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

} 
