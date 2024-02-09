package br.com.pedrocamargo.vrvendas.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ConverterTimeStampUtil {
    
    public static LocalDateTime converteTimeStampToLocalDateTime(String timeStampString){
        Instant instant = Instant.parse(timeStampString);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("America/Sao_Paulo"));
        return localDateTime;
    }
}
