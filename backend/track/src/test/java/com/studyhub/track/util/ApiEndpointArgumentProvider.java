package com.studyhub.track.util;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import java.util.stream.Stream;

public class ApiEndpointArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of("chart"),
                Arguments.of("get-total-study-time"),
                Arguments.of("get-average-study-time-per-day"),
                Arguments.of("get-total-study-time-per-semester"),
                Arguments.of("get-number-active-module"),
                Arguments.of("get-number-not-active-module"),
                Arguments.of("get-max-studied-modul"),
                Arguments.of("get-min-studied-modul")
        );
    }
}