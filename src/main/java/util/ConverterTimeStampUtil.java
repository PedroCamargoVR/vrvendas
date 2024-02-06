package util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ConverterTimeStampUtil {
    
    public static LocalDateTime converteTimeStampToLocalDateTime(String timeStampString){
        Instant instant = Instant.parse(timeStampString);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("America/Sao_Paulo"));
        return localDateTime;
    }
}
