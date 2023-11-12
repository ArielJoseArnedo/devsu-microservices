\c movements;

CREATE TABLE accounts
(
    client_id                       VARCHAR(120)        NOT NULL,
    client_name                     VARCHAR(120)        NOT NULL,
    number_account                  VARCHAR(120)        NOT NULL,
    account_type                    VARCHAR(32)         NOT NULL,
    general_balance                 DECIMAL             NOT NULL,
    state                           VARCHAR(32)         NOT NULL,
    CONSTRAINT accounts_pk          PRIMARY KEY (client_id, number_account)
);

COMMENT ON TABLE accounts IS 'Table of accounts';
COMMENT ON COLUMN accounts.client_id IS 'Represents the client`s identiticacion ID';
COMMENT ON COLUMN accounts.client_name IS 'Represents the client`s name';
COMMENT ON COLUMN accounts.number_account IS 'Represents the client`s number account';
COMMENT ON COLUMN accounts.account_type IS 'Represents the account type client`s account (SAVINGS, CHECKING, UNKNOWN)';
COMMENT ON COLUMN accounts.general_balance IS 'Represents the general balance of the client`s account';
COMMENT ON COLUMN accounts.state IS 'Represents the account state client`s account (ACTIVE, INACTIVE,UNKNOWN)';


CREATE TABLE movements
(
    movement_id                         VARCHAR(120)                    NOT NULL,
    client_id                           VARCHAR(120)                    NOT NULL,
    number_account                      VARCHAR(120)                    NOT NULL,
    movement_type                       VARCHAR(32)                     NOT NULL,
    amount                              DECIMAL                         NOT NULL,
    balance                             DECIMAL                         NOT NULL,
    registration_date                   TIMESTAMP WITH TIME ZONE        NOT NULL,
    CONSTRAINT movements_pk             PRIMARY KEY (movement_id, client_id, number_account),
    CONSTRAINT accounts_movements_fk    FOREIGN KEY (client_id, number_account) REFERENCES accounts (client_id, number_account) ON DELETE CASCADE
);

COMMENT ON TABLE movements IS 'Table of movements';
COMMENT ON COLUMN movements.movement_id IS 'Represents the movements`s identiticacion ID';
COMMENT ON COLUMN movements.client_id IS 'Represents the client`s identiticacion ID (part of the foreign key of the table accounts)';
COMMENT ON COLUMN movements.number_account IS 'Represents the client`s number account (part of the foreign key of the table accounts)';
COMMENT ON COLUMN movements.movement_type IS 'Represents the type of movement in the client`s account (DEPOSIT, WITHDRAWAL, UNKNOWN)';
COMMENT ON COLUMN movements.amount IS 'Represents the amount of movement';
COMMENT ON COLUMN movements.balance IS 'Represents the current balance after the movement';
COMMENT ON COLUMN movements.registration_date IS 'Represents the date on which the movement was registered';