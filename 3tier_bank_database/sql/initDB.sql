DROP TABLE IF EXISTS Customer CASCADE;
DROP TABLE IF EXISTS ExchangeRate CASCADE;
DROP TABLE IF EXISTS Account CASCADE;
DROP TABLE IF EXISTS Transaction CASCADE;
DROP TYPE IF EXISTS transactionType CASCADE;
DROP TYPE IF EXISTS currencyType CASCADE;

CREATE TABLE Customer(
  cpr VARCHAR(20) PRIMARY KEY,
  name VARCHAR(40) NOT NULL,
  address VARCHAR(60) NOT NULL
);

CREATE TYPE currencyType AS ENUM ('EUR', 'USD', 'DKK');

-- Select all currencyType values --
SELECT unnest(enum_range(NULL::currencyType)) AS currencyType;

CREATE TABLE ExchangeRate(
  currency VARCHAR PRIMARY KEY check(currency in ('EUR', 'USD', 'DKK')),
  rate DECIMAL NOT NULL CHECK(rate > 0)
);

INSERT INTO ExchangeRate VALUES
  ('EUR', 1),
  ('USD', 1.2),
  ('DKK', 7.6);

CREATE TABLE Account(
  number SERIAL PRIMARY KEY,
  customerCpr VARCHAR(20) NOT NULL,
  amount DECIMAL NOT NULL DEFAULT 0,
  currency VARCHAR NOT NULL DEFAULT 'EUR',
  FOREIGN KEY(customerCpr) REFERENCES Customer(cpr) ON DELETE CASCADE
);

CREATE TYPE transactionType AS ENUM ('DEPOSIT', 'WITHDRAW', 'TRANSFER');

-- Select all transactionType values --
SELECT unnest(enum_range(NULL::transactionType)) AS transactionType;

CREATE TABLE Transaction(
  id SERIAL PRIMARY KEY,
  date TIMESTAMP DEFAULT current_timestamp,
  type VARCHAR NOT NULL check(type in ('DEPOSIT', 'WITHDRAW', 'TRANSFER')),
  amount DECIMAL CHECK(amount > 0),
  sourceAccountNumber INT NOT NULL,
  destinationAccountNumber INT REFERENCES Account(number),
  FOREIGN KEY(sourceAccountNumber) REFERENCES Account(number) ON DELETE CASCADE
);

INSERT INTO Customer VALUES
  ('1234-56789', 'Octavian', 'Horsens, Geneesagade'),
  ('9876-54321', 'Claus', 'Vejle, Smukgade')
;
INSERT INTO Account(customerCpr, amount, currency) VALUES
  ('1234-56789', 12000, 'EUR'),
  ('1234-56789', 30000, 'DKK'),
  ('9876-54321', 20000, 'EUR'),
  ('9876-54321', 5000, 'USD')
;

INSERT INTO Transaction(type, amount, sourceAccountNumber) VALUES
  ('DEPOSIT', 500, 1);
UPDATE Account SET amount = amount + 500 WHERE number = 1;

INSERT INTO Transaction(type, amount, sourceAccountNumber) VALUES
  ('WITHDRAW', 500, 1);
UPDATE Account SET amount = amount - 500 WHERE number = 1;

INSERT INTO Transaction(type, amount, sourceAccountNumber, destinationAccountNumber) VALUES
  ('TRANSFER', 500, 1, 2);
UPDATE Account SET amount = amount - 500 WHERE number = 1;
UPDATE Account SET amount = amount + 500 WHERE number = 2;

SELECT * FROM Transaction;
SELECT * FROM Account;
SELECT * FROM Customer;

SELECT * FROM ExchangeRate;