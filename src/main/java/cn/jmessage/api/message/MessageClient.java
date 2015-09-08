package cn.jmessage.api.message;


import cn.jmessage.api.common.BaseClient;
import cn.jmessage.api.common.ServiceConstant;
import cn.jmessage.api.common.model.MessagePayload;
import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.ResponseWrapper;
import cn.jpush.api.utils.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageClient extends BaseClient {

    //private static final Logger LOG = LoggerFactory.getLogger(MessageClient.class);

    public MessageClient(String appkey, String masterSecret) {
        super(appkey, masterSecret);
    }

    public MessageClient(String appkey, String masterSecret, int maxRetryTimes) {
        super(appkey, masterSecret, maxRetryTimes);
    }

    public MessageClient(String appkey, String masterSecret, int maxRetryTimes, HttpProxy proxy) {
        super(appkey, masterSecret, maxRetryTimes, proxy);
    }

    public ResponseWrapper sendMessage(MessagePayload payload)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(!(null == payload), "Message payload should not be null");

        return _httpClient.sendPost(_baseUrl + ServiceConstant.MESSAGE_PATH, payload.toString());
    }

}
