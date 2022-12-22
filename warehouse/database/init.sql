create table warehouse.product_warehouse
(
    product_id   int           not null
        primary key,
    total        int default 0 null,
    total_sell   int default 0 null,
    revert_total int default 0 null
);

