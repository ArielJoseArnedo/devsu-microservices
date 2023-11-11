CREATE TABLE persons
(
    identification                  VARCHAR(120)        NOT NULL,
    name                            VARCHAR(120)        NOT NULL,
    gender                          VARCHAR(32)         NOT NULL,
    age                             VARCHAR(32)         NOT NULL,
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
