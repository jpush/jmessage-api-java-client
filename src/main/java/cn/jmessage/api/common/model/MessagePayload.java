package cn.jmessage.api.common.model;

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
    private static final String MESSAGE_TYPE = "msg_type";
    private static final String TARGET_ID = "target_id";
    private static final String FROM_ID = "from_id";
    private static final String MSG_BODY = "msg_body";
    private static final String MSG_BODY_TEXT = "text";
    private static final String MSG_BODY_EXTRAS = "extras";

    private static Gson gson = new Gson();

    private int version = 1;
    private String target_type;
    private String target_id;
    private String from_type;
    private String from_id;
    private String message_type;


    //todo finish payload build

    @Override
    public JsonElement toJSON() {
        return null;
    }
}
