package cn.jmessage.api.user;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class UserStateListResult extends BaseResult {

    @Expose List<Device> devices = new ArrayList<Device>();
    @Expose String username;

    public String getUsername() {
        return this.username;
    }

    private class Device {
        @Expose boolean login;
        @Expose boolean online;
        @Expose String platform;

        public boolean getLogin() {
            return this.login;
        }

        public boolean getOnline() {
            return this.online;
        }

        public String getPlatform() {
            return this.platform;
        }
    }
}
