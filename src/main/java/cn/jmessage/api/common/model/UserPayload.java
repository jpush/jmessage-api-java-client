package cn.jmessage.api.common.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.utils.StringUtils;

public class UserPayload implements IModel {

    public static final String NICKNAME = "nickname";
    public static final String BIRTHDAY = "birthday";
    public static final String SIGNATURE = "signature";
    public static final String GENDER = "gender";
    public static final String REGION = "region";
    public static final String ADDRESS = "address";
    public static final String AVATAR = "avatar";
    private static final String EXTRAS = "extras";

    private static Gson _gson = new Gson();

    private String nickname;
    private String birthday;
    private String signature;
    private int gender;
    private String region;
    private String address;
    private String avatar;
    private final Map<String, String> extras;
    private final Map<String, Number> numberExtras;
    private final Map<String, Boolean> booleanExtras;
    private final Map<String, JsonObject> jsonExtras;

    private UserPayload(String nickname, String birthday, String signature, int gender, String region,
                        String address, String avatar, 
                		Map<String, String> extras, 
                		Map<String, Number> numberExtras,
                		Map<String, Boolean> booleanExtras,
                		Map<String, JsonObject> jsonExtras) {
        this.nickname = nickname;
        this.birthday = birthday;
        this.signature = signature;
        this.gender = gender;
        this.region = region;
        this.address = address;
        this.avatar = avatar;
        this.extras = extras;
        this.numberExtras = numberExtras;
        this.booleanExtras = booleanExtras;
        this.jsonExtras = jsonExtras;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();

        if ( null != nickname ) {
            json.addProperty(NICKNAME, nickname);
        }

        if ( null != birthday ) {
            json.addProperty(BIRTHDAY, birthday);
        }

        if ( -1 != gender ) {
            json.addProperty(GENDER, gender);
        }

        if ( null != signature ) {
            json.addProperty(SIGNATURE, signature);
        }

        if ( null != region ) {
            json.addProperty(REGION, region);
        }

        if ( null != address ) {
            json.addProperty(ADDRESS, address);
        }

        if (null != avatar) {
            json.addProperty(AVATAR, avatar);
        }
        
        JsonObject extrasObject = null;
        if (null != extras || null != numberExtras || null != booleanExtras) {
            extrasObject = new JsonObject();
        }
        
        if (null != extras) {
            for (String key : extras.keySet()) {
                if (extras.get(key) != null) {
                    extrasObject.add(key, new JsonPrimitive(extras.get(key)));
                } else {
                    extrasObject.add(key, JsonNull.INSTANCE);
                }
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
        if (null != jsonExtras) {
            for (String key : jsonExtras.keySet()) {
                extrasObject.add(key, jsonExtras.get(key));
            }
        }

        if (null != extras || null != numberExtras || null != booleanExtras) {
            json.add(EXTRAS, extrasObject);
        }

        return json;
    }

    @Override
    public String toString() {
        return _gson.toJson(toJSON());
    }

    public static class Builder {

        private String nickname;
        private String birthday;
        private String signature;
        private int gender = -1;
        private String region;
        private String address;
        private String avatar;
        private Map<String, String> extrasBuilder;
        private Map<String, Number> numberExtrasBuilder;
        private Map<String, Boolean> booleanExtrasBuilder;
        protected Map<String, JsonObject> jsonExtrasBuilder;

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder setBirthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder setSignature(String signature) {
            this.signature = signature;
            return this;
        }

        public Builder setGender(int gender) {
            this.gender = gender;
            return this;
        }

        public Builder setRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }
        
        public Builder addExtra(String key, String value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            extrasBuilder.put(key, value);
            return this;
        }
        
        public Builder addExtras(Map<String, String> extras) {
            Preconditions.checkArgument(! (null == extras), "extras should not be null.");
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            for (String key : extras.keySet()) {
                extrasBuilder.put(key, extras.get(key));
            }
            return this;
        }
        
        public Builder addExtra(String key, Number value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == numberExtrasBuilder) {
                numberExtrasBuilder = new HashMap<String, Number>();
            }
            numberExtrasBuilder.put(key, value);
            return this;
        }
        
        public Builder addExtra(String key, Boolean value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == booleanExtrasBuilder) {
                booleanExtrasBuilder = new HashMap<String, Boolean>();
            }
            booleanExtrasBuilder.put(key, value);
            return this;
        }
        
        public Builder addExtra(String key, JsonObject value) {
        	Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == jsonExtrasBuilder) {
            	jsonExtrasBuilder = new HashMap<String, JsonObject>();
            }
            jsonExtrasBuilder.put(key, value);
            return this;
        }

        public UserPayload build() {

            if ( null != nickname ) {
                Preconditions.checkArgument(nickname.getBytes().length <= 64,
                        "The length of nickname must less than 64 bytes.");
                Preconditions.checkArgument(!StringUtils.isLineBroken(nickname),
                        "The nickname must not contain line feed character.");
            }

            if ( null != birthday) {
                Preconditions.checkArgument( ServiceHelper.isValidBirthday(birthday),
                        "Invalid birthday.");
            }

            if (null != signature) {
                Preconditions.checkArgument(signature.getBytes().length <= 250,
                        "The length of signature must not more than 250 bytes.");
            }

            Preconditions.checkArgument( gender >= -1 && gender <= 2,
                    "Invalid gender. 0 for unknown , 1 for male and 2 for female." );

            if ( null != region ) {
                Preconditions.checkArgument(region.getBytes().length <= 250,
                       "The length of region must not more than 250 bytes." );
            }

            if ( null != address ) {
                Preconditions.checkArgument(address.getBytes().length <= 250,
                        "The length of address must not more than 250 bytes." );
            }

            return new UserPayload(nickname, birthday, signature, gender, region, address, avatar, 
            		extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder,jsonExtrasBuilder);
        }

    }
}
