package com.genymobile.scrcpy.control;

public final class DeviceMessage {

    public static final int TYPE_CLIPBOARD = 0;
    public static final int TYPE_ACK_CLIPBOARD = 1;
    public static final int TYPE_UHID_OUTPUT = 2;

    private int type;
    private String text;
    private long sequence;
    private int id;
    private byte[] data;

    private DeviceMessage() {
    }

    public static DeviceMessage createClipboard(String text) {
        DeviceMessage event = new DeviceMessage();
        event.type = TYPE_CLIPBOARD;
        event.text = text;
        return event;
    }

    public static DeviceMessage createAckClipboard(long sequence) {
        DeviceMessage event = new DeviceMessage();
        event.type = TYPE_ACK_CLIPBOARD;
        event.sequence = sequence;
        return event;
    }

    public static DeviceMessage createUhidOutput(int id, byte[] data) {
        DeviceMessage event = new DeviceMessage();
        event.type = TYPE_UHID_OUTPUT;
        event.id = id;
        event.data = data;
        return event;
    }

    public int getType() {
        return type;
    }

    public String getTypeStr() {
        switch (type) {
            case TYPE_CLIPBOARD:
                return "TYPE_CLIPBOARD";
            case TYPE_ACK_CLIPBOARD:
                return "TYPE_ACK_CLIPBOARD";
            case TYPE_UHID_OUTPUT:
                return "TYPE_UHID_OUTPUT";
            default:
                return "UNKNOWN";
        }
    }

    public String getText() {
        return text;
    }

    public long getSequence() {
        return sequence;
    }

    public int getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "DeviceMessage{" +
                "type=" + getTypeStr() + ", " +
                "text='" + text + '\'' + ", " +
                "sequence=" + sequence + ", " +
                "id=" + id + ", " +
                "data=" + data +
                '}';
    }
}
