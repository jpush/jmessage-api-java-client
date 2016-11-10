package cn.jmessage.api.group;

import com.google.gson.annotations.Expose;

import cn.jiguang.common.resp.BaseResult;

public class MemberResult extends BaseResult {

    @Expose String username;
    @Expose String nickname;
    @Expose String avatar;
    @Expose String birthday;
    @Expose Integer gender;
    @Expose String signature;
    @Expose String region;
    @Expose String address;
    @Expose Integer flag;
    @Expose String appkey;

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public Integer getGender() {
        return gender;
    }

    public String getSignature() {
        return signature;
    }

    public String getRegion() {
        return region;
    }

    public String getAddress() {
        return address;
    }

    public Integer getFlag() {
        return flag;
    }

    public String getAppkey() {
        return appkey;
    }
}
