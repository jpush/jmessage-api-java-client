package cn.jmessage.api.group;

import cn.jpush.api.common.resp.BaseResult;
import cn.jpush.api.common.resp.ResponseWrapper;

public class MemberListResult extends BaseResult {

    private MemberResult[] members;

    public static MemberListResult fromResponse(ResponseWrapper responseWrapper) {
        MemberListResult  result = new MemberListResult();
        if (responseWrapper.isServerResponse()) {
            result.members = _gson.fromJson(responseWrapper.responseContent, MemberResult[].class);
        } else {
            // nothing
        }
        result.setResponseWrapper(responseWrapper);
        return result;
    }

    public MemberResult[] getMembers() {
        return members;
    }
}
