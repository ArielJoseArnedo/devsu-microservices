package co.com.devsu.clients.infrastructure.util;

import io.vavr.control.Option;

import java.util.Arrays;

public interface OptionUtil {

    static <T> T updateOrElse(T oldValue, T newValue) {
        return Option.of(newValue)
          .map(newV -> newV.equals(oldValue) ? oldValue : newV)
          .getOrElse(oldValue);
    }

    static <T> T updateOrElse(T oldValue, T newValue, T... notAllowed) {
        return Option.of(newValue)
          .filter(newV -> !Arrays.asList(notAllowed).contains(newV))
          .map(newV -> newV.equals(oldValue) ? oldValue : newV)
          .getOrElse(oldValue);
    }
}
