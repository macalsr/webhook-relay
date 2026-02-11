package com.mariaribeiro.webhookrelay.application.port.out;

import com.mariaribeiro.webhookrelay.domain.event.Event;
import com.mariaribeiro.webhookrelay.domain.event.EventId;

import java.util.Optional;

public interface EventStore {
    Event save(Event event);

    Optional<Event> findBySourceAndEventKey(String source, String eventKey);

    Optional<Event> findById(EventId id); // ou EventId, se quiser manter purismo até aqui
}