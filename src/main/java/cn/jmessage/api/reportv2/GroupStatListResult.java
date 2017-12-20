package cn.jmessage.api.reportv2;

import cn.jiguang.common.resp.BaseResult;
import cn.jiguang.common.resp.ResponseWrapper;
import com.google.gson.annotations.Expose;

public class GroupStatListResult extends BaseResult {

    @Expose private GroupStatResult[] array;

    public static GroupStatListResult fromResponse(ResponseWrapper responseWrapper) {
        GroupStatListResult result = new GroupStatListResult();
        if (responseWrapper.isServerResponse()) {
            result.array = _gson.fromJson(responseWrapper.responseContent, GroupStatResult[].class);
        }
        result.setResponseWrapper(responseWrapper);
        return result;
    }

    public GroupStatResult[] getArray() {
        return array;
    }

    public class GroupStatResult {
        @Expose private String date;
        @Expose private Integer active_group;
        @Expose private Integer total_group;
        @Expose private Integer new_group;

        public String getDate() {
            return date;
        }

        public Integer getActive_group() {
            return active_group;
        }

        public Integer getTotal_group() {
            return total_group;
        }

        public Integer getNew_group() {
            return new_group;
        }
    }

}
