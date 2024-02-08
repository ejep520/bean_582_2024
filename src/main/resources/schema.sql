DROP TABLE users IF EXISTS;
DROP TABLE units IF EXISTS;
DROP TABLE authorities IF EXISTS;
CREATE TABLE users (
  username VARCHAR(50) PRIMARY KEY,
  zip CHAR(5),
  prefName VARCHAR(50),
  role INTEGER,
  password CHAR(60) -- 60 is to accommodate the BCrypt password hashing.
);
CREATE TABLE units (
  unitNumber VARCHAR(10),
  address VARCHAR(50),
  zip CHAR(5),
  bedrooms INTEGER,
  bathrooms FLOAT,
  livingRoom VARCHAR(50),
  diningRoom VARCHAR(50),
  kitchen VARCHAR(50),
  featured BIT,
  url VARCHAR(50),
  hashValue INTEGER PRIMARY KEY
);
CREATE TABLE authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  CONSTRAINT fk_auth_email FOREIGN KEY (email) REFERENCES users(email)
  );
CREATE UNIQUE INDEX ix_auth_username on authorities(username, authority);
