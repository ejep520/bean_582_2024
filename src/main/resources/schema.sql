DROP TABLE users IF EXISTS;
DROP TABLE units IF EXISTS;
DROP TABLE wishList IF EXISTS;
CREATE TABLE users (
  uName VARCHAR(50) PRIMARY KEY,
  fName VARCHAR(50),
  lName VARCHAR(50),
  zip CHAR(5),
  prefName VARCHAR(50),
  passHash VARCHAR(50)
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
  hashValue INTEGER PRIMARY KEY
);
CREATE TABLE wishList (
  uName VARCHAR(50),
  unitHashValue INTEGER,
  wishHashValue INTEGER PRIMARY KEY,
  CONSTRAINT FK_WishUnit FOREIGN KEY (unitHashValue) REFERENCES units(hashValue),
  CONSTRAINT FK WishUser FOREIGN KEY (uName) REFERENCES users(uName)
);
