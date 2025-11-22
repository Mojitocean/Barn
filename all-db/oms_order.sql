CREATE TABLE `oms_order`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
    `order_sn`     varchar(64)    DEFAULT NULL COMMENT '订单编号',
    `user_id`      bigint(20) NOT NULL COMMENT '用户id',
    `total_amount` decimal(10, 2) DEFAULT NULL COMMENT '订单总金额',
    `pay_amount`   decimal(10, 2) DEFAULT NULL COMMENT '应付金额（实际支付金额）',
    `status`       int(1) DEFAULT NULL COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
    `create_time`  datetime       DEFAULT NULL COMMENT '提交时间',
    `modify_time`  datetime       DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_order_sn` (`order_sn`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';