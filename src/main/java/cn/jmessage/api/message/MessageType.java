package cn.jmessage.api.message;

public enum MessageType {
    TEXT("text"),
    IMAGE("image"),
    VOICE("voice"),
    CUSTOM("custom");

    private String value;

    private MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
