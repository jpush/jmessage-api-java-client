package cn.jmessage.api.common.model;

import cn.jiguang.common.utils.Preconditions;
import com.google.gson.*;

public class CrossFriendPayload implements IModel {

    private final static String APP_KEY = "appkey";
    private final static String USERS = "users";
    private Gson gson = new Gson();
    private String appKey;
    private String[] users;

    public CrossFriendPayload(String appKey, String[] users) {
        this.appKey = appKey;
        this.users = users;
    }

    public CrossFriendPayload newBuilder() {
        return new Builder().build();
    }

    public static class Builder {
        private String appKey;
        private String[] users;

        public Builder setAppKey(String appKey) {
            this.appKey = appKey;
            return this;
        }

        public Builder setUsers(String...users) {
            this.users = users;
            return this;
        }

        public CrossFriendPayload build() {
            Preconditions.checkArgument(null != appKey, "AppKey should not be null!");
            Preconditions.checkArgument(null != users, "Users should not be null");
            return new CrossFriendPayload(appKey, users);
        }
    }


    @Override
    public JsonElement toJSON() {
        JsonObject jsonObject = new JsonObject();

        if (null != appKey) {
            jsonObject.addProperty(APP_KEY, appKey);
        }

        if (null != users) {
            JsonArray array = new JsonArray();
            for (String user : users) {
                array.add(new JsonPrimitive(user));
            }
            jsonObject.add(USERS, array);
        }

        return jsonObject;
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }
}
