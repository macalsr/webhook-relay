package com.mariaribeiro.webhookrelay.domain.event;

import java.time.Instant;
import java.util.Objects;

public final class Event {
    private final EventId id;
    private final String source;
    private final String eventKey;
    private final String payload;
    private final Instant createdAt;
    private final EventProcessingState processing;

    private Event(EventId id,
                  String source,
                  String eventKey,
                  String payload,
                  Instant createdAt,
                  EventProcessingState processing) {
        this.id = Objects.requireNonNull(id);
        this.source = requireNonBlank(source, "source");
        this.eventKey = requireNonBlank(eventKey, "eventKey");
        this.payload = requireNonBlank(payload, "payload");
        this.createdAt = Objects.requireNonNull(createdAt);
        this.processing = Objects.requireNonNull(processing);
    }


    public static Event newEvent(String source, String eventKey, String payload, Instant now) {
        return new Event(EventId.newId(), source, eventKey, payload, now, EventProcessingState.initial(now));
    }

    public Event markProcessing(Instant now) {
        return new Event(id, source, eventKey, payload, createdAt, processing.markProcessing(now));
    }

    public Event markProcessed(Instant now) {
        return new Event(id, source, eventKey, payload, createdAt, processing.markProcessed(now));
    }

    public Event registerFailure(String error, Instant now) {
        return new Event(id, source, eventKey, payload, createdAt, processing.registerFailure(error, now));
    }

    public static Event rehydrate(
            EventId id,
            String source,
            String eventKey,
            String payload,
            Instant createdAt,
            EventProcessingState processing
    ) {
        return new Event(id, source, eventKey, payload, createdAt, processing);
    }

    public EventId id() { return id; }
    public String source() { return source; }
    public String eventKey() { return eventKey; }
    public String payload() { return payload; }
    public Instant createdAt() { return createdAt; }
    public EventProcessingState processing() { return processing; }

    private static String requireNonBlank(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " cannot be blank");
        return value;
    }
}