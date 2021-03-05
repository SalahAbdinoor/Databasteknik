

DROP DATABASE IF EXISTS `database_webshop`;

create database `database_webshop`;

use `database_webshop`;


SET SQL_SAFE_UPDATES = 0;
create table Ort
(
ID int not null auto_increment,
Namn varchar(30) not null,

primary key(ID)
);

create table Kund
(
ID int not null auto_increment,
förnamn varchar(30) not null,
efternamn varchar(30) not null,
Adress varchar(30) not null,
Mail varchar(30) not null,
Telefonnummer int not null,
OrtID int not null,
lösenord varchar(30) not null,

primary key(ID),
foreign key (OrtID) references Ort(id)
);



create table Betyg
(
ID int not null auto_increment,
betygnr int default 0,
Omdöme varchar(30) not null,
primary key(ID));

create index index_betygnr on betyg(betygnr);

create table Märke
(
ID int not null auto_increment,
Namn varchar(30) not null,

primary key(ID)
);

create table Kategori
(
ID int not null auto_increment,
Namn varchar(30) not null,

primary key(ID)
);

create table Produkt
(
ID int not null auto_increment,
Namn varchar(30) not null,
Pris int not null,
Storlek int not null,
Färg varchar(30) not null, 
MärkeID int not null,
Lager int not null, 

primary key(ID),
foreign key (MärkeID) references Märke(ID) on delete cascade
);

create table Recension
(
ID int not null auto_increment,
Kommentar varchar(140) default 'Saknar kommentar',
Datum timestamp not null,
BetygID int default null,
ProduktID int,
KundID int,

primary key(ID),
foreign key (BetygID) references Betyg(betygnr),
foreign key (ProduktID) references Produkt(ID) on delete cascade,
foreign key (KundID) references Kund(ID)
);

create table Beställning
(
ID int not null auto_increment,
Köpnr int not null,
Summa int not null,
Datum timestamp not null,
KundID int not null,

primary key(ID),
foreign key (KundID) references Kund(ID)
);

create index index_köpnr on beställning(köpnr);


create table Skickar
(
ID int not null auto_increment,
Köpnr int not null,
ProduktID int not null,
primary key(ID),
foreign key (köpnr) references beställning(köpnr),
foreign key (ProduktID) references Produkt(ID) on delete cascade
);

create table Tillhör
(ID int not null auto_increment,
KategoriID int not null,
ProduktID int not null,
primary key(ID),
foreign key (kategoriID) references kategori(id),
foreign key (produktID) references produkt(id)on delete cascade);

create table SlutILager
(ID int not null auto_increment,
datum timestamp not null,
produktid int not null,
primary key (ID),
foreign key (produktid) references produkt(id) on delete cascade);

create table Beskriver
(ID int not null auto_increment,
recensionID int not null,
Betygnr int not null,
primary key(ID),
foreign key(recensionid) references recension(id),
foreign key(betygnr) references betyg(betygnr));

insert into kategori(namn) values
('Sandaler'),
('Promenadskor'),
('Löparskor'),
('Gympaskor'),
('Fotbollskor'),
('Sneakers');

insert into märke(namn) values
('Ecco'),
('Jordan'),
('Puma');


insert into ort(namn) values
('Huddinge'),
('Hässelby'),
('Nacka');

insert into kund(förnamn, efternamn, adress, mail, telefonnummer, ortid, lösenord) values
('Daniel', 'Bojic', 'Botkyrkavägen 9', 'dannekun@hotmail.com', 0700164429, 1, 'danne123'),
('Salah','Elnour',  'Kvarnhagsgatan 165', 'salah@hotmail.com', 999999999, 2, 'salah123'),
('Sigrun','Olofsdottir',  'Programmeringsvägen 12', 'sigrun@nackademin.se', 123456789, 3, 'sigrun123'),
('Venkat','Subramaniam',  'Programmeringsvägen 7', 'venkat@fun.se', 987654321, 3, 'venkat123'),
('Mahmud','Al-Hakim',  'Datagatan 163', 'mahmud@nackademin.se', 567891234, 2, 'mahmud123')
;

