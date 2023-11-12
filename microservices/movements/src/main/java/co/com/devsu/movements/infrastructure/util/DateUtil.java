package co.com.devsu.movements.infrastructure.util;

import io.vavr.control.Try;
import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@UtilityClass
public class DateUtil {

    public static ZonedDateTime fromTimestamp(Timestamp timestamp) {
        return Try.of(() -> timestamp.toInstant().atZone(ZoneId.of("Z")))
          .onFailure(t -> System.out.println("Error " + t.getMessage()))
          .getOrNull();
    }

    public static Timestamp fromZonedDateTime(ZonedDateTime zonedDateTime) {
        return Try.of(() -> new Timestamp(zonedDateTime.toInstant().toEpochMilli()))
          .getOrNull();
    }
}
