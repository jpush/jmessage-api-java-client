package cn.jmessage.api.message;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

import java.util.HashMap;

public class MessageBody extends BaseResult {

    @Expose String text;
    @Expose HashMap<String, String> extras;

    public String getText() {
        return text;
    }

    public HashMap<String, String> getExtras() {
        return extras;
    }
}
