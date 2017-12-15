package cn.jmessage.api.chatroom;

import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jiguang.common.utils.Preconditions;
import cn.jmessage.api.common.BaseClient;
import cn.jmessage.api.common.JMessageConfig;
import cn.jmessage.api.common.model.Members;
import cn.jmessage.api.common.model.chatroom.ChatRoomPayload;
import cn.jmessage.api.user.UserListResult;
import cn.jmessage.api.utils.StringUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ChatRoomClient extends BaseClient {

    private String mChatRoomPath;
    private String mUserPath;

    public ChatRoomClient(String appKey, String masterSecret) {
        this(appKey, masterSecret, null, JMessageConfig.getInstance());
    }

    public ChatRoomClient(String appKey, String masterSecret, HttpProxy proxy) {
        this(appKey, masterSecret, proxy, JMessageConfig.getInstance());
    }

    /**
     * Create a JMessage Base Client
     *
     * @param appKey       The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param proxy        The proxy, if there is no proxy, should be null.
     * @param config       The client configuration. Can use JMessageConfig.getInstance() as default.
     */
    public ChatRoomClient(String appKey, String masterSecret, HttpProxy proxy, JMessageConfig config) {
        super(appKey, masterSecret, proxy, config);
        mChatRoomPath = (String) config.get(JMessageConfig.CHAT_ROOM_PATH);
        mUserPath = (String) config.get(JMessageConfig.USER_PATH);
    }

    /**
     * Create chat room
     *
     * @param payload {@link ChatRoomPayload}
     * @return result contains room id
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public CreateChatRoomResult createChatRoom(ChatRoomPayload payload) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != payload, "ChatRoomPayload should not be null");
        ResponseWrapper responseWrapper = _httpClient.sendPost(_baseUrl + mChatRoomPath, payload.toString());
        return CreateChatRoomResult.fromResponse(responseWrapper, CreateChatRoomResult.class);
    }

    /**
     * Get chat room information by room ids
     *
     * @param roomIds Array of room id
     * @return {@link ChatRoomListResult}
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ChatRoomListResult getBatchChatRoomInfo(long... roomIds) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(roomIds != null && roomIds.length > 0, "Room ids should not be null");
        JsonArray array = new JsonArray();
        for (long id : roomIds) {
            array.add(new JsonPrimitive(id));
        }
        ResponseWrapper responseWrapper = _httpClient.sendPost(_baseUrl + mChatRoomPath + "/batch", array.toString());
        return ChatRoomListResult.fromResponse(responseWrapper);
    }

    /**
     * Get user's whole chat room information
     *
     * @param username required
     * @return {@link ChatRoomListResult}
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ChatRoomListResult getUserChatRoomInfo(String username) throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        ResponseWrapper responseWrapper = _httpClient.sendGet(_baseUrl + mUserPath + "/" + username + "/chatroom");
        return ChatRoomListResult.fromResponse(responseWrapper);
    }

    /**
     * Get application's chat room list
     *
     * @param start start index
     * @param count list count
     * @return {@link ChatRoomListResult}
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ChatRoomListResult getAppChatRoomInfo(int start, int count)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(start >= 0 && count > 0, "Illegal argument");
        ResponseWrapper responseWrapper = _httpClient.sendGet(_baseUrl + mChatRoomPath + "?start=" + start + "&count=" + count);
        return ChatRoomListResult.fromResponse(responseWrapper, ChatRoomListResult.class);
    }

    /**
     * Update chat room info
     *
     * @param roomId        room id
     * @param ownerUsername owner username
     * @param name          new chat room name
     * @param desc          chat room description
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ResponseWrapper updateChatRoomInfo(long roomId, String ownerUsername, String name, String desc)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(roomId > 0, "room id is invalid");
        StringUtils.checkUsername(ownerUsername);
        Preconditions.checkArgument(null != name, "Chat room name is null");
        Preconditions.checkArgument(null != desc, "Description is null");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ChatRoomPayload.OWNER, ownerUsername);
        jsonObject.addProperty(ChatRoomPayload.NAME, name);
        jsonObject.addProperty(ChatRoomPayload.DESC, desc);
        return _httpClient.sendPut(_baseUrl + mChatRoomPath + "/" + roomId, _gson.toJson(jsonObject));
    }

    /**
     * Delete chat room by id
     *
     * @param roomId room id
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ResponseWrapper deleteChatRoom(long roomId) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(roomId > 0, "room id is invalid");
        return _httpClient.sendDelete(_baseUrl + mChatRoomPath + "/" + roomId);
    }

    /**
     * Update user speak status
     *
     * @param roomId   chat room id
     * @param username user to be modified
     * @param flag     0 means allow to speak, 1 means forbid to speak in chat room
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ResponseWrapper updateUserSpeakStatus(long roomId, String username, int flag)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(roomId > 0, "room id is invalid");
        StringUtils.checkUsername(username);
        Preconditions.checkArgument(flag == 0 || flag == 1, "flag should be 0 or 1");
        return _httpClient.sendPut(_baseUrl + mChatRoomPath + "/" + roomId + "/forbidden/" + username + "?status=" + flag, null);
    }

    /**
     * Get all member info of chat room
     *
     * @param roomId chat room id
     * @param start  start index
     * @param count member count
     * @return {@link ChatRoomMemberList}
     */
    public ChatRoomMemberList getChatRoomMembers(long roomId, int start, int count)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(roomId > 0, "room id is invalid");
        Preconditions.checkArgument(start >= 0 && count > 0, "Illegal argument");
        ResponseWrapper responseWrapper = _httpClient.sendGet(_baseUrl + mChatRoomPath + "/" + roomId + "/members"
                + "?start=" + start + "&count=" + count);
        return ChatRoomMemberList.fromResponse(responseWrapper, ChatRoomMemberList.class);
    }

    /**
     * Add members to chat room
     *
     * @param roomId  chat room id
     * @param members username array
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ResponseWrapper addChatRoomMember(long roomId, String... members)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(roomId > 0, "room id is invalid");
        Preconditions.checkArgument(members != null && members.length > 0, "member should not be empty");
        JsonArray array = new JsonArray();
        for (String username : members) {
            array.add(new JsonPrimitive(username));
        }
        return _httpClient.sendPut(_baseUrl + mChatRoomPath + "/" + roomId + "/members", array.toString());
    }

    /**
     * Add members to chat room
     *
     * @param roomId  chat room id
     * @param members {@link Members}
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ResponseWrapper addChatRoomMember(long roomId, Members members)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(roomId > 0, "room id is invalid");
        Preconditions.checkArgument(members != null, "members should not be empty");
        return _httpClient.sendPut(_baseUrl + mChatRoomPath + "/" + roomId + "/members", members.toString());
    }

    /**
     * remove members from chat room
     *
     * @param roomId  chat room id
     * @param members username array
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ResponseWrapper removeChatRoomMembers(long roomId, String... members)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(roomId > 0, "room id is invalid");
        Preconditions.checkArgument(members != null && members.length > 0, "member should not be empty");
        JsonArray array = new JsonArray();
        for (String username : members) {
            array.add(new JsonPrimitive(username));
        }
        return _httpClient.sendDelete(_baseUrl + mChatRoomPath + "/" + roomId + "/members", array.toString());
    }

    /**
     * remove members from chat room
     *
     * @param roomId  chat room id
     * @param members {@link Members}
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException    request exception
     */
    public ResponseWrapper removeChatRoomMembers(long roomId, Members members)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(roomId > 0, "room id is invalid");
        Preconditions.checkArgument(members != null, "members should not be empty");
        return _httpClient.sendDelete(_baseUrl + mChatRoomPath + "/" + roomId + "/members", members.toString());
    }
}
