package cn.jmessage.api.chatroom;

import cn.jiguang.common.resp.BaseResult;
import cn.jiguang.common.resp.ResponseWrapper;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomListResult extends BaseResult {

    @Expose private List<ChatRoomResult> rooms = new ArrayList<ChatRoomResult>();
    @Expose private Integer total;
    @Expose private ChatRoomResult[] roomsArray;
    @Expose private Integer start;
    @Expose private Integer count;

    public static ChatRoomListResult fromResponse(ResponseWrapper responseWrapper) {
        ChatRoomListResult result = new ChatRoomListResult();
        if (responseWrapper.isServerResponse()) {
            result.roomsArray = _gson.fromJson(responseWrapper.responseContent, ChatRoomResult[].class);
        }
        result.setResponseWrapper(responseWrapper);
        return result;
    }

    public ChatRoomResult[] getRooms() {
        return this.roomsArray;
    }

    public List<ChatRoomResult> getList() {
        return this.rooms;
    }

    public Integer getTotal() {
        return this.total;
    }

    public Integer getStart() {
        return this.start;
    }

    public Integer getCount() {
        return this.count;
    }
}
