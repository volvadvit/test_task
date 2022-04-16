package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.Converter;
import org.eagleinvsys.test.dto.ConvertibleCollection;
import org.eagleinvsys.test.dto.impl.DefaultConvertibleCollection;
import org.eagleinvsys.test.dto.impl.DefaultConvertibleMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.eagleinvsys.test.constants.ConverterCommonConstants.ERROR_OUTPUTSTREAM_MESSAGE;

class CsvConverterTest {

    private static final String[] SAMPLE_ARRAY = new String[]{"1", "2"};
    private static final String FILE_PATH = System.getProperty("user.dir") + "/build/test-results/csv/test.csv";
    private static final String CORRECT_RESULT = "\"title1\",\"title2\"\n\"1\",\"2\"\n\"1\",\"2\"\n\"1\",\"2\"\n";

    private static Converter converter;
    private static ConvertibleCollection collection;

    @BeforeAll
    static void beforeAll() {
        converter = new CsvConverter();
        collection = generateConvertableCollection();
    }

    @BeforeEach
    void setUp() throws IOException {
        createFile().toFile();
    }

    @Test
    void testConvert() throws IOException {
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(FILE_PATH));
             InputStream is = new BufferedInputStream(new FileInputStream(FILE_PATH)))
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

    @Test
    void testConverterException() {
        IllegalArgumentException thrown = assertThrows(
                  IllegalArgumentException.class,
                () -> converter.convert(collection, null),
                "Expected convert() to throw exception, but it didn't"
        );
        assertEquals(ERROR_OUTPUTSTREAM_MESSAGE, thrown.getMessage());
    }

    private static DefaultConvertibleCollection generateConvertableCollection() {
        return new DefaultConvertibleCollection(
                List.of("title1", "title2"),
                List.of(new DefaultConvertibleMessage(SAMPLE_ARRAY),
                        new DefaultConvertibleMessage(SAMPLE_ARRAY),
                        new DefaultConvertibleMessage(SAMPLE_ARRAY)));
    }

    private Path createFile() throws IOException {
        Path uri = Paths.get(FILE_PATH);
        Files.deleteIfExists(uri);
        Files.createDirectories(uri.getParent());
        Path result = Files.createFile(uri);
        return result;
    }
}