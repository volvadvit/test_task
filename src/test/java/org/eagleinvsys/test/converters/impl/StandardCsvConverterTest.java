package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.StandardConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.eagleinvsys.test.constants.ConverterCommonConstants.ERROR_OUTPUTSTREAM_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StandardCsvConverterTest {

    private static final String FILE_PATH = System.getProperty("user.dir") + "/build/test-results/csv/test.csv";
    private static final String CORRECT_RESULT = "\"title1\",\"title2\"\n\"1\",\"2\"\n\"3\",\"4\"\n";

    private static StandardConverter standardConverter;
    private static List<Map<String, String>> collection;

    @BeforeAll
    static void beforeAll() {
        standardConverter = new StandardCsvConverter(new CsvConverter());
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
            standardConverter.convert(collection, os);
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
                () -> standardConverter.convert(collection, null),
                "Expected convert() to throw exception, but it didn't"
        );
        assertEquals(ERROR_OUTPUTSTREAM_MESSAGE, thrown.getMessage());
    }

    private static List<Map<String, String>> generateConvertableCollection() {
        return List.of(
                Map.of("title1", "1", "title2", "2"),
                Map.of("title1", "3", "title2", "4")
        );
    }

    private Path createFile() throws IOException {
        Path uri = Paths.get(FILE_PATH);
        Files.deleteIfExists(uri);
        Files.createDirectories(uri.getParent());
        Path result = Files.createFile(uri);
        return result;
    }
}