package cn.jmessage.api.examples;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.cross.CrossBlacklist;
import cn.jmessage.api.common.model.cross.CrossFriendPayload;
import cn.jmessage.api.common.model.cross.CrossGroup;
import cn.jmessage.api.common.model.cross.CrossNoDisturb;
import cn.jmessage.api.group.MemberListResult;
import cn.jmessage.api.group.MemberResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CrossAppExample {

    private static Logger LOG = LoggerFactory.getLogger(CrossAppExample.class);
    private static final String appkey = "242780bfdd7315dc1989fe2b";
    private static final String masterSecret = "2f5ced2bef64167950e63d13";

    public static void main(String[] args) {

    }

    public static void testAddOrRemoveMembersFromCrossGroup() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            List<CrossGroup> crossGroups = new ArrayList<CrossGroup>();
            CrossGroup crossGroup = new CrossGroup.Builder()
                    .setAppKey(appkey)
                    .setAddUsers("test_user1", "test_user2")
                    .setRemoveUsers("test_user3")
                    .build();
            crossGroups.add(crossGroup);
            CrossGroup[] array = new CrossGroup[crossGroups.size()];
            ResponseWrapper response = client.addOrRemoveCrossGroupMember(10004809, crossGroups.toArray(array));
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testGetCrossGroupMembers() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            MemberListResult result = client.getCrossGroupMembers(10004809);
            MemberResult[] members = result.getMembers();
            LOG.info("Member size " + members.length);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testAddCrossBlacklist() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            List<CrossBlacklist> crossBlacklists = new ArrayList<CrossBlacklist>();
            CrossBlacklist blacklist = new CrossBlacklist.Builder()
                    .setAppKey(appkey)
                    .addUsers("test_user1", "test_user2")
                    .build();

            crossBlacklists.add(blacklist);
            CrossBlacklist[] array = new CrossBlacklist[crossBlacklists.size()];
            ResponseWrapper response = client.addCrossBlacklist("test_user", crossBlacklists.toArray(array));
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testDeleteCrossBlacklist() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            List<CrossBlacklist> crossBlacklists = new ArrayList<CrossBlacklist>();
            CrossBlacklist blacklist = new CrossBlacklist.Builder()
                    .setAppKey(appkey)
                    .addUsers("test_user1", "test_user2")
                    .build();

            crossBlacklists.add(blacklist);
            CrossBlacklist[] array = new CrossBlacklist[crossBlacklists.size()];
            ResponseWrapper response = client.deleteCrossBlacklist("test_user", crossBlacklists.toArray(array));
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testSetCrossNoDisturb() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            List<CrossNoDisturb> list = new ArrayList<CrossNoDisturb>();
            CrossNoDisturb crossNoDisturb = new CrossNoDisturb.Builder()
                    .setAppKey(appkey)
                    .setRemoveSingleUsers("test_user1", "test_user2")
                    .setRemoveGroupIds(10004809L)
                    .build();
            list.add(crossNoDisturb);
            CrossNoDisturb[] array = new CrossNoDisturb[list.size()];
            ResponseWrapper response = client.setCrossNoDisturb("test_user", list.toArray(array));
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testAddCrossUsers() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            CrossFriendPayload payload = new CrossFriendPayload.Builder()
                    .setAppKey(appkey)
                    .setUsers("test_user1", "test_user2")
                    .build();
            ResponseWrapper response = client.addCrossFriends("test_user", payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

}
