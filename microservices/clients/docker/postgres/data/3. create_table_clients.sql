CREATE TABLE clients
(
    client_id                           VARCHAR(120)        NOT NULL,
    password                            VARCHAR(120)        NOT NULL,
    state                               VARCHAR(32)         NOT NULL,
    identification_id                   VARCHAR(120)        NOT NULL,
    CONSTRAINT clients_pk               PRIMARY KEY (client_id),
    CONSTRAINT identification_id_fk     FOREIGN KEY (identification_id) REFERENCES persons (identification) ON DELETE CASCADE
);

COMMENT ON TABLE clients is'';
COMMENT ON COLUMN clients.client_id IS '';
COMMENT ON COLUMN clients.password IS '';
COMMENT ON COLUMN clients.state IS '';
