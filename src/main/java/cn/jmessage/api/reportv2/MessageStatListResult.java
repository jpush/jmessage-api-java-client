package cn.jmessage.api.reportv2;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class MessageStatListResult extends BaseResult {

    @Expose private List<SendMsgStat> send_msg_stat = new ArrayList<SendMsgStat>();
    @Expose private MsgStat group_msg_stat;
    @Expose private MsgStat single_msg_stat;

    public List<SendMsgStat> getSend_msg_stat() {
        return send_msg_stat;
    }

    public MsgStat getGroup_msg_stat() {
        return group_msg_stat;
    }

    public MsgStat getSingle_msg_stat() {
        return single_msg_stat;
    }

    public class SendMsgStat {
        @Expose private String time;
        @Expose private Long group_send_msg;
        @Expose private Long single_send_msg;

        public String getTime() {
            return time;
        }

        public Long getGroup_send_msg() {
            return group_send_msg;
        }

        public Long getSingle_send_msg() {
            return single_send_msg;
        }
    }

    public class MsgStat {
        @Expose private Long txt_msg;
        @Expose private Long image_msg;
        @Expose private Long voice_msg;
        @Expose private Long other_msg;

        public Long getTxt_msg() {
            return txt_msg;
        }

        public Long getImage_msg() {
            return image_msg;
        }

        public Long getVoice_msg() {
            return voice_msg;
        }

        public Long getOther_msg() {
            return other_msg;
        }
    }
}
