package cn.jmessage.api.CrossApp;

import cn.jiguang.commom.utils.Preconditions;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.common.BaseClient;
import cn.jmessage.api.common.JMessageConfig;
import cn.jmessage.api.common.model.*;
import cn.jmessage.api.group.MemberListResult;
import cn.jmessage.api.user.UserInfoResult;
import cn.jmessage.api.utils.StringUtils;


public class CrossAppClient extends BaseClient {

    private String crossUserPath;
    private String crossGroupPath;

    public CrossAppClient(String appkey, String masterSecret) {
        this(appkey, masterSecret, null, JMessageConfig.getInstance());
    }

    public CrossAppClient(String appkey, String masterSecret, HttpProxy proxy) {
        this(appkey, masterSecret, proxy, JMessageConfig.getInstance());
    }

    /**
     * Create a JMessage Base Client
     *
     * @param appkey       The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param proxy        The proxy, if there is no proxy, should be null.
     * @param config       The client configuration. Can use JMessageConfig.getInstance() as default.
     */
    public CrossAppClient(String appkey, String masterSecret, HttpProxy proxy, JMessageConfig config) {
        super(appkey, masterSecret, proxy, config);
        this.crossUserPath = (String) config.get(JMessageConfig.CROSS_USER_PATH);
        this.crossGroupPath = (String) config.get(JMessageConfig.CROSS_GROUP_PATH);
    }

    /**
     * Add or remove group members from a given group id.
     * @param gid Necessary, target group id.
     * @param groups Necessary
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ResponseWrapper addOrRemoveCrossGroupMembers(long gid, CrossGroup[] groups)
            throws APIConnectionException, APIRequestException {
        CrossGroupPayload payload = new CrossGroupPayload.Builder()
                .setCrossGroups(groups)
                .build();
        Preconditions.checkArgument(0 != gid, "gid should not be empty");
        Preconditions.checkArgument(null != payload, "CrossGroup must not be null");
        return _httpClient.sendPost(_baseUrl + crossGroupPath + "/" + gid + "/members", payload.toString());
    }

    /**
     * Get members' info from cross group
     * @param gid Necessary, target group id
     * @return Member info array
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public MemberListResult getCrossGroupMembers(long gid)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(0 != gid, "gid must not be empty");
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + crossGroupPath + "/" + gid + "/members/");
        return MemberListResult.fromResponse(response);
    }

    /**
     * Add blacklist whose users belong to another app to a given user.
     * @param username The owner of the blacklist
     * @param blacklists CrossBlacklist array
     * @return No Content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ResponseWrapper addCrossBlacklist(String username, CrossBlacklist[] blacklists)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        CrossBlacklistPayload payload = new CrossBlacklistPayload.Builder()
                .setCrossBlacklists(blacklists)
                .build();
        return _httpClient.sendPut(_baseUrl + crossUserPath + "/" + username +"/blacklist", payload.toString());
    }

    /**
     * Delete blacklist whose users belong to another app from a given user.
     * @param username The owner of the blacklist
     * @param blacklists CrossBlacklist array
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ResponseWrapper deleteCrossBlacklist(String username, CrossBlacklist[] blacklists)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        CrossBlacklistPayload payload = new CrossBlacklistPayload.Builder()
                .setCrossBlacklists(blacklists)
                .build();
        return _httpClient.sendDelete(_baseUrl + crossUserPath + "/" + username + "/blacklist", payload.toString());
    }

    /**
     * Get all user's info(contains appkey) of user's cross app blacklist
     * @param username Necessary, the owner of blacklist
     * @return UserInfo array
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public UserInfoResult[] getCrossBlacklist(String username)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        ResponseWrapper response = _httpClient.sendGet(_baseUrl + crossUserPath + "/" + username + "/blacklist");
        return _gson.fromJson(response.responseContent, UserInfoResult[].class);
    }

    /**
     * Set cross app no disturb
     * @link https://docs.jiguang.cn/jmessage/server/rest_api_im/#api_1
     * @param username Necessary
     * @param array CrossNoDisturb array
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ResponseWrapper setCrossNoDisturb(String username, CrossNoDisturb[] array)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        CrossNoDisturbPayload payload = new CrossNoDisturbPayload.Builder()
                .setCrossNoDistrub(array)
                .build();
        return _httpClient.sendPost(_baseUrl + crossUserPath + "/" + username + "/nodisturb", payload.toString());
    }


}
