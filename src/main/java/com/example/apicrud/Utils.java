package com.example.apicrud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
@Component
public class Utils {

    public static final ZoneId ZONE_UTC = ZoneId.of("UTC");
    public static final ZoneId ZONE_JAKARTA = ZoneId.of("UTC+07:00");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String ISO_INDONESIA = "ID";
    private static final Locale mLocale = new Locale("en", ISO_INDONESIA);

    public static LocalDateTime getCurrentDateTimeUTC() {
        ZonedDateTime zoned = ZonedDateTime.ofInstant(Instant.now(), ZONE_UTC);
        return zoned.toLocalDateTime();
    }
}
