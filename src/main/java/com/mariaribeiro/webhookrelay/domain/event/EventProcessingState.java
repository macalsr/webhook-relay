package com.mariaribeiro.webhookrelay.domain.event;

import lombok.Data;

import java.time.Instant;
import java.util.Optional;

@Data
public final class EventProcessingState {
    private final EventStatus status;
    private final int attempts;
    private final String lastError;
    private final Instant updatedAt;

    public EventProcessingState(EventStatus status, int attempts, String lastError, Instant updatedAt) {
        this.status = status;
        this.attempts = attempts;
        this.lastError = lastError;
        this.updatedAt = updatedAt;
    }

    public static EventProcessingState initial(Instant now) {
        return new EventProcessingState(EventStatus.PENDING, 0, null, now);
    }

    public EventProcessingState markProcessing(Instant now) {
        return new EventProcessingState(EventStatus.PROCESSING, this.attempts, this.lastError, now);
    }


    public EventProcessingState markProcessed(Instant now) {
        return new EventProcessingState(EventStatus.PROCESSED, this.attempts, null, now);
    }

    public EventProcessingState registerFailure(String error, Instant now) {
        String safeError = (error == null || error.isBlank()) ? "Unknown error" : error;
        return new EventProcessingState(EventStatus.FAILED, this.attempts + 1, safeError, now);
    }

    public static EventProcessingState fromPersistence(
            EventStatus status,
            int attempts,
            String lastError,
            Instant updatedAt
    ) {
        return new EventProcessingState(status, attempts, lastError, updatedAt);
    }
    public EventStatus status(){
        return status;
    }

    public int attempts(){
        return attempts;
    }

    public Optional<String> lastError(){
        return Optional.ofNullable(lastError);
    }

}
