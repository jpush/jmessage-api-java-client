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
    @Expose String ctime;
    @Expose String mtime;
    @Expose String appkey;
    @Expose String avatar;
    @Expose Integer MaxMemberCount;

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

    public Integer getMaxMemberCount() {
        return MaxMemberCount;
    }

    public String getAppkey() {
        return  appkey;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCtime() {
        return ctime;
    }

    public String getMtime() {
        return mtime;
    }
}
