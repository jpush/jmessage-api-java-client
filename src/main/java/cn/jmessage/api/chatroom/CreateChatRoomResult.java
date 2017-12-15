package cn.jmessage.api.chatroom;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class CreateChatRoomResult extends BaseResult {

    @Expose private Long chatroom_id;

    public Long getChatroom_id() {
        return chatroom_id;
    }
}
