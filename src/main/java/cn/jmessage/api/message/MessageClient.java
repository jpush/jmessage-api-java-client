package cn.jmessage.api.message;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.common.BaseClient;
import cn.jmessage.api.common.JMessageConfig;
import cn.jmessage.api.common.model.message.MessagePayload;
import cn.jmessage.api.utils.StringUtils;


public class MessageClient extends BaseClient {

    //private static final Logger LOG = LoggerFactory.getLogger(MessageClient.class);

    private String messagePath;
    private String v2_messagePath;
    private String reportBaseUrl;
    private String v2_userPath;


    public MessageClient(String appkey, String masterSecret) {
        this(appkey, masterSecret, null, JMessageConfig.getInstance());
    }

    public MessageClient(String appkey, String masterSecret, HttpProxy proxy, JMessageConfig config) {
        super(appkey, masterSecret, proxy, config);
        this.messagePath = (String) config.get(JMessageConfig.MESSAGE_PATH);
        this.reportBaseUrl = (String) config.get(JMessageConfig.API_REPORT_HOST_NAME);
        this.v2_userPath = (String) config.get(JMessageConfig.V2_USER_PATH);
        this.v2_messagePath = (String) config.get(JMessageConfig.V2_MESSAGE_PATH);
    }

    public SendMessageResult sendMessage(MessagePayload payload)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(!(null == payload), "Message payload should not be null");

        ResponseWrapper response = _httpClient.sendPost(_baseUrl + messagePath, payload.toString());
        return SendMessageResult.fromResponse(response, SendMessageResult.class);
    }

    /**
     * Get message list from history, messages will store 60 days.
     * @param count Necessary parameter. The count of the message list.
     * @param begin_time Necessary parameter. The format must follow by 'yyyy-MM-dd HH:mm:ss'
     * @param end_time Necessary parameter. The format must follow by 'yyyy-MM-dd HH:mm:ss'
     * @return MessageListResult
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public MessageListResult getMessageList(int count, String begin_time, String end_time)
            throws APIConnectionException, APIRequestException {
        if(count <= 0 || count > 1000) {
            throw new IllegalArgumentException("count must more than 0 and less than 1001");
        }

        String requestUrl = reportBaseUrl + v2_messagePath + "?count=" + count;
        String beginEncoded = null;
        String endEncoded = null;
        if (null != begin_time && StringUtils.isNotEmpty(begin_time) && null != end_time
                && StringUtils.isNotEmpty(end_time)) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date beginDate = format.parse(begin_time);
                Date endDate = format.parse(end_time);
                if (endDate.getTime() - beginDate.getTime() < 0) {
                    throw new IllegalArgumentException("end time must lager than begin time");
                } else if (endDate.getTime() - beginDate.getTime() > 7 * 24 * 60 * 60 * 1000) {
                    throw new IllegalArgumentException("end time lager than begin time over 7 days");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                beginEncoded = URLEncoder.encode(begin_time, "utf-8");
                endEncoded = URLEncoder.encode(end_time, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            requestUrl = requestUrl + "&begin_time=" + beginEncoded + "&end_time=" + endEncoded;
        } else {
            throw new IllegalArgumentException("begin time or end time is null or empty");
        }

        ResponseWrapper response = _httpClient.sendGet(requestUrl);
        return MessageListResult.fromResponse(response, MessageListResult.class);
    }

    /**
     * Get message list with cursor, the cursor will effective in 120 seconds. And will
     * return same count of messages as first request.
     * @param cursor First request will return cursor
     * @return MessageListResult
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public MessageListResult getMessageListByCursor(String cursor)
            throws APIConnectionException, APIRequestException {
        if (null != cursor) {
            String requestUrl = reportBaseUrl + v2_messagePath + "?cursor=" + cursor;
            ResponseWrapper response = _httpClient.sendGet(requestUrl);
            return MessageListResult.fromResponse(response, MessageListResult.class);
        } else {
            throw new IllegalArgumentException("the cursor parameter should not be null");
        }
    }

    /**
     * Get message list from user's record, messages will store 60 days.
     * @param username Necessary parameter.
     * @param count Necessary parameter. The count of the message list.
     * @param begin_time Optional parameter. The format must follow by 'yyyy-MM-dd HH:mm:ss'
     * @param end_time Optional parameter. The format must follow by 'yyyy-MM-dd HH:mm:ss'
     * @return MessageListResult
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public MessageListResult getUserMessages(String username, int count, String begin_time, String end_time)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);

        if(count <= 0 || count > 1000) {
            throw new IllegalArgumentException("count must more than 0 and less than 1001");
        }

        String requestUrl = reportBaseUrl + v2_userPath + "/" + username + "/messages?count=" + count;
        String beginEncoded = null;
        String endEncoded = null;
        if (null != begin_time && StringUtils.isNotEmpty(begin_time) && null != end_time
                && StringUtils.isNotEmpty(end_time)) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date beginDate = format.parse(begin_time);
                Date endDate = format.parse(end_time);
                if (endDate.getTime() - beginDate.getTime() < 0) {
                    throw new IllegalArgumentException("end time must lager than begin time");
                } else if (endDate.getTime() - beginDate.getTime() > 7 * 24 * 60 * 60 * 1000) {
                    throw new IllegalArgumentException("end time lager than begin time over 7 days");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                beginEncoded = URLEncoder.encode(begin_time, "utf-8");
                endEncoded = URLEncoder.encode(end_time, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            requestUrl = requestUrl + "&begin_time=" + beginEncoded + "&end_time=" + endEncoded;
        } else {
            throw new IllegalArgumentException("begin time or end time is null or empty");
        }

        ResponseWrapper response = _httpClient.sendGet(requestUrl);
        return MessageListResult.fromResponse(response, MessageListResult.class);
    }

    /**
     * Get user's message list with cursor, the cursor will effective in 120 seconds.
     * And will return same count of messages as first request.
     * @param username Necessary parameter.
     * @param cursor First request will return cursor
     * @return MessageListResult
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public MessageListResult getUserMessagesByCursor(String username, String cursor)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        if (null != cursor) {
            String requestUrl = reportBaseUrl + v2_userPath + "/" + username + "/messages?cursor=" + cursor;
            ResponseWrapper response = _httpClient.sendGet(requestUrl);
            return MessageListResult.fromResponse(response, MessageListResult.class);
        } else {
            throw new IllegalArgumentException("the cursor parameter should not be null");
        }
    }

    /**
     * retract message
     * 消息撤回
     * @param username 用户名
     * @param messageId message id
     * @return No Content， Error Code：
     * 855001 out of retract message time, the effective time is within 3 minutes after sending message
     * 855003 the retract message is not exist
     * 855004 the message had been retracted
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ResponseWrapper retractMessage(String username, long messageId)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        return _httpClient.sendPost(_baseUrl + messagePath + "/" + username + "/" + messageId + "/retract", null);
    }

}
