package cn.jmessage.api.examples;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.resource.DownloadResult;
import cn.jmessage.api.resource.ResourceClient;
import cn.jmessage.api.resource.UploadResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceExample {

    protected static final Logger LOG = LoggerFactory.getLogger(ResourceExample.class);

    private static final String appkey = "242780bfdd7315dc1989fe2b";
    private static final String masterSecret = "2f5ced2bef64167950e63d13";

    public static void main(String[] args) {

    }

    public void testDownloadFile() {
        ResourceClient client = new ResourceClient(appkey, masterSecret);
        try {
            DownloadResult result = client.downloadFile("qiniu/image/r/48449FBC073184BDB5B50964D45FC8C3");
            String url = result.getUrl();
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }

    }

    public void testUploadFile() {
        ResourceClient client = new ResourceClient(appkey, masterSecret);
        try {
            UploadResult result = client.uploadFile("icon", "G:\\MyDownloads\\discourse-icon.png");
            String mediaId = result.getMediaId();
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
}
