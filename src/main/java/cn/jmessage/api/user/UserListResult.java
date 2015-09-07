package cn.jmessage.api.user;

import cn.jpush.api.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

import java.util.List;

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
