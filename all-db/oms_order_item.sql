CREATE TABLE `oms_order_item`
(
    `id`       bigint(20) NOT NULL AUTO_INCREMENT,
    `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
    `order_sn` varchar(64)    DEFAULT NULL COMMENT '订单编号',
    `sku_id`   bigint(20) DEFAULT NULL COMMENT '商品sku id',
    `sku_name` varchar(200)   DEFAULT NULL COMMENT '商品sku名称',
    `sku_pic`  varchar(500)   DEFAULT NULL COMMENT '商品sku图片',
    `price`    decimal(10, 2) DEFAULT NULL COMMENT '销售价格',
    `count`    int(11) DEFAULT NULL COMMENT '购买数量',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单包含的商品';