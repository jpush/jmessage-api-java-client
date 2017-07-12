package cn.jmessage.api.message;

import com.google.gson.annotations.Expose;

import cn.jiguang.common.resp.BaseResult;

public class SendMessageResult extends BaseResult{

    @Expose Long msg_id;
    @Expose Long msg_ctime;

    public Long getMsg_id() {
        return msg_id;
    }

    public Long getMsgCtime() {
        return msg_ctime;
    }
}
