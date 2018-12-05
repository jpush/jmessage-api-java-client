package cn.jmessage.api.message;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class MessageResult extends BaseResult {

    @Expose String target_type;
    @Expose String msg_type;
    @Expose String target_name;
    @Expose String target_id;
    @Expose String from_id;
    @Expose String from_name;
    @Expose String from_type;
    @Expose String from_platform;
    @Expose MessageBodyResult msg_body;
    @Expose Long create_time;
    @Expose Integer version;
    @Expose Long msgid;
    @Expose Integer msg_level;
    @Expose Long msg_ctime;
    @Expose Boolean no_offline;
    @Expose Boolean no_notification;

    public String getTargetType() {
        return target_type;
    }

    public String getMsgType() {
        return msg_type;
    }

    public String getTargetName() {
        return target_name;
    }

    public String getTargetId() {
        return target_id;
    }

    public String getFromId() {
        return from_id;
    }

    public String getFromName() {
        return from_name;
    }

    public String getFromType() {
        return from_type;
    }

    public String getFromPlatform() {
        return from_platform;
    }

    public MessageBodyResult getMsgBody() {
        return msg_body;
    }

    public Long getCreateTime() {
        return create_time;
    }

    public Integer getVersion() {
        return version;
    }

    public Long getMsgId() {
        return msgid;
    }

    public Integer getMsgLevel() {
        return msg_level;
    }

    public Long getMsgCtime() {
        return msg_ctime;
    }

    public Boolean getNoOffline() {
        return no_offline;
    }

    public Boolean getNoNotification() {
        return no_notification;
    }
}

