create table AUTHOR (PERSON_ID bigint not null, FIRSTNAME varchar(30) not null, GENDER integer, LASTNAME varchar(30) not null, primary key (PERSON_ID))
create table AUTHOR_Book (AUTHOR_PERSON_ID bigint not null, _writtenBooks_BOOK_ID bigint not null)
create table Book (BOOK_ID bigint not null, GENRE integer, LANGUAGE integer, NAME varchar(255) not null, TYPE integer, _author_PERSON_ID bigint not null, primary key (BOOK_ID))
create table OWNED_BOOKS (OWNER_ID bigint not null, BOOK_ID bigint not null)
create table REQUEST_LIST (USER_ID bigint not null, REQUEST_ID bigint not null)
create table Request (REQUEST_ID bigint not null, LATITUDE double, LONGITUDE double, _msg varchar(255), _book_BOOK_ID bigint not null, _requestor_PERSON_ID bigint not null, primary key (REQUEST_ID))
create table USER (PERSON_ID bigint not null, FIRSTNAME varchar(30) not null, GENDER integer, LASTNAME varchar(30) not null, CITY varchar(255), USERNAME varchar(30) not null, primary key (PERSON_ID))
alter table AUTHOR add constraint UK_1sohklw0opg3g3kgwk1dxvcfy  unique (FIRSTNAME, LASTNAME)
alter table AUTHOR_Book add constraint UK_cu9wpp4dvyv6bycntoc1x9it  unique (_writtenBooks_BOOK_ID)
alter table Book add constraint UK_g3fnfqluut0fs1v35cv8dvydu  unique (NAME)
alter table REQUEST_LIST add constraint UK_ldygmkg9061e8me6kne7cq7p9  unique (REQUEST_ID)
alter table Request add constraint UK_incrxkymfdr3xw7p4ni4c549d  unique (_book_BOOK_ID)
alter table Request add constraint UK_jtbq338hed7mm4exe2wnv406q  unique (_requestor_PERSON_ID)
alter table USER add constraint UK_6rqyc8y5oe9gjmojvq0phor74  unique (FIRSTNAME, LASTNAME)
alter table USER add constraint UK_lb5yrvw2c22im784wwrpwuq06  unique (USERNAME)
alter table AUTHOR_Book add constraint FK_cu9wpp4dvyv6bycntoc1x9it foreign key (_writtenBooks_BOOK_ID) references Book
alter table AUTHOR_Book add constraint FK_83hu90vwvkdwnsguu8ippu85f foreign key (AUTHOR_PERSON_ID) references AUTHOR
alter table Book add constraint FK_ocjktu6wcsvl99jkpl1c7e3v6 foreign key (_author_PERSON_ID) references AUTHOR
alter table OWNED_BOOKS add constraint FK_9erx1ple5p3dpot0rvn5fsa54 foreign key (BOOK_ID) references Book
alter table OWNED_BOOKS add constraint FK_3xabmkwr1tt49iejs8q7cxl5q foreign key (OWNER_ID) references USER
alter table REQUEST_LIST add constraint FK_ldygmkg9061e8me6kne7cq7p9 foreign key (REQUEST_ID) references Request
alter table REQUEST_LIST add constraint FK_8lkyanvi6otfiiufce93js9dg foreign key (USER_ID) references USER
alter table Request add constraint FK_incrxkymfdr3xw7p4ni4c549d foreign key (_book_BOOK_ID) references Book
alter table Request add constraint FK_jtbq338hed7mm4exe2wnv406q foreign key (_requestor_PERSON_ID) references USER
create sequence hibernate_sequence start with 1 increment by 1
