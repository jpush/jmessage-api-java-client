package cn.jmessage.api.reportv2;

import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.utils.TimeUtils;
import cn.jmessage.api.common.BaseClient;
import cn.jmessage.api.common.JMessageConfig;
import cn.jmessage.api.message.MessageListResult;
import cn.jmessage.api.user.UserStateListResult;
import cn.jmessage.api.utils.StringUtils;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Rest Report V2 相关接口, 文档链接： https://docs.jiguang.cn/jmessage/server/rest_api_im_report_v2/
 * 之前 MessageClient 下的获取历史消息接口全部转移到这里
 */
public class ReportClient extends BaseClient {

    private String mBaseReportPath;
    private String mV2MessagePath;
    private String mV2UserPath;
    private String mV2GroupPath;
    private String mV2StatisticPath;

    public ReportClient(String appKey, String masterSecret) {
        this(appKey, masterSecret, null, JMessageConfig.getInstance());
    }

    public ReportClient(String appKey, String masterSecret, HttpProxy proxy) {
        this(appKey, masterSecret, proxy, JMessageConfig.getInstance());
    }

    /**
     * Create a JMessage Base Client
     *
     * @param appKey       The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param proxy        The proxy, if there is no proxy, should be null.
     * @param config       The client configuration. Can use JMessageConfig.getInstance() as default.
     */
    public ReportClient(String appKey, String masterSecret, HttpProxy proxy, JMessageConfig config) {
        super(appKey, masterSecret, proxy, config);
        mBaseReportPath = (String) config.get(JMessageConfig.API_REPORT_HOST_NAME);
        mV2MessagePath = (String) config.get(JMessageConfig.V2_MESSAGE_PATH);
        mV2UserPath = (String) config.get(JMessageConfig.V2_USER_PATH);
        mV2GroupPath = (String) config.get(JMessageConfig.V2_GROUP_PATH);
        mV2StatisticPath = (String) config.get(JMessageConfig.V2_STATISTIC_PATH);
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
    public MessageListResult v2GetMessageList(int count, String begin_time, String end_time)
            throws APIConnectionException, APIRequestException {
        if(count <= 0 || count > 1000) {
            throw new IllegalArgumentException("count must more than 0 and less than 1001");
        }

        String requestUrl = mBaseReportPath + mV2MessagePath + "?count=" + count;
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
    public MessageListResult v2GetMessageListByCursor(String cursor)
            throws APIConnectionException, APIRequestException {
        if (null != cursor) {
            String requestUrl = mBaseReportPath + mV2MessagePath + "?cursor=" + cursor;
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
    public MessageListResult v2GetUserMessages(String username, int count, String begin_time, String end_time)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);

        if(count <= 0 || count > 1000) {
            throw new IllegalArgumentException("count must more than 0 and less than 1001");
        }

        String requestUrl = mBaseReportPath + mV2UserPath + "/" + username + "/messages?count=" + count;
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
    public MessageListResult v2GetUserMessagesByCursor(String username, String cursor)
            throws APIConnectionException, APIRequestException {
        StringUtils.checkUsername(username);
        if (null != cursor) {
            String requestUrl = mBaseReportPath + mV2UserPath + "/" + username + "/messages?cursor=" + cursor;
            ResponseWrapper response = _httpClient.sendGet(requestUrl);
            return MessageListResult.fromResponse(response, MessageListResult.class);
        } else {
            throw new IllegalArgumentException("the cursor parameter should not be null");
        }
    }

    /**
     * Get group message list
     * @param groupId group id
     * @param count message list count
     * @param begin_time Necessary parameter. The format must follow by 'yyyy-MM-dd HH:mm:ss'
     * @param end_time Necessary parameter. The format must follow by 'yyyy-MM-dd HH:mm:ss'
     * @return {@link MessageListResult}
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public MessageListResult v2GetGroupMessageList(long groupId, int count, String begin_time, String end_time)
            throws APIConnectionException, APIRequestException{
        if(count <= 0 || count > 1000) {
            throw new IllegalArgumentException("count must more than 0 and less than 1001");
        }
        String requestUrl = mBaseReportPath + mV2GroupPath + "/" + groupId + "/messages?count=" + count;
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

    // ==========================       VIP Only     =======================
    /**
     * Get user statistic, now time unit only supports DAY
     * @param startTime start time, format: yyyy-MM-dd
     * @param duration 0-60
     * @return {@link UserStatListResult}
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public UserStatListResult getUserStatistic(String startTime, int duration)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(verifyDateFormat("DAY", startTime), "Illegal date format");
        Preconditions.checkArgument(0 <= duration && duration <= 60, " 0 <= duration <= 60");
        String url = mBaseReportPath + mV2StatisticPath + "/users?time_unit=DAY&start=" + startTime + "&duration=" + duration;
        ResponseWrapper responseWrapper = _httpClient.sendGet(url);
        return UserStatListResult.fromResponse(responseWrapper);
    }

    /**
     * Get message statistic. Detail instructions please refer to https://docs.jiguang.cn/jmessage/server/rest_api_im_report_v2/#_6
     * @param timeUnit MUST be one of HOUR, DAY, MONTH
     * @param start start time, when timeUnit is HOUR, format: yyyy-MM-dd HH,
     * @param duration depends on timeUnit, the duration has limit
     * @return {@link MessageStatListResult}
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public MessageStatListResult getMessageStatistic(String timeUnit, String start, int duration)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(verifyDateFormat(timeUnit, start), "Time format error, please check your argument");
        if (timeUnit.equals("HOUR")) {
            Preconditions.checkArgument(0 <= duration && duration <= 24, "time unit is HOUR, duration must between 0 and 24 ");
        } else if (timeUnit.equals("DAY")) {
            Preconditions.checkArgument(0 <= duration && duration <= 60, "time unit is DAY, duration must between 0 and 60");
        } else if (timeUnit.equals("MONTH")) {
            Preconditions.checkArgument(0 <= duration && duration <= 2, "time unit is MONTH, duration must between 0 and 2");
        } else throw new IllegalArgumentException("Time unit error");
        String url = mBaseReportPath + mV2StatisticPath + "/messages?time_unit=" + timeUnit + "&start=" + start + "&duration=" + duration;
        ResponseWrapper responseWrapper = _httpClient.sendGet(url);
        return MessageStatListResult.fromResponse(responseWrapper, MessageStatListResult.class);
    }

    /**
     * Get group statistic, time unit only supports DAY now.
     * @param start Format: yyyy-MM-dd
     * @param duration 0 <= duration <= 60
     * @return {@link GroupStatListResult}
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public GroupStatListResult getGroupStatistic(String start, int duration)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(verifyDateFormat("DAY", start), "Illegal date format");
        Preconditions.checkArgument(0 <= duration && duration <= 60, " 0 <= duration <= 60");
        String url = mBaseReportPath + mV2StatisticPath + "/groups?time_unit=DAY&start=" + start + "&duration=" + duration;
        ResponseWrapper responseWrapper = _httpClient.sendGet(url);
        return GroupStatListResult.fromResponse(responseWrapper);
    }

    private boolean verifyDateFormat(String timeUnit, String formatStr) {
        try {
            String format;
            if (timeUnit.equals("HOUR")) {
                format = "yyyy-MM-dd HH";
            } else if (timeUnit.equals("DAY")) {
                format = "yyyy-MM-dd";
            } else format = "yyyy-MM";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            simpleDateFormat.parse(formatStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

}
