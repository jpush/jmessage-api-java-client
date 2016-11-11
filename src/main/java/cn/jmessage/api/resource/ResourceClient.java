package cn.jmessage.api.resource;

import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.common.BaseClient;
import cn.jmessage.api.common.JMessageConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ResourceClient extends BaseClient {

    private static Logger LOG = LoggerFactory.getLogger(ResourceClient.class);

    private String resourcePath;
    private String authCode;


    public ResourceClient(String appkey, String masterSecret) {
        this(appkey, masterSecret, null, JMessageConfig.getInstance());
    }

    public ResourceClient(String appkey, String masterSecret, HttpProxy proxy) {
        this(appkey, masterSecret, proxy, JMessageConfig.getInstance());
    }

    /**
     * Create a JMessage Base Client
     *
     * @param appkey       The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param proxy        The proxy, if there is no proxy, should be null.
     * @param config       The client configuration. Can use JMessageConfig.getInstance() as default.
     */
    public ResourceClient(String appkey, String masterSecret, HttpProxy proxy, JMessageConfig config) {
        super(appkey, masterSecret, proxy, config);
        this.resourcePath = (String) config.get(JMessageConfig.RESOURCE_PATH);
        this.authCode = ServiceHelper.getBasicAuthorization(appkey, masterSecret);
    }

    /**
     * Download file with mediaId, will return DownloadResult which include url.
     * @param mediaId Necessary
     * @return DownloadResult
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public DownloadResult downloadFile(String mediaId)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != mediaId, "mediaId is necessary");

        ResponseWrapper response = _httpClient.sendGet(_baseUrl + resourcePath + "?mediaId=" + mediaId);
        return DownloadResult.fromResponse(response, DownloadResult.class);
    }

    /**
     * Upload file, only support image file(jpg, bmp, gif, png) currently,
     * file size should not larger than 8M.
     * @param name Necessary The file name which saved in server
     * @param path Necessary, the native path of the file you want to upload
     * @return UploadResult
     */
    public UploadResult uploadFile(String name, String path)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != path, "filename is necessary");
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            long fileSize = file.length();
            if (fileSize > 8 * 1024 * 1024) {
                throw new IllegalArgumentException("File size should not larger than 8M");
            }
            try {
                // 换行符
                final String newLine = "\r\n";
                final String boundaryPrefix = "--";
                // 定义数据分隔线
                String BOUNDARY = "========7d4a6d158c9";
                URL url = new URL(_baseUrl + resourcePath + "?type=image");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("Charset", "UTF-8");
                conn.setRequestProperty("Authorization", this.authCode);
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
                OutputStream out = new DataOutputStream(conn.getOutputStream());
                StringBuilder sb = new StringBuilder();
                sb.append(boundaryPrefix);
                sb.append(BOUNDARY);
                sb.append(newLine);
                sb.append("Content-Disposition: form-data;name=\"" + name + "\";filename=\"" + path + "\"" + newLine);
                sb.append("Content-Type:application/octet-stream");
                // 参数头设置完以后需要两个换行，然后才是参数内容
                sb.append(newLine);
                sb.append(newLine);
                out.write(sb.toString().getBytes());
                DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
                byte[] bufferOut = new byte[1024];
                int bytes = 0;
                while ((bytes = dataInputStream.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                out.write(newLine.getBytes());
                dataInputStream.close();
                // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
                byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                        .getBytes();
                // 写上结尾标识
                out.write(end_data);
                out.flush();
                out.close();
                LOG.debug("Send request - POST"  + " " + url);

                int status1 = conn.getResponseCode();
                StringBuffer stringBuffer = new StringBuffer();
                InputStream in = null;
                if(status1 / 100 == 2) {
                    in = conn.getInputStream();
                } else {
                    in = conn.getErrorStream();
                }
                if(null != in) {
                    InputStreamReader responseContent = new InputStreamReader(in, "UTF-8");
                    char[] quota = new char[1024];

                    int remaining;
                    while((remaining = responseContent.read(quota)) > 0) {
                        stringBuffer.append(quota, 0, remaining);
                    }
                }
                ResponseWrapper wrapper = new ResponseWrapper();
                String responseContent1 = stringBuffer.toString();
                wrapper.responseCode = status1;
                wrapper.responseContent = responseContent1;
                String quota1 = conn.getHeaderField("X-Rate-Limit-Limit");
                String remaining1 = conn.getHeaderField("X-Rate-Limit-Remaining");
                String reset = conn.getHeaderField("X-Rate-Limit-Reset");
                wrapper.setRateLimit(quota1, remaining1, reset);
                if(status1 >= 200 && status1 < 300) {
                    LOG.debug("Succeed to get response OK - responseCode:" + status1);
                    LOG.debug("Response Content - " + responseContent1);
                } else {
                    if(status1 < 300 || status1 >= 400) {
                        LOG.warn("Got error response - responseCode:" + status1 + ", responseContent:" + responseContent1);
                        switch(status1) {
                            case 400:
                                LOG.error("Your request params is invalid. Please check them according to error message.");
                                wrapper.setErrorObject();
                                break;
                            case 401:
                                LOG.error("Authentication failed! Please check authentication params according to docs.");
                                wrapper.setErrorObject();
                                break;
                            case 403:
                                LOG.error("Request is forbidden! Maybe your appkey is listed in blacklist or your params is invalid.");
                                wrapper.setErrorObject();
                                break;
                            case 404:
                                LOG.error("Request page is not found! Maybe your params is invalid.");
                                wrapper.setErrorObject();
                                break;
                            case 410:
                                LOG.error("Request resource is no longer in service. Please according to notice on official website.");
                                wrapper.setErrorObject();
                            case 429:
                                LOG.error("Too many requests! Please review your appkey\'s request quota.");
                                wrapper.setErrorObject();
                                break;
                            case 500:
                            case 502:
                            case 503:
                            case 504:
                                LOG.error("Seems encountered server error. Maybe JPush is in maintenance? Please retry later.");
                                break;
                            default:
                                LOG.error("Unexpected response.");
                        }

                        throw new APIRequestException(wrapper);
                    }

                    LOG.warn("Normal response but unexpected - responseCode:" + status1 + ", responseContent:" + responseContent1);
                }
                return UploadResult.fromResponse(wrapper, UploadResult.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("File name is invalid, please check again");
        }
        return null;
    }





}
