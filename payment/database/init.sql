create table payment.order_payment
(
    id       int auto_increment
        primary key,
    order_id int           not null,
    amount   int default 0 not null,
    status   int           null
);

