package cn.jmessage.api.message;


import cn.jmessage.api.common.BaseClient;
import cn.jmessage.api.common.JMessageConfig;
import cn.jmessage.api.common.model.MessagePayload;
import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.ResponseWrapper;
import cn.jpush.api.utils.Preconditions;

public class MessageClient extends BaseClient {

    //private static final Logger LOG = LoggerFactory.getLogger(MessageClient.class);

    private String messagePath;

    public MessageClient(String appkey, String masterSecret) {
        this(appkey, masterSecret, null, JMessageConfig.getInstance());
    }

    public MessageClient(String appkey, String masterSecret, HttpProxy proxy, JMessageConfig config) {
        super(appkey, masterSecret, proxy, config);
        this.messagePath = (String) config.get(JMessageConfig.MESSAGE_PATH);
    }

    public ResponseWrapper sendMessage(MessagePayload payload)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(!(null == payload), "Message payload should not be null");

        return _httpClient.sendPost(_baseUrl + messagePath, payload.toString());
    }

}
