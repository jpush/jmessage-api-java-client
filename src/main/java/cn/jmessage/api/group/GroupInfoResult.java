package cn.jmessage.api.group;

import com.google.gson.annotations.Expose;

import cn.jiguang.common.resp.BaseResult;

public class GroupInfoResult extends BaseResult {

    @Expose private Long gid;
    @Expose private String name;
    @Expose private String desc;
    @Expose private String appkey;
    @Expose private Integer level;
    @Expose private String ctime;
    @Expose private String mtime;
    @Expose private Integer MaxMemberCount;

    public Long getGid() {
        return gid;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getAppkey() {
        return appkey;
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

    public Integer getMaxMemberCount() {
        return MaxMemberCount;
    }
}
