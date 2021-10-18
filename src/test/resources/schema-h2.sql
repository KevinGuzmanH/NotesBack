create table usuario
(
    id        serial not null
        constraint usuario_pkey
            primary key,
    last_name varchar(255),
    name      varchar(255),
    password  varchar(255),
    username  varchar(255)
);