DROP TRIGGER auto_incr_tickets;
DROP TRIGGER auto_incr_flights;
DROP TRIGGER auto_incr_airplanes;
DROP TRIGGER auto_incr_airplanes_seats;
DROP TRIGGER auto_incr_airports;

DROP SEQUENCE seq_tickets;
DROP SEQUENCE seq_flights;
DROP SEQUENCE seq_airplanes;
DROP SEQUENCE seq_airplanes_seats;
DROP SEQUENCE seq_airports;

DROP TABLE tickets;
DROP TABLE flights;
DROP TABLE airports;
DROP TABLE airplanes_seats;
DROP TABLE airplanes;

CREATE TABLE airports (
    id INT,
    short_name CHAR(3) NOT NULL,
    long_name VARCHAR2(100) NOT NULL,
    CONSTRAINT airports_pk PRIMARY KEY (id),
    CONSTRAINT airports_uniqueness UNIQUE (short_name)
);

CREATE TABLE airplanes (
    id INT,
    name VARCHAR2(100) NOT NULL,
    CONSTRAINT airplanes_pk PRIMARY KEY (id)
);

CREATE TABLE airplanes_seats (
    id INT,
    airplane_id INT NOT NULL,
    seat VARCHAR2(5) NOT NULL,
    CONSTRAINT airplanes_seats_pk PRIMARY KEY (id),
    CONSTRAINT airplanes_seats_uniqueness UNIQUE (airplane_id, seat),
    CONSTRAINT airplanes_seats_airplanes_fk FOREIGN KEY (airplane_id) REFERENCES airplanes (id)
);

CREATE TABLE flights (
    id INT,
    departure_airport_id INT NOT NULL,
    departure_date DATE,
    arrival_airport_id INT NOT NULL,
    arrival_date DATE,
    airplane_id INT NOT NULL,
    CONSTRAINT flights_pk PRIMARY KEY (id),
    CONSTRAINT flights_departure_airports_fk FOREIGN KEY (departure_airport_id) REFERENCES airports (id),
    CONSTRAINT flights_arrival_airports_fk FOREIGN KEY (arrival_airport_id) REFERENCES airports (id),
    CONSTRAINT flights_airplanes_fk FOREIGN KEY (airplane_id) REFERENCES airplanes (id)
);

CREATE TABLE tickets (
    id INT,
    flight_id INT NOT NULL,
    seat_id INT NOT NULL,
    client_name VARCHAR(100),
    client_passport VARCHAR(20),
    CONSTRAINT tickets_pk PRIMARY KEY (id),
    CONSTRAINT tickets_flights_fk FOREIGN KEY (flight_id) REFERENCES flights (id),
    CONSTRAINT tickets_seats_uniqueness UNIQUE (flight_id, seat_id),
    CONSTRAINT tickets_seats_fk FOREIGN KEY (seat_id) REFERENCES airplanes_seats (id)
);


CREATE SEQUENCE seq_airplanes
  MINVALUE 1
  NOMAXVALUE
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

CREATE SEQUENCE seq_airplanes_seats
  MINVALUE 1
  NOMAXVALUE
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

CREATE SEQUENCE seq_airports
  MINVALUE 1
  NOMAXVALUE
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

CREATE SEQUENCE seq_flights
  MINVALUE 1
  NOMAXVALUE
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

CREATE SEQUENCE seq_tickets
  MINVALUE 1
  NOMAXVALUE
  START WITH 1
  INCREMENT BY 1
  CACHE 20;


CREATE TRIGGER auto_incr_airplanes BEFORE INSERT ON airplanes
FOR EACH ROW
WHEN (new.id is NULL)
BEGIN
  SELECT seq_airplanes.nextval into :new.id from dual;
END;
/

CREATE TRIGGER auto_incr_airplanes_seats BEFORE INSERT ON airplanes_seats
FOR EACH ROW
WHEN (new.id is NULL)
BEGIN
  SELECT seq_airplanes_seats.nextval into :new.id from dual;
END;
/

CREATE TRIGGER auto_incr_airports BEFORE INSERT ON airports
FOR EACH ROW
WHEN (new.id is NULL)
BEGIN
  SELECT seq_airports.nextval into :new.id from dual;
END;
/

CREATE TRIGGER auto_incr_flights BEFORE INSERT ON flights
FOR EACH ROW
WHEN (new.id is NULL)
BEGIN
  SELECT seq_flights.nextval into :new.id from dual;
END;
/

CREATE TRIGGER auto_incr_tickets BEFORE INSERT ON tickets
FOR EACH ROW
WHEN (new.id is NULL)
BEGIN
  SELECT seq_tickets.nextval into :new.id from dual;
END;
/


INSERT INTO airports (short_name, long_name) VALUES ('LED', 'Saint Petersburg, Russia');

INSERT INTO airplanes (name) VALUES ('sample');

INSERT INTO flights (departure_airport_id, departure_date, arrival_airport_id, arrival_date, airplane_id)
VALUES ((SELECT id FROM airports WHERE short_name = 'LED'), 
TO_DATE('01.01.2001 14:40', 'DD.MM.YYYY HH24:MI'), 
1, 
TO_DATE('01.01.2001', 'DD.MM.YYYY'), 
1);


EXIT;
