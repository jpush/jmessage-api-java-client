package cn.jmessage.api;

import cn.jmessage.api.common.JMessageConfig;
import cn.jmessage.api.common.model.*;
import cn.jmessage.api.group.*;
import cn.jmessage.api.message.MessageClient;
import cn.jmessage.api.message.SendMessageResult;
import cn.jmessage.api.user.UserClient;

import cn.jmessage.api.user.UserGroupsResult;
import cn.jmessage.api.user.UserInfoResult;
import cn.jmessage.api.user.UserListResult;
import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;

public class JMessageClient {

    private final UserClient _userClient;
    private final GroupClient _groupClient;
    private final MessageClient _messageClient;
    private final int _sendVersion;

    /**
     * Create a JMessage Client.
     *
     * @param appkey The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     */
    public JMessageClient(String appkey, String masterSecret) {
        this(appkey, masterSecret, null, JMessageConfig.getInstance());
    }

    /**
     * Create a JMessage Client with custom maxRetryTimes.
     *
     * @param appkey The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param maxRetryTimes The max retry times.
     */
    public JMessageClient(String appkey, String masterSecret, int maxRetryTimes) {
        this(appkey, masterSecret, null, JMessageConfig.getInstance().setMaxRetryTimes(maxRetryTimes));
    }

    /**
     * Create a JMessage Client with a proxy.
     *
     * @param appkey The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param proxy The proxy, if there is no proxy, should be null.
     */
    public JMessageClient(String appkey, String masterSecret, HttpProxy proxy) {
        this(appkey, masterSecret, proxy, JMessageConfig.getInstance());
    }

    /**
     * Create a JMessage Client with a custom hostname.
     * If you are using JPush/JMessage privacy cloud, maybe this constructor is what you needed.
     *
     * @param appkey The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param hostname The custom hostname.
     */
    public JMessageClient(String appkey, String masterSecret, String hostname) {
        this(appkey, masterSecret, null, JMessageConfig.getInstance().setApiHostName(hostname));
    }


    /**
     * Create a JMessage Client by custom Client configuration.
     * If you are using custom parameters, maybe this constructor is what you needed.
     *
     * @param appkey The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param proxy The proxy, if there is no proxy, should be null.
     * @param config The client configuration. Can use JMessageConfig.getInstance() as default.
     */
    public JMessageClient(String appkey, String masterSecret, HttpProxy proxy, JMessageConfig config) {
        _userClient = new UserClient(appkey, masterSecret, proxy, config);
        _groupClient = new GroupClient(appkey, masterSecret, proxy, config);
        _messageClient = new MessageClient(appkey, masterSecret, proxy, config);
        _sendVersion = (Integer) config.get(JMessageConfig.SEND_VERSION);
    }

    public String registerUsers(RegisterInfo[] users)
            throws APIConnectionException, APIRequestException
    {
        RegisterPayload payload = RegisterPayload.newBuilder()
                .addUsers(users)
                .build();

        return _userClient.registerUsers(payload).responseContent;
    }

    public String registerAdmins(String username, String password)
            throws APIConnectionException, APIRequestException
    {
        RegisterInfo payload = RegisterInfo.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();

        return _userClient.registerAdmins(payload).responseContent;
    }

    public UserInfoResult getUserInfo(String username)
            throws APIConnectionException, APIRequestException
    {
        return _userClient.getUserInfo(username);
    }

    public void updateUserPassword(String username, String password)
            throws APIConnectionException, APIRequestException
    {
        _userClient.updatePassword(username, password);
    }

    public void updateUserInfo(String username, String nickname, String birthday, String signature, int gender,
                               String region, String address)
            throws APIConnectionException, APIRequestException
    {
        UserPayload payload = UserPayload.newBuilder()
                .setNickname(nickname)
                .setBirthday(birthday)
                .setSignature(signature)
                .setGender(gender)
                .setRegion(region)
                .setAddress(address)
                .build();

        _userClient.updateUserInfo(username, payload);
    }

    /**
     * Get user list
     * @param start The start index of the list
     * @param count The number that how many you want to get from list
     * @return
     * @throws APIConnectionException
     * @throws APIRequestException
     */
    public UserListResult getUserList(int start, int count)
            throws APIConnectionException, APIRequestException
    {
        return _userClient.getUserList(start, count);
    }
    
