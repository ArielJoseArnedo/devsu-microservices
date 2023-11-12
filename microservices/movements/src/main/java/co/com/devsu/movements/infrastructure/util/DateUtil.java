package co.com.devsu.movements.infrastructure.util;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@UtilityClass
public class DateUtil {

    public static ZonedDateTime fromTimestamp(Timestamp timestamp) {
        return Try.of(() -> timestamp.toInstant().atZone(ZoneId.of("Z")))
          .getOrNull();
    }

    public static Timestamp fromZonedDateTime(ZonedDateTime zonedDateTime) {
        return Try.of(() -> new Timestamp(zonedDateTime.toInstant().toEpochMilli()))
          .getOrNull();
    }
}
