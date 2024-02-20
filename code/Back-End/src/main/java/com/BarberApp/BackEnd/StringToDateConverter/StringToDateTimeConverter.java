package com.BarberApp.BackEnd.StringToDateConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDateTimeConverter implements Converter<String, ZonedDateTime> {

    @Override
    public ZonedDateTime convert(String source) {
        // Definisci il formato della data che stai aspettando
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        // Effettua la conversione della stringa nella data
        ZonedDateTime dateTime = ZonedDateTime.parse(source, formatter).withZoneSameInstant(ZoneOffset.UTC);
        return dateTime;
    }
}
