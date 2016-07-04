package cn.jmessage.api.user;

import java.util.List;

import com.google.gson.annotations.Expose;

import cn.jiguang.common.resp.BaseResult;

public class UserListResult extends BaseResult {

    @Expose Integer total;
    @Expose Integer start;
    @Expose Integer count;
    @Expose List<UserInfoResult> users;

    public Integer getTotal() {
        return total;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getCount() {
        return count;
    }

    public List<UserInfoResult> getUsers() {
        return users;
    }
}
