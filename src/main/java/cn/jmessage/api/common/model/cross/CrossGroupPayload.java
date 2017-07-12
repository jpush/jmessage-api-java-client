package cn.jmessage.api.common.model.cross;

import cn.jiguang.common.utils.Preconditions;
import cn.jmessage.api.common.model.IModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class CrossGroupPayload implements IModel {
    private static Gson gson = new Gson();

    private JsonArray array ;

    private CrossGroupPayload(JsonArray array) {
        this.array = array;
    }

    public static Builder newBuilder() {
        return new CrossGroupPayload.Builder();
    }

    @Override
    public JsonElement toJSON() {
        return array;
    }

    public static class Builder {

        private JsonArray array = new JsonArray();

        public Builder setCrossGroups(CrossGroup... groups) {

            if( null == groups ) {
                return this;
            }

            for ( CrossGroup group : groups) {

                array.add(group.toJSON());
            }

            return this;
        }

        public CrossGroupPayload build() {

            Preconditions.checkArgument(0 != array.size(), "The array must not be empty.");
            Preconditions.checkArgument(array.size() <= 500, "The array size must not over 500");

            return new CrossGroupPayload(array);
        }
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }
}
