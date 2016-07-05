package cn.jmessage.api.message;

import com.google.gson.annotations.Expose;

import cn.jiguang.common.resp.BaseResult;

public class SendMessageResult extends BaseResult{

    @Expose Long msg_id;

    public Long getMsg_id() {
        return msg_id;
    }
}
