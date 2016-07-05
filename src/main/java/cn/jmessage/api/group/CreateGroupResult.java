package cn.jmessage.api.group;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;

import cn.jiguang.common.resp.BaseResult;

public class CreateGroupResult extends BaseResult {

    @Expose Long gid;
    @Expose String owner_username;
    @Expose String name;
    @Expose JsonArray members_username;
    @Expose String desc;
    @Expose Integer level;
    @Expose String ctime;
    @Expose String mtime;

    public Long getGid() {
        return gid;
    }

    public String getOwner_username() {
        return owner_username;
    }

    public String getName() {
        return name;
    }

    public JsonArray getMembers_username() {
        return members_username;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getLevel() {
        return level;
    }

    public String getCtime() {
        return ctime;
    }

    public String getMtime() {
        return mtime;
    }
}
