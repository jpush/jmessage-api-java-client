package cn.jmessage.api.common.model;


import cn.jmessage.api.utils.StringUtils;
import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.utils.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RegisterInfo implements IModel {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    private static Gson gson = new Gson();

    private String username;
    private String password;

    private RegisterInfo(String username, String password) {
        this.username = username;
        this.password = password;
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

        return json;
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }

    public static class Builder{
        private String username;
        private String password;

        public Builder setUsername(String username) {
            this.username = username.trim();
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password.trim();
            return this;
        }

        public RegisterInfo build() {

        	Preconditions.checkArgument(StringUtils.isUsernameValid(username), "The username is illegal, please check again");
        	Preconditions.checkArgument(StringUtils.isPasswordValid(password), "The password is illegal, please check again");
            return new RegisterInfo(username, password);
        }

    }
}
