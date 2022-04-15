package org.eagleinvsys.test.dto.impl;

import org.eagleinvsys.test.dto.ConvertibleCollection;
import org.eagleinvsys.test.dto.ConvertibleMessage;

import java.util.Collection;

public class DefaultConvertibleCollection implements ConvertibleCollection {

    private Collection<String> headers;
    private Iterable<ConvertibleMessage> records;

    public DefaultConvertibleCollection() {}

    public DefaultConvertibleCollection(Collection<String> headers, Iterable<ConvertibleMessage> records) {
        this.headers = headers;
        this.records = records;
    }

    @Override
    public Collection<String> getHeaders() {
        return headers;
    }

    @Override
    public Iterable<ConvertibleMessage> getRecords() {
        return records;
    }
}
