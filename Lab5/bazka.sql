DROP TABLE Osoba CASCADE CONSTRAINTS;
DROP TABLE Zwierze CASCADE CONSTRAINTS;

CREATE TABLE Osoba (
  ID NUMBER(3,0) NOT NULL,
  Imie VARCHAR(20),
  Nazwisko VARCHAR(30),
  Pensja NUMBER(6,0),
  Jakiespole VARCHAR(50),
  PRIMARY KEY (ID));

CREATE TABLE Zwierze (
  ID NUMBER(3,0) NOT NULL,
  Imie VARCHAR(20),
  IDWlasciciela NUMBER(3,0),
  PRIMARY KEY (ID));
  
ALTER TABLE Zwierze 
ADD CONSTRAINT Zwierze_Osoba_FK
  FOREIGN KEY (IDWlasciciela)
  REFERENCES Osoba (ID);
  
select * from Osoba;
select * from Zwierze;