DROP TABLE units IF EXISTS;
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
