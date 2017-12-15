package cn.jmessage.api.common.model.group;


import cn.jmessage.api.common.model.IModel;
import cn.jmessage.api.common.model.Members;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.utils.StringUtils;


public class GroupPayload implements IModel {

    public static final String OWNER = "owner_username";
    public static final String GROUP_NAME = "name";
    public static final String MEMBERS = "members_username";
    public static final String DESC = "desc";
    public static final String AVATAR = "avatar";
    public static final String FLAG = "flag";

    private static Gson gson = new Gson();

    private String owner;
    private String name;
    private Members members;
    private String desc;
    // 上传接口后的 media_id
    private String avatar;
    // 类型，1 为私有群，2 为公开群，默认为 1
    private int flag = 1;

    private GroupPayload(String owner, String name, Members members, String desc, String avatar, int flag) {
        this.owner = owner;
        this.name = name;
        this.members = members;
        this.desc = desc;
        this.avatar = avatar;
        this.flag = flag;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public JsonElement toJSON() {

        JsonObject json = new JsonObject();

        if ( null != owner ) {
            json.addProperty(OWNER, owner);
        }

        if ( null != name ) {
            json.addProperty(GROUP_NAME, name);
        }

        if ( null != members ) {
            json.add(MEMBERS, members.toJSON());
        }

        if ( null != desc ) {
            json.addProperty(DESC, desc);
        }

        if (null != avatar) {
            json.addProperty(AVATAR, avatar);
        }

        if (flag != 1) {
            json.addProperty(FLAG, flag);
        }

        return json;

    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }

    public static class Builder{

        private String owner;
        private String name;
        private Members members;
        private String desc;
        private String avatar;
        private int flag;

        public Builder setOwner(String owner) {
            this.owner = owner.trim();
            return this;
        }

        public Builder setName(String name) {
            this.name = name.trim();
            return this;
        }

        public Builder setMembers(Members members) {
            this.members = members;
            return this;
        }

        public Builder setDesc(String desc) {
            this.desc = desc.trim();
            return this;
        }

        public Builder setAvatar(String mediaId) {
            this.avatar = mediaId;
            return this;
        }

        public Builder setFlag(int flag) {
            Preconditions.checkArgument(flag == 1 || flag == 2, "Flag must be 1 or 2");
            this.flag = flag;
            return this;
        }

        public GroupPayload build() {

            Preconditions.checkArgument(StringUtils.isNotEmpty(owner), "The owner must not be empty.");
            Preconditions.checkArgument(StringUtils.isNotEmpty(name), "The group name must not be empty.");
            Preconditions.checkArgument(!StringUtils.isLineBroken(owner), 
            		"The owner name must not contain line feed character.");
            Preconditions.checkArgument(name.getBytes().length <= 64,
                    "The length of group name must not more than 64 bytes.");
            Preconditions.checkArgument( !StringUtils.isLineBroken(name),
                    "The group name must not contain line feed character.");

            if ( null != desc ) {
                Preconditions.checkArgument( desc.getBytes().length <= 250,
                        "The length of group description must not more than 250 bytes.");
            }

            return new GroupPayload(owner, name, members, desc, avatar, flag);
        }

    }
}
