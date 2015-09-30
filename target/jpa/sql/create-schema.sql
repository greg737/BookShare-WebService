create table Author (PERSON_ID bigint not null, FIRSTNAME varchar(30) not null, GENDER integer, LASTNAME varchar(30) not null, primary key (PERSON_ID))
create table Author_Book (Author_PERSON_ID bigint not null, _writtenBooks_BOOK_ID bigint not null)
create table Book (BOOK_ID bigint not null, GENRE integer, LANGUAGE integer, NAME varchar(255) not null, TYPE integer, _author_PERSON_ID bigint not null, primary key (BOOK_ID))
create table OWNED_BOOKS (OWNER_ID bigint not null, BOOK_ID bigint not null)
create table REQUEST_LIST (USER_ID bigint not null, REQUEST_ID bigint not null)
create table Request (REQUEST_ID bigint not null, LATITUDE double, LONGITUDE double, _msg varchar(255), _book_BOOK_ID bigint not null, _requestor_PERSON_ID bigint not null, primary key (REQUEST_ID))
create table User (PERSON_ID bigint not null, FIRSTNAME varchar(30) not null, GENDER integer, LASTNAME varchar(30) not null, CITY varchar(255), USERNAME varchar(30) not null, primary key (PERSON_ID))
alter table Author_Book add constraint UK_d67x8hlr935k8fqpxo7ul6bdf  unique (_writtenBooks_BOOK_ID)
alter table Book add constraint UK_g3fnfqluut0fs1v35cv8dvydu  unique (NAME)
alter table REQUEST_LIST add constraint UK_ldygmkg9061e8me6kne7cq7p9  unique (REQUEST_ID)
alter table Request add constraint UK_incrxkymfdr3xw7p4ni4c549d  unique (_book_BOOK_ID)
alter table Request add constraint UK_jtbq338hed7mm4exe2wnv406q  unique (_requestor_PERSON_ID)
alter table User add constraint UK_4xc5q2uhghtb4qrbyheoohvs  unique (USERNAME)
alter table Author_Book add constraint FK_d67x8hlr935k8fqpxo7ul6bdf foreign key (_writtenBooks_BOOK_ID) references Book
alter table Author_Book add constraint FK_91oj38bl104bu1jfmpnb4ioa5 foreign key (Author_PERSON_ID) references Author
alter table Book add constraint FK_ocjktu6wcsvl99jkpl1c7e3v6 foreign key (_author_PERSON_ID) references Author
alter table OWNED_BOOKS add constraint FK_9erx1ple5p3dpot0rvn5fsa54 foreign key (BOOK_ID) references Book
alter table OWNED_BOOKS add constraint FK_3xabmkwr1tt49iejs8q7cxl5q foreign key (OWNER_ID) references User
alter table REQUEST_LIST add constraint FK_ldygmkg9061e8me6kne7cq7p9 foreign key (REQUEST_ID) references Request
alter table REQUEST_LIST add constraint FK_8lkyanvi6otfiiufce93js9dg foreign key (USER_ID) references User
alter table Request add constraint FK_incrxkymfdr3xw7p4ni4c549d foreign key (_book_BOOK_ID) references Book
alter table Request add constraint FK_jtbq338hed7mm4exe2wnv406q foreign key (_requestor_PERSON_ID) references User
create sequence hibernate_sequence start with 1 increment by 1
