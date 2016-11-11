package cn.jmessage.api.common.model;

import cn.jiguang.common.utils.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class CrossBlacklistPayload implements IModel {
    private static Gson gson = new Gson();

    private JsonArray array ;

    private CrossBlacklistPayload(JsonArray array) {
        this.array = array;
    }

    public static Builder newBuilder() {
        return new CrossBlacklistPayload.Builder();
    }

    @Override
    public JsonElement toJSON() {
        return array;
    }

    public static class Builder {

        private JsonArray array = new JsonArray();

        public Builder setCrossBlacklists(CrossBlacklist... blacklists) {

            if( null == blacklists ) {
                return this;
            }

            for ( CrossBlacklist blacklist : blacklists) {

                array.add(blacklist.toJSON());
            }

            return this;
        }

        public CrossBlacklistPayload build() {

            Preconditions.checkArgument(0 != array.size(), "The array must not be empty.");
            Preconditions.checkArgument(array.size() <= 500, "The array size must not over 500");

            return new CrossBlacklistPayload(array);
        }
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }
}
