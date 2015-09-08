package cn.jmessage.api.message;

import cn.jpush.api.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class SendMessageResult extends BaseResult{

    @Expose Long msg_id;

    public Long getMsg_id() {
        return msg_id;
    }
}
