package cn.jmessage.api.message;

public enum MessageType {
    TEXT("text"),
    IMAGE("image"),
    CUSTOM("custom");

    private String value;

    private MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
