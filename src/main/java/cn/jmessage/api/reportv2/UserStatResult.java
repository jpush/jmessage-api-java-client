package cn.jmessage.api.reportv2;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class UserStatResult extends BaseResult {

    @Expose private Long active_users;
    @Expose private Long total_users;
    @Expose private Long send_msg_users;
    @Expose private Long new_users;
    @Expose private String date;

    public Long getActive_users() {
        return active_users;
    }

    public Long getTotal_users() {
        return total_users;
    }

    public Long getSend_msg_users() {
        return send_msg_users;
    }

    public Long getNew_users() {
        return new_users;
    }

    public String getDate() {
        return date;
    }
}
