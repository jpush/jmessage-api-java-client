package cn.jmessage.api.common;

import cn.jpush.api.common.ClientConfig;


public class JMessageConfig {

    private static ClientConfig clientConfig = ClientConfig.getInstance();

    private static JMessageConfig instance = new JMessageConfig();

    public static final String API_HOST_NAME = "im.api.host.name";

    public static final String ADMIN_PATH = "im.admin.path";

    public static final String USER_PATH = "im.user.path";

    public static final String GROUP_PATH = "im.group.path";

    public static final String MESSAGE_PATH = "im.message.path";

    public static final String MAX_RETRY_TIMES = ClientConfig.MAX_RETRY_TIMES;

    public static final String SEND_VERSION = "send.version";
    public static final Object SEND_VERSION_SCHMEA = Integer.class;

    private JMessageConfig() {
        clientConfig.put(API_HOST_NAME, "https://api.im.jpush.cn");
        clientConfig.put(ADMIN_PATH, "/v1/admins");
        clientConfig.put(USER_PATH, "/v1/users");
        clientConfig.put(GROUP_PATH, "/v1/groups");
        clientConfig.put(MESSAGE_PATH, "/v1/messages");
        clientConfig.put(MAX_RETRY_TIMES, 3);
        clientConfig.put(SEND_VERSION, 1);
    }

    public static JMessageConfig getInstance() {
        return instance;
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public JMessageConfig setApiHostName(String hostName) {
        clientConfig.put(API_HOST_NAME, hostName);
        return this;
    }

    public JMessageConfig setMaxRetryTimes(int maxRetryTimes) {
        clientConfig.setMaxRetryTimes(maxRetryTimes);
        return this;
    }

    public void put(String key, Object value) {
        clientConfig.put(key, value);
    }

    public Object get(String key) {
        return clientConfig.get(key);
    }

}
