package cn.jmessage.api.sensitiveword;

import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jiguang.common.utils.Preconditions;
import cn.jmessage.api.common.BaseClient;
import cn.jmessage.api.common.JMessageConfig;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Set;

public class SensitiveWordClient extends BaseClient {

    private String sensitiveWordPath;

    public SensitiveWordClient(String appKey, String masterSecret) {
        this(appKey, masterSecret, null, JMessageConfig.getInstance());
    }

    /**
     * Create a JMessage Base Client
     *
     * @param appkey       The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param proxy        The proxy, if there is no proxy, should be null.
     * @param config       The client configuration. Can use JMessageConfig.getInstance() as default.
     */
    public SensitiveWordClient(String appkey, String masterSecret, HttpProxy proxy, JMessageConfig config) {
        super(appkey, masterSecret, proxy, config);
        this.sensitiveWordPath = (String) config.get(JMessageConfig.SENSITIVE_WORD_PATH);
    }

    /**
     * Add sensitive words
     * @param words String array
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ResponseWrapper addSensitiveWords(String...words) throws APIConnectionException, APIRequestException {
        JsonArray array = new JsonArray();
        for (String word: words) {
            Preconditions.checkArgument(word.length() <= 10, "one word's max length is 10");
            array.add(new JsonPrimitive(word));
        }
        return _httpClient.sendPost(_baseUrl + sensitiveWordPath, array.toString());
    }

    /**
     * Add sensitive words
     * @param words String Set
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ResponseWrapper addSensitiveWords(Set<String> words) throws APIConnectionException, APIRequestException {
        JsonArray array = new JsonArray();
        for (String word : words) {
            Preconditions.checkArgument(word.length() <= 10, "one word's max length is 10");
            array.add(new JsonPrimitive(word));
        }
        return _httpClient.sendPost(_baseUrl + sensitiveWordPath, array.toString());
    }

    /**
     * Update sensitive word
     * @param newWord new word
     * @param oldWord old word
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ResponseWrapper updateSensitiveWord(String newWord, String oldWord)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(newWord.length() <= 10, "one word's max length is 10");
        Preconditions.checkArgument(oldWord.length() <= 10, "one word's max length is 10");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("new_word", newWord);
        jsonObject.addProperty("old_word", oldWord);
        return _httpClient.sendPut(_baseUrl + sensitiveWordPath, jsonObject.toString());
    }

    /**
     * Delete sensitive word
     * @param word word to be deleted
     * @return No content
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ResponseWrapper deleteSensitiveWord(String word) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(word.length() <= 10, "one word's max length is 10");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("word", word);
        return _httpClient.sendDelete(_baseUrl + sensitiveWordPath, jsonObject.toString());
    }

    /**
     * Get sensitive word list
     * @param start the begin of the list
     * @param count the count of the list
     * @return SensitiveWordListResult
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public SensitiveWordListResult getSensitiveWordList(int start, int count)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(start >= 0, "start should not less than 0");
        Preconditions.checkArgument(count <= 2000, "count should not bigger than 2000");
        ResponseWrapper responseWrapper = _httpClient.sendGet(_baseUrl + sensitiveWordPath + "?start=" + start + "&count=" + count);
        return _gson.fromJson(responseWrapper.responseContent, SensitiveWordListResult.class);
    }

    /**
     * Update sensitive word status
     * @param status 1 represent turn on filtering, 0 represent turn off.
     * @return No content
     * @throws APIConnectionException
     * @throws APIRequestException
     */
    public ResponseWrapper updateSensitiveWordStatus(int status) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(status == 0 || status == 1, "status should be 0 or 1");
        return _httpClient.sendPut(_baseUrl + sensitiveWordPath + "/status?status=" + status, null);
    }

    /**
     * Get sensitive word status
     * @return status of sensitive word
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public SensitiveWordStatusResult getSensitiveWordStatus() throws APIConnectionException, APIRequestException {
        ResponseWrapper responseWrapper = _httpClient.sendGet(_baseUrl + sensitiveWordPath + "/status");
        return _gson.fromJson(responseWrapper.responseContent, SensitiveWordStatusResult.class);
    }
}
