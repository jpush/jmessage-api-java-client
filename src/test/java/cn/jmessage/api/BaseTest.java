package cn.jmessage.api;


import com.google.gson.JsonParser;

public class BaseTest {

    protected static final String APP_KEY ="c12bb0902ad1c069ffb67667";
    protected static final String CROSS_APP_KEY = "4f7aef34fb361292c566a1cd";
    protected static final String MASTER_SECRET = "bcf206e4dde5d40875c16a51";

    protected static final String MORE_THAN_64 = "a0123456789012345678901234567890123456789012345678901234567890123";

    protected static final String MORE_THAN_128 =  "a012345678901234567890123456789012345678901234567890123456789" +
            "01234567890123456789012345678901234567890123456789012345678901234567";

    protected static final String MORE_THAN_250 =
            "a0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789" +
            "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789" +
            "01234567890123456789012345678901234567890123456789" ;

    protected static JsonParser parser = new JsonParser();

    protected static final String JUNIT_ADMIN = "junit_admin";

    protected static final String JUNIT_USER = "junit_no_delete";

    protected static final String JUNIT_USER1 = "junit_no_delete1";

    protected static final String JUNIT_USER2 = "junit_no_delete2";

    protected static final long JUNIT_GID1 = 23160755;
    protected static final long JUNIT_GID2 = 10055373;

}
