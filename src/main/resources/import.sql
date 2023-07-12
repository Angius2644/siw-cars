insert into account values(nextval('account_seq'), 'test', 'DEFAULT', 'test');
insert into account values(nextval('account_seq'), '$2a$10$KW0voOEHo/IxQPBoSctkI.gnqU68E56dZdWLctte8ZFRmkgQgUVYu', 'ADMIN', 'admin');
insert into account values(nextval('account_seq'), 'utente2', 'DEFAULT', 'utente2')

insert into person values(nextval('person_seq'), 'Ciafrone', '1998-04-24', 'Roma', 'Davide', 3663600879);
insert into person values(nextval('person_seq'), 'Ciafrone', '1961-02-26', 'Salerno', 'Massimo', 3398957785);
insert into person values(nextval('person_seq'), 'Ciafrone', '1999-11-15', 'Roma', 'Lorenzo', 3341174838);
insert into person values(nextval('person_seq'), 'Liso', '2000-01-03', 'Anzio', 'Sabrina', 3298812927);

insert into car values(nextval('car_seq'), 27234, 'Rosso', 2023, 'Tesla', 'Model S', 'CZ010ZE', 1);
insert into car values(nextval('car_seq'), 37583, 'Bianco', 2021, 'Volkswagen', 'ID.4', 'CL677WJ', 1);
insert into car values(nextval('car_seq'), 79805, 'Bianco', 2019, 'Toyota', 'Mirai', 'AZ484AL', 1);
insert into car values(nextval('car_seq'), 4654, 'Grigio', 2023, 'Rivian', 'R1T', 'EV220NX', 51);