package cn.jmessage.api.common.model;

import cn.jiguang.common.utils.Preconditions;
import com.google.gson.*;

public class CrossBlacklist implements IModel {

    private static String APP_KEY = "appkey";
    private static String USERNAMES = "usernames";

    private Gson gson = new Gson();

    private String appKey;
    private String[] users;

    private CrossBlacklist(String appKey, String[] users) {
        this.appKey = appKey;
        this.users = users;
    }

    public Builder newBuilder() {
        return new CrossBlacklist.Builder();
    }

    public static class Builder {
        private String appKey;
        private String[] users;

        public Builder setAppKey(String appKey) {
            this.appKey = appKey;
            return this;
        }

        public Builder addUsers(String...users) {
            this.users = users;
            return this;
        }

        public CrossBlacklist build() {
            Preconditions.checkArgument(null != appKey, "AppKey must not be null");
            Preconditions.checkArgument(null != users, "At least add one user");
            return new CrossBlacklist(appKey, users);
        }
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();

        if (null != appKey) {
            json.addProperty(APP_KEY, appKey);
        }

        if (null != users) {
            JsonArray array = new JsonArray();
            for (String user : users) {
                array.add(new JsonPrimitive(user));
            }
            json.add(USERNAMES, array);
        }

        return json;
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }
}
