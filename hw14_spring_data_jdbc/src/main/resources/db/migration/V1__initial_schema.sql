create table address
(
    address_id bigserial not null primary key,
    street varchar(200),
    client_id bigint
);

create table client
(
    id bigserial not null primary key,
    name varchar(50) not null
);

create table phone
(
    phone_id bigserial not null primary key,
    number varchar(50),
    client_id bigint
);