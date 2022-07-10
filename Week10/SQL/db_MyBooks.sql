drop database if exists MyBooks;

create database MyBooks;
use MyBooks;

drop table if exists finishedPublishedBook;
drop table if exists publishedBook;
drop table if exists bookauthor;
drop table if exists book;
drop table if exists author;
drop table if exists publisher;
drop table if exists languages;

create table languages(
	LanguageID int not null auto_increment,
    LanguageDescription varchar(50) not null,
    primary key (LanguageID)    
);

create table publisher(
	PublisherID int not null auto_increment,
    PublisherName varchar(100),
    primary key (publisherID)
);

create table author(
	AuthorID int not null auto_increment,
    FirstName varchar(50) not null,
    MiddleName varchar(50),
    LastName varchar(50),
    Aka varchar(50),
    primary key (AuthorID)
);

create table book(
	BookID int not null auto_increment,
    OriginalBookName varchar(100) not null,
    FirstEditionYear year not null,
    OriginalLanguageID int not null,
    primary key (BookID),
    foreign key (OriginalLanguageID) references languages(languageID)
);

create table bookAuthor (
	BookID int not null,
    AuthorID int not null,
    primary key (BookID, AuthorID),
    foreign key (BookID) references book(BookID),
    foreign key (AuthorID) references author(authorID)
);

create table publishedBook(
	PublishedBookID int not null auto_increment,
    BookID int not null,
    PublishedBookName varchar(100) not null,
    YearPublished year not null,
    PublisherID int not null,
    LanguageID int not null,
    Translator varchar(50),
    primary key (PublishedBookID),
    foreign key (BookID) references book(BookID),
    foreign key (PublisherID) references publisher(PublisherID),
    foreign key (LanguageID) references languages(LanguageID)
);

create table finishedPublishedBook (
	FinishedPublishedBookID int not null auto_increment,
    PublishedBookID int not null,
    DateFinished date not null,
    primary key (FinishedPublishedBookID),
    foreign key (PublishedBookID) references publishedbook(PublishedBookID)
);

insert into `mybooks`.`languages`
	(`LanguageDescription`)
values
	('English'),
    ('Czech'),
    ('Dutch');

INSERT INTO `mybooks`.`publisher`
	(`PublisherName`)
VALUES
	('Albatros'), -- CZ capek 
    ('Voetnoot, Uitgeverij'), -- NL capek 2019
    ('James Press'), -- eng capek 2014
	('Talpress'), -- CZ good omens barvy
	('De Boekerij'), -- NL good omens kleur 
    ('Haper Collins Publishers'), -- ENG good omens & neverwhere & color of magic
	('Polaris'), -- nikdykde
    ('Uitgeverij Luitingh-Sijthoff B.V.'), -- niemandsland
    ('Host'), -- doka CZ
    ('The Overlook Press'), -- doka eng
    ('Uitgeverij G.A. van Oorschot B.V.');
    
INSERT INTO 
	`mybooks`.`author` (`FirstName`, `MiddleName`, `LastName`, `Aka`)
VALUES
	('Willem', 'Frederik', 'Hermans', 'WF Hermans'),
	('Karel', null, 'Čapek', null),
	('Terry', null, 'Pratchett', 'Sir Terence David John Pratchett'),
	('Neil', 'Richard', 'Gaiman', 'Neil Richard MacKinnon Gaiman');
   
INSERT INTO 
	`mybooks`.`book` (`OriginalBookName`, `FirstEditionYear`, `OriginalLanguageID`)
VALUES
	('Dášeňka čili život štěněte', 1933, 2),
	('Neverwhere', 1996, 1),
	('The Colour of Magic', 1983, 1),
	('Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch', 1990, 1),
	('De donkere kamer van Damokles', 1958, 3);
    
INSERT INTO 
	`mybooks`.`bookauthor` (`BookID`, `AuthorID`)
VALUES
	(1, 2),
    (2, 4),
    (3, 3),
    (4, 4),
    (4, 3),
    (5, 1);

INSERT INTO 
	`mybooks`.`publishedbook` (`BookID`, `PublishedBookName`, `YearPublished`, `PublisherID`, `LanguageID`, `Translator`)
VALUES
	(1, 'Dášeňka čili život štěněte', 2021, 1, 2, null), -- Dasenka
    (1, 'Dasja', 2019, 2, 3, 'Edgar de Bruin'),
    (1, 'Dashenka Or, The Life of a Puppy', 2013 , 3, 1, 'M. & R. Weatherall'), 
    (2, 'Neverwhere', 2016, 6, 1, null), -- neverwhere
    (2, 'Nikdykde', 2002, 7, 2, 'Ladislava Vojtková'),
    (2, 'Niemandsland', 2009, 8, 3, 'Erica Rijsewijk'),
    (3, 'Color of Magic', 2013, 6, 1, null), -- colour
    (3, 'Barva kouzel', 1993, 4, 2, 'Jan Kantůrek'),
    (3, 'De kleur van toverij', 2011, 5, 3, 'Venugopalan Ittekot'),
    (4, 'Good Omens: The Nice and Accurate Prophecies of Agnes Nutter, Witch', 2006, 6, 1, null), -- good
    (4, 'Dobrá znamení', 1997, 4, 2, 'Jan Kantůrek'),
    (4, 'Hoge Omens', 2010, 6, 3, 'Venugopalan Ittekot'),
    (5, 'De donkere kamer van Damokles', 2016, 11, 3, null), -- doka
    (5, 'The Darkroom of Damocles', 2009, 10, 1,'Ina Rilke'),
    (5, 'Temná komora Damoklova', 2010, 9, 2, 'Magda de Bruin Hüblová');
    
INSERT INTO 
	`mybooks`.`finishedpublishedbook` (`PublishedBookID`, `DateFinished`)
VALUES
	(1, '1990-08-30'),
    (4, '2000-03-04'),
    (5, '2000-05-07'),
    (13, '2002-09-21');