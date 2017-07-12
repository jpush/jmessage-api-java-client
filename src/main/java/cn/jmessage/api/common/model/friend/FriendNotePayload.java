package cn.jmessage.api.common.model.friend;

import cn.jiguang.common.utils.Preconditions;
import cn.jmessage.api.common.model.IModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class FriendNotePayload implements IModel {

    private static Gson gson = new Gson();

    private JsonArray array ;

    private FriendNotePayload(JsonArray array) {
        this.array = array;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public JsonElement toJSON() {
        return array;
    }

    public static class Builder {

        private JsonArray array = new JsonArray();

        public Builder setFriendNotes(FriendNote... users) {

            if( null == users ) {
                return this;
            }

            for ( FriendNote user : users) {

                array.add(user.toJSON());
            }

            return this;
        }

        public FriendNotePayload build() {

            Preconditions.checkArgument(0 != array.size(), "The array must not be empty.");
            Preconditions.checkArgument(array.size() <= 500, "The array size must not over 500");

            return new FriendNotePayload(array);
        }
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }
}
