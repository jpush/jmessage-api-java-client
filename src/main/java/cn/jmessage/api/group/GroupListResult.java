package cn.jmessage.api.group;

import java.util.List;

import com.google.gson.annotations.Expose;

import cn.jiguang.common.resp.BaseResult;

public class GroupListResult extends BaseResult {

    @Expose Integer total;
    @Expose Integer start;
    @Expose Integer count;
    @Expose List<GroupInfoResult> groups;

    public Integer getTotal() {
        return total;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getCount() {
        return count;
    }

    public List<GroupInfoResult> getGroups() {
        return groups;
    }
}
