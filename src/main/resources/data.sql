insert into users (username, password, enabled)
values ('admin', '$2a$10$Mcg/T/z.743jysl6pPu8.escLlDMP5lWlNx2fHw2YlJKCyig3rCvS', 1);

insert into users (username, password, enabled)
values ('student', '$2a$10$o37iji5IxKMGKn7NKZJOHO8hfFUxJSn17vzd7wBncS7ktjdPfV/aa', 1);

insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');
insert into authorities (username, authority) values ('admin', 'ROLE_USER');
insert into authorities (username, authority) values ('student', 'ROLE_USER');
insert into Wallets (walletName, walletType, createDate, userName) values('adminWallet', 'LEATHER', curdate(), 'admin');
insert into Wallets (walletName, walletType, createDate, userName) values('studentWallet', 'LINEN', curdate(), 'student');
insert into Wallets (walletName, walletType, createDate, userName) values('peroWallet', 'LINEN', curdate(), 'pero');