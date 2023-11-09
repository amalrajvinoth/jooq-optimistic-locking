CREATE TABLE accounts (
    id                  TEXT            NOT NULL CONSTRAINT account_pk PRIMARY KEY,
    balance             DECIMAL(28,9)   NOT NULL DEFAULT 0,
    currency            VARCHAR(3)      NOT NULL DEFAULT 'EUR',
    version             INTEGER         NOT NULL DEFAULT 1,
    created             TIMESTAMPTZ     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated             TIMESTAMPTZ     NOT NULL DEFAULT CURRENT_TIMESTAMP
);
