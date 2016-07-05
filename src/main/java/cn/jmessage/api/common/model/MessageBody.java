package cn.jmessage.api.common.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.commom.utils.Preconditions;
import cn.jiguang.commom.utils.StringUtils;

/**
 * MessageBody
 * Created by tangyikai on 15/9/8.
 */
public class MessageBody implements IModel {

    private static final String MSG_BODY_TEXT = "text";
    private static final String MSG_BODY_EXTRAS = "extras";

    private static Gson gson = new Gson();

    private Map<String, String> extras;
    private Map<String, Number> numberExtras;
    private Map<String, Boolean> booleanExtras;

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

        public MessageBody build() {
            Preconditions.checkArgument(StringUtils.isNotEmpty(text), "The text should be set");
            return new MessageBody(text,
                    extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder);
        }
    }
}
