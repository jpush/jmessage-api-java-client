package cn.jmessage.api.chatroom;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class ChatRoomResult extends BaseResult {

    @Expose private Long id;
    @Expose private String owner_username;
    @Expose private String appkey;
    @Expose private String max_member_count;
    @Expose private String name;
    @Expose private String description;
    @Expose private Integer total_member_count;
    @Expose private String ctime;

    @Override
    public String toString() {
        return "room id: " + id + ", name: " + name +", owner username: " + owner_username + ", appKey: " + appkey
                + ", max member count: " + max_member_count + ", description: " + description
                + ", total member count: " + total_member_count;
    }

    public Long getId() {
        return this.id;
    }

    public String getOwnerUsername() {
        return owner_username;
    }

    public String getAppkey() {
        return appkey;
    }

    public String getMaxMemberCount() {
        return max_member_count;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getTotalMemberCount() {
        return total_member_count;
    }

    public String getCtime() {
        return ctime;
    }
}
