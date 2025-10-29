package com.studyhub.track.service;

import com.studyhub.track.application.service.SessionBeendetEventRepository;
import com.studyhub.track.application.service.SessionEventsService;
import com.studyhub.track.domain.model.session.SessionBeendetEvent;
import com.studyhub.track.domain.model.session.SessionBewertung;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionEventsServiceTest {

    @Mock
    private SessionBeendetEventRepository eventRepository;

    @InjectMocks
    private SessionEventsService sessionEventsService;

    @Test
    @DisplayName("Ein neues Event wird erfolgreich gespeichert")
    void test_1() {
        SessionBeendetEvent event = new SessionBeendetEvent(UUID.randomUUID(), UUID.randomUUID(), "peter77", LocalDateTime.now(), new SessionBewertung(1,1,1), false);
        when(eventRepository.save(event)).thenReturn(event);

        boolean saved = sessionEventsService.save(event);

        assertThat(saved).isTrue();
    }

    @Test
    @DisplayName("Wenn ein neues Event nicht in der Datenbank abgespeichert wurde, wird false zurückgegeben")
    void test_2() {
        SessionBeendetEvent event = new SessionBeendetEvent(UUID.randomUUID(), UUID.randomUUID(),"peter77", LocalDateTime.now(), new SessionBewertung(1,1,1), false);
        when(eventRepository.save(event)).thenReturn(null);

        boolean saved = sessionEventsService.save(event);

        assertThat(saved).isFalse();
    }
}
