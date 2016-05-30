package cn.jmessage.api.user;


import cn.jmessage.api.common.BaseClient;
import cn.jmessage.api.common.JMessageConfig;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.common.model.RegisterPayload;
import cn.jmessage.api.common.model.UserPayload;
import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.ResponseWrapper;
import cn.jpush.api.utils.Preconditions;
import cn.jpush.api.utils.StringUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            throws APIConnectionException, APIRequestException
    {

        Preconditions.checkArgument(!(null == payload), "payload should not be null");

        return _httpClient.sendPost(_baseUrl + userPath, payload.toString());
    }

    public ResponseWrapper registerAdmins(RegisterInfo payload)
            throws APIConnectionException, APIRequestException
    {

        Preconditions.checkArgument( !(null == payload), "payload should not be null");

        return _httpClient.sendPost(_baseUrl + adminPath, payload.toString());

    }

    public UserInfoResult getUserInfo( String username )
            throws APIConnectionException, APIRequestException
    {

        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(username), "username should not be empty");

        ResponseWrapper response = _httpClient.sendGet(_baseUrl + userPath + "/" + username);
        return UserInfoResult.fromResponse(response, UserInfoResult.class);
    }

    public ResponseWrapper updatePassword( String username, String password )
            throws APIConnectionException, APIRequestException
    {

        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(username), "username should not be empty");
        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(password), "password should not be empty");

        Preconditions.checkArgument( password.getBytes().length >= 4 && password.getBytes().length <=128,
                "The length of password must between 4 and 128 bytes. Input is " + password);

        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("new_password", password);

        return _httpClient.sendPut(_baseUrl + userPath + "/" + username + "/password",
                jsonObj.toString());

    }

    public ResponseWrapper updateUserInfo( String username, UserPayload payload )
            throws APIConnectionException,APIRequestException
    {
        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(username), "username should not be empty");
        Preconditions.checkArgument( !(null == payload), "payload should not be null");

        return _httpClient.sendPut(_baseUrl + userPath + "/" + username, payload.toString());
    }

    public UserListResult getUserList( int start, int count )
            throws APIConnectionException, APIRequestException
    {

        if(start < 0 || count <= 0 || count > 500) {
        	throw new IllegalArgumentException("negative index or count must more than 0 and less than 501");
        }
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + userPath + "?start=" + start + "&count=" + count);
        return UserListResult.fromResponse(response, UserListResult.class);

    }
    
    public UserListResult getAdminListByAppkey(int start, int count)
    		throws APIConnectionException, APIRequestException
    {
    	if(start < 0 || count <= 0 || count > 500) {
        	throw new IllegalArgumentException("negative index or count must more than 0 and less than 501");
        }
    	ResponseWrapper response = _httpClient.sendGet(_baseUrl + adminPath + "?start=" + start + "&count=" + count);
    	return UserListResult.fromResponse(response, UserListResult.class);
    
    }

    public UserGroupsResult getGroupList( String username )
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(username), "username should not be empty");
        Preconditions.checkArgument(!StringUtils.isLineBroken(username), "username must not contain line feed character. ");

        ResponseWrapper response = _httpClient.sendGet(_baseUrl + userPath + "/" + username + "/groups");

        return UserGroupsResult.fromResponse(response);
    }

    public ResponseWrapper deleteUser( String username )
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(username), "username should not be empty");
        Preconditions.checkArgument(!StringUtils.isLineBroken(username), "username must not contain line feed character. ");
        
        return _httpClient.sendDelete(_baseUrl + userPath + "/" + username);
    }

    public ResponseWrapper addBlackList( String username, String... users )
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(username), "username should not be empty");
        Preconditions.checkArgument( null != users && users.length > 0, "black list should not be empty");

        JsonArray array = new JsonArray();
        for (String user : users) {
            array.add(new JsonPrimitive(user));
        }
        return _httpClient.sendPut(_baseUrl + userPath + "/" + username + "/blacklist", array.toString());
    }

    public ResponseWrapper removeBlackList( String username, String... users)
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(username), "username should not be empty");
        Preconditions.checkArgument( null != users && users.length > 0, "black list should not be empty");

        JsonArray array = new JsonArray();
        for (String user : users) {
            array.add(new JsonPrimitive(user));
        }

        return _httpClient.sendDelete(_baseUrl + userPath + "/" + username + "/blacklist", array.toString());
    }

    public ResponseWrapper getBlackList( String username)
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(username), "username should not be empty");

        return _httpClient.sendGet( _baseUrl + userPath + "/" + username + "/blacklist");
    }
}
