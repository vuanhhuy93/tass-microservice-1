create table product.product
(
    id     int auto_increment
        primary key,
    name   varchar(255)  null,
    status int default 1 not null
);