insert into produkt(namn, pris, storlek, färg, märkeid,lager) values
('Shoeshine', 2500, 40, 'Svart', 1,1),
('Retro', 3000, 43, 'Röd', 1,5),
('Mid', 1500, 41, 'Blå', 2,12),
('Flippis', 500, 38, 'Svart', 1,13),
('Phresh', 999, 42, 'Vit', 3,3),
('Kicks', 699, 41, 'Vit', 3, 7),
('Winner', 2499, 45, 'Svart', 2,6),
('Beginners', 199, 25, 'Svart', 1,9),
('Floppis', 99, 38, 'Svart', 1,10);
		
insert into beställning(köpnr, summa, datum, kundID) values
(1,(select produkt.pris from produkt where produkt.id = 1),"2020-02-10 20:06:11", 1),
(1, (select produkt.pris from produkt where produkt.id = 2),"2020-02-10 20:06:11", 1),
(1, (select produkt.pris from produkt where produkt.id = 3),"2020-02-10 20:06:11", 1),
(2, (select produkt.pris from produkt where produkt.id = 4),"2020-03-15 12:30:51", 2),
(3, (select produkt.pris from produkt where produkt.id = 5),"2020-04-22 16:20:35", 2),
(4, (select produkt.pris from produkt where produkt.id = 6),"2020-04-05 09:15:23", 3),
(4, (select produkt.pris from produkt where produkt.id = 7),"2020-04-05 09:15:23", 3),
(5, (select produkt.pris from produkt where produkt.id = 8),"2020-02-10 20:06:11", 4),
(6, (select produkt.pris from produkt where produkt.id = 1),"2020-03-03 12:02:54", 4),
(7, (select produkt.pris from produkt where produkt.id = 2),"2020-02-28 00:00:01", 5),
(7, (select produkt.pris from produkt where produkt.id = 7),"2020-02-28 00:00:01", 5),
(8, (select produkt.pris from produkt where produkt.id = 9),"2020-03-25 19:23:58", 1);

insert into skickar(köpnr, produktid) values
(1, 1),
(1, 2),
(1, 3),
(2, 4),
(3, 5),
(4, 6),
(4, 7),
(5, 8),
(6, 1),
(7, 2),
(7, 7),
(8, 9);

insert into tillhör(kategoriid, produktid) values
(2, 1),
(4, 1),
(1, 1),
(4, 2),
(1, 2),
(3, 3),
(5, 3),
(1, 4),
(6, 4),
(2, 5),
(4, 5),
(3, 6),
(5, 6),
(3, 7),
(6, 7),
(3, 8),
(2, 8),
(1, 9);

insert into betyg(betygnr,omdöme) values
(0,'Omdöme saknas'),
(1,'Missnöjd'),
(2,'Ganska nöjd'),
(3,'Nöjd'),
(4,'Mycket nöjd');

insert into recension(kommentar, datum, betygid, produktid, kundid) values
('Bästa skorna!', '2020-02-20 14:12:32', 4, 2, 1),
('Bra sandalerna!', '2020-03-30 18:30:24', 3, 4, 2),  
('Sådär kicks', '2020-04-20 12:30:45', 2, 6, 3),
(null, current_timestamp,0 , 1, null),
(null, current_timestamp,0 , 3, null),
(null, current_timestamp,0 , 5, null),
(null, current_timestamp,0 , 7, null),
(null, current_timestamp,0 , 8, null),
(null, current_timestamp,0 , 9, null),
('Skräp', '2020-03-01 00:00:01', 1, 2, 5);  

insert into beskriver(recensionid,betygnr) values
(1,4),
(2,3),
(3,2),
(10,1);


create index index_produkt on produkt(namn);



