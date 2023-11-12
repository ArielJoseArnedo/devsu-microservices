\c clients;

CREATE TABLE persons
(
    identification                  VARCHAR(120)        NOT NULL,
    name                            VARCHAR(120)        NOT NULL,
    gender                          VARCHAR(32)         NOT NULL,
    age                             INTEGER             NOT NULL,
    address                         VARCHAR(32)         NOT NULL,
    phone                           VARCHAR(32)         NOT NULL,
    CONSTRAINT persons_pk           PRIMARY KEY (identification)
);

COMMENT ON TABLE persons is'';
COMMENT ON COLUMN persons.name IS '';
COMMENT ON COLUMN persons.gender IS '';
COMMENT ON COLUMN persons.age IS '';
COMMENT ON COLUMN persons.address IS '';
COMMENT ON COLUMN persons.phone IS '';

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


