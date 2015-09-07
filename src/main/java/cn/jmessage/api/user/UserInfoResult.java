package cn.jmessage.api.user;

import cn.jpush.api.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class UserInfoResult extends BaseResult {

    @Expose String username;
    @Expose String nickname;
    @Expose String avatar;
    @Expose String birthday;
    @Expose Integer gender;
    @Expose String signature;
    @Expose String region;
    @Expose String address;
    @Expose String ctime;
    @Expose String mtime;

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

    public String getCtime() {
        return ctime;
    }

    public String getMtime() {
        return mtime;
    }
}
