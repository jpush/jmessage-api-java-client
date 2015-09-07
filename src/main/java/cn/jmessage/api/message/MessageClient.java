package cn.jmessage.api.message;


import cn.jmessage.api.common.BaseClient;
import cn.jpush.api.common.connection.HttpProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageClient extends BaseClient {

    private static final Logger LOG = LoggerFactory.getLogger(MessageClient.class);

    public MessageClient(String appkey, String masterSecret) {
        super(appkey, masterSecret);
    }

    public MessageClient(String appkey, String masterSecret, int maxRetryTimes) {
        super(appkey, masterSecret, maxRetryTimes);
    }

    public MessageClient(String appkey, String masterSecret, int maxRetryTimes, HttpProxy proxy) {
        super(appkey, masterSecret, maxRetryTimes, proxy);
    }

    //todo finish response

}
