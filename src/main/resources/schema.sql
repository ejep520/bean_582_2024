DROP TABLE users IF EXISTS;
DROP TABLE units IF EXISTS;
CREATE TABLE users (
  email VARCHAR(50) PRIMARY KEY,
  zip CHAR(5),
  prefName VARCHAR(50),
  role INTEGER,
  passHash CHAR(60) -- 60 is to accommodate the BCrypt password hashing.
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
