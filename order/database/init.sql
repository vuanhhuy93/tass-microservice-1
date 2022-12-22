create table `order.order`
(
    id          int auto_increment
        primary key,
    user_id     int           null,
    product_id  int           not null,
    is_success  int           null,
    error_code  varchar(50)   null,
    total_items int default 0 not null
);

