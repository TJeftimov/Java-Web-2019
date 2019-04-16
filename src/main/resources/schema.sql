create table if not exists users (
 id bigint primary key auto_increment,
 username varchar(20) not null unique ,
 password varchar(100) not null,
 enabled bit not null
);
create table if not exists authorities (
 username varchar(20) not null,
 authority varchar(20) not null
);

create table if not exists expenses (
  id bigint not null primary key auto_increment,
  name varchar(100) not null,
  amount decimal not null,
  type varchar(20) not null,
  createDate datetime not null,
  walletId bigint not null
);

create table if not exists wallets (
  id bigint not null primary key auto_increment,
  walletName varchar(100) not null,
  walletType varchar(20) not null,
  createDate datetime not null,
  userName varchar(20) not null
);