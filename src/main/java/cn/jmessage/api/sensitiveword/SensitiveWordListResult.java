package cn.jmessage.api.sensitiveword;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class SensitiveWordListResult extends BaseResult {

    @Expose Integer start;
    @Expose Integer count;
    @Expose Integer total;
    @Expose List<Word> words = new ArrayList<Word>();

    public static class Word {
        @Expose String name;
        @Expose String itime;

        public String getName() {
            return name;
        }

        public String getItime() {
            return itime;
        }
    }

    public Integer getStart() {
        return start;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getTotal() {
        return total;
    }

    public List<Word> getWords() {
        return words;
    }
}
