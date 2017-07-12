package cn.jmessage.api.common.model.friend;

import cn.jiguang.common.utils.Preconditions;
import cn.jmessage.api.common.model.IModel;
import cn.jmessage.api.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;

public class FriendNote implements IModel {

    private static final String NOTE_NAME = "note_name";
    private static final String OTHERS = "others";
    private static final String USERNAME = "username";

    private String note_name;
    private String others;
    private String username;
    private Gson gson = new Gson();

    private FriendNote(String note_name, String others, String username) {
        this.note_name = note_name;
        this.others = others;
        this.username = username;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String note_name;
        private String others;
        private String username;

        public Builder setNoteName(String noteName) {
            this.note_name = noteName;
            return this;
        }

        public Builder setOthers(String others) {
            this.others = others;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public FriendNote builder() {
            StringUtils.checkUsername(username);
            try {
                Preconditions.checkArgument(note_name.trim().getBytes("UTF-8").length <= 250, "length of note name should less than 250");
                Preconditions.checkArgument(others.getBytes("UTF-8").length <= 250, "length of others should not larger than 250");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return new FriendNote(note_name, others, username);
        }
    }
    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if (null != note_name) {
            json.addProperty(NOTE_NAME, note_name);
        }

        if (null != others) {
            json.addProperty(OTHERS, others);
        }

        if (null != username) {
            json.addProperty(USERNAME, username);
        }
        return json;
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }
}
