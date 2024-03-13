insert into users (name, username, password, AuthConfirmCode, confirm)
values ('John Doe', 'johndoe@gmail.com', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W','aboba1',false),
       ('Mike Smith', 'mikesmith@yahoo.com', '$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m','aboba2',false),
       ('Evreniy Porkul', '999.haha.990@gmail.com', '$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m','aboba3',false);


insert into requests(title,text,status,phone_number,user_id,created_at,created_by)
values('1st request','1st text','DRAFT','0984832753',2,'2024-03-02 12:00:00','Max'),
      ('2nd request','2nt text','SENT','0845869464',1,'2024-03-02 10:00:00','Stas'),
      ('3rd request','3rd text','REJECTED','0876265403',3,'2024-03-02 13:00:00','Bomj');



insert into users_roles (user_id, role)
values (1, 'ADMIN'),
       (2, 'OPERATOR'),
       (3, 'USER');