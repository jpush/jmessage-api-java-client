package cn.jmessage.api.resource;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class UploadResult extends BaseResult {

    @Expose String media_id;
    @Expose Long media_crc32;
    @Expose Integer width;
    @Expose Integer height;
    @Expose String format;
    @Expose Integer fsize;

    public String getMediaId() {
        return media_id;
    }

    public Long getMediaCrc32() {
        return media_crc32;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public String getFormat() {
        return format;
    }

    public Integer getFsize() {
        return fsize;
    }
}
