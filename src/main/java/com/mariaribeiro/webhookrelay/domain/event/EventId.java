package com.mariaribeiro.webhookrelay.domain.event;

import java.util.UUID;

public record EventId(UUID value) {
    public static EventId newId(){
        return new EventId(UUID.randomUUID());
    }
}
