insert into users (name, username, password)
values ('John Doe', 'johndoe@gmail.com', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W'),
       ('Mike Smith', 'mikesmith@yahoo.com', '$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m'),
       ('Ken Carlson', 'mgang@gmail.com', '$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m');


insert into requests(title,text,status,phone_number,created_at)
values('1st request','1st text','DRAFT','0984832753','2024-03-02 12:00:00'),
      ('2nd request','2nt text','SENT','0845869464','2024-03-02 10:00:00'),
      ('3rd request','3rd text','REJECTED','0945846219','2024-03-02 13:00:00');

insert into users_requests (request_id, user_id)
values (2, 1),
       (1, 2),
       (3, 3);

insert into users_roles (user_id, role)
values (1, 'ADMIN'),
       (2, 'OPERATOR'),
       (2, 'USER');