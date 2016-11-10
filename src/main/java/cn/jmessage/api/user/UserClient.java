package cn.jmessage.api.user;


import cn.jmessage.api.common.model.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.commom.utils.Preconditions;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.common.BaseClient;
import cn.jmessage.api.common.JMessageConfig;
import cn.jmessage.api.utils.StringUtils;

public class UserClient extends BaseClient {

    private static final Logger LOG = LoggerFactory.getLogger(UserClient.class);

    private String userPath;
    private String adminPath;

    /**
     * Create a User Client with default parameters.
     *
     * @param appkey The key of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     */
    public UserClient(String appkey, String masterSecret) {
        this(appkey, masterSecret, null, JMessageConfig.getInstance());
    }

    /**
     * Create a User Client with a proxy.
     *
     * @param appkey The key of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param proxy The proxy, if there is no proxy, should be null.
     */
    public UserClient(String appkey, String masterSecret, HttpProxy proxy) {
        this(appkey, masterSecret, proxy, JMessageConfig.getInstance());
    }

    /**
     * Create a User Client with custom config.
     * If you are using JMessage privacy cloud or custom parameters, maybe this constructor is what you needed.
     *
     * @param appkey The key of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param proxy The proxy, if there is no proxy, should be null.
     * @param config The client configuration. Can use JMessageConfig.getInstance() as default.
     */
    public UserClient(String appkey, String masterSecret, HttpProxy proxy, JMessageConfig config) {
        super(appkey, masterSecret, proxy, config);
        userPath = (String) config.get(JMessageConfig.USER_PATH);
        adminPath = (String) config.get(JMessageConfig.ADMIN_PATH);
    }

    public ResponseWrapper registerUsers(RegisterPayload payload)
            throws APIConnectionException, APIRequestException {

        Preconditions.checkArgument(null != payload, "payload should not be null");

        return _httpClient.sendPost(_baseUrl + userPath, payload.toString());
    }

    public ResponseWrapper registerAdmins(RegisterInfo payload)
            throws APIConnectionException, APIRequestException {

        Preconditions.checkArgument(null != payload, "payload should not be null");

        return _httpClient.sendPost(_baseUrl + adminPath, payload.toString());

    }

