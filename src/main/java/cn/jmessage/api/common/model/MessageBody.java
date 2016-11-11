package cn.jmessage.api.common.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.utils.StringUtils;

/**
 * MessageBodyResult
 * Created by tangyikai on 15/9/8.
 */
public class MessageBody implements IModel {

    private static final String MSG_BODY_TEXT = "text";
    private static final String MSG_BODY_EXTRAS = "extras";
    private static final String MEDIA_ID = "media_id";
    private static final String MEDIA_CRC32 = "media_crc32";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String FORMAT = "format";
    private static final String FSIZE = "fsize";

    private static Gson gson = new Gson();

    private Map<String, String> extras;
    private Map<String, Number> numberExtras;
    private Map<String, Boolean> booleanExtras;
    private String media_id;
    private Long media_crc32;
    private Integer width;
    private Integer height;
    private String format;
    private Integer fsize;

    private String text;

    private MessageBody(String text,
                        Map<String, String> extra,
                        Map<String, Number> numberExtra,
                        Map<String, Boolean> booleanExtra) {
        this.text = text;
        this.extras = extra;
        this.numberExtras = numberExtra;
        this.booleanExtras = booleanExtra;
    }

    private MessageBody(String mediaId, Long crc32, Integer width, Integer height, String format, Integer fsize) {
        this.media_id = mediaId;
        this.media_crc32 = crc32;
        this.width = width;
        this.height = height;
        this.format = format;
        this.fsize = fsize;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static MessageBody text(String text) {
        return new Builder().setText(text).build();
    }

    @Override
    public JsonElement toJSON() {

        JsonObject json = new JsonObject();

        if (null != text) {
            json.addProperty(MSG_BODY_TEXT, text);
        }

        JsonObject extrasObject = null;
        if (null != extras || null != numberExtras || null != booleanExtras) {
            extrasObject = new JsonObject();
        }

        if (null != extras) {
            for (String key : extras.keySet()) {
                extrasObject.add(key, new JsonPrimitive(extras.get(key)));
            }
        }
        if (null != numberExtras) {
            for (String key : numberExtras.keySet()) {
                extrasObject.add(key, new JsonPrimitive(numberExtras.get(key)));
            }
        }
        if (null != booleanExtras) {
            for (String key : booleanExtras.keySet()) {
                extrasObject.add(key, new JsonPrimitive(booleanExtras.get(key)));
            }
        }

        if (null != extras || null != numberExtras || null != booleanExtras) {
            json.add(MSG_BODY_EXTRAS, extrasObject);
        }

        if (null != media_id) {
            json.addProperty(MEDIA_ID, media_id);
        }

        if (null != media_crc32) {
            json.addProperty(MEDIA_CRC32, media_crc32);
        }

        if (null != width) {
            json.addProperty(WIDTH, width);
        }

        if (null != height) {
            json.addProperty(HEIGHT, height);
        }

        if (null != format) {
            json.addProperty(FORMAT, format);
        }

        if (null != fsize) {
            json.addProperty(FSIZE, fsize);
        }

        return json;
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }

    public static class Builder {
        private String text;
        private Map<String, String> extrasBuilder;
        private Map<String, Number> numberExtrasBuilder;
        private Map<String, Boolean> booleanExtrasBuilder;
        private String media_id;
        private Long media_crc32;
        private Integer width;
        private Integer height;
        private String format;
        private Integer fsize;

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder addExtra(String key, String value) {
            Preconditions.checkArgument(!(null == key || null == value), "Key/Value should not be null.");
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            extrasBuilder.put(key, value);
            return this;
        }

        public Builder addExtras(Map<String, String> extras) {
            Preconditions.checkArgument(!(null == extras), "extras should not be null.");
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            for (String key : extras.keySet()) {
                extrasBuilder.put(key, extras.get(key));
            }
            return this;
        }

        public Builder addExtra(String key, Number value) {
            Preconditions.checkArgument(!(null == key || null == value), "Key/Value should not be null.");
            if (null == numberExtrasBuilder) {
                numberExtrasBuilder = new HashMap<String, Number>();
            }
            numberExtrasBuilder.put(key, value);
            return this;
        }

        public Builder addExtra(String key, Boolean value) {
            Preconditions.checkArgument(!(null == key || null == value), "Key/Value should not be null.");
            if (null == booleanExtrasBuilder) {
                booleanExtrasBuilder = new HashMap<String, Boolean>();
            }
            booleanExtrasBuilder.put(key, value);
            return this;
        }

        public Builder setMediaId(String mediaId) {
            this.media_id = mediaId;
            return this;
        }

        public Builder setMediaCrc32(Long crc32) {
            this.media_crc32 = crc32;
            return this;
        }

        public Builder setWidth(Integer width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(Integer height) {
            this.height = height;
            return this;
        }

        public Builder setFormat(String format) {
            this.format = format;
            return this;
        }

        public Builder setFsize(Integer fsize) {
            this.fsize = fsize;
            return this;
        }

        public MessageBody build() {
            if (null != media_id) {
                return new MessageBody(media_id, media_crc32, width, height, format, fsize);
            }
            return new MessageBody(text, extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder);
        }
    }
}
