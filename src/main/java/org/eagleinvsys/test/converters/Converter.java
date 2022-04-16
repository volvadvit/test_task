package org.eagleinvsys.test.converters;

import org.eagleinvsys.test.dto.ConvertibleCollection;

import java.io.OutputStream;

public interface Converter {

    /**
     * Default spliterator for all csv convertors
     */
    char DEFAULT_SPLITERATOR = ',';

    /**
     * Converts given {@link ConvertibleCollection} and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert
     * @param outputStream        output stream to write results to
     *
     * @throws IllegalArgumentException if any of params has bad format
     */
    void convert(ConvertibleCollection collectionToConvert, OutputStream outputStream) throws IllegalArgumentException;

}