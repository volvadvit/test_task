package org.eagleinvsys.test.converters.impl;

import com.opencsv.CSVWriter;
import org.eagleinvsys.test.converters.Converter;
import org.eagleinvsys.test.dto.ConvertibleCollection;
import org.eagleinvsys.test.dto.ConvertibleMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Iterator;

public class CsvConverter implements Converter {

    /**
     * Converts given {@link ConvertibleCollection} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format
     * @param outputStream        output stream to write CSV conversion result as text to
     */
    @Override
    public void convert(ConvertibleCollection collectionToConvert, OutputStream outputStream) {
        try(CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream), DEFAULT_SPLITERATOR)) {
            Collection<String> headers = collectionToConvert.getHeaders();
            int headersCount = headers.size();

            String[] headersArray = headers.toArray(String[]::new);
            writer.writeNext(headersArray);

            Iterable<ConvertibleMessage> records = collectionToConvert.getRecords();
            Iterator<ConvertibleMessage> recordsIterator = records.iterator();
            String[] recordsArray;
            while (recordsIterator.hasNext()) {
                recordsArray = new String[headersCount];
                for (int i = 0; i < headersCount; i++) {
                    recordsArray[i] = recordsIterator.next().getElement(String.valueOf(i));
                }
                writer.writeNext(recordsArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}