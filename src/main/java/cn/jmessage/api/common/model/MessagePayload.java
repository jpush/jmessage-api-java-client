package cn.jmessage.api.common.model;

import cn.jpush.api.utils.Preconditions;
import cn.jpush.api.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * MessagePayload
 * Created by tangyikai on 15/9/7.
 */
public class MessagePayload implements IModel {

    private static final String VERSION = "version";
    private static final String TARGET_TYPE = "target_type";
    private static final String FROM_TYPE = "from_type";
    private static final String MSG_TYPE = "msg_type";
    private static final String TARGET_ID = "target_id";
    private static final String FROM_ID = "from_id";
    private static final String MSG_BODY = "msg_body";


    private static Gson gson = new Gson();

    private Integer version;
    private String target_type;
    private String target_id;
    private String from_type;
    private String from_id;
    private String msg_type;
    private MessageBody msg_body;

    public MessagePayload(Integer version, String target_type, String target_id,
                          String from_type, String from_id, String msg_type,
                          MessageBody msg_body) {
        this.version = version;
        this.target_type = target_type;
        this.target_id = target_id;
        this.from_type = from_type;
        this.from_id = from_id;
        this.msg_type = msg_type;
        this.msg_body = msg_body;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();

        if (null != version) {
            json.addProperty(VERSION, version);
        }
        if (null != target_type) {
            json.addProperty(TARGET_TYPE, target_type);
        }
        if (null != target_id) {
            json.addProperty(TARGET_ID, target_id);
        }
        if (null != from_type) {
            json.addProperty(FROM_TYPE, from_type);
        }
        if (null != from_id) {
            json.addProperty(FROM_ID, from_id);
        }
        if (null != msg_type) {
            json.addProperty(MSG_TYPE, msg_type);
        }
        if (null != msg_body) {
            json.add(MSG_BODY, msg_body.toJSON());
        }

        return json;
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }

    public static class Builder {
        private Integer version;
        private String target_type;
        private String target_id;
        private String from_type;
        private String from_id;
        private String msg_type;
        private MessageBody msg_body;

        public Builder setVersion(Integer version) {
            this.version = version;
            return this;
        }

        public Builder setTargetType(String target_type) {
            this.target_type = target_type.trim();
            return this;
        }

        public Builder setTargetId(String target_id) {
            this.target_id = target_id.trim();
            return this;
        }

        public Builder setFromType(String from_type) {
            this.from_type = from_type.trim();
            return this;
        }

        public Builder setFromId(String from_id) {
            this.from_id = from_id.trim();
            return this;
        }

        public Builder setMessageType(String msg_type) {
            this.msg_type = msg_type.trim();
            return this;
        }

        public Builder setMessageBody(MessageBody msg_body) {
            this.msg_body = msg_body;
            return this;
        }

        public MessagePayload build() {
            Preconditions.checkArgument(null != version, "The version must not be empty!");
            Preconditions.checkArgument(StringUtils.isNotEmpty(target_type), "The target type must not be empty!");
            Preconditions.checkArgument(cn.jmessage.api.utils.StringUtils.isUsernameValid(target_id), "The target ID is illegal, please check again");
            Preconditions.checkArgument(StringUtils.isNotEmpty(from_type), "The from type must not be empty!");
            Preconditions.checkArgument(cn.jmessage.api.utils.StringUtils.isUsernameValid(from_id), "The from ID is illegal, please check again");
            Preconditions.checkArgument(StringUtils.isNotEmpty(msg_type), "The message type must not be empty!");
            Preconditions.checkArgument(null != msg_body, "The message body must not be empty!");

            return new MessagePayload(version, target_type, target_id, from_type, from_id, msg_type, msg_body);
        }
    }
}
