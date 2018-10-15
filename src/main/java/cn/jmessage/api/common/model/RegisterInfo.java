package cn.jmessage.api.common.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.utils.Preconditions;
import cn.jmessage.api.utils.StringUtils;

public class RegisterInfo implements IModel {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String NICKNAME = "nickname";
    private static final String AVATAR = "avatar";
    private static final String BIRTHDAY = "birthday";
    private static final String SIGNATURE = "signature";
    private static final String GENDER = "gender";
    private static final String REGION = "region";
    private static final String ADDRESS = "address";
    private static final String EXTRAS = "extras";
    
    private static Gson gson = new Gson();

    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String birthday;
    private String signature;
    private int gender = -1;
    private String region;
    private String address;
    private final Map<String, String> extras;
    private final Map<String, Number> numberExtras;
    private final Map<String, Boolean> booleanExtras;
    private final Map<String, JsonObject> jsonExtras;

    private RegisterInfo(String username, String password, String nickname, String avatar, String birthday,
                         String signature, int gender, String region, String address, 
                         Map<String, String> extras, 
                 		Map<String, Number> numberExtras,
                		Map<String, Boolean> booleanExtras,
                		Map<String, JsonObject> jsonExtras) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.birthday = birthday;
        this.signature = signature;
        this.gender = gender;
        this.region = region;
        this.address = address;
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

        if ( null != username ) {
            json.addProperty(USERNAME, username);
        }

        if ( null != password ) {
            json.addProperty(PASSWORD, password);
        }

        if (null != nickname) {
            json.addProperty(NICKNAME, nickname);
        }

        if (null != avatar) {
            json.addProperty(AVATAR, avatar);
        }

        if (null != birthday) {
            json.addProperty(BIRTHDAY, birthday);
        }

        if (null != signature) {
            json.addProperty(SIGNATURE, signature);
        }

        if (gender != -1) {
            json.addProperty(GENDER, gender);
        }

        if (null != region) {
            json.addProperty(REGION, region);
        }

        if (null != address) {
            json.addProperty(ADDRESS, address);
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
        return gson.toJson(toJSON());
    }

    public static class Builder{
        private String username;
        private String password;
        private String nickname;
        private String avatar;
        private String birthday;
        private String signature;
        private int gender = -1;
        private String region;
        private String address;
        private Map<String, String> extrasBuilder;
        private Map<String, Number> numberExtrasBuilder;
        private Map<String, Boolean> booleanExtrasBuilder;
        protected Map<String, JsonObject> jsonExtrasBuilder;

        public Builder setUsername(String username) {
            this.username = username.trim();
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password.trim();
            return this;
        }

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder setAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder setBirthday(String birthday) {
            Preconditions.checkArgument(isDateFormat(birthday), "Birthday format must be yyyy-MM-dd");
            this.birthday = birthday;
            return this;
        }

        public Builder setSignature(String signature) {
            this.signature = signature;
            return this;
        }

        public Builder setGender(int gender) {
            Preconditions.checkArgument(gender == 0 || gender == 1 || gender == 2, "Gender must be 0, or 1, or 2");
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

        public RegisterInfo build() {

        	StringUtils.checkUsername(username);
        	StringUtils.checkPassword(password);
            return new RegisterInfo(username, password, nickname, avatar, birthday, signature, gender, region, address,
            		extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder,jsonExtrasBuilder);
        }

        private boolean isDateFormat(String date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                sdf.parse(date);
                return true;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }

    }
}
