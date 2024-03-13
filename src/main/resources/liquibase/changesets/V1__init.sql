create table if not exists users
(
    id bigserial primary key,
    name            varchar(255) not null,
    username        varchar(255) not null unique,
    password        varchar(255) not null,
    AuthConfirmCode varchar(255) unique,
    confirm          BOOLEAN not null

);
create table if not exists requests
(
    id bigserial primary key,
    title        varchar(255) not null,
    text         varchar(255) not null,
    status       varchar(255) not null,
    phone_number varchar(255) not null,
    user_id      bigint    default null,
    constraint fk_users_requests_user foreign key (user_id) references users (id) on delete cascade on update no action,
    created_at   timestamp default current_timestamp,
    created_by   varchar(255) not null
);

-- create table if not exists users_requests
-- (
--     user_id    bigint not null,
--     request_id bigint not null,
--     primary key (user_id, request_id),
--     constraint fk_users_requests_users foreign key (user_id) references users (id) on delete cascade on update no action,
--     constraint fk_users_users_requests foreign key (request_id) references requests (id) on delete cascade on update no action
-- );
create table if not exists users_roles
(
    user_id bigint       not null,
    role    varchar(255) not null,
    primary key (user_id, role),
    constraint fk_users_roles_users foreign key (user_id) references users (id) on delete cascade on update no action
);
