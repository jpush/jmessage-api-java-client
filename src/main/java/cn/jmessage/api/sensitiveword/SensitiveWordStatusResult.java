package cn.jmessage.api.sensitiveword;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class SensitiveWordStatusResult extends BaseResult {

    @Expose Integer status;

    public Integer getStatus() {
        return status;
    }
}
