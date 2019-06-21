package cn.jmessage.api.chatroom;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

import java.util.Map;

public class ChatRoomHistoryResult extends BaseResult {

    @Expose
    private Integer total;

    @Expose
    private String cursor;

    @Expose
    private Integer count;

    @Expose
    private ChatRoomBaseMessageResult[] messages;

    public Integer getTotal() {
        return total;
    }

    public String getCursor() {
        return cursor;
    }

    public Integer getCount() {
        return count;
    }

    public static class ChatRoomBaseMessageResult {

        @Expose
        private Integer set_from_name;

        @Expose
        private String from_platform;

        @Expose
        private String target_name;

        @Expose
        private String msg_type;

        @Expose
        private Integer version;

        @Expose
        private String target_id;

        @Expose
        private String from_appkey;

        @Expose
        private String from_name;

        @Expose
        private String from_id;

        @Expose
        private Map<String, Object> msg_body;

        @Expose
        private Long create_time;

        @Expose
        private String from_type;

        @Expose
        private String target_appkey;

        @Expose
        private String target_type;

        @Expose
        private Long msgid;

        @Expose
        private Long msg_ctime;

        @Expose
        private Integer msg_level;

        public Integer getSet_from_name() {
            return set_from_name;
        }

        public String getFrom_platform() {
            return from_platform;
        }

        public String getTarget_name() {
            return target_name;
        }

        public String getMsg_type() {
            return msg_type;
        }

        public Integer getVersion() {
            return version;
        }

        public String getTarget_id() {
            return target_id;
        }

        public String getFrom_appkey() {
            return from_appkey;
        }

        public String getFrom_name() {
            return from_name;
        }

        public String getFrom_id() {
            return from_id;
        }

        public Map<String, Object> getMsg_body() {
            return msg_body;
        }

        public Long getCreate_time() {
            return create_time;
        }

        public String getFrom_type() {
            return from_type;
        }

        public String getTarget_appkey() {
            return target_appkey;
        }

        public String getTarget_type() {
            return target_type;
        }

        public Long getMsgid() {
            return msgid;
        }

        public Long getMsg_ctime() {
            return msg_ctime;
        }

        public Integer getMsg_level() {
            return msg_level;
        }
    }

    private static class BaseMessageResult {

        @Expose
        private String text;

        @Expose
        private Map<String, Object> extras;

        public String getText() {
            return text;
        }

        public Map<String, Object> getExtras() {
            return extras;
        }
    }

}
