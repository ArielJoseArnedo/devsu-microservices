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

COMMENT ON TABLE persons IS 'Table of persons';
COMMENT ON COLUMN persons.name IS 'Represents the client`s name';
COMMENT ON COLUMN persons.gender IS 'Represents the client`s gender';
COMMENT ON COLUMN persons.age IS 'Represents the client`s age';
COMMENT ON COLUMN persons.address IS 'Represents the client`s address';
COMMENT ON COLUMN persons.phone IS 'Represents the nomber client`s name';

CREATE TABLE clients
(
    client_id                           VARCHAR(120)        NOT NULL,
    password                            VARCHAR(120)        NOT NULL,
    state                               VARCHAR(32)         NOT NULL,
    identification_id                   VARCHAR(120)        NOT NULL,
    CONSTRAINT clients_pk               PRIMARY KEY (client_id),
    CONSTRAINT identification_id_fk     FOREIGN KEY (identification_id) REFERENCES persons (identification) ON DELETE CASCADE
);

COMMENT ON TABLE clients IS 'Table of clients';
COMMENT ON COLUMN clients.client_id IS 'Represents the client`s identificacion ID';
COMMENT ON COLUMN clients.password IS 'Represents the client`s password';
COMMENT ON COLUMN clients.state IS 'Represents the client`s state (ACTIVE, INACTIVE, UNKNOWN)';


