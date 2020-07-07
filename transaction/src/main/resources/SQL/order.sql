create table demo_order
(
  id             int auto_increment
    primary key,
  stock_id       int         null comment '订单对应的库存id',
  stock_num      int         null comment '减库存的数据',
  transaction_id varchar(50) null,
  create_time    datetime    null
);



