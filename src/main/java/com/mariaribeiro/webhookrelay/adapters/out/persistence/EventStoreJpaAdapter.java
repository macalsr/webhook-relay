package com.mariaribeiro.webhookrelay.adapters.out.persistence;

import com.mariaribeiro.webhookrelay.application.port.out.EventStore;
import com.mariaribeiro.webhookrelay.domain.event.Event;
import com.mariaribeiro.webhookrelay.domain.event.EventId;
import com.mariaribeiro.webhookrelay.domain.event.EventProcessingState;
import com.mariaribeiro.webhookrelay.domain.event.EventStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EventStoreJpaAdapter implements EventStore {

    private final SpringDataEventJpaRepository repo;

    public EventStoreJpaAdapter(SpringDataEventJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Event save(Event event) {
        EventJpaEntity entity = toEntity(event);
        EventJpaEntity saved = repo.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Event> findBySourceAndEventKey(String source, String eventKey) {
        return repo.findBySourceAndEventKey(source, eventKey).map(this::toDomain);
    }

    @Override
    public Optional<Event> findById(EventId id) {
        return repo.findById(id.value()).map(this::toDomain);
    }

    private EventJpaEntity toEntity(Event e) {
        EventJpaEntity j = new EventJpaEntity();
        j.setId(e.id().value());
        j.setSource(e.source());
        j.setEventKey(e.eventKey());
        j.setPayload(e.payload());
        j.setStatus(EventStatusJpa.valueOf(e.processing().status().name()));
        j.setAttempts(e.processing().attempts());
        j.setLastError(e.processing().lastError().orElse(null));
        j.setCreatedAt(e.createdAt());
        j.setUpdatedAt(e.processing().getUpdatedAt());
        return j;
    }

    private Event toDomain(EventJpaEntity j) {
        EventId id = new EventId(j.getId());
        EventProcessingState state = EventProcessingState.fromPersistence(
                EventStatus.valueOf(j.getStatus().name()),
                j.getAttempts(),
                j.getLastError(),
                j.getUpdatedAt()
        );

        return Event.rehydrate(
                id,
                j.getSource(),
                j.getEventKey(),
                j.getPayload(),
                j.getCreatedAt(),
                state
        );
    }
}