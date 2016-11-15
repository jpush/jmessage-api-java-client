package cn.jmessage.api.common.model;

import com.google.gson.*;

public class NoDisturbPayload implements IModel{

    public static String SINGLE = "single";
    public static String ADD = "add";
    public static String REMOVE = "remove";
    public static String GROUP = "group";
    public static String GLOBAL = "global";

    private static Gson gson = new Gson();

    private String[] add_single_users;
    private String[] remove_single_users;
    private Long[] add_group_ids;
    private Long[] remove_group_ids;
    //Global no disturb setting, global value could be 0 or 1, 0 represents close, 1 represents open.
    //Default value is 0.
    private int global = 0;

    private NoDisturbPayload(String[] add_single_users, String[] remove_single_users, Long[] add_group_ids,
                             Long[] remove_group_ids, int global) {
        this.add_single_users = add_single_users;
        this.remove_single_users = remove_single_users;
        this.add_group_ids = add_group_ids;
        this.remove_group_ids = remove_group_ids;
        this.global = global;
    }


    @Override
    public JsonElement toJSON() {

        JsonObject json = new JsonObject();

        if (null != add_single_users) {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            for (String username : add_single_users) {
                jsonArray.add(new JsonPrimitive(username));
            }
            jsonObject.add(ADD, jsonArray);
            if (null != remove_single_users) {
                JsonArray jsonArray1 = new JsonArray();
                for (String username : remove_single_users) {
                    jsonArray1.add(new JsonPrimitive(username));
                }
                jsonObject.add(REMOVE, jsonArray1);
            }
            json.add(SINGLE, jsonObject);
        } else if (null != remove_single_users) {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            for (String username : remove_single_users) {
                jsonArray.add(new JsonPrimitive(username));
            }
            jsonObject.add(REMOVE, jsonArray);
            json.add(SINGLE, jsonObject);
        }

        if (null != add_group_ids) {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            for (Long groupId : add_group_ids) {
                jsonArray.add(new JsonPrimitive(groupId));
            }
            jsonObject.add(ADD, jsonArray);

            if (null != remove_group_ids) {
                JsonArray jsonArray1 = new JsonArray();
                for (Long groupId : remove_group_ids) {
                    jsonArray1.add(new JsonPrimitive(groupId));
                }
                jsonObject.add(REMOVE, jsonArray1);
            }
            json.add(GROUP, jsonObject);
        } else if (null != remove_group_ids) {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            for (Long groupId : remove_group_ids) {
                jsonArray.add(new JsonPrimitive(groupId));
            }
            jsonObject.add(REMOVE, jsonArray);
            json.add(GROUP, jsonObject);
        }

        if (0 != global) {
            json.addProperty(GLOBAL, global);
        }

        return json;
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }

    public static class Builder {
        private String[] add_single_users;
        private String[] remove_single_users;
        private Long[] add_group_ids;
        private Long[] remove_group_ids;
        private int global = 0;

        public Builder setAddSingleUsers(String...add_single_users) {
            this.add_single_users = add_single_users;
            return this;
        }

        public Builder setRemoveSingleUsers(String...remove_single_users) {
            this.remove_single_users = remove_single_users;
            return this;
        }

        public Builder setAddGroupIds(Long...add_group_ids) {
            this.add_group_ids = add_group_ids;
            return this;
        }

        public Builder setRemoveGroupIds(Long...remove_group_ids) {
            this.remove_group_ids = remove_group_ids;
            return this;
        }

        public Builder setGlobal(int global) {
            this.global = global;
            return this;
        }

        public NoDisturbPayload build() {
            return new NoDisturbPayload(add_single_users, remove_single_users, add_group_ids, remove_group_ids, global);
        }
    }
}
