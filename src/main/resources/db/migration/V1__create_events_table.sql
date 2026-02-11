-- V1__create_events_table.sql

CREATE TABLE IF NOT EXISTS events (
                                      id          UUID PRIMARY KEY,
                                      source      VARCHAR(100) NOT NULL,
    event_key   VARCHAR(150) NOT NULL,
    status      VARCHAR(30)  NOT NULL,
    attempts    INT          NOT NULL DEFAULT 0,
    payload     TEXT         NOT NULL,
    last_error  TEXT,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT now()
    );

-- evita duplicar o mesmo evento por origem + chave
CREATE UNIQUE INDEX IF NOT EXISTS uk_events_source_event_key
    ON events (source, event_key);

-- útil pra buscar “pendentes”/“falhas”
CREATE INDEX IF NOT EXISTS ix_events_status_updated_at
    ON events (status, updated_at DESC);