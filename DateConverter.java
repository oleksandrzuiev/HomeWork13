package ua.hillel.qaauto;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateConverter {
    private static final List<String> DATE_FORMATS = Arrays.asList(
            "dd-MM-yyyy",
            "MM/dd/yyyy",
            "yyyy-MM-dd",
            "dd MMM yyyy",
            "dd MMMM yyyy"
    );

    /**
     * Converts a date from one format to another.
     *
     * @param date        the date string to convert
     * @param inputFormat  the format of the input date string
     * @param outputFormat the desired format of the output date string
     * @return the converted date string
     */
    public static String convertDate(String date, String inputFormat, String outputFormat) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputFormat);
        LocalDate localDate = LocalDate.parse(date, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputFormat);
        return localDate.format(outputFormatter);
    }

    /**
     * Detects the format of a given date string.
     *
     * @param dateStr the date string to detect its format
     * @return the string of detected format, or null if not detected
     */
    public static String detectDateFormat(String dateStr) {
        for (String format : DATE_FORMATS) {
            try {
                LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(format));
                return format;
            } catch (DateTimeParseException e) {}
        }
        return null;
    }

}
