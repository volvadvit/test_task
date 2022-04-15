package org.eagleinvsys.test.dto.impl;

import org.eagleinvsys.test.dto.ConvertibleMessage;

public class DefaultConvertibleMessage implements ConvertibleMessage {

    private String[] message;

    public DefaultConvertibleMessage(String[] message) {
        this.message = message;
    }

    public DefaultConvertibleMessage() {}

    @Override
    public String getElement(String elementId) {
        return message[Integer.parseInt(elementId)];
    }
}
