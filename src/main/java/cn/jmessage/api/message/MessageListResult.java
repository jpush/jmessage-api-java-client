package cn.jmessage.api.message;

import cn.jiguang.common.resp.BaseResult;
import cn.jiguang.common.resp.ResponseWrapper;
import com.google.gson.annotations.Expose;

import java.util.List;

public class MessageListResult extends BaseResult {

    @Expose Integer total;
    @Expose String cursor;
    @Expose Integer count;
    @Expose MessageResult[] messages;

    public static MessageListResult fromResponse(ResponseWrapper responseWrapper) {
        MessageListResult result = new MessageListResult();
        if (responseWrapper.isServerResponse()) {
            result.messages = _gson.fromJson(responseWrapper.responseContent, MessageResult[].class);
        }
        result.setResponseWrapper(responseWrapper);
        return result;
    }


    public Integer getTotal() {
        return total;
    }

    public String getCursor() {
        return cursor;
    }

    public Integer getCount() {
        return count;
    }

    public MessageResult[] getMessages() {
        return messages;
    }

}
