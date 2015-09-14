package cn.jmessage.api.user;

import cn.jmessage.api.group.GroupInfoResult;
import cn.jpush.api.common.resp.BaseResult;
import cn.jpush.api.common.resp.ResponseWrapper;

public class UserGroupsResult extends BaseResult{

    private GroupInfoResult[] groups = null;

    public static UserGroupsResult fromResponse(ResponseWrapper responseWrapper) {
        UserGroupsResult  result = new UserGroupsResult();
        if (responseWrapper.isServerResponse()) {
            result.groups = _gson.fromJson(responseWrapper.responseContent, GroupInfoResult[].class);
        } else {
            // nothing
        }
        result.setResponseWrapper(responseWrapper);
        return result;
    }

    public GroupInfoResult[] getGroups() {
        return groups;
    }
}
