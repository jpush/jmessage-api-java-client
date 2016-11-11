package cn.jmessage.api.common.model;

import cn.jiguang.commom.utils.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class CrossNoDisturbPayload implements IModel {

    private static Gson gson = new Gson();

    private JsonArray array ;

    private CrossNoDisturbPayload(JsonArray array) {
        this.array = array;
    }

    public static Builder newBuilder() {
        return new CrossNoDisturbPayload.Builder();
    }

    @Override
    public JsonElement toJSON() {
        return array;
    }

    public static class Builder {

        private JsonArray array = new JsonArray();

        public Builder setCrossNoDistrub(CrossNoDisturb... lists) {

            if( null == lists ) {
                return this;
            }

            for ( CrossNoDisturb entity : lists) {

                array.add(entity.toJSON());
            }

            return this;
        }

        public CrossNoDisturbPayload build() {

            Preconditions.checkArgument(0 != array.size(), "The array must not be empty.");
            Preconditions.checkArgument(array.size() <= 500, "The array size must not over 500");

            return new CrossNoDisturbPayload(array);
        }
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }
}
