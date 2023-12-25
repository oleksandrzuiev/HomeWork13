package ua.hillel.qaauto;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("DateConverter Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DateConverterTest {

    @BeforeAll
    public static void initAll() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("Testing is finished.");
    }

    @BeforeEach
    public void init(TestInfo testInfo) {
        System.out.println("Test - " + testInfo.getDisplayName() + " starts.");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Test is finished");
    }

    @ParameterizedTest
    @Order(1)
    @MethodSource("factoryMethodWithArguments")
    @DisplayName("Positive convert date test with method source.")
    void convertDatePositiveTest(String inputDate, String inputFormat, String outputFormat, String expectedOutputDate) {
        String actualOutputDate = DateConverter.convertDate(inputDate, inputFormat, outputFormat);
        assertEquals(expectedOutputDate, actualOutputDate);
    }

    public static Stream<Arguments> factoryMethodWithArguments() {
        return Stream.of(
                Arguments.arguments("02-10-1995", "dd-MM-yyyy", "dd-MM-yyyy", "02-10-1995"),
                Arguments.arguments("10/02/1995", "MM/dd/yyyy", "dd MMM yyyy", "02 Oct 1995"),
                Arguments.arguments("1995-10-02", "yyyy-MM-dd", "dd MMMM yyyy", "02 October 1995")
        );
    }

    @ParameterizedTest
    @Order(2)
    @CsvSource({
            "10 Oct 1995, dd MMMM yyyy, dd/MM/yyyy",
            "10/02/1995, true, dd MMM yyyy",
            "1995-10-02, dd MMMM yyyy, dd-MM-yyyy"})
    @DisplayName("Negative Convert Date Test with csv source.")
    void convertDateNegativeTest(String inputDate, String inputFormat, String outputFormat) {
        assertThrows(Exception.class, () -> DateConverter.convertDate(inputDate, inputFormat, outputFormat));
    }

    @ParameterizedTest
    @Order(3)
    @CsvSource({
            "10 Oct 1995, dd MMM yyyy",
            "10/02/1995, MM/dd/yyyy",
            "1995-10-02, yyyy-MM-dd"})
    @DisplayName("Positive detect date test with csv source.")
    void detectDateFormatPositiveTest(String inputDate, String expectedResult) {
        assertEquals(expectedResult, DateConverter.detectDateFormat(inputDate));
    }


    @ParameterizedTest
    @Order(4)
    @ValueSource(strings = {
            "",
            "true",
            "02-10-95",
            "00/00/0000",
            "02 1995",
    })
    @DisplayName("Negative detect format date test with value source.")
    public void testDetectDateFormat_Negative(String inputDate) {
        assertNull(DateConverter.detectDateFormat(inputDate));
    }
}