    /**
     * Get admins by appkey
     * @param start The start index of the list
     * @param count The number that how many you want to get from list
     * @return
     * @throws APIConnectionException
     * @throws APIRequestException
     */
    public UserListResult getAdminListByAppkey(int start, int count)
    		throws APIConnectionException, APIRequestException
    {
    	return _userClient.getAdminListByAppkey(start, count);
    }

    /**
     * Get all groups of a user
     * @param username
     * @return
     * @throws APIConnectionException
     * @throws APIRequestException
     */
    public UserGroupsResult getGroupListByUser(String username)
            throws APIConnectionException, APIRequestException
    {
        return _userClient.getGroupList(username);
    }

    public void deleteUser(String username)
            throws APIConnectionException, APIRequestException
    {
        _userClient.deleteUser(username);
    }


    public GroupInfoResult getGroupInfo(long gid)
            throws APIConnectionException, APIRequestException
    {
        return _groupClient.getGroupInfo(gid);
    }

    public MemberListResult getGroupMembers(long gid)
            throws APIConnectionException, APIRequestException
    {
        return _groupClient.getGroupMembers(gid);
    }

    public GroupListResult getGroupListByAppkey(int start, int count)
            throws APIConnectionException, APIRequestException
    {
        return _groupClient.getGroupListByAppkey(start, count);
    }

    public CreateGroupResult createGroup(String owner, String gname, String desc, String... userlist)
            throws APIConnectionException, APIRequestException
    {
        Members members = Members.newBuilder().addMember(userlist).build();

        GroupPayload payload = GroupPayload.newBuilder()
                .setOwner(owner)
                .setName(gname)
                .setDesc(desc)
                .setMembers(members)
                .build();

        return _groupClient.createGroup(payload);
    }

    /**
     * Add or remove members from a group
     * @param gid The group id
     * @param addList If this parameter is null then send remove request
     * @param removeList If this parameter is null then send add request
     * @throws APIConnectionException
     * @throws APIRequestException
     */
    public void addOrRemoveMembers(long gid, String[] addList, String[] removeList)
            throws APIConnectionException, APIRequestException
    {
        Members add = Members.newBuilder()
                .addMember(addList)
                .build();

        Members remove = Members.newBuilder()
                .addMember(removeList)
                .build();

        _groupClient.addOrRemoveMembers(gid, add, remove);
    }

    public void deleteGroup(long gid)
            throws APIConnectionException, APIRequestException
    {
        _groupClient.deleteGroup(gid);
    }

    public void updateGroupInfo(long gid, String groupName, String groupDesc)
            throws APIConnectionException, APIRequestException
    {
        _groupClient.updateGroupInfo(gid, groupName, groupDesc);
    }

    /**
     * Send message
     * @param version Current version is 1
     * @param targetType Group or single
     * @param targetId The message receiver 
     * @param fromType Only support admin now
     * @param fromId Sender
     * @param messageType Only support text now
     * @param messageBody A MessageBody instance
     * @return
     * @throws APIConnectionException
     * @throws APIRequestException
     */
    public SendMessageResult sendMessage(Integer version, String targetType, String targetId,
                            String fromType, String fromId, String messageType, MessageBody messageBody)
            throws APIConnectionException, APIRequestException {
        MessagePayload payload = MessagePayload.newBuilder()
                .setVersion(version)
                .setTargetType(targetType)
                .setTargetId(targetId)
                .setFromType(fromType)
                .setFromId(fromId)
                .setMessageType(messageType)
                .setMessageBody(messageBody)
                .build();
        return _messageClient.sendMessage(payload);
    }

    /**
     * Send single text message by admin
     * @param targetId 
     * @param fromId
     * @param body
     * @return
     * @throws APIConnectionException
     * @throws APIRequestException
     */
    public SendMessageResult sendSingleTextByAdmin(String targetId, String fromId, MessageBody body)
            throws APIConnectionException, APIRequestException
    {
        return sendMessage(_sendVersion, "single", targetId, "admin",fromId, "text", body);
    }

    /**
     * Send group text message by admin
     * @param targetId
     * @param fromId
     * @param body
     * @return
     * @throws APIConnectionException
     * @throws APIRequestException
     */
    public SendMessageResult sendGroupTextByAdmin(String targetId, String fromId, MessageBody body)
            throws APIConnectionException, APIRequestException
    {
        return sendMessage(_sendVersion, "group", targetId, "admin",fromId, "text", body);
    }

}
