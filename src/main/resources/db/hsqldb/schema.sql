DROP TABLE physician_specialties IF EXISTS;
DROP TABLE physicians IF EXISTS;
DROP TABLE specialties IF EXISTS;
DROP TABLE visits IF EXISTS;
DROP TABLE patients IF EXISTS;
DROP TABLE types IF EXISTS;
DROP TABLE patients IF EXISTS;


CREATE TABLE physicians (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR(30)
);
CREATE INDEX physicians_last_name ON physicians (last_name);

CREATE TABLE specialties (
  id   INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(80)
);
CREATE INDEX specialties_name ON specialties (name);

CREATE TABLE physician_specialties (
  physician_id       INTEGER NOT NULL,
  specialty_id INTEGER NOT NULL
);
ALTER TABLE physician_specialties ADD CONSTRAINT fk_physician_specialties_physicians FOREIGN KEY (physician_id) REFERENCES physicians (id);
ALTER TABLE physician_specialties ADD CONSTRAINT fk_physician_specialties_specialties FOREIGN KEY (specialty_id) REFERENCES specialties (id);

CREATE TABLE types (
  id   INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(80)
);
CREATE INDEX types_name ON types (name);

CREATE TABLE patients (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR_IGNORECASE(30),
  address    VARCHAR(255),
  city       VARCHAR(80),
  telephone  VARCHAR(20)
);
CREATE INDEX patients_last_name ON patients (last_name);

CREATE TABLE patients (
  id         INTEGER IDENTITY PRIMARY KEY,
  name       VARCHAR(30),
  birth_date DATE,
  type_id    INTEGER NOT NULL,
  owner_id   INTEGER
);
ALTER TABLE patients ADD CONSTRAINT fk_patients_patients FOREIGN KEY (owner_id) REFERENCES patients (id);
ALTER TABLE patients ADD CONSTRAINT fk_patients_types FOREIGN KEY (type_id) REFERENCES types (id);
CREATE INDEX patients_name ON patients (name);

CREATE TABLE visits (
  id          INTEGER IDENTITY PRIMARY KEY,
  pet_id      INTEGER,
  visit_date  DATE,
  description VARCHAR(255)
);
ALTER TABLE visits ADD CONSTRAINT fk_visits_patients FOREIGN KEY (pet_id) REFERENCES patients (id);
CREATE INDEX visits_pet_id ON visits (pet_id);
