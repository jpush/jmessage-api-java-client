package cn.jmessage.api.user;

import cn.jpush.api.common.resp.BaseResult;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

import java.util.List;

public class RegisterResult extends BaseResult {

    @Expose List<RegisterEntity> array;

    class RegisterEntity {
        @Expose String username;
        @Expose JsonObject error;

        public String getUsername() {
            return username;
        }

        public JsonObject getError() {
            return error;
        }

        public boolean hasError() {
            return null != error;
        }

        public String getErrorMessage() {
            if(null != error) {
                return error.get("message").getAsString();
            }
            return null;
        }

        public int getErrorCode() {
            if(null != error) {
                return error.get("code").getAsInt();
            }
            return -1;
        }

    }

    public List<RegisterEntity> getArray() {
        return array;
    }
}
