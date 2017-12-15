package cn.jmessage.api.common.model.chatroom;

import cn.jmessage.api.common.model.IModel;
import cn.jmessage.api.common.model.Members;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ChatRoomPayload implements IModel {

    public static final String OWNER = "owner_username";
    public static final String NAME = "name";
    public static final String MEMBERS = "members_username";
    public static final String DESC = "description";
    public static final String FLAG = "flag";

    private static Gson gson = new Gson();

    private String owner;
    private String name;
    private Members members;
    private String desc;
    // 禁言标志，0 表示不禁言，1 表示禁言
    private int flag = -1;

    public ChatRoomPayload(String name, String ownerName, Members members, String desc, int flag) {
        this.name = name;
        this.owner = ownerName;
        this.members = members;
        this.desc = desc;
        this.flag = flag;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    @Override
    public JsonElement toJSON() {
        JsonObject jsonObject = new JsonObject();
        if (null != name) {
            jsonObject.addProperty(NAME, name);
        }

        if (null != owner) {
            jsonObject.addProperty(OWNER, owner);
        }

        if ( null != members ) {
            jsonObject.add(MEMBERS, members.toJSON());
        }

        if ( null != desc ) {
            jsonObject.addProperty(DESC, desc);
        }

        if (flag != -1) {
            jsonObject.addProperty(FLAG, flag);
        }

        return jsonObject;
    }

    public static class Builder {
        private String owner;
        private String name;
        private Members members;
        private String desc;
        private int flag = -1;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setOwnerUsername(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder setMembers(Members members) {
            this.members = members;
            return this;
        }

        public Builder setDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder setFlag(int flag) {
            this.flag = flag;
            return this;
        }

        public ChatRoomPayload build() {
            return new ChatRoomPayload(name, owner, members, desc, flag);
        }

    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }
}
