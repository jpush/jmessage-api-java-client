package cn.jmessage.api;

import cn.jmessage.api.common.JMessageConfig;
import cn.jmessage.api.user.UserClient;
import cn.jmessage.api.group.GroupClient;
import cn.jmessage.api.common.model.GroupPayload;
import cn.jmessage.api.common.model.Members;
import cn.jmessage.api.common.model.RegisterPayload;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.common.model.UserPayload;

import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;

public class JMessageClient {

    private final UserClient _userClient;
    private final GroupClient _groupClient;

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
    }

    public String registerUsers(RegisterInfo[] users)
            throws APIConnectionException, APIRequestException
    {
        RegisterPayload payload = RegisterPayload.newBuilder()
                .addUsers(users)
                .build();

        return _userClient.registerUsers(payload).responseContent;
    }

    public String registerAdmins(RegisterInfo[] admins)
            throws APIConnectionException, APIRequestException
    {
        RegisterPayload payload = RegisterPayload.newBuilder()
                .addUsers(admins)
                .build();

        return _userClient.registerAdmins(payload).responseContent;
    }

    public String getUserInfo(String username)
            throws APIConnectionException, APIRequestException
    {
        return _userClient.getUserInfo(username).toString();
    }

    public void updateUserPassword(String username, String password)
            throws APIConnectionException, APIRequestException
    {
        _userClient.updatePassword(username, password);
    }

    public void updateUserInfo(String username, String nickname, String birthday, String signature, int gender,
                               String region, String address, String avatar)
            throws APIConnectionException, APIRequestException
    {
        UserPayload payload = UserPayload.newBuilder()
                .setNickname(nickname)
                .setBirthday(birthday)
                .setSignature(signature)
                .setGender(gender)
                .setRegion(region)
                .setAddress(address)
                .setAvatar(avatar)
                .build();

        _userClient.updateUserInfo(username, payload);
    }

    public String getUserList(int start, int count)
            throws APIConnectionException, APIRequestException
    {
        return _userClient.getUserList(start, count).toString();
    }

    public String getGroupListByUser(String username)
            throws APIConnectionException, APIRequestException
    {
        return _userClient.getGroupList(username).toString();
    }

    public void deleteUser(String username)
            throws APIConnectionException, APIRequestException
    {
        _userClient.deleteUser(username);
    }


    public String getGroupInfo(long gid)
            throws APIConnectionException, APIRequestException
    {
        return _groupClient.getGroupInfo(gid).responseContent;
    }

    public String getGroupMembers(long gid)
            throws APIConnectionException, APIRequestException
    {
        return _groupClient.getGroupMembers(gid).responseContent;
    }

    public String getGroupListByAppkey(int start, int count)
            throws APIConnectionException, APIRequestException
    {
        return _groupClient.getGroupListByAppkey(start, count).responseContent;
    }

    public String createGroup(String owner, String gname, String desc, String... userlist)
            throws APIConnectionException, APIRequestException
    {
        Members members = Members.newBuilder().addMember(userlist).build();

        GroupPayload payload = GroupPayload.newBuilder()
                .setOwner(owner)
                .setName(gname)
                .setDesc(desc)
                .setMembers(members)
                .build();

        return _groupClient.createGroup(payload).responseContent;
    }

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

}
