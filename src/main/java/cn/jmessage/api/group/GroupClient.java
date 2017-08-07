package cn.jmessage.api.group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.common.BaseClient;
import cn.jmessage.api.common.JMessageConfig;
import cn.jmessage.api.common.model.group.GroupPayload;
import cn.jmessage.api.common.model.Members;
import cn.jmessage.api.utils.StringUtils;


public class GroupClient extends BaseClient {

    private static final Logger LOG = LoggerFactory.getLogger(GroupClient.class);

    private String groupPath;

    /**
     * Create a Group Client with default parameters.
     *
     * @param appkey The key of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     */
    public GroupClient(String appkey, String masterSecret) {
        this(appkey, masterSecret, null, JMessageConfig.getInstance());
    }

    /**
     * Create a Group Client with a proxy.
     *
     * @param appkey The key of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param proxy The proxy, if there is no proxy, should be null.
     */
    public GroupClient(String appkey, String masterSecret, HttpProxy proxy) {
        this(appkey, masterSecret, proxy, JMessageConfig.getInstance());
    }

    /**
     * Create a Group Client by custom config.
     * If you are using JMessage privacy cloud or custom parameters, maybe this constructor is what you needed.
     *
     * @param appkey The key of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param proxy The proxy, if there is no proxy, should be null.
     * @param config The client configuration. Can use JMessageConfig.getInstance() as default.
     */
    public GroupClient(String appkey, String masterSecret, HttpProxy proxy, JMessageConfig config) {
        super(appkey, masterSecret, proxy, config);
        this.groupPath = (String) config.get(JMessageConfig.GROUP_PATH);
    }

    public GroupInfoResult getGroupInfo( long gid )
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkArgument(gid > 0, "gid should more than 0.");

        ResponseWrapper response = _httpClient.sendGet(_baseUrl + groupPath + "/" + gid);

        return GroupInfoResult.fromResponse(response, GroupInfoResult.class);
    }

    public MemberListResult getGroupMembers( long gid )
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkArgument(gid > 0, "gid should more than 0.");

        ResponseWrapper response = _httpClient.sendGet(_baseUrl + groupPath + "/" + gid + "/members");

        return MemberListResult.fromResponse(response);
    }

    public GroupListResult getGroupListByAppkey( int start, int count )
            throws APIConnectionException, APIRequestException
    {
    	if(start < 0 || count <= 0 || count > 500) {
        	throw new IllegalArgumentException("negative index or count must more than 0 and less than 501");
        }
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + groupPath + "?start=" + start + "&count=" + count);
        return GroupListResult.fromResponse(response, GroupListResult.class);
    }

    public CreateGroupResult createGroup(GroupPayload payload)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(! (null == payload), "group payload should not be null");

        ResponseWrapper response = _httpClient.sendPost(_baseUrl + groupPath, payload.toString());
        return CreateGroupResult.fromResponse(response, CreateGroupResult.class);
    }

    public ResponseWrapper addOrRemoveMembers( long gid, Members add, Members remove)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(gid > 0, "gid should more than 0.");

        if ( null == add && null == remove ) {
            Preconditions.checkArgument(false, "add list or remove list should not be null at the same time.");
        }

        JsonObject json = new JsonObject();

        if ( null != add ) {
            json.add("add", add.toJSON());
        }

        if ( null != remove ) {
            json.add("remove", remove.toJSON());
        }

        return _httpClient.sendPost(_baseUrl + groupPath + "/" + gid + "/members", json.toString());

    }

    public ResponseWrapper deleteGroup( long gid )
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(gid > 0, "gid should more than 0.");
        return _httpClient.sendDelete(_baseUrl + groupPath + "/" + gid);
    }

    public ResponseWrapper updateGroupInfo( long gid, String groupName, String groupDesc )
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(gid > 0, "gid should more than 0.");

        if ( StringUtils.isTrimedEmpty(groupName) && StringUtils.isTrimedEmpty(groupDesc)) {
            Preconditions.checkArgument(false, "group name or group description should not be null at the same time.");
        }

        JsonObject json = new JsonObject();
        if (StringUtils.isNotEmpty(groupName)) {
            groupName = groupName.trim();
            Preconditions.checkArgument( groupName.getBytes().length <= 64,
                    "The length of group name must not more than 64 bytes.");
            Preconditions.checkArgument( !StringUtils.isLineBroken(groupName),
                    "The group name must not contain line feed character.");
            json.addProperty(GroupPayload.GROUP_NAME, groupName);
        }

        if (StringUtils.isNotEmpty(groupDesc)) {
            groupDesc = groupDesc.trim();
            Preconditions.checkArgument(groupDesc.getBytes().length <= 250,
                    "The length of group description must not more than 250 bytes.");
            json.addProperty(GroupPayload.DESC, groupDesc);
        }

        return _httpClient.sendPut(_baseUrl + groupPath + "/" + gid, json.toString());
    }

}
