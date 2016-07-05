package cn.jmessage.api.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.group.CreateGroupResult;
import cn.jmessage.api.group.GroupInfoResult;
import cn.jmessage.api.group.GroupListResult;
import cn.jmessage.api.group.MemberListResult;
import cn.jmessage.api.user.UserGroupsResult;

public class GroupExample {

    protected static final Logger LOG = LoggerFactory.getLogger(GroupExample.class);

    private static final String appkey = "242780bfdd7315dc1989fe2b";
    private static final String masterSecret = "2f5ced2bef64167950e63d13";

    public static void main(String[] args) {
//        testGetGroupInfo();
    }
    
    public static void testCreateGroup() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            CreateGroupResult res = client.createGroup("test_user", "test_gname1", "description", "test_user");
            LOG.info(res.getName());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testGetGroupInfo() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            GroupInfoResult res = client.getGroupInfo(10003767);
            LOG.info(res.getOriginalContent());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testGetGroupMemberList() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            MemberListResult res = client.getGroupMembers(10003767);
            LOG.info(res.getOriginalContent());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Get group list by appkey, this method will invoke getGroupListByAppkey() in GroupClient, whose parameters
     * are list as follow:
     * @param start The start index of the list
     * @param count The number that you want to get from the list
     */
    public static void testGetGroupListByAppkey() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            GroupListResult res = client.getGroupListByAppkey(0, 30);
            LOG.info(res.getOriginalContent());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testManageGroup() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            String[] addList = {"baobao148"};
            String[] removeList = {"baobao148"};
            client.addOrRemoveMembers(10003767, addList, null );
            client.addOrRemoveMembers(10003767, null, removeList);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testUpdateGroupInfo() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            client.updateGroupInfo(10003767, "test_gname_new", "update desc");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
    
    public static void testGetGroupsByUser() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            UserGroupsResult res = client.getGroupListByUser("test_user");
            LOG.info(res.getOriginalContent());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testDeleteGroup() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            client.deleteGroup(10003765);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

}
