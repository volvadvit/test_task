package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.Converter;
import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.eagleinvsys.test.dto.ConvertibleCollection;
import org.eagleinvsys.test.dto.impl.DefaultConvertibleCollection;
import org.eagleinvsys.test.dto.impl.DefaultConvertibleMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvConverterTests {

    private static final String[] SAMPLE_ARRAY = new String[]{"1", "2", "3"};
    private static final String FILE_PATH = "/test_result.csv";
    private static final String CORRECT_RESULT =
            "\"title1\",\"title2\"\n\"1\",\"2\",\"3\"\n\"1\",\"2\",\"3\"";

    private static Converter converter;
    private static ConvertibleCollection collection;

    @BeforeAll
    static void setUp() throws FileNotFoundException {
        converter = new CsvConverter();
        collection = generateConvertableCollection();
    }

    @Test
    void testConvert() throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(FILE_PATH));
             OutputStream os = new BufferedOutputStream(new FileOutputStream(FILE_PATH)))
        {
            converter.convert(collection, os);
            int charByte;
            StringBuilder result = new StringBuilder("");
            while ((charByte=is.read()) != -1) {
                result.append((char) charByte);
            }
            assertEquals(CORRECT_RESULT, result.toString());
        }
    }

    private static DefaultConvertibleCollection generateConvertableCollection() {
        return new DefaultConvertibleCollection(
                List.of("title1", "title2"),
                List.of(new DefaultConvertibleMessage(SAMPLE_ARRAY),
                        new DefaultConvertibleMessage(SAMPLE_ARRAY)));
    }
}