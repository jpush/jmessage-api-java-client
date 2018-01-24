package cn.jmessage.api.common.model.message;

import cn.jmessage.api.common.model.IModel;
import cn.jmessage.api.message.MessageType;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cn.jiguang.common.utils.Preconditions;
import cn.jmessage.api.utils.StringUtils;

/**
 * MessagePayload https://docs.jiguang.cn/jmessage/server/rest_api_im/#_17
 */
public class MessagePayload implements IModel {

    private static final String VERSION = "version";
    private static final String TARGET_TYPE = "target_type";
    private static final String FROM_TYPE = "from_type";
    private static final String MSG_TYPE = "msg_type";
    private static final String TARGET_ID = "target_id";
    private static final String FROM_ID = "from_id";
    private static final String TARGET_APP_KEY = "target_appkey";
    private static final String FROM_NAME = "from_name";
    private static final String TARGET_NAME = "target_name";
    private static final String MSG_BODY = "msg_body";
    private static final String NO_OFFLINE = "no_offline";
    private static final String NO_NOTIFICATION = "no_notification";
    private static final String NOTIFICATION = "notification";


    private static Gson gson = new Gson();

    private Integer mVersion;
    private String mTargetType;
    private String mTargetId;
    private String mFromType;
    private String mFromId;
    private String mTargetAppKey;
    private String mFromName;
    private String mTargetName;
    // 默认为false，表示需要离线存储
    private boolean mNoOffline = false;
    // 默认为false，表示在通知栏展示
    private boolean mNoNotification = false;
    private MessageType mMsgType;
    private MessageBody mMsgBody;
    private Notification mNotification;

    public MessagePayload(Integer version, String targetType, String targetId, String fromType, String fromId,
                          String targetAppKey, String fromName, String targetName, boolean noOffline,
                          boolean noNotification, MessageType msgType, MessageBody msgBody, Notification notification) {
        this.mVersion = version;
        this.mTargetType = targetType;
        this.mTargetId = targetId;
        this.mFromType = fromType;
        this.mFromId = fromId;
        this.mTargetAppKey = targetAppKey;
        this.mFromName = fromName;
        this.mTargetName = targetName;
        this.mNoOffline = noOffline;
        this.mNoNotification = noNotification;
        this.mMsgType = msgType;
        this.mMsgBody = msgBody;
        this.mNotification = notification;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();

        if (null != mVersion) {
            json.addProperty(VERSION, mVersion);
        }
        if (null != mTargetType) {
            json.addProperty(TARGET_TYPE, mTargetType);
        }
        if (null != mTargetId) {
            json.addProperty(TARGET_ID, mTargetId);
        }
        if (null != mFromType) {
            json.addProperty(FROM_TYPE, mFromType);
        }
        if (null != mFromId) {
            json.addProperty(FROM_ID, mFromId);
        }
        if (null != mTargetAppKey) {
            json.addProperty(TARGET_APP_KEY, mTargetAppKey);
        }
        if (null != mFromName) {
            json.addProperty(FROM_NAME, mFromName);
        }
        if (null != mTargetName) {
            json.addProperty(TARGET_NAME, mTargetName);
        }
        if (mNoOffline) {
            json.addProperty(NO_OFFLINE, mNoOffline);
        }
        if (mNoNotification) {
            json.addProperty(NO_NOTIFICATION, mNoNotification);
        }
        if (null != mMsgType) {
            json.addProperty(MSG_TYPE, mMsgType.getValue());
        }
        if (null != mMsgBody) {
            json.add(MSG_BODY, mMsgBody.toJSON());
        }

        if (null != mNotification) {
            json.add(NOTIFICATION, mNotification.toJSON());
        }

        return json;
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }

    public static class Builder {
        private Integer mVersion;
        private String mTargetType;
        private String mTargetId;
        private String mFromType;
        private String mFromId;
        private String mTargetAppKey;
        private String mFromName;
        private String mTargetName;
        // 默认为false，表示需要离线存储
        private boolean mNoOffline = false;
        // 默认为false，表示在通知栏展示
        private boolean mNoNotification = false;
        private MessageType mMsgType;
        private MessageBody mMsgBody;
        private Notification mNotification;

        public Builder setVersion(Integer version) {
            this.mVersion = version;
            return this;
        }

        public Builder setTargetType(String targetType) {
            this.mTargetType = targetType.trim();
            return this;
        }

        public Builder setTargetId(String targetId) {
            this.mTargetId = targetId.trim();
            return this;
        }

        public Builder setFromType(String fromType) {
            this.mFromType = fromType.trim();
            return this;
        }

        public Builder setFromId(String fromId) {
            this.mFromId = fromId.trim();
            return this;
        }
        
        public Builder setTargetAppKey(String appKey) {
            this.mTargetAppKey = appKey;
            return this;
        }
        
        public Builder setFromName(String name) {
            this.mFromName = name;
            return this;
        }
        
        public Builder setTargetName(String name) {
            this.mTargetName = name;
            return this;
        }
        
        public Builder setNoOffline(boolean noOffline) {
            this.mNoOffline = noOffline;
            return this;
        }
        
        public Builder setNoNotification(boolean noNotification) {
            this.mNoNotification = noNotification;
            return this;
        }

        public Builder setMessageType(MessageType msgType) {
            this.mMsgType = msgType;
            return this;
        }

        public Builder setMessageBody(MessageBody msgBody) {
            this.mMsgBody = msgBody;
            return this;
        }

        public Builder setNotification(Notification notification) {
            this.mNotification = notification;
            return this;
        }

        public MessagePayload build() {
            Preconditions.checkArgument(null != mVersion, "The version must not be empty!");
            Preconditions.checkArgument(StringUtils.isNotEmpty(mTargetType), "The target type must not be empty!");
            StringUtils.checkUsername(mTargetId);
            Preconditions.checkArgument(StringUtils.isNotEmpty(mFromType), "The from type must not be empty!");
            StringUtils.checkUsername(mFromId);
            Preconditions.checkArgument(mMsgType != null, "The message type must not be empty!");
            Preconditions.checkArgument(null != mMsgBody, "The message body must not be empty!");

            return new MessagePayload(mVersion, mTargetType, mTargetId, mFromType, mFromId, mTargetAppKey, mFromName,
                    mTargetName, mNoOffline, mNoNotification, mMsgType, mMsgBody, mNotification);
        }
    }
}
