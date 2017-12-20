package cn.jmessage.api.reportv2;

import cn.jiguang.common.resp.BaseResult;
import cn.jiguang.common.resp.ResponseWrapper;

public class UserStatListResult extends BaseResult {

    private UserStatResult[] array;

    public static UserStatListResult fromResponse(ResponseWrapper responseWrapper) {
        UserStatListResult result = new UserStatListResult();
        if (responseWrapper.isServerResponse()) {
            result.array = _gson.fromJson(responseWrapper.responseContent, UserStatResult[].class);
        }
        result.setResponseWrapper(responseWrapper);
        return result;
    }

    public UserStatResult[] getArray() {
        return array;
    }
}
