package cn.jmessage.api.cross;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.BaseTest;
import cn.jmessage.api.CrossApp.CrossAppClient;
import cn.jmessage.api.common.model.CrossBlacklist;
import cn.jmessage.api.common.model.CrossFriendPayload;
import cn.jmessage.api.common.model.CrossGroup;
import cn.jmessage.api.common.model.CrossNoDisturb;
import cn.jmessage.api.group.MemberListResult;
import cn.jmessage.api.group.MemberResult;
import cn.jmessage.api.user.UserInfoResult;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CrossAppClientTest extends BaseTest {

    private Logger LOG = LoggerFactory.getLogger(CrossAppClientTest.class);
    private CrossAppClient client = null;

    @Before
    public void before() throws Exception {
        client = new CrossAppClient(APP_KEY, MASTER_SECRET);
    }

    @Test
    public void testAddOrRemoveMembersFromCrossGroup() {
        try {
            List<CrossGroup> crossGroups = new ArrayList<CrossGroup>();
            CrossGroup crossGroup = new CrossGroup.Builder()
                    .setAppKey(APP_KEY)
                    .setAddUsers(JUNIT_USER1, JUNIT_USER2)
                    .build();
            crossGroups.add(crossGroup);
            CrossGroup[] array = new CrossGroup[crossGroups.size()];
            ResponseWrapper response = client.addOrRemoveCrossGroupMembers(JUNIT_GID, crossGroups.toArray(array));
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testGetCrossGroupMembers() {
        try {
            MemberListResult result = client.getCrossGroupMembers(JUNIT_GID);
            MemberResult[] members = result.getMembers();
            LOG.info("Member size " + members.length);
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
    public void testAddCrossBlacklist() {
        try {
            List<CrossBlacklist> crossBlacklists = new ArrayList<CrossBlacklist>();
            CrossBlacklist blacklist = new CrossBlacklist.Builder()
                    .setAppKey(APP_KEY)
                    .addUsers(JUNIT_USER1, JUNIT_USER2)
                    .build();

            crossBlacklists.add(blacklist);
            CrossBlacklist[] array = new CrossBlacklist[crossBlacklists.size()];
            ResponseWrapper response = client.addCrossBlacklist(JUNIT_USER, crossBlacklists.toArray(array));
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteCrossBlacklist() {
        try {
            List<CrossBlacklist> crossBlacklists = new ArrayList<CrossBlacklist>();
            CrossBlacklist blacklist = new CrossBlacklist.Builder()
                    .setAppKey(APP_KEY)
                    .addUsers(JUNIT_USER1, JUNIT_USER2)
                    .build();

            crossBlacklists.add(blacklist);
            CrossBlacklist[] array = new CrossBlacklist[crossBlacklists.size()];
            ResponseWrapper response = client.deleteCrossBlacklist(JUNIT_USER, crossBlacklists.toArray(array));
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testGetCrossBlacklist() {
        try {
            UserInfoResult[] result = client.getCrossBlacklist(JUNIT_USER);
            LOG.info("Users' info size: " + result.length);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testSetCrossNoDisturb() {
        try {
            List<CrossNoDisturb> list = new ArrayList<CrossNoDisturb>();
            CrossNoDisturb crossNoDisturb = new CrossNoDisturb.Builder()
                    .setAppKey(APP_KEY)
                    .setRemoveSingleUsers(JUNIT_USER1, JUNIT_USER2)
                    .setRemoveGroupIds(JUNIT_GID)
                    .build();
            list.add(crossNoDisturb);
            CrossNoDisturb[] array = new CrossNoDisturb[list.size()];
            ResponseWrapper response = client.setCrossNoDisturb(JUNIT_USER, list.toArray(array));
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testAddCrossUsers() {
        try {
            CrossFriendPayload payload = new CrossFriendPayload.Builder()
                    .setAppKey(CROSS_APP_KEY)
                    .setUsers("0001", "0002")
                    .build();
            ResponseWrapper response = client.addCrossFriends(JUNIT_USER, payload);
            LOG.info("Http status: " + response.responseCode);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteCrossFriends() {
        try {
            CrossFriendPayload payload = new CrossFriendPayload.Builder()
                    .setAppKey(CROSS_APP_KEY)
                    .setUsers("0001", "0002")
                    .build();
            ResponseWrapper response = client.deleteCrossFriends(JUNIT_USER, payload);
            LOG.info("Http status: " + response.responseCode);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
}
