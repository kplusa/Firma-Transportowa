create table Adres
  (
  id int primary KEY IDENTITY (1,1),
  ulica varchar(20)  not null,
  nrDomu varchar(10) not null,
  miejscowosc varchar(20)  not null,
  kodPocztowy varchar(6) not null,
  )

create table Uzytkownik
  (
  id int primary KEY IDENTITY (1,1),
  imie varchar(20) not null,
  nazwisko varchar(20)  not null,
  mail varchar(30) unique not null,
  haslo varchar(20) not null,
  telefon int  not null,
  typUzytkownika varchar(20) not null,
  adresId int not null,
  Foreign key (adresId) references Adres (id)
  )

  create table Oddzial
  (
  id int primary KEY IDENTITY (1,1),
  miejscowosc varchar(20)  not null,
  )
  create table Oplata
  (
  id int primary KEY IDENTITY (1,1),
  termin date not null,
  kwota float not null,
  status varchar(20) not null,
  sposob varchar(20) not null,
  )
  create table Cennik
  (
  id int primary KEY IDENTITY (1,1),
  gabaryt varchar(20)  not null,
  kwota float not null,
  opis varchar(20) not null,
  dataZmiany date not null,
  limit int
  )
   create table Zlecenie
  (
  id int primary KEY IDENTITY (1,1),
  dataNadania date not null,
  adresPoczatkowy varchar(255) not null,
  adresKoncowy varchar(255),
  status varchar(40),
  uzytkownikId int,
  oddzialPoczatkowyId int,
  oddzialKoncowyId int,
  Foreign key (uzytkownikId) references Uzytkownik (id),
  Foreign key (oddzialPoczatkowyId) references Oddzial (id),
  Foreign key (oddzialKoncowyId) references Oddzial (id)
  
  
  )
    create table Paczka
  (
  id int primary KEY IDENTITY (1,1),
  waga float not null,
  zlecenieId int not null,
  cennikId int not null,
  oplataId int not null,
  Foreign key (zlecenieId) references Zlecenie (id),
  Foreign key (oplataId) references Oplata (id),
  Foreign key (cennikId) references Cennik (id),

  )
  create table Doplata
  (
  id int primary KEY IDENTITY (1,1),
  typDoplaty varchar(35)  not null,
  kwota float  not null,
  dataZmiany date not null,
  )
  create table PaczkaDoplata
  (
  id int primary KEY IDENTITY (1,1),
  paczkaId int not null,
  doplataId int not null,
  Foreign key (paczkaId) references Paczka (id),
  Foreign key (doplataId) references Doplata (id),
  )
  create table Kurier
  (
  id int primary KEY IDENTITY (1,1),
  typKuriera varchar(20)  not null,
  imie varchar(20)  not null,
  nazwisko varchar(20)  not null,
  )
  create table ZlecenieKurier
  (
  id int primary KEY IDENTITY (1,1),
  zlecenieId int not null,
  kurierId int not null,
  numerPorzadkowy int not null,
  Foreign key (zlecenieId) references Zlecenie (id),
  Foreign key (kurierId) references Kurier (id),
  )
 