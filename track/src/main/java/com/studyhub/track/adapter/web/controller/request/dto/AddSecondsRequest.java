package com.studyhub.track.adapter.web.controller.request.dto;

import java.time.LocalTime;
import java.util.UUID;

public record AddSecondsRequest(
        UUID modulId,
        Integer secondsToAdd
) {
}
