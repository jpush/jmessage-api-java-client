package cn.jmessage.api.common.model.message;

import cn.jmessage.api.common.model.IModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Notification implements IModel {

    private static final String TITLE = "title";
    private static final String ALTRT = "alert";

    private static Gson gson = new Gson();

    private String title;
    private String alert;

    public Notification(String title, String alert) {
        this.title = title;
        this.alert = alert;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    @Override
    public JsonElement toJSON() {
        JsonObject jsonObject = new JsonObject();
        if (title != null) {
            jsonObject.addProperty(TITLE, title);
        }

        if (alert != null) {
            jsonObject.addProperty(ALTRT, alert);
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }

    public static class Builder {
        private String title;
        private String alert;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        public Notification build() {
            return new Notification(title, alert);
        }
    }
}
