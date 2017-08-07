package cn.jmessage.api.common.model.group;

import cn.jiguang.common.utils.Preconditions;
import cn.jmessage.api.common.model.IModel;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

public class GroupShieldPayload implements IModel {

    private static final String ADD = "add";
    private static final String REMOVE = "remove";

    private static final Gson gson = new Gson();

    private List<Long> addList;
    private List<Long> removeList;

    public GroupShieldPayload(List<Long> addList, List<Long> removeList) {
        this.addList = addList;
        this.removeList = removeList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private List<Long> addList = new ArrayList<Long>();
        private List<Long> removeList = new ArrayList<Long>();

        public Builder addGroupShield(Long...gid) {
            for (long id : gid) {
                addList.add(id);
            }
            return this;
        }

        public Builder setAddGroupShield(List<Long> list) {
            Preconditions.checkArgument(null != list, "group id list is null");
            addList = list;
            return this;
        }

        public Builder removeGroupShield(long...ids) {
            for (long id : ids) {
                removeList.add(id);
            }
            return this;
        }

        public Builder setRemoveGroupShield(List<Long> list) {
            Preconditions.checkArgument(null != list, "group id list is null");
            removeList = list;
            return this;
        }

        public GroupShieldPayload build() {
            return new GroupShieldPayload(addList, removeList);
        }
    }

    @Override
    public JsonElement toJSON() {
        JsonObject jsonObject = new JsonObject();
        if (null != addList && addList.size() > 0) {
            JsonArray jsonArray = new JsonArray();
            for (long id : addList) {
                jsonArray.add(new JsonPrimitive(id));
            }
            jsonObject.add(ADD, jsonArray);
        }

        if (null != removeList && removeList.size() > 0) {
            JsonArray jsonArray = new JsonArray();
            for (long id : removeList) {
                jsonArray.add(new JsonPrimitive(id));
            }
            jsonObject.add(REMOVE, jsonArray);
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }
}