    public UserInfoResult getUserInfo(String username)
            throws APIConnectionException, APIRequestException {

    	StringUtils.checkUsername(username);
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + userPath + "/" + username);
        return UserInfoResult.fromResponse(response, UserInfoResult.class);
    }

    public ResponseWrapper updateUserInfo(String username, UserPayload payload)
            throws APIConnectionException,APIRequestException {
        StringUtils.checkUsername(username);
        Preconditions.checkArgument(null != payload, "payload should not be null");

        return _httpClient.sendPut(_baseUrl + userPath + "/" + username, payload.toString());
    }

    /**
     * Get user state
     * @param username Necessary
     * @return user state info, include login and online message.
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public UserStateResult getUserState(String username)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + userPath + "/" + username + "/userstat");
        return UserStateResult.fromResponse(response, UserStateResult.class);
    }

    public ResponseWrapper updatePassword( String username, String password )
            throws APIConnectionException, APIRequestException {

    	StringUtils.checkUsername(username);
    	StringUtils.checkPassword(password);

        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("new_password", password);

        return _httpClient.sendPut(_baseUrl + userPath + "/" + username + "/password",
                jsonObj.toString());

    }

    public ResponseWrapper deleteUser( String username )
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        return _httpClient.sendDelete(_baseUrl + userPath + "/" + username);
    }

    /**
     * Get a user's all black list
     * @param username The owner of the black list
     * @return UserList
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public UserInfoResult[] getBlackList(String username)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        ResponseWrapper response =  _httpClient.sendGet( _baseUrl + userPath + "/" + username + "/blacklist");
        return _gson.fromJson(response.responseContent, UserInfoResult[].class);
    }

    /**
     * Add Users to black list
     * @param username The owner of the black list
     * @param users The users that will add to black list
     * @return add users to black list
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ResponseWrapper addBlackList( String username, String... users )
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        Preconditions.checkArgument(null != users && users.length > 0, "black list should not be empty");

        JsonArray array = new JsonArray();
        for (String user : users) {
            array.add(new JsonPrimitive(user));
        }
        return _httpClient.sendPut(_baseUrl + userPath + "/" + username + "/blacklist", array.toString());
    }

    public ResponseWrapper removeBlackList( String username, String... users)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        Preconditions.checkArgument(null != users && users.length > 0, "black list should not be empty");
        JsonArray array = new JsonArray();
        for (String user : users) {
            array.add(new JsonPrimitive(user));
        }

        return _httpClient.sendDelete(_baseUrl + userPath + "/" + username + "/blacklist", array.toString());
    }

    /**
     * Get user list
     * @param start The start index of the list
     * @param count The number that how many you want to get from list
     * @return User info list
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public UserListResult getUserList( int start, int count )
            throws APIConnectionException, APIRequestException {

        if(start < 0 || count <= 0 || count > 500) {
            throw new IllegalArgumentException("negative index or count must more than 0 and less than 501");
        }
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + userPath + "?start=" + start + "&count=" + count);
        return UserListResult.fromResponse(response, UserListResult.class);

    }

    /**
     * Get admins by appkey
     * @param start The start index of the list
     * @param count The number that how many you want to get from list
     * @return admin user info list
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public UserListResult getAdminListByAppkey(int start, int count)
    		throws APIConnectionException, APIRequestException {
    	if(start < 0 || count <= 0 || count > 500) {
        	throw new IllegalArgumentException("negative index or count must more than 0 and less than 501");
        }
    	ResponseWrapper response = _httpClient.sendGet(_baseUrl + adminPath + "?start=" + start + "&count=" + count);
    	return UserListResult.fromResponse(response, UserListResult.class);
    
    }

    /**
     * Set don't disturb service while receiving messages.
     * You can Add or remove single conversation or group conversation
     * @param username Necessary
     * @param payload NoDisturbPayload
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ResponseWrapper setNoDisturb(String username, NoDisturbPayload payload)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        Preconditions.checkArgument(null != payload, "payload should not be null");

        return _httpClient.sendPost(_baseUrl + userPath + "/" + username + "/nodisturb", payload.toString());
    }

    /**
     * Get all groups of a user
     * @param username Necessary
     * @return Group info list
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public UserGroupsResult getGroupList(String username)
            throws APIConnectionException, APIRequestException {
    	StringUtils.checkUsername(username);
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + userPath + "/" + username + "/groups");

        return UserGroupsResult.fromResponse(response);
    }

    /**
     * Add friends to username
     * @param username Necessary
     * @param users username to be add
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ResponseWrapper addFriends(String username, String...users)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        Preconditions.checkArgument(null != users && users.length > 0, "friend list should not be empty");

        JsonArray array = new JsonArray();
        for (String user : users) {
            array.add(new JsonPrimitive(user));
        }

        return _httpClient.sendPost(_baseUrl + userPath + "/" + username + "/friends", array.toString());
    }

    /**
     * Delete friends
     * @param username Friends you want to delete to
     * @param users username to be delete
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ResponseWrapper deleteFriends(String username, String...users)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        Preconditions.checkArgument(null != users && users.length > 0, "friend list should not be empty");

        JsonArray array = new JsonArray();
        for (String user : users) {
            array.add(new JsonPrimitive(user));
        }
        return _httpClient.sendDelete(_baseUrl + userPath + "/" + username + "/friends", array.toString());
    }

    /**
     * Update friends' note information. The size is limit to 500.
     * @param username Necessary
     * @param array FriendNote array
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ResponseWrapper updateFriendsNote(String username, FriendNote[] array)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        FriendNotePayload payload = new FriendNotePayload.Builder()
                .setFriendNotes(array)
                .build();
        Preconditions.checkArgument(null != payload, "FriendNotePayload should not be null");
        return _httpClient.sendPut(_baseUrl + userPath + "/" + username + "/friends", payload.toString());
    }

    /**
     * Get friends'info from user
     * @param username Necessary
     * @return UserList
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public UserInfoResult[] getFriendsInfo(String username)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + userPath + "/" + username + "/friends");
        return _gson.fromJson(response.responseContent, UserInfoResult[].class);
    }

}
