package cn.jmessage.api.message;


import cn.jiguang.commom.utils.Preconditions;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.common.BaseClient;
import cn.jmessage.api.common.JMessageConfig;
import cn.jmessage.api.common.model.MessagePayload;

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

    public SendMessageResult sendMessage(MessagePayload payload)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(!(null == payload), "Message payload should not be null");

        ResponseWrapper response = _httpClient.sendPost(_baseUrl + messagePath, payload.toString());
        return SendMessageResult.fromResponse(response, SendMessageResult.class);
    }

}
