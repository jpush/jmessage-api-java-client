package cn.jmessage.api.common;

import java.util.HashMap;

public class JMessageConfig extends HashMap<String, Object> {

    private static JMessageConfig instance = new JMessageConfig();

    public static final String API_HOST_NAME = "im.api.host.name";

    public static final String ADMIN_PATH = "im.admin.path";

    public static final String USER_PATH = "im.user.path";

    public static final String GROUP_PATH = "im.group.path";

    public static final String MESSAGE_PATH = "im.message.path";

    public static final String MAX_RETRY_TIMES = "max.retry.times";
    public static final Object MAX_RETRY_TIMES_SCHMEA = Integer.class;

    private JMessageConfig() {
        super(6);
        this.put(API_HOST_NAME, "https://api.im.jpush.cn");
        this.put(ADMIN_PATH, "/v1/admins");
        this.put(USER_PATH, "/v1/users");
        this.put(GROUP_PATH, "/v1/groups");
        this.put(MESSAGE_PATH, "/v1/messages");
        this.put(MAX_RETRY_TIMES, 3);
    }

    public static JMessageConfig getInstance() {
        return instance;
    }

    public JMessageConfig setApiHostName(String hostName) {
        setApiHostName(this, hostName);
        return this;
    }

    public static void setApiHostName(JMessageConfig conf, String hostName) {
        conf.put(API_HOST_NAME, hostName);
    }

    public JMessageConfig setMaxRetryTimes(int maxRetryTimes) {
        setMaxRetryTimes(this, maxRetryTimes);
        return this;
    }

    public static void setMaxRetryTimes(JMessageConfig conf, int maxRetryTimes) {
        conf.put(MAX_RETRY_TIMES, maxRetryTimes);
    }

}
