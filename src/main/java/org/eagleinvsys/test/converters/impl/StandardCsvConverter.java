package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.StandardConverter;
import org.eagleinvsys.test.dto.ConvertibleMessage;
import org.eagleinvsys.test.dto.impl.DefaultConvertibleCollection;
import org.eagleinvsys.test.dto.impl.DefaultConvertibleMessage;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.eagleinvsys.test.constants.ConverterCommonConstants.ERROR_COLLECTION_MESSAGE;
import static org.eagleinvsys.test.constants.ConverterCommonConstants.ERROR_OUTPUTSTREAM_MESSAGE;

public class StandardCsvConverter implements StandardConverter {

    private final CsvConverter csvConverter;

    public StandardCsvConverter(CsvConverter csvConverter) {
        this.csvConverter = csvConverter;
    }

    /**
     * Converts given {@link List<Map>} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format. All maps must have the same set of keys
     * @param outputStream        output stream to write CSV conversion result as text to
     *
     * @throws IllegalArgumentException if any of params has bad format
     */
    @Override
    public void convert(List<Map<String, String>> collectionToConvert, OutputStream outputStream) throws IllegalArgumentException {
        if (collectionToConvert == null) {
            throw new IllegalArgumentException(ERROR_COLLECTION_MESSAGE);
        }
        if (outputStream == null) {
            throw new IllegalArgumentException(ERROR_OUTPUTSTREAM_MESSAGE);
        }
        Collection<String> headers = collectionToConvert.get(0).keySet();
        Iterable<ConvertibleMessage> records = collectionToConvert.stream()
                .map(map -> map.values().toArray(String[]::new))
                .map(DefaultConvertibleMessage::new)
                .collect(Collectors.toList());
        csvConverter.convert( new DefaultConvertibleCollection(headers, records), outputStream);
    }
}