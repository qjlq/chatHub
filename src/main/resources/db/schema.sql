create table if not exists tb_user (
USER_ID int not null primary key auto_increment,
USER_NAME varchar(100)
);
INSERT INTO tb_user (USER_ID,USER_NAME) VALUES(2,'test');
