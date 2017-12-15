package cn.jmessage.api.chatroom;


import cn.jiguang.common.resp.BaseResult;
import cn.jiguang.common.resp.ResponseWrapper;
import com.google.gson.annotations.Expose;

public class ChatRoomMemberList extends BaseResult {

    @Expose private ChatRoomMember[] users;
    @Expose private Integer total;
    @Expose private Integer start;
    @Expose private Integer count;

    public static ChatRoomMemberList fromResponse(ResponseWrapper responseWrapper) {
        ChatRoomMemberList  result = new ChatRoomMemberList();
        if (responseWrapper.isServerResponse()) {
            result.users = _gson.fromJson(responseWrapper.responseContent, ChatRoomMember[].class);
        } else {
            // nothing
        }
        result.setResponseWrapper(responseWrapper);
        return result;
    }

    public class ChatRoomMember {
        @Expose String username;
        @Expose Integer flag;
        @Expose String room_ctime;
        @Expose String mtime;
        @Expose String ctime;

        public String getUsername() {
            return username;
        }

        public Integer getFlag() {
            return flag;
        }

        public String getRoom_ctime() {
            return room_ctime;
        }

        public String getMtime() {
            return mtime;
        }

        public String getCtime() {
            return ctime;
        }
    }

    public ChatRoomMember[] getMembers() {
        return this.users;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getCount() {
        return count;
    }
}
