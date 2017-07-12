package cn.jmessage.api.common.model.cross;

import cn.jiguang.common.utils.Preconditions;
import cn.jmessage.api.common.model.IModel;
import com.google.gson.*;

public class CrossGroup implements IModel {

    private static final String APP_KEY = "appkey";
    private static final String ADD = "add";
    private static final String REMOVE = "remove";

    private Gson gson = new Gson();

    private String appKey;
    private String[] add_users;
    private String[] remove_users;

    private CrossGroup(String appKey, String[] add_users, String[] remove_users) {
        this.appKey = appKey;
        this.add_users = add_users;
        this.remove_users = remove_users;
    }

    public CrossGroup newBuilder() {
        return new Builder().build();
    }

    public static class Builder {
        private String appKey;
        private String[] add_users;
        private String[] remove_users;

        public Builder setAppKey(String appKey) {
            this.appKey = appKey;
            return this;
        }

        public Builder setAddUsers(String...users) {
            this.add_users = users;
            return this;
        }

        public Builder setRemoveUsers(String...users) {
            this.remove_users = users;
            return this;
        }

        public CrossGroup build() {
            Preconditions.checkArgument(null != appKey, "AppKey must not be null");
            if (null == add_users && null == remove_users) {
                throw new IllegalArgumentException("At least one of add array or remove array should not be null");
            }
            return new CrossGroup(appKey, add_users, remove_users);
        }


    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();

        if (null != appKey) {
            json.addProperty(APP_KEY, appKey);
        }

        if (null != add_users) {
            JsonArray array = new JsonArray();
            for (String user : add_users) {
                array.add(new JsonPrimitive(user));
            }
            json.add(ADD, array);
        }

        if (null != remove_users) {
            JsonArray array = new JsonArray();
            for (String user : remove_users) {
                array.add(new JsonPrimitive(user));
            }
            json.add(REMOVE, array);
        }

        return json;
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }
}
