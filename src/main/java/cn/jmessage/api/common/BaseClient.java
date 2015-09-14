package cn.jmessage.api.common;

import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.connection.NativeHttpClient;

public class BaseClient {

    protected final NativeHttpClient _httpClient;
    protected String _baseUrl;


    /**
     * Create a JMessage Base Client
     *
     * @param appkey The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param proxy The proxy, if there is no proxy, should be null.
     * @param config The client configuration. Can use JMessageConfig.getInstance() as default.
     */
    public BaseClient(String appkey, String masterSecret, HttpProxy proxy, JMessageConfig config) {
        ServiceHelper.checkBasic(appkey, masterSecret);
        String authCode = ServiceHelper.getBasicAuthorization(appkey, masterSecret);
        this._baseUrl = (String) config.get(JMessageConfig.API_HOST_NAME);
        this._httpClient = new NativeHttpClient(authCode, (Integer) config.get(JMessageConfig.MAX_RETRY_TIMES), proxy);
    }

}
